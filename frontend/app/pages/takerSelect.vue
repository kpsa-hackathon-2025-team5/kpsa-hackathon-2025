<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useMembersApi } from "@/composable/useMembersApi";
import { useApi } from "@/composable/useApi";
const { apiCall } = useApi();

// 타입 정의
interface CaregivingPerson {
  id: string;
  name: string;
  age: number;
  gender: string;
  image: string;
}

interface Member {
  id: number;
  name: string;
  memberType: string;
  status: string;
  birthDate?: string;
  gender?: string;
}

const selectedCaregiving = ref<string>("");
const caregivingList = ref<CaregivingPerson[]>([]);
const isLoading = ref<boolean>(true);

const { getMembers } = useMembersApi();

const goBack = (): void => {
  window.history.back();
};

const selectCaregiving = (person: string): void => {
  selectedCaregiving.value = person;
};

const confirmSelection = (): void => {
  if (selectedCaregiving.value) {
    console.log("선택된 복용자:", selectedCaregiving.value);
    // 다음 페이지로 이동
    navigateTo(`/takerInformation/${selectedCaregiving.value}`);
  }
};

// 복용자 데이터 불러오기
const loadPatients = async (): Promise<void> => {
  try {
    isLoading.value = true;

    console.log("=== API 호출 시작 ===");
    const members: Member[] = await apiCall("/api/v1/guardians/1/patients");

    console.log("=== 전체 멤버 데이터 ===");
    console.log("총 멤버 수:", members.length);
    console.log("전체 데이터:", members);

    // 각 멤버의 상세 정보 출력
    members.forEach((member, index) => {
      console.log(`--- 멤버 ${index + 1} ---`);
      console.log("ID:", member.id);
      console.log("이름:", member.name);
      console.log("멤버 타입:", member.memberType);
      console.log("상태:", member.status);
      console.log("생년월일:", member.birthDate);
      console.log("성별:", member.gender);
      console.log("전체 객체:", member);
    });

    // PATIENT 타입의 멤버만 필터링하고 변환
    const filteredMembers = members.filter((member: Member) => {
      console.log(
        `필터링 확인 - ${member.name}: 타입=${member.memberType}, 상태=${member.status}`
      );

      // 다양한 가능성을 고려한 필터링
      const isPatient =
        member.memberType === "PATIENT" ||
        member.memberType === "patient" ||
        member.memberType === "복용자" ||
        member.memberType === "환자";

      const isActive =
        member.status === "ACTIVE" ||
        member.status === "active" ||
        member.status === "활성";

      const result = isPatient && isActive;
      console.log(`${member.name} 필터링 결과:`, result);

      return result;
    });

    console.log("=== 필터링 결과 ===");
    console.log("필터링된 복용자 수:", filteredMembers.length);
    console.log("필터링된 데이터:", filteredMembers);

    caregivingList.value = filteredMembers.map(
      (member: Member): CaregivingPerson => ({
        id: member.id.toString(),
        name: member.name,
        age: calculateAge(member.birthDate) || 79,
        gender: member.gender || "여성",
        image: `https://images.unsplash.com/photo-${
          member.id % 2 === 0
            ? "1544005313-94ddf0286df2"
            : "1507003211169-0a1dd7228f2d"
        }?w=150&h=150&fit=crop&crop=face`,
      })
    );

    console.log("=== 최종 화면 표시 데이터 ===");
    console.log("caregivingList:", caregivingList.value);

    // 복용자가 없는 경우 메시지 표시
    if (caregivingList.value.length === 0) {
      console.warn("⚠️ 필터링된 복용자가 없습니다. 기본 데이터를 사용합니다.");
      throw new Error("복용자 데이터 없음");
    }
  } catch (error) {
    console.log("=== API 호출 실패 - 기본 데이터 사용 ===");
    console.error("복용자 데이터 로딩 실패:", error);

    // 에러 시 기본 데이터 사용 (복용자만)
    const defaultData = [
      {
        id: "lee-jeong-sun",
        name: "이정순",
        age: 79,
        gender: "여성",
        image:
          "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=150&h=150&fit=crop&crop=face",
      },
      {
        id: "kim-cheol-su",
        name: "김철수",
        age: 81,
        gender: "남성",
        image:
          "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150&h=150&fit=crop&crop=face",
      },
      {
        id: "kim-jeong-hee",
        name: "김정희",
        age: 86,
        gender: "여성",
        image:
          "https://images.unsplash.com/photo-1559839734-2b71ea197ec2?w=150&h=150&fit=crop&crop=face",
      },
    ] as CaregivingPerson[];

    caregivingList.value = defaultData;
    console.log("기본 데이터 설정:", defaultData);
  } finally {
    isLoading.value = false;
    console.log("=== 데이터 로딩 완료 ===");
  }
};

