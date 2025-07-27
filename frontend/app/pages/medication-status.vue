<script setup lang="ts">
import { ref, computed, onMounted, watch } from "vue";
import { useApi } from "@/composable/useApi";

const { apiCall } = useApi();

// 환자 이름을 저장할 반응형 변수
const patientName = ref("사용자");

const goBack = () => {
  window.history.back();
};

// 현재 날짜 정보
const currentDate = ref(new Date());
const currentYear = ref(currentDate.value.getFullYear());
const currentMonth = ref(currentDate.value.getMonth());

// 복약 데이터
const medicationData = ref<Record<string, { result: number }>>({});
const isLoading = ref(false);

// 환자 정보 가져오기
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
        body: { patientId: 10 },
      });

      console.log("POST API 응답:", result);

      const name =
        result.name || result.patientName || result.memberName || "사용자";
      patientName.value = name;

      return result;
    } catch (postError) {
      const message = postError?.message || "환자 정보를 가져올 수 없습니다.";
      console.error("API 호출 오류:", message);
      patientName.value = "이정순"; // 기본값을 이정순으로 설정
    }
  }
};

// API 호출 함수
const fetchMedicationData = async () => {
  isLoading.value = true;

  // 현재 월의 시작일과 종료일
  const startDate = `${currentYear.value}-${String(
    currentMonth.value + 1
  ).padStart(2, "0")}-01`;
  const endDate = `${currentYear.value}-${String(
    currentMonth.value + 1
  ).padStart(2, "0")}-${String(daysInMonth.value).padStart(2, "0")}`;

  try {
    const apiUrl = `/api/v1/medications/reports/monthlyCompliance?memberId=10&startDate=${startDate}&endDate=${endDate}`;
    console.log("📡 API 호출 URL:", apiUrl);

    let data;
    try {
      data = await apiCall(apiUrl);
      console.log("📦 실제 API 응답:", data);
    } catch (apiError) {
      console.log("❌ API 호출 실패, 테스트 데이터 사용:", apiError);
      data = [
        { date: `${startDate}`, result: 1 },
        { date: `${endDate}`, result: 0 },
      ];
    }

    const dataMap: Record<string, { result: number }> = {};
    if (Array.isArray(data)) {
      data.forEach((item: { date: string; result: number }) => {
        dataMap[item.date] = { result: item.result };
      });
    }

    medicationData.value = dataMap;
  } catch (error) {
    console.error("복약 데이터 로드 실패:", error);
  } finally {
    isLoading.value = false;
  }
};

// 월 이름
const monthNames = [
  "1월",
  "2월",
  "3월",
  "4월",
  "5월",
  "6월",
  "7월",
  "8월",
  "9월",
  "10월",
  "11월",
  "12월",
];
const dayNames = ["일", "월", "화", "수", "목", "금", "토"];

// 현재 월의 첫째 날과 마지막 날
const firstDayOfMonth = computed(
  () => new Date(currentYear.value, currentMonth.value, 1)
);
const lastDayOfMonth = computed(
  () => new Date(currentYear.value, currentMonth.value + 1, 0)
);
const firstDayWeekday = computed(() => firstDayOfMonth.value.getDay());
const daysInMonth = computed(() => lastDayOfMonth.value.getDate());

// 이전 월의 마지막 날들
const prevMonthDays = computed(() => {
  const prevMonth = new Date(currentYear.value, currentMonth.value - 1, 0);
  const days = [];
  for (let i = firstDayWeekday.value - 1; i >= 0; i--) {
    days.push(prevMonth.getDate() - i);
  }
  return days;
});

// 다음 월의 첫 날들
const nextMonthDays = computed(() => {
  const totalCells = 42; // 6주 * 7일
  const currentDays = daysInMonth.value;
  const prevDays = prevMonthDays.value.length;
  const remainingCells = totalCells - currentDays - prevDays;

  const days = [];
  for (let i = 1; i <= remainingCells && i <= 14; i++) {
    days.push(i);
  }
  return days;
});

// 이전 월로 이동
const goToPrevMonth = () => {
  if (currentMonth.value === 0) {
    currentMonth.value = 11;
    currentYear.value--;
  } else {
    currentMonth.value--;
  }
};

// 다음 월로 이동
const goToNextMonth = () => {
  if (currentMonth.value === 11) {
    currentMonth.value = 0;
    currentYear.value++;
  } else {
    currentMonth.value++;
  }
};

// 날짜의 복약 상태 가져오기
const getMedicationStatus = (day: number) => {
  const dateKey = `${currentYear.value}-${String(
    currentMonth.value + 1
  ).padStart(2, "0")}-${String(day).padStart(2, "0")}`;
  return medicationData.value[dateKey];
};

