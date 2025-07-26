<script setup lang="ts">
import { useApi } from "@/composable/useApi";
const { apiCall } = useApi();

// 환자 이름을 저장할 반응형 변수
const patientName = ref("사용자");
const visitSchedules = ref([]);
const isLoading = ref(false);

const goBack = () => {
  window.history.back();
};

// 환자 정보 가져오기
const getPatientInfo = async () => {
  try {
    const patientId = 9;
    console.log("환자 정보 API 요청", { patientId });

    const result = await apiCall(`/api/v1/members/${patientId}`, {
      method: "GET",
    });

    console.log("API 응답:", result);
    const name =
      result.name || result.patientName || result.memberName || "사용자";
    patientName.value = name;

    return result;
  } catch (error) {
    console.log("GET 요청 실패, POST 방식으로 재시도");

    try {
      const result = await apiCall("/api/v1/members", {
        method: "POST",
        body: { patientId: 9 },
      });

      const name =
        result.name || result.patientName || result.memberName || "사용자";
      patientName.value = name;

      return result;
    } catch (postError) {
      console.error("API 호출 오류:", postError);
      patientName.value = "사용자";
    }
  }
};

// 방문 일정 가져오기
const getVisitSchedules = async () => {
  try {
    isLoading.value = true;
    const patientId = 9;

    console.log("방문 일정 API 요청", { patientId });

    // 방문 일정 API 호출 (실제 엔드포인트에 맞게 수정 필요)
    const result = await apiCall(`/api/v1/visits/patient/${patientId}`, {
      method: "GET",
    });

    console.log("방문 일정 응답:", result);
    visitSchedules.value = result.visits || result.schedules || result || [];
  } catch (error) {
    console.log("GET 요청 실패, POST 방식으로 재시도");

    try {
      const result = await apiCall("/api/v1/visits", {
        method: "POST",
        body: { patientId: 9 },
      });

      visitSchedules.value = result.visits || result.schedules || result || [];
    } catch (postError) {
      console.error("방문 일정 API 호출 오류:", postError);
      // 에러 시 더미 데이터로 테스트 (실제 운영에서는 제거)
      visitSchedules.value = [
        {
          id: 1,
          date: "2025-08-01",
          time: "14:00",
          pharmacist: "김약사",
          status: "confirmed",
          purpose: "복약 상담 및 복용법 안내",
        },
        {
          id: 2,
          date: "2025-08-15",
          time: "10:30",
          pharmacist: "이약사",
          status: "pending",
          purpose: "정기 복약 점검",
        },
        {
          id: 3,
          date: "2025-07-20",
          time: "16:00",
          pharmacist: "박약사",
          status: "completed",
          purpose: "신규 처방전 상담",
        },
      ];
    }
  } finally {
    isLoading.value = false;
  }
};

// 날짜 포맷팅
const formatDate = (dateString: string) => {
  const date = new Date(dateString);
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const weekdays = ["일", "월", "화", "수", "목", "금", "토"];
  const weekday = weekdays[date.getDay()];

  return `${month}월 ${day}일 (${weekday})`;
};

// 상태별 스타일 및 텍스트
const getStatusInfo = (status: string) => {
  switch (status) {
    case "confirmed":
      return {
        text: "확정",
        bgColor: "bg-blue-100",
        textColor: "text-blue-800",
      };
    case "pending":
      return {
        text: "대기중",
        bgColor: "bg-yellow-100",
        textColor: "text-yellow-800",
      };
    case "completed":
      return {
        text: "완료",
        bgColor: "bg-green-100",
        textColor: "text-green-800",
      };
    default:
      return {
        text: "미정",
        bgColor: "bg-gray-100",
        textColor: "text-gray-800",
      };
  }
};

// 일정 상세 보기
const viewScheduleDetail = (schedule: any) => {
  console.log("일정 상세:", schedule);
  // 상세 페이지로 이동하거나 모달 표시
  // navigateTo(`/pharmacy-visit/${schedule.id}`);
};

// 새 일정 요청
const requestNewSchedule = () => {
  console.log("새 일정 요청");
  // 새 일정 요청 페이지로 이동
  // navigateTo("/pharmacy-visit/new");
};

// 컴포넌트 마운트 시 데이터 가져오기
onMounted(async () => {
  await getPatientInfo();
  await getVisitSchedules();
});
</script>

