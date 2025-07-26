export interface Member {
    id: number
    username: string
    password: string
    name: string
    memberType: MemberType
    status: MemberStatus
}

export enum MemberType {
    PHARMACIST = "PHARMACIST",
    PATIENT = "PATIENT",
    GUARDIAN = "GUARDIAN"
}

export enum MemberStatus {
    ACTIVE = "ACTIVE",
    INACTIVE = "INACTIVE",
}
