<template>
  <div class="h-full w-full bg-gray-200 flex flex-col">
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
    <div class="flex-1 px-6 overflow-auto">
      <!-- 질문 텍스트 -->
      <div class="mb-8">
        <h2 class="text-3xl font-medium text-gray-900 mb-2">
          저녁 <span class="text-blue-600">약</span> 드셨나요?
        </h2>
        <p class="text-xl text-gray-700">
          약은 총
          <span class="text-blue-600 font-semibold">{{
            medications.length
          }}</span
          >개입니다.
        </p>
      </div>

      <!-- 약 목록 -->
      <!-- 약 목록 표시 -->
      <div class="space-y-4 pb-4">
        <div
          v-for="(med, index) in medications"
          :key="med.medicationScheduleId"
          class="p-4 bg-white rounded-xl shadow-md"
        >
          <h3 class="text-lg font-semibold text-gray-800">
            {{ index + 1 }}. {{ med.drugName }}
          </h3>
          <p class="text-gray-600">
            {{ getTimeLabel(med.timeLabel) }} /
            {{ formatTime(med.medicationTime) }}
          </p>
          <p class="text-gray-500 text-sm">{{ med.dosageInstruction }}</p>
        </div>
      </div>

      <!-- 버튼 영역 -->
      <div class="px-0 bg-gray-200">
        <div class="grid grid-cols-2 gap-4">
          <!-- 복용 완료 버튼 -->
          <button
            @click="markAsCompleted"
            :disabled="isSubmitting"
            class="bg-blue-500 hover:bg-blue-600 text-white py-3 rounded-3xl flex flex-col items-center justify-center space-y-2 shadow-md"
          >
            <svg
              class="w-18 h-18"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
              viewBox="0 0 24 24"
            >
              <circle
                cx="12"
                cy="12"
                r="10"
                stroke="currentColor"
                stroke-width="2"
                fill="none"
              />
            </svg>
            <span class="font-semibold text-base">복용 완료</span>
          </button>

          <!-- 복용 미완료 버튼 -->
          <button
            @click="markAsIncomplete"
            :disabled="isSubmitting"
            class="bg-red-400 hover:bg-red-500 text-white py-6 rounded-3xl flex flex-col items-center justify-center space-y-3 w-full shadow-md"
          >
            <svg
              class="w-18 h-18"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
              viewBox="0 0 24 24"
              stroke-linecap="round"
              stroke-linejoin="round"
            >
              <line x1="18" y1="6" x2="6" y2="18" />
              <line x1="6" y1="6" x2="18" y2="18" />
            </svg>
            <span class="font-semibold">복용 미완료</span>
          </button>
        </div>
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
    // const url = `;

    const data = await apiCall(
      `/api/v1/patients/10/medicationRecords/today?timeLabel=MORNING&date=2025-07-27`
    );
    console.log("API 호출 URL:", data);
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
    navigateTo("/adverseReporting");
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
    navigateTo("/adverseReporting");
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
