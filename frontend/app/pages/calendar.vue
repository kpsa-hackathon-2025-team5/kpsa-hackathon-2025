<script setup lang="ts">
import Datepicker from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";
import { useApi } from "@/composable/useApi";
const { apiCall } = useApi();

const selectedDate = ref(new Date());
const selectedTime = ref("");
const isTimeExpanded = ref(true);

const timeSlots = {
  morning: ["8:00", "9:00", "10:00", "11:00"],
  afternoon: ["12:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00"],
};

const goBack = () => {
  window.history.back();
};

const selectTime = (time: string) => {
  selectedTime.value = time;
};

const toggleTimeSection = () => {
  isTimeExpanded.value = !isTimeExpanded.value;
};

const formatSelectedDate = () => {
  if (!selectedDate.value) return "";
  const date = new Date(selectedDate.value);
  return `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일`;
};

// 기존 confirmBooking 함수를 다음과 같이 수정하세요
// 1. 먼저 API 서버 상태 확인하는 함수 추가
const confirmBooking = async () => {
  if (!selectedDate.value || !selectedTime.value) {
    alert("날짜와 시간을 모두 선택해주세요.");
    return;
  }

  try {
    const date = new Date(selectedDate.value);
    const dateStr = `${date.getFullYear()}-${String(
      date.getMonth() + 1
    ).padStart(2, "0")}-${String(date.getDate()).padStart(2, "0")}`;
    const timeStr = selectedTime.value.padStart(5, "0");

    const requestBody = {
      patientId: 10,
      pharmacistId: 7,
      scheduledStartDateTime: `${dateStr} ${timeStr}`,
    };

    console.log("API 요청:", requestBody);

    const result = await apiCall(`/api/v1/visits`, {
      method: "POST",
      body: requestBody,
    });

    console.log("예약 성공:", result);
    alert("예약이 완료되었습니다!");
    navigateTo("/takerInformation");
  } catch (error: any) {
    console.error("예약 실패:", error);

    const message =
      error?.data?.message || error?.message || "알 수 없는 오류입니다.";
    alert(`예약 실패: ${message}`);
  }
};
</script>

