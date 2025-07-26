<template>
  <div class="h-screen w-full bg-gray-200 flex flex-col pb-20">
    <!-- 헤더 -->
    <div class="relative flex items-center px-6 py-4 bg-gray-200">
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
        복약 확인
      </p>
    </div>

    <!-- 메인 컨텐츠 -->
    <div class="flex-1 px-6 overflow-y-auto pb-4">
      <!-- 질문 텍스트 -->
      <div class="mb-8">
        <h2 class="text-3xl font-medium text-gray-900 mb-2">
          지금 <span class="text-blue-600">약</span> 드셨나요?
        </h2>
        <p class="text-xl text-gray-700">
          약은 총 <span class="text-blue-600 font-semibold">4개</span>입니다.
        </p>
      </div>

      <!-- 약 목록 -->
      <div class="space-y-4">
        <!-- 첫 번째 약 -->
        <div class="bg-white rounded-2xl p-5 shadow-sm">
          <div class="flex items-center space-x-4">
            <div
              class="w-20 h-16 bg-blue-100 rounded-xl flex items-center justify-center"
            >
              <div
                class="w-16 h-12 bg-white rounded-lg flex items-center justify-center"
              >
                <div class="flex space-x-1">
                  <div class="w-4 h-4 bg-gray-300 rounded-full"></div>
                  <div class="w-4 h-4 bg-gray-300 rounded-full"></div>
                </div>
              </div>
            </div>
            <div class="flex-1">
              <h3 class="font-semibold text-gray-900 text-lg mb-1">
                클루르코파지정 500mg
              </h3>
              <div class="inline-block bg-blue-50 px-3 py-1 rounded-full">
                <span class="text-sm font-medium text-blue-700">당뇨병</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 두 번째 약 -->
        <div class="bg-white rounded-2xl p-5 shadow-sm">
          <div class="flex items-center space-x-4">
            <div
              class="w-20 h-16 bg-blue-100 rounded-xl flex items-center justify-center"
            >
              <div
                class="w-16 h-12 bg-white rounded-lg flex items-center justify-center"
              >
                <div class="grid grid-cols-2 gap-1">
                  <div class="w-2 h-2 bg-gray-300 rounded"></div>
                  <div class="w-2 h-2 bg-gray-300 rounded"></div>
                  <div class="w-2 h-2 bg-gray-300 rounded"></div>
                  <div class="w-2 h-2 bg-gray-300 rounded"></div>
                </div>
              </div>
            </div>
            <div class="flex-1">
              <h3 class="font-semibold text-gray-900 text-lg mb-1">
                세비브란스정슬 100mg
              </h3>
              <div class="inline-block bg-blue-50 px-3 py-1 rounded-full">
                <span class="text-sm font-medium text-blue-700">관절염</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 세 번째 약 -->
        <div class="bg-white rounded-2xl p-5 shadow-sm">
          <div class="flex items-center space-x-4">
            <div
              class="w-20 h-16 bg-blue-100 rounded-xl flex items-center justify-center"
            >
              <div
                class="w-16 h-12 bg-white rounded-lg flex items-center justify-center"
              >
                <div class="flex space-x-1">
                  <div class="w-3 h-6 bg-gray-300 rounded-full"></div>
                  <div class="w-6 h-6 bg-gray-300 rounded-full"></div>
                </div>
              </div>
            </div>
            <div class="flex-1">
              <h3 class="font-semibold text-gray-900 text-lg mb-1">
                코지정 50mg
              </h3>
              <div class="inline-block bg-blue-50 px-3 py-1 rounded-full">
                <span class="text-sm font-medium text-blue-700">관절염</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 네 번째 약 (건강기능식품) -->
        <div class="bg-white rounded-2xl p-5 shadow-sm">
          <div class="flex items-center space-x-4">
            <div
              class="w-20 h-16 bg-blue-100 rounded-xl flex items-center justify-center"
            >
              <div
                class="w-16 h-12 bg-white rounded-lg flex items-center justify-center"
              >
                <div
                  class="w-12 h-8 bg-gray-300 rounded-lg flex items-center justify-center"
                >
                  <div class="text-xs text-gray-600">병</div>
                </div>
              </div>
            </div>
            <div class="flex-1">
              <h3 class="font-semibold text-gray-900 text-lg mb-1">
                중근당 오메가3 앤 칸칠
              </h3>
              <div class="inline-block bg-green-50 px-3 py-1 rounded-full">
                <span class="text-sm font-medium text-green-700"
                  >건강기능식품</span
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 버튼 영역 -->
    <div class="px-6 py-2 bg-gray-200 shrink-0">
      <div class="grid grid-cols-2 gap-4">
        <!-- 복용 완료 버튼 -->
        <button
          @click="markAsCompleted"
          :disabled="isSubmitting"
          class="bg-blue-500 hover:bg-blue-600 text-white py-6 rounded-3xl flex flex-col items-center justify-center space-y-3"
        >
          <div
            class="w-12 h-12 bg-white bg-opacity-20 rounded-full flex items-center justify-center"
          >
            <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 24 24">
              <circle
                cx="12"
                cy="12"
                r="10"
                stroke="currentColor"
                stroke-width="2"
                fill="none"
              />
            </svg>
          </div>
          <span class="font-semibold">복용 완료</span>
        </button>

        <!-- 복용 미완료 버튼 -->
        <button
          @click="markAsIncomplete"
          :disabled="isSubmitting"
          class="bg-red-400 hover:bg-red-500 text-white py-6 rounded-3xl flex flex-col items-center justify-center space-y-3"
        >
          <div
            class="w-12 h-12 bg-white bg-opacity-20 rounded-full flex items-center justify-center"
          >
            <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 24 24">
              <path
                d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"
              />
            </svg>
          </div>
          <span class="font-semibold">복용 미완료</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { useApi } from "@/composable/useApi";