// 나이 계산 함수
const calculateAge = (birthDate?: string): number | null => {
  if (!birthDate) return null;
  const today = new Date();
  const birth = new Date(birthDate);
  let age = today.getFullYear() - birth.getFullYear();
  const monthDiff = today.getMonth() - birth.getMonth();
  if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birth.getDate())) {
    age--;
  }
  return age;
};

onMounted(() => {
  loadPatients();
});
</script>

<template>
  <div class="bg-gray-50">
    <!-- 헤더 -->
    <header
      class="relative flex items-center px-4 py-4 bg-white border-b border-gray-100"
    >
      <button @click="goBack" class="mr-3 p-2 -ml-2">
        <svg
          class="w-6 h-6 text-gray-800"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M15 19l-7-7 7-7"
          />
        </svg>
      </button>
      <h2
        class="absolute left-1/2 transform -translate-x-1/2 text-lg font-semibold text-gray-900"
      >
        복용자 선택하기
      </h2>
    </header>

    <!-- 메인 콘텐츠 -->
    <main class="px-4 pt-6 pb-28">
      <!-- 제목 -->
      <section class="mb-8">
        <h1 class="text-2xl font-bold text-gray-900 leading-tight">
          복용자를<br />선택해주세요.
        </h1>
      </section>

      <!-- 복용자 목록 -->
      <section class="mb-8">
        <div v-if="isLoading" class="flex justify-center py-8">
          <div
            class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500"
          ></div>
        </div>
        <div v-else class="space-y-4">
          <div
            v-for="person in caregivingList"
            :key="person.id"
            @click="selectCaregiving(person.id)"
            :class="[
              'bg-white rounded-2xl p-5 border transition-all cursor-pointer',
              selectedCaregiving === person.id
                ? 'border-blue-500 bg-blue-50'
                : 'border-gray-200 hover:border-gray-300',
            ]"
          >
            <div class="flex items-center justify-between">
              <div class="flex items-center space-x-4">
                <!-- 프로필 이미지 -->
                <div
                  class="w-16 h-16 rounded-full overflow-hidden bg-gray-200 flex-shrink-0"
                >
                  <img
                    :src="person.image"
                    :alt="person.name + ' 프로필'"
                    class="w-full h-full object-cover"
                  />
                </div>

                <!-- 정보 -->
                <div>
                  <h3 class="text-xl font-semibold text-gray-900 mb-1">
                    {{ person.name }}
                  </h3>
                  <p class="text-sm text-gray-500">
                    (만 {{ person.age }}세, {{ person.gender }})
                  </p>
                </div>
              </div>

              <!-- 화살표 -->
              <div class="text-gray-400">
                <svg
                  class="w-6 h-6"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M9 5l7 7-7 7"
                  />
                </svg>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 신청하기 버튼 -->
      <section>
        <button
          @click="confirmSelection"
          :disabled="!selectedCaregiving"
          :class="[
            'w-full py-5 rounded-2xl font-semibold text-lg transition-all',
            selectedCaregiving
              ? 'bg-blue-500 text-white hover:bg-blue-600 active:bg-blue-700'
              : 'bg-gray-300 text-gray-500 cursor-not-allowed',
          ]"
        >
          신청하기
        </button>
      </section>
    </main>
  </div>
</template>
