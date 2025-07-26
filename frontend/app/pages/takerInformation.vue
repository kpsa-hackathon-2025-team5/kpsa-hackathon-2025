<script setup lang="ts">
import { useMembersApi } from "@/composable/useMembersApi";
import { useApi } from "@/composable/useApi";
const { apiCall } = useApi();

// 환자 이름을 저장할 반응형 변수
const patientName = ref("사용자");

const goBack = () => {
  window.history.back();
};

const selectService = (serviceType: string) => {
  console.log("선택된 서비스:", serviceType);

  switch (serviceType) {
    case "medication-check":
      navigateTo("/medication-status");
      break;
    case "report-view":
      navigateTo("/report-view");
      break;
    case "pharmacy-visit":
      navigateTo("/pharmacy-visit");
      break;
  }
};

// 환자 정보 가져오기 (기존 userInfo 함수 수정)
const getPatientInfo = async () => {
  try {
    const patientId = 10;

    console.log("환자 정보 API 요청", { patientId });

    // GET 방식으로 시도
    const result = await apiCall(`/api/v1/members/${patientId}`, {
      method: "GET",
    });

    console.log("API 응답:", result);

    // API 응답에서 이름 추출 (실제 API 응답 구조에 맞게 조정)
    const name =
      result.name || result.patientName || result.memberName || "사용자";
    patientName.value = name;

    console.log("환자 이름:", name);
    return result;
  } catch (error) {
    console.log("GET 요청 실패, POST 방식으로 재시도");

    try {
      // GET이 실패하면 POST 방식으로 재시도
      const result = await apiCall("/api/v1/members", {
        method: "POST",
        body: { patientId: 9 },
      });

      console.log("POST API 응답:", result);

      const name =
        result.name || result.patientName || result.memberName || "사용자";
      patientName.value = name;

      return result;
    } catch (postError) {
      const message = postError?.message || "환자 정보를 가져올 수 없습니다.";
      console.error("API 호출 오류:", message);
      patientName.value = "사용자"; // 기본값 설정
    }
  }
};

// 컴포넌트 마운트 시 환자 정보 가져오기
onMounted(() => {
  getPatientInfo();
});

// 서비스 목록 데이터
const services = [
  {
    id: "medication-check",
    title: "복약 현황 확인하기",
    icon: "💊",
    description: "현재 복용 중인 약물 현황을 확인합니다",
    bgColor: "bg-gradient-to-br from-blue-400 to-blue-600",
  },
  {
    id: "report-view",
    title: "리포트 열람하기",
    icon: "📊",
    description: "복약 관리 리포트를 확인합니다",
    bgColor: "bg-gradient-to-br from-purple-400 to-purple-600",
  },
  {
    id: "pharmacy-visit",
    title: "약사 방문 일정 확인하기",
    icon: "🚗",
    description: "약사 방문 일정을 확인하고 관리합니다",
    bgColor: "bg-gradient-to-br from-green-400 to-green-600",
  },
];
</script>

<template>
  <!-- 서비스 선택 페이지 전체 wrap -->
  <div class="min-h-screen w-full bg-gray-100 flex flex-col">
    <!-- 헤더 -->
    <div class="relative flex items-center px-6 py-6 bg-gray-100">
      <button
        @click="goBack"
        class="mr-4 p-2 hover:bg-gray-200 rounded-lg transition-colors"
      >
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
      <h1
        class="absolute left-1/2 transform -translate-x-1/2 text-lg font-semibold text-gray-900"
      >
        {{ patientName }}
      </h1>
    </div>

    <!-- 메인 콘텐츠 -->
    <div class="flex-1 px-6 pt-8 pb-32">
      <!-- 서비스 카드들 -->
      <div class="space-y-4">
        <div
          v-for="service in services"
          :key="service.id"
          @click="selectService(service.id)"
          class="bg-white rounded-3xl p-6 shadow-sm border border-gray-100 cursor-pointer hover:shadow-md transition-all active:scale-95"
        >
          <div class="flex items-center justify-between">
            <div class="flex items-center space-x-4 flex-1">
              <!-- 서비스 제목 -->
              <div class="flex-1">
                <h3 class="text-lg font-bold text-gray-900 mb-1">
                  {{ service.title }}
                </h3>
              </div>

              <!-- 아이콘 영역 -->
              <div class="relative w-16 h-16 flex-shrink-0">
                <!-- 복약 현황 확인하기 아이콘 (MedicalRecord.png) -->
                <div
                  v-if="service.id === 'medication-check'"
                  class="w-full h-full flex items-center justify-center"
                >
                  <img
                    src="~/assets/MedicalRecord.png"
                    alt="복약 현황 확인"
                    class="w-14 h-14 object-contain"
                  />
                </div>

                <!-- 리포트 열람하기 아이콘 (report.png) -->
                <div
                  v-else-if="service.id === 'report-view'"
                  class="w-full h-full flex items-center justify-center"
                >
                  <img
                    src="~/assets/report.png"
                    alt="리포트 열람"
                    class="w-14 h-14 object-contain"
                  />
                </div>

                <!-- 약사 방문 일정 확인하기 아이콘 (visit.png) -->
                <div
                  v-else-if="service.id === 'pharmacy-visit'"
                  class="w-full h-full flex items-center justify-center"
                >
                  <img
                    src="~/assets/visit.png"
                    alt="약사 방문 일정"
                    class="w-14 h-14 object-contain"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 하단 인디케이터 -->
    <div class="flex justify-center pb-8">
      <div class="w-32 h-1 bg-black rounded-full"></div>
    </div>
  </div>
</template>
