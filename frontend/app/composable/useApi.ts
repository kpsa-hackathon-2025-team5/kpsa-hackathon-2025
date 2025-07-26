import {useAuthStore} from "~/stores/useAuthStore"

export const useApi = () => {
    const config = useRuntimeConfig()
    const baseURL = config.public.apiBaseUrl

    const {memberId} = storeToRefs(useAuthStore())

    const apiCall = async (endpoint: string, options: any = {}) => {
        try {
            return await $fetch(`${baseURL}${endpoint}`, {
                headers: {
                    'Content-Type': 'application/json',
                    ...options.headers
                },
                ...options
            })
        } catch (error) {
            console.error('API 호출 오류:', error)
            throw error
        }
    }

    const authenticatedCall = async (endpoint: string, options: any = {}) => {
        if (!memberId) {
            throw new Error('사용자가 인증되지 않았습니다.')
        }
        return apiCall(endpoint, {
            ...options,
            headers: {
                'Authorization': `Bearer ${memberId.value}`,
                ...options.headers
            }
        })
    }

    return {
        apiCall,
        authenticatedCall
    }
}
