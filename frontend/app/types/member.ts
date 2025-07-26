export interface Member {
  id: number;
  username: string;
  password: string;
  name: string;
  memberType: MemberType;
  status: MemberStatus;
}

export enum MemberType {
  PHARMACIST = "PHARMACIST", // 약사
  PATIENT = "PATIENT", // 복용자
  GUARDIAN = "GUARDIAN", // 보호자
}

export enum MemberStatus {
  ACTIVE = "ACTIVE",
  INACTIVE = "INACTIVE",
}