// 날짜별 스타일 클래스 (디버깅 로그 추가)
const getDayClass = (day: number) => {
  const status = getMedicationStatus(day);
  const baseClass =
    "w-8 h-8 flex items-center justify-center text-sm font-medium transition-all duration-200 rounded-lg relative";

  const dateKey = `${currentYear.value}-${String(
    currentMonth.value + 1
  ).padStart(2, "0")}-${String(day).padStart(2, "0")}`;

  if (status) {
    const result = status.result;
    if (result === 1) {
      console.log(`🟦 ${dateKey}: 연한 파란색`);
      return `${baseClass} bg-blue-100 text-blue-700 hover:bg-blue-200`;
    } else if (result === 2) {
      console.log(`🔲 ${dateKey}: 테두리만 있음`);
      return `${baseClass} border border-blue-500 text-blue-700`;
    } else {
      console.log(`✅ ${dateKey}: 기타 처리 필요`);
      return `${baseClass} bg-blue-500 text-white hover:bg-blue-600`;
    }
  }

  return `${baseClass} text-gray-700 hover:bg-gray-100`;
};

// 오늘 날짜인지 확인
const isToday = (day: number) => {
  const today = new Date();
  return (
    day === today.getDate() &&
    currentMonth.value === today.getMonth() &&
    currentYear.value === today.getFullYear()
  );
};

// 월이 변경될 때마다 데이터 다시 로드
watch([currentYear, currentMonth], () => {
  fetchMedicationData();
});

// 컴포넌트 마운트 시 데이터 로드
onMounted(() => {
  getPatientInfo(); // 환자 정보 먼저 로드
  fetchMedicationData();
});
</script>

<template>
  <!-- 복약 현황 페이지 전체 wrap -->
  <div class="h-full w-full bg-gray-100 flex flex-col">
    <!-- 뒤로가기 및 소형 타이틀 wrap -->
    <div class="relative flex items-center px-6 py-6 bg-gray-100 text-black">
      <button @click="goBack" class="mr-4 p-2">
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
            d="M15 19l-7-7 7-7"
          />
        </svg>
      </button>
      <p
        class="absolute left-1/2 transform -translate-x-1/2 text-lg font-medium text-black"
      >
        복약 현황
      </p>
    </div>

    <!-- 복약 현황 컨텐츠 -->
    <div class="flex-1 px-6 py-4">
      <!-- 안내 텍스트 -->
      <div class="mb-6">
        <p class="text-lg text-gray-800">
          {{ patientName }}님 복약 현황입니다.
        </p>
      </div>

      <!-- 로딩 상태 -->
      <div v-if="isLoading" class="flex justify-center items-center py-8">
        <div
          class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500"
        ></div>
      </div>

      <!-- 캘린더 -->
      <div v-else class="bg-white rounded-2xl p-6 shadow-sm">
        <!-- 캘린더 헤더 -->
        <div class="flex items-center justify-between mb-6">
          <button
            @click="goToPrevMonth"
            class="p-2 hover:bg-gray-100 rounded-lg transition-colors"
          >
            <svg
              class="w-5 h-5 text-gray-600"
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

          <h2 class="text-xl font-semibold text-gray-900">
            {{ currentYear }}년 {{ monthNames[currentMonth] }}
          </h2>

          <button
            @click="goToNextMonth"
            class="p-2 hover:bg-gray-100 rounded-lg transition-colors"
          >
            <svg
              class="w-5 h-5 text-gray-600"
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
          </button>
        </div>

        <!-- 요일 헤더 -->
        <div class="grid grid-cols-7 gap-2 mb-4">
          <div
            v-for="day in dayNames"
            :key="day"
            class="w-10 h-8 flex items-center justify-center text-sm font-medium text-gray-500"
          >
            {{ day }}
          </div>
        </div>

        <!-- 캘린더 그리드 -->
        <div class="grid grid-cols-7 gap-2">
          <!-- 이전 월 날짜들 -->
          <div
            v-for="day in prevMonthDays"
            :key="`prev-${day}`"
            class="w-10 h-10 flex items-center justify-center text-sm text-gray-300 font-medium"
          >
            {{ day }}
          </div>

          <!-- 현재 월 날짜들 -->
          <div v-for="day in daysInMonth" :key="day" :class="getDayClass(day)">
            {{ day }}

            <!-- 오늘 날짜 표시 -->
            <div
              v-if="isToday(day) && !getMedicationStatus(day)"
              class="absolute inset-0 border-2 border-blue-400 rounded-lg pointer-events-none"
            ></div>
          </div>

          <!-- 다음 월 날짜들 -->
          <div
            v-for="day in nextMonthDays"
            :key="`next-${day}`"
            class="w-10 h-10 flex items-center justify-center text-sm text-gray-300 font-medium"
          >
            {{ day }}
          </div>
        </div>

        <!-- 복약 이해를 위한 범례 -->
        <div class="mt-6 pt-6 border-t border-gray-100">
          <div class="flex items-center justify-center">
            <button
              class="flex items-center text-sm text-gray-500 hover:text-gray-700 transition-colors"
            >
              <span>복약 이행율</span>
              <svg
                class="w-4 h-4 ml-1"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <circle cx="12" cy="12" r="10"></circle>
                <path d="m9,12 2,2 4,-4"></path>
              </svg>
            </button>
          </div>
        </div>

        <!-- 범례 -->
        <!-- <div class="mt-4 flex justify-center space-x-6">
          <div class="flex items-center space-x-2">
            <div class="w-2 h-2 bg-blue-500 rounded-full"></div>
            <span class="text-xs text-gray-600">복약함</span>
          </div>
        </div> -->
      </div>
    </div>
  </div>
</template>