const { apiCall } = useApi();

interface MedicationRecord {
  medicationScheduleId: number;
  memberId: number;
  medicationTime: string;
  timeLabel: string;
  isActive: boolean;
  lastTakenAt: string | null;
  scheduleMemo: string;
  medicationPrescriptionId: number;
  drugName: string;
  prescriptionName: string;
  startDate: string;
  endDate: string;
  dosagePerTime: string;
  dosageInstruction: string;
  memo: string;
}

const medications = ref<MedicationRecord[]>([]);
const isLoading = ref(false);
const isSubmitting = ref(false);

const goBack = () => {
  window.history.back();
};

// 시간 라벨 변환
const getTimeLabel = (timeLabel: string) => {
  const labels: Record<string, string> = {
    MORNING: "아침",
    LUNCH: "점심",
    DINNER: "저녁",
    BEDTIME: "취침전",
  };
  return labels[timeLabel] || timeLabel;
};

// 시간 포맷
const formatTime = (timeString: string) => {
  const [hours, minutes] = timeString.split(":");
  return `${hours}:${minutes}`;
};

// 오늘 복용할 약 목록 조회
const fetchTodayMedications = async () => {
  isLoading.value = true;
  try {
    // const today = new Date().toISOString().split("T")[0]; // YYYY-MM-DD 형식
    const url = `/api/v1/patients/10/medicationRecords/today?timeLabel=MORNING&date=2025-07-27`;

    console.log("API 호출 URL:", url);

    const data = await apiCall(url);
    medications.value = data;

    console.log("조회된 약 목록:", data);
  } catch (error) {
    console.error("약 목록 조회 실패:", error);

    // 테스트 데이터는 이제 사용하지 않고 하드코딩된 4개 약물 표시
  } finally {
    isLoading.value = false;
  }
};

// 복용 완료 처리
const markAsCompleted = async () => {
  isSubmitting.value = true;
  try {
    // TODO: 복용 완료 API 호출
    console.log("복용 완료 처리");

    // 성공 후 이전 페이지로 이동
    setTimeout(() => {
      goBack();
    }, 500);
  } catch (error) {
    console.error("복용 완료 처리 실패:", error);
  } finally {
    isSubmitting.value = false;
  }
};

// 복용 미완료 처리
const markAsIncomplete = async () => {
  isSubmitting.value = true;
  try {
    // TODO: 복용 미완료 API 호출
    console.log("복용 미완료 처리");

    // 성공 후 이전 페이지로 이동
    setTimeout(() => {
      goBack();
    }, 500);
  } catch (error) {
    console.error("복용 미완료 처리 실패:", error);
  } finally {
    isSubmitting.value = false;
  }
};

onMounted(() => {
  fetchTodayMedications();
});
</script>
