<script setup lang="ts">
import { ref } from "vue";

const selectedServices = ref<string[]>([]);

const goBack = () => {
  window.history.back();
};

const toggleService = (serviceType: string) => {
  const index = selectedServices.value.indexOf(serviceType);
  if (index > -1) {
    // 이미 선택된 경우 제거
    selectedServices.value.splice(index, 1);
  } else {
    // 선택되지 않은 경우 추가
    selectedServices.value.push(serviceType);
  }
};

const isServiceSelected = (serviceType: string) => {
  return selectedServices.value.includes(serviceType);
};

const applyService = () => {
  if (selectedServices.value.length > 0) {
    console.log("선택된 서비스들:", selectedServices.value);
    // 다음 페이지로 이동
    navigateTo("/calendar");
  }
};
</script>

<template>
  <!-- 서비스 신청 페이지 전체 wrap -->
  <div class="h-full w-full bg-gray-100 flex flex-col">
    <!-- 헤더 -->
    <div class="relative flex items-center px-6 py-6 bg-gray-100">
      <button @click="goBack" class="mr-4 p-2">
        <svg
          class="w-6 h-6 text-black"
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
        서비스 신청
      </p>
    </div>

    <!-- 메인 컨텐츠 -->
    <div class="flex-1 px-6 py-8 overflow-y-auto pb-24">
      <!-- 제목 -->
      <div class="mb-8">
        <h1 class="text-2xl font-bold text-black leading-tight">
          원하시는 서비스를<br />선택해주세요.
        </h1>
        <p class="text-sm text-gray-600 mt-2">
          여러 서비스를 선택할 수 있습니다.
        </p>
      </div>

      <!-- 서비스 옵션들 -->
      <div class="space-y-4">
        <!-- 방문형 맞춤 복약 상담 서비스 -->
        <div
          @click="toggleService('visit')"
          :class="[
            'rounded-2xl p-6 shadow-sm border-2 transition-all cursor-pointer',
            isServiceSelected('visit')
              ? 'bg-blue-500 border-blue-500 text-white'
              : 'bg-white border-gray-200 text-black',
          ]"
        >
          <div class="flex items-start justify-between">
            <div class="flex-1">
              <div class="flex items-center justify-between mb-4">
                <h3 class="text-lg font-semibold">
                  방문형 맞춤 복약 상담 서비스
                </h3>
                <div
                  :class="[
                    'w-6 h-6 rounded-full border-2 flex items-center justify-center',
                    isServiceSelected('visit')
                      ? 'border-white bg-white'
                      : 'border-gray-300',
                  ]"
                >
                  <svg
                    v-if="isServiceSelected('visit')"
                    class="w-4 h-4 text-blue-500"
                    fill="currentColor"
                    viewBox="0 0 20 20"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                      clip-rule="evenodd"
                    />
                  </svg>
                </div>
              </div>

              <ul class="space-y-2 mb-4 text-sm opacity-90">
                <li class="flex items-start">
                  <span
                    :class="[
                      'w-1 h-1 rounded-full mt-2 mr-2 flex-shrink-0',
                      isServiceSelected('visit') ? 'bg-white' : 'bg-gray-400',
                    ]"
                  ></span>
                  약사가 직접 가정을 방문하여 약을 정리하고 복약 일정을 재설계
                  합니다.
                </li>
                <li class="flex items-start">
                  <span
                    :class="[
                      'w-1 h-1 rounded-full mt-2 mr-2 flex-shrink-0',
                      isServiceSelected('visit') ? 'bg-white' : 'bg-gray-400',
                    ]"
                  ></span>
                  복약 오류, 중복 처방, 유효기간 경과 등을 현장에서 바로
                  점검합니다.
                </li>
              </ul>

              <div class="flex items-center justify-between">
                <div class="flex items-center">
                  <!-- 아이콘 자리 -->
                  <div
                    :class="[
                      'w-12 h-12 rounded-lg mr-4 flex items-center justify-center',
                      isServiceSelected('visit')
                        ? 'bg-blue-400'
                        : 'bg-blue-100',
                    ]"
                  >
                    <img
                      src="~/assets/visit.png"
                      alt="방문형 서비스"
                      class="w-8 h-8 object-contain"
                    />
                  </div>
                </div>
                <div class="text-right">
                  <p class="text-lg font-bold">
                    기본 월 2회 <br />
                    회당 79,900원
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- AI 레포트 -->
        <div
          @click="toggleService('ai')"
          :class="[
            'rounded-2xl p-6 shadow-sm border-2 transition-all cursor-pointer',
            isServiceSelected('ai')
              ? 'bg-blue-500 border-blue-500 text-white'
              : 'bg-white border-gray-200 text-black',
          ]"
        >
          <div class="flex items-start justify-between">
            <div class="flex-1">
              <div class="flex items-center justify-between mb-4">
                <h3 class="text-lg font-semibold">AI 레포트</h3>
                <div
                  :class="[
                    'w-6 h-6 rounded-full border-2 flex items-center justify-center',
                    isServiceSelected('ai')
                      ? 'border-white bg-white'
                      : 'border-gray-300',
                  ]"
                >
                  <svg
                    v-if="isServiceSelected('ai')"
                    class="w-4 h-4 text-blue-500"
                    fill="currentColor"
                    viewBox="0 0 20 20"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                      clip-rule="evenodd"
                    />
                  </svg>
                </div>
              </div>

              <ul class="space-y-2 mb-4 text-sm opacity-90">
                <li class="flex items-start">
                  <span
                    :class="[
                      'w-1 h-1 rounded-full mt-2 mr-2 flex-shrink-0',
                      isServiceSelected('ai') ? 'bg-white' : 'bg-gray-400',
                    ]"
                  ></span>
                  환자의 복용약, 질환, 식습관을 바탕으로 복약 주의사항을 자동
                  분석합니다.
                </li>
                <li class="flex items-start">
                  <span
                    :class="[
                      'w-1 h-1 rounded-full mt-2 mr-2 flex-shrink-0',
                      isServiceSelected('ai') ? 'bg-white' : 'bg-gray-400',
                    ]"
                  ></span>
                  가족에게 실시간으로 공유되는 맞춤형 복약관리 리포트를
                  제공합니다.
                </li>
              </ul>

              <div class="flex items-center justify-between">
                <div class="flex items-center">
                  <!-- 아이콘 자리 -->
                  <div
                    :class="[
                      'w-12 h-12 rounded-lg mr-4 flex items-center justify-center',
                      isServiceSelected('ai') ? 'bg-blue-400' : 'bg-blue-100',
                    ]"
                  >
                    <img
                      src="~/assets/report.png"
                      alt="AI 레포트"
                      class="w-8 h-8 object-contain"
                    />
                  </div>
                </div>
                <div class="text-right">
                  <p class="text-lg font-bold">월 / 9,900원</p>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 스마트 복약 키트 -->
        <div
          @click="toggleService('smart')"
          :class="[
            'rounded-2xl p-6 shadow-sm border-2 transition-all cursor-pointer',
            isServiceSelected('smart')
              ? 'bg-blue-500 border-blue-500 text-white'
              : 'bg-white border-gray-200 text-black',
          ]"
        >
          <div class="flex items-start justify-between">
            <div class="flex-1">
              <div class="flex items-center justify-between mb-4">
                <h3 class="text-lg font-semibold">스마트 복약 키트</h3>
                <div
                  :class="[
                    'w-6 h-6 rounded-full border-2 flex items-center justify-center',
                    isServiceSelected('smart')
                      ? 'border-white bg-white'
                      : 'border-gray-300',
                  ]"
                >
                  <svg
                    v-if="isServiceSelected('smart')"
                    class="w-4 h-4 text-blue-500"
                    fill="currentColor"
                    viewBox="0 0 20 20"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                      clip-rule="evenodd"
                    />
                  </svg>
                </div>
              </div>

              <ul class="space-y-2 mb-4 text-sm opacity-90">
                <li class="flex items-start">
                  <span
                    :class="[
                      'w-1 h-1 rounded-full mt-2 mr-2 flex-shrink-0',
                      isServiceSelected('smart') ? 'bg-white' : 'bg-gray-400',
                    ]"
                  ></span>
                  복약 일정에 맞춰 약을 담고 정리할 수 있는 키트입니다.
                </li>
                <li class="flex items-start">
                  <span
                    :class="[
                      'w-1 h-1 rounded-full mt-2 mr-2 flex-shrink-0',
                      isServiceSelected('smart') ? 'bg-white' : 'bg-gray-400',
                    ]"
                  ></span>
                  방문 서비스 혹은 맞춤 정리된 약 키트를 제공해 복약 순응도를
                  높입니다.
                </li>
              </ul>

              <div class="flex items-center justify-between">
                <div class="flex items-center">
                  <!-- 아이콘 자리 -->
                  <div
                    :class="[
                      'w-12 h-12 rounded-lg mr-4 flex items-center justify-center',
                      isServiceSelected('smart')
                        ? 'bg-blue-400'
                        : 'bg-blue-100',
                    ]"
                  >
                    <img
                      src="~/assets/MedicalRecord.png"
                      alt="스마트 키트"
                      class="w-8 h-8 object-contain"
                    />
                  </div>
                </div>
                <div class="text-right">
                  <p class="text-lg font-bold">39,900원</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 선택된 서비스 개수 표시 -->
      <div
        v-if="selectedServices.length > 0"
        class="mt-6 p-4 bg-blue-50 rounded-xl"
      >
        <p class="text-sm text-blue-700">
          <span class="font-semibold">{{ selectedServices.length }}개</span>의
          서비스가 선택되었습니다.
        </p>
      </div>

      <!-- 신청하기 버튼 -->
      <div class="mt-8 pb-6">
        <button
          @click="applyService"
          :disabled="selectedServices.length === 0"
          :class="[
            'w-full py-4 rounded-2xl font-semibold transition-all',
            selectedServices.length > 0
              ? 'bg-blue-500 text-white hover:bg-blue-600'
              : 'bg-gray-300 text-gray-500 cursor-not-allowed',
          ]"
        >
          {{
            selectedServices.length > 0
              ? `${selectedServices.length}개 서비스 신청하기`
              : "신청하기"
          }}
        </button>
      </div>
    </div>
  </div>
</template>