<template>
  <!-- 약사 방문 일정 페이지 전체 wrap -->
  <div class="min-h-screen w-full bg-gray-100 flex flex-col">
    <!-- 헤더 -->
    <div class="relative flex items-center px-6 py-6 bg-white shadow-sm">
      <button
        @click="goBack"
        class="mr-4 p-2 hover:bg-gray-100 rounded-lg transition-colors"
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
        {{ patientName }} 방문 일정
      </h1>
    </div>

    <!-- 메인 콘텐츠 -->
    <div class="flex-1 px-6 pt-6 pb-32">
      <!-- 새 일정 요청 버튼 -->
      <div class="mb-6">
        <button
          @click="requestNewSchedule"
          class="w-full bg-blue-600 text-white py-4 px-6 rounded-2xl font-semibold hover:bg-blue-700 transition-colors active:scale-95 flex items-center justify-center space-x-2"
        >
          <svg
            class="w-5 h-5"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M12 4v16m8-8H4"
            />
          </svg>
          <span>새 방문 일정 요청하기</span>
        </button>
      </div>

      <!-- 로딩 상태 -->
      <div v-if="isLoading" class="flex justify-center items-center py-12">
        <div
          class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"
        ></div>
      </div>

      <!-- 방문 일정 목록 -->
      <div v-else class="space-y-4">
        <h2 class="text-lg font-semibold text-gray-900 mb-4">
          예정된 방문 일정 ({{ visitSchedules.length }}건)
        </h2>

        <!-- 일정이 없는 경우 -->
        <div
          v-if="visitSchedules.length === 0"
          class="bg-white rounded-2xl p-8 text-center shadow-sm border border-gray-100"
        >
          <div
            class="w-16 h-16 mx-auto mb-4 bg-gray-100 rounded-full flex items-center justify-center"
          >
            <svg
              class="w-8 h-8 text-gray-400"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"
              />
            </svg>
          </div>
          <p class="text-gray-600 mb-2">예정된 방문 일정이 없습니다</p>
          <p class="text-sm text-gray-500">새 일정을 요청해보세요</p>
        </div>

        <!-- 방문 일정 카드들 -->
        <div
          v-for="schedule in visitSchedules"
          :key="schedule.id"
          @click="viewScheduleDetail(schedule)"
          class="bg-white rounded-2xl p-6 shadow-sm border border-gray-100 cursor-pointer hover:shadow-md transition-all active:scale-95"
        >
          <div class="flex items-start justify-between mb-4">
            <div class="flex-1">
              <div class="flex items-center space-x-3 mb-2">
                <h3 class="text-lg font-bold text-gray-900">
                  {{ formatDate(schedule.date) }}
                </h3>
                <span
                  :class="[
                    'px-3 py-1 rounded-full text-xs font-medium',
                    getStatusInfo(schedule.status).bgColor,
                    getStatusInfo(schedule.status).textColor,
                  ]"
                >
                  {{ getStatusInfo(schedule.status).text }}
                </span>
              </div>
              <p class="text-gray-600 text-sm mb-1">⏰ {{ schedule.time }}</p>
              <p class="text-gray-600 text-sm mb-3">
                👨‍⚕️ {{ schedule.pharmacist }}
              </p>
              <p class="text-gray-800 text-sm">
                {{ schedule.purpose }}
              </p>
            </div>

            <!-- 화살표 아이콘 -->
            <svg
              class="w-5 h-5 text-gray-400 flex-shrink-0 mt-1"
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

          <!-- 추가 정보 (완료된 방문의 경우) -->
          <div
            v-if="schedule.status === 'completed'"
            class="pt-3 border-t border-gray-100"
          >
            <p class="text-xs text-gray-500">✅ 방문 완료</p>
          </div>
        </div>
      </div>

      <!-- 도움말 섹션 -->
      <div class="mt-8 bg-blue-50 rounded-2xl p-4">
        <h3 class="font-semibold text-blue-900 mb-2">💡 방문 일정 안내</h3>
        <ul class="text-sm text-blue-800 space-y-1">
          <li>• 방문 일정은 최소 1일 전에 확정됩니다</li>
          <li>• 일정 변경이 필요한 경우 담당 약사에게 연락해주세요</li>
          <li>• 방문 시 현재 복용 중인 약물을 준비해주세요</li>
        </ul>
      </div>
    </div>

    <!-- 하단 인디케이터 -->
    <div class="flex justify-center pb-8">
      <div class="w-32 h-1 bg-black rounded-full"></div>
    </div>
  </div>
</template>
