<script setup lang="ts">
import { ref } from "vue";

const currentStep = ref(1);
const selectedSymptom = ref("");
const selectedDetails = ref<string[]>([]);
const customSymptom = ref("");

const goBack = () => {
  if (currentStep.value === 2) {
    currentStep.value = 1;
    selectedDetails.value = [];
  } else {
    window.history.back();
  }
};

const selectSymptom = (symptom: string) => {
  selectedSymptom.value = symptom;
  currentStep.value = 2;
};

const toggleDetail = (detail: string) => {
  const index = selectedDetails.value.indexOf(detail);
  if (index > -1) {
    selectedDetails.value.splice(index, 1);
  } else {
    selectedDetails.value.push(detail);
  }
};

const isDetailSelected = (detail: string) => {
  return selectedDetails.value.includes(detail);
};

const completeRecord = () => {
  // 현재 URL에서 id 추출
  const currentPath = window.location.pathname;
  const pathParts = currentPath.split("/");
  const patientId = pathParts[pathParts.length - 1]; // 마지막 부분이 id

  if (selectedSymptom.value === "기타") {
    console.log("선택된 증상:", selectedSymptom.value);
    console.log("사용자 입력 증상:", customSymptom.value);
  } else {
    console.log("선택된 증상:", selectedSymptom.value);
    console.log("선택된 세부사항:", selectedDetails.value);
  }

  // id 파라미터를 포함해서 첫 번째 페이지로 이동
  navigateTo(`/takerInformation/${patientId}`);
};

// 증상별 세부 옵션
const getDetailOptions = (symptom: string) => {
  const options = {
    어지러움: ["가벼움", "보통", "심함"],
    인지저하: ["기억력 저하", "집중력 저하", "판단력 저하"],
    "탈수 및 무기력": ["갈증", "피로", "무기력"],
    "위장 장애": ["메스꺼움", "구토", "복통"],
    근육통: ["목", "어깨", "다리"],
    기타: [], // 기타는 직접 입력
  };
  return options[symptom] || [];
};

const getSymptomTitle = (symptom: string) => {
  return symptom === "기타" ? "기타" : symptom;
};
</script>

