import {useApi} from "@/composable/useApi"

export const useMembersApi = () => {
    const {apiCall, authenticatedCall} = useApi()

    const getMembers = async () => {
        return apiCall('/api/v1/members')
    }

    return {
        getMembers
    }
}