<template>
  <!-- 사용자유형 페이지 전체 wrap -->
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
        약사 방문 일정 예약
      </p>
    </div>

    <!-- 메인 컨텐츠 -->
    <div class="flex-1 px-6 py-4 overflow-y-auto pb-20">
      <!-- 제목 -->
      <h2 class="text-xl font-bold mb-6 text-black">1차 방문 일정 예약</h2>

      <!-- 달력 섹션 -->
      <div class="bg-white rounded-lg p-4 mb-6 shadow-sm">
        <Datepicker
          v-model="selectedDate"
          locale="ko"
          :min-date="new Date()"
          inline
          auto-apply
          :enable-time-picker="false"
          :format="'yyyy년 MM월 dd일'"
          :preview-format="'yyyy년 MM월 dd일'"
          week-start="0"
          :day-names="['일', '월', '화', '수', '목', '금', '토']"
          :month-change-on-scroll="false"
          :disabled-week-days="[0, 7]"
        />
      </div>

      <!-- 시간 선택 섹션 -->
      <div class="bg-white rounded-lg p-4 shadow-sm">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-medium text-black">시간 선택</h3>
          <button @click="toggleTimeSection" class="p-2">
            <svg
              :class="[
                'w-5 h-5 transform transition-transform text-gray-600',
                isTimeExpanded ? 'rotate-0' : 'rotate-180',
              ]"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M5 15l7-7 7 7"
              />
            </svg>
          </button>
        </div>

        <div v-if="isTimeExpanded" class="space-y-4">
          <!-- 오전 -->
          <div>
            <p class="text-sm text-gray-600 mb-2">오전</p>
            <div class="grid grid-cols-4 gap-2">
              <button
                v-for="time in timeSlots.morning"
                :key="time"
                @click="selectTime(time)"
                :class="[
                  'py-2 px-3 rounded-lg border text-sm transition-colors',
                  selectedTime === time
                    ? 'bg-blue-500 text-white border-blue-500'
                    : 'bg-gray-100 text-gray-700 border-gray-200 hover:bg-gray-200',
                ]"
              >
                {{ time }}
              </button>
            </div>
          </div>

          <!-- 오후 -->
          <div>
            <p class="text-sm text-gray-600 mb-2">오후</p>
            <div class="grid grid-cols-4 gap-2">
              <button
                v-for="time in timeSlots.afternoon"
                :key="time"
                @click="selectTime(time)"
                :class="[
                  'py-2 px-3 rounded-lg border text-sm transition-colors',
                  selectedTime === time
                    ? 'bg-blue-500 text-white border-blue-500'
                    : 'bg-gray-100 text-gray-700 border-gray-200 hover:bg-gray-200',
                ]"
              >
                {{ time }}
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- 선택 정보 표시 -->
      <div
        v-if="selectedDate && selectedTime"
        class="mt-4 p-4 bg-blue-50 rounded-lg border border-blue-200"
      >
        <p class="text-sm text-blue-700 font-medium">
          선택된 일정: {{ formatSelectedDate() }} {{ selectedTime }}
        </p>
      </div>

      <!-- 방문 약사 프로필 -->
      <div
        v-if="selectedDate && selectedTime"
        class="bg-white rounded-lg p-4 shadow-sm mt-6"
      >
        <!-- 제목과 전체보기 버튼 -->
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-lg font-semibold text-black">방문 약사 프로필</h3>
          <button class="flex items-center text-blue-500 text-sm">
            <span>전체보기</span>
            <svg
              class="w-4 h-4 ml-1"
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

        <!-- 프로필 상단 정보 (이미지 + 기본 정보) -->
        <div class="flex items-start space-x-4 mb-4">
          <!-- 프로필 이미지 -->
          <div
            class="w-20 h-20 rounded-lg overflow-hidden bg-gray-200 flex-shrink-0"
          >
            <img
              src="https://via.placeholder.com/80x80/E5E7EB/9CA3AF?text=약사"
              alt="약사 프로필 이미지"
              class="w-full h-full object-cover"
            />
          </div>

          <!-- 기본 정보 -->
          <div class="flex-1">
            <div class="mb-2">
              <h4 class="text-lg font-semibold text-black">
                김지윤 (34세, 여성)
              </h4>
            </div>

            <!-- 연락처 -->
            <div>
              <p class="text-gray-600 mb-1 text-sm">연락처</p>
              <div class="flex items-center">
                <span class="text-black">010-1234-5678</span>
                <button class="ml-2 p-1">
                  <svg
                    class="w-4 h-4 text-gray-400"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z"
                    />
                  </svg>
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- 경력 (전체 너비 사용) -->
        <div class="border-t border-gray-100 pt-4">
          <p class="text-gray-600 mb-2 text-sm">경력</p>
          <div class="space-y-2">
            <div class="flex items-start">
              <div
                class="w-1.5 h-1.5 bg-gray-400 rounded-full mt-2 mr-3 flex-shrink-0"
              ></div>
              <span class="text-black text-sm leading-relaxed">
                신촌약 약국 근무 (2025.05~현재)
              </span>
            </div>
            <div class="flex items-start">
              <div
                class="w-1.5 h-1.5 bg-gray-400 rounded-full mt-2 mr-3 flex-shrink-0"
              ></div>
              <span class="text-black text-sm leading-relaxed">
                종합내 병원 근무 (2021.05~2025.04)
              </span>
            </div>
          </div>
        </div>
      </div>

      <!-- 다음 버튼 -->
      <div v-if="selectedDate && selectedTime" class="mt-6 mb-6">
        <button
          @click="confirmBooking"
          class="w-full py-4 bg-blue-500 text-white rounded-2xl font-semibold text-lg hover:bg-blue-600 transition-colors active:bg-blue-700"
        >
          약사 방문 서비스 신청하기
        </button>
      </div>
    </div>
  </div>
</template>

<style>
/* Datepicker 커스텀 스타일 */
.dp__theme_light {
  --dp-background-color: #ffffff;
  --dp-text-color: #374151;
  --dp-hover-color: #f3f4f6;
  --dp-hover-text-color: #374151;
  --dp-hover-icon-color: #374151;
  --dp-primary-color: #3b82f6;
  --dp-primary-text-color: #ffffff;
  --dp-secondary-color: #e5e7eb;
  --dp-border-color: #e5e7eb;
  --dp-menu-border-color: #e5e7eb;
  --dp-border-color-hover: #d1d5db;
  --dp-disabled-color: #f9fafb;
  --dp-disabled-color-text: #9ca3af;
  --dp-scroll-bar-background: #f3f4f6;
  --dp-scroll-bar-color: #d1d5db;
  --dp-success-color: #10b981;
  --dp-success-color-disabled: #86efac;
  --dp-icon-color: #6b7280;
  --dp-danger-color: #ef4444;
  --dp-highlight-color: rgba(59, 130, 246, 0.1);
}

.dp__input_readonly {
  display: none;
}

.dp__menu {
  border: none !important;
  box-shadow: none !important;
}
</style>