<template>
  <!-- 이상반응 기록 페이지 전체 wrap -->
  <div class="bg-gray-100">
    <!-- 헤더 -->
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
        이상반응 기록
      </p>
    </div>

    <!-- 첫 번째 단계: 증상 선택 -->
    <div v-if="currentStep === 1" class="px-6 py-8 pb-24">
      <!-- 제목 -->
      <div class="mb-12">
        <h1 class="text-3xl font-bold text-gray-900 leading-tight">
          오늘 불편한 점이<br />있었다면 체크해주세요.
        </h1>
      </div>

      <!-- 증상 선택 그리드 -->
      <div class="grid grid-cols-2 gap-4 mb-8">
        <!-- 어지러움 -->
        <div
          @click="selectSymptom('어지러움')"
          class="bg-white rounded-2xl p-6 border-2 border-gray-200 transition-all cursor-pointer hover:border-blue-300 flex flex-col items-center justify-center h-32"
        >
          <div class="text-4xl mb-3">😵‍💫</div>
          <h3 class="text-lg font-semibold text-gray-900">어지러움</h3>
        </div>

        <!-- 인지저하 -->
        <div
          @click="selectSymptom('인지저하')"
          class="bg-white rounded-2xl p-6 border-2 border-gray-200 transition-all cursor-pointer hover:border-blue-300 flex flex-col items-center justify-center h-32"
        >
          <div class="text-4xl mb-3">🤔</div>
          <h3 class="text-lg font-semibold text-gray-900">인지저하</h3>
        </div>

        <!-- 탈수 및 무기력 -->
        <div
          @click="selectSymptom('탈수 및 무기력')"
          class="bg-white rounded-2xl p-6 border-2 border-gray-200 transition-all cursor-pointer hover:border-blue-300 flex flex-col items-center justify-center h-32"
        >
          <div class="text-4xl mb-3">😴</div>
          <h3 class="text-lg font-semibold text-gray-900">탈수 및 무기력</h3>
        </div>

        <!-- 위장 장애 -->
        <div
          @click="selectSymptom('위장 장애')"
          class="bg-white rounded-2xl p-6 border-2 border-gray-200 transition-all cursor-pointer hover:border-blue-300 flex flex-col items-center justify-center h-32"
        >
          <div class="text-4xl mb-3">🤢</div>
          <h3 class="text-lg font-semibold text-gray-900">위장 장애</h3>
        </div>

        <!-- 근육통 -->
        <div
          @click="selectSymptom('근육통')"
          class="bg-white rounded-2xl p-6 border-2 border-gray-200 transition-all cursor-pointer hover:border-blue-300 flex flex-col items-center justify-center h-32"
        >
          <div class="text-4xl mb-3">💪</div>
          <h3 class="text-lg font-semibold text-gray-900">근육통</h3>
        </div>

        <!-- 기타 -->
        <div
          @click="selectSymptom('기타')"
          class="bg-white rounded-2xl p-6 border-2 border-gray-200 transition-all cursor-pointer hover:border-blue-300 flex flex-col items-center justify-center h-32"
        >
          <div class="text-4xl mb-3">❓</div>
          <h3 class="text-lg font-semibold text-gray-900">
            기타 <span class="text-sm text-gray-500">(직접입력)</span>
          </h3>
        </div>
      </div>

      <!-- 선택 완료 버튼 -->
      <div>
        <button
          class="w-full py-4 rounded-2xl font-semibold text-lg bg-blue-500 text-white hover:bg-blue-600 transition-all"
          disabled
        >
          선택 완료
        </button>
      </div>
    </div>

    <!-- 두 번째 단계: 세부 선택 -->
    <div v-if="currentStep === 2" class="px-6 py-8 pb-24">
      <!-- 선택된 증상 표시 -->
      <div class="mb-8 text-center">
        <div class="text-6xl mb-4">
          {{
            selectedSymptom === "어지러움"
              ? "😵‍💫"
              : selectedSymptom === "인지저하"
              ? "🤔"
              : selectedSymptom === "탈수 및 무기력"
              ? "😴"
              : selectedSymptom === "위장 장애"
              ? "🤢"
              : selectedSymptom === "근육통"
              ? "💪"
              : "❓"
          }}
        </div>
        <h1 class="text-2xl font-bold text-gray-900 mb-2">
          {{ getSymptomTitle(selectedSymptom) }}
        </h1>
        <p class="text-gray-600">이 증상이<br />얼마큼 불편하셨나요?</p>
      </div>

      <!-- 세부 옵션 선택 -->
      <div v-if="selectedSymptom !== '기타'" class="space-y-4 mb-8">
        <div
          v-for="option in getDetailOptions(selectedSymptom)"
          :key="option"
          @click="toggleDetail(option)"
          :class="[
            'bg-white rounded-2xl p-5 border-2 transition-all cursor-pointer flex items-center justify-between',
            isDetailSelected(option)
              ? 'border-blue-500 bg-blue-50'
              : 'border-gray-200 hover:border-blue-300',
          ]"
        >
          <span class="text-lg font-medium text-gray-900">{{ option }}</span>
          <div
            :class="[
              'w-6 h-6 rounded-full border-2 flex items-center justify-center',
              isDetailSelected(option)
                ? 'border-blue-500 bg-blue-500'
                : 'border-gray-300',
            ]"
          >
            <svg
              v-if="isDetailSelected(option)"
              class="w-4 h-4 text-white"
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
      </div>

      <!-- 기타 증상 직접 입력 -->
      <div v-if="selectedSymptom === '기타'" class="mb-8">
        <label class="block text-sm font-medium text-gray-700 mb-2">
          어떤 증상이 있으셨나요?
        </label>
        <textarea
          v-model="customSymptom"
          placeholder="증상을 자세히 설명해주세요..."
          class="w-full p-4 border-2 border-gray-200 rounded-2xl resize-none h-32 text-gray-900 placeholder-gray-500 focus:border-blue-500 focus:outline-none bg-white"
        ></textarea>
      </div>

      <!-- 완료 버튼 -->
      <div>
        <button
          @click="completeRecord"
          :disabled="
            selectedSymptom === '기타'
              ? !customSymptom.trim()
              : selectedDetails.length === 0
          "
          :class="[
            'w-full py-4 rounded-2xl font-semibold text-lg transition-all',
            (
              selectedSymptom === '기타'
                ? customSymptom.trim()
                : selectedDetails.length > 0
            )
              ? 'bg-blue-500 text-white hover:bg-blue-600'
              : 'bg-gray-300 text-gray-500 cursor-not-allowed',
          ]"
        >
          선택 완료
        </button>
      </div>
    </div>
  </div>
</template>
