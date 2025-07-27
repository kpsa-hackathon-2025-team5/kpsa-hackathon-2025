export const usePatientStore = defineStore("member", {
    state: () => {
        return {
            member: {
                id: ''
            }
        }
    },
    getters: {
        memberId: (state): string => state.member.id
    },
    actions: {
        setMemberId(memberId: any) {
            this.member.id = memberId
        }
    }
})
