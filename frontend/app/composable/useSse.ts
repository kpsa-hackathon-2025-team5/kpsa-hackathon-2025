import { ref, onUnmounted } from 'vue'

interface SSEOptions {
    url: string
    eventType?: string
    reconnect?: boolean
    reconnectDelay?: number
    maxReconnectAttempts?: number
}

export type MessageType = 'BroadCast' | 'Notification' | 'Private'

interface SseMessage {
    content: any
    isChecked: boolean
    createdAt: Date
    updatedAt: Date
    type: MessageType
}

export const useSSE = (memberId: number, options: SSEOptions) => {
    const config = useRuntimeConfig()
    const baseURL = config.public.apiBaseUrl
    const {
        url = `${baseURL}/subscribe/${memberId}`,
        eventType = 'sse',
        reconnect = true,
        reconnectDelay = 3000,
        maxReconnectAttempts = 5
    } = options

    const isConnected = ref(false)
    const isConnecting = ref(false)
    const error = ref<string | null>(null)
    const messages = ref<SseMessage[]>([])
    const latestMessage = ref<SseMessage | null>(null)

    let eventSource: EventSource | null = null
    let reconnectAttempts = 0
    let reconnectTimeout: NodeJS.Timeout | null = null

    const connect = () => {
        if (!memberId) {
            console.log("유효하지 않은 memberId:", memberId)
            return
        }

        if (isConnecting.value || isConnected.value) return

        isConnecting.value = true
        error.value = null

        try {
            eventSource = new EventSource(url)

            eventSource.addEventListener(eventType, (event) => {
                try {
                    const data: SseMessage = JSON.parse(event.data)

                    // TODO callback 구현


                    messages.value.push(data)
                    latestMessage.value = data

                    // 메시지 개수 제한 (메모리 관리)
                    if (messages.value.length > 100) {
                        messages.value = messages.value.slice(-50)
                    }

                    console.log('SSE 메시지 수신:', data)
                } catch (parseError) {
                    console.error('메시지 파싱 오류:', parseError)
                }
            })

            eventSource.onopen = () => {
                isConnected.value = true
                isConnecting.value = false
                reconnectAttempts = 0
                error.value = null
                console.log('SSE 연결 성공')
            }

            eventSource.onerror = (event) => {
                console.error('SSE 오류 발생:', event)
                isConnected.value = false
                isConnecting.value = false
                error.value = 'SSE 연결 오류가 발생했습니다'

                eventSource?.close()

                if (reconnect && reconnectAttempts < maxReconnectAttempts) {
                    attemptReconnect()
                }
            }
        } catch (err) {
            isConnecting.value = false
            error.value = 'EventSource 생성 실패'
            console.error('EventSource 생성 오류:', err)
        }
    }

    const attemptReconnect = () => {
        if (reconnectAttempts >= maxReconnectAttempts) {
            error.value = '재연결 최대 시도 횟수를 초과했습니다'
            return
        }

        reconnectAttempts++
        console.log(`재연결 시도 ${reconnectAttempts}/${maxReconnectAttempts}`)

        reconnectTimeout = setTimeout(() => {
            connect()
        }, reconnectDelay)
    }

    const disconnect = () => {
        if (reconnectTimeout) {
            clearTimeout(reconnectTimeout)
            reconnectTimeout = null
        }

        if (eventSource) {
            eventSource.close()
            eventSource = null
        }

        isConnected.value = false
        isConnecting.value = false
        reconnectAttempts = 0
    }

    const clearMessages = () => {
        messages.value = []
        latestMessage.value = null
    }

    onUnmounted(() => {
        disconnect()
    })

    return {
        isConnected: readonly(isConnected),
        isConnecting: readonly(isConnecting),
        error: readonly(error),
        messages: readonly(messages),
        latestMessage: readonly(latestMessage),

        connect,
        disconnect,
        clearMessages
    }
}
