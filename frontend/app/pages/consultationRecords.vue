<script setup lang="ts">
import { ref, computed } from "vue";

const goBack = () => {
  window.history.back();
};

const downloadReport = () => {
  console.log("AI 리포트 열람(PDF) 다운로드");
};

// 복약 이행률 (동적으로 변경 가능)
const adherenceRate = ref(80);

// 원형 프로그레스 계산
const progressCalculation = computed(() => {
  const radius = 40;
  const circumference = 2 * Math.PI * radius;
  const progress = (adherenceRate.value / 100) * circumference;
  const remaining = circumference - progress;

  return {
    circumference,
    strokeDasharray: circumference,
    strokeDashoffset: remaining,
  };
});

// 복약 데이터
const medicationData = [
  {
    id: 1,
    time: "아침 8시",
    medications: [
      { name: "관리 약품", description: "고혈압약 5mg + 혈압관리약 500mg" },
      {
        name: "혈압 및 심장 보건",
        description: "혈압 낮은 중 : 하루 중 혈압 상승 70-80",
      },
      {
        name: "가이드",
        description:
          "★ 혈압 낮추려면 걷기운동을 추천, 및 낮잠 보호진신 주의 생활 관찰물이 혈압을 개방 : 높는 부, 싶기 낮춘다 그 칼레고리 방어 보관조전중치 낮춰지는 수용액을 의해 남기좋다.",
      },
    ],
  },
  {
    id: 2,
    time: "아침 반응 2",
    description: "없음없음",
  },
  {
    id: 3,
    time: "아침 반응 3",
    medications: [
      { name: "관리 약품", description: "관리 500mg" },
      {
        name: "혈압 및 심장 보건",
        description: "CPU가 양이 부위 = 관찰 기준 증상 유지",
      },
      {
        name: "가이드",
        description:
          "★ 관리방법이나 호실운동을 가들진 등돌선 약무있고증 : 허가, 배양력침 증보, 돌가 혈압장 마시적.",
      },
    ],
  },
  {
    id: 4,
    time: "아침 반응 4",
    description: "없음",
  },
];

// 의약사 정보
const pharmacistInfo = {
  name: "김지윤",
  title: "약사, 복약",
  license: "면허번호",
  number: "101-1234-56789",
  phone: "010-1234-5678",
  location: "서울특별시",
  history: [
    "신촌약 약국 근무 (2025.05~현재)",
    "종합내 병원 근무 (2021.05~2025.04)]",
  ],
};
</script>

<template>
  <!-- 리포트 페이지 전체 wrap -->
  <div class="min-h-screen w-full bg-gray-50">
    <!-- 헤더 -->
    <div
      class="relative flex items-center px-6 py-6 bg-white border-b border-gray-100"
    >
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
        2025년 5월 15일
      </p>
    </div>

    <!-- 메인 콘텐츠 -->
    <div class="px-4 py-6 pb-28">
      <!-- 기저 질환 섹션 -->
      <div class="mb-6">
        <div class="flex items-center mb-4">
          <div
            class="w-6 h-6 bg-blue-500 rounded text-white flex items-center justify-center text-sm font-bold mr-3"
          >
            1
          </div>
          <h2 class="text-lg font-semibold text-gray-900">기저 질환</h2>
        </div>
        <div class="bg-white rounded-2xl p-4 space-y-3">
          <div class="flex space-x-2">
            <span
              class="px-3 py-1 bg-blue-100 text-blue-700 rounded-full text-sm"
              >고혈압</span
            >
            <span
              class="px-3 py-1 bg-blue-100 text-blue-700 rounded-full text-sm"
              >당뇨</span
            >
          </div>
        </div>
      </div>

      <!-- 이상 반응 섹션 -->
      <div class="mb-6">
        <div class="flex items-center mb-4">
          <div
            class="w-6 h-6 bg-blue-500 rounded text-white flex items-center justify-center text-sm font-bold mr-3"
          >
            2
          </div>
          <h2 class="text-lg font-semibold text-gray-900">이상 반응</h2>
        </div>

        <!-- 복약 기록들 -->
        <div class="space-y-4">
          <div
            v-for="record in medicationData"
            :key="record.id"
            class="bg-white rounded-2xl p-4"
          >
            <div class="flex items-center mb-3">
              <span class="text-2xl mr-3">💊</span>
              <h3 class="font-semibold text-gray-900">{{ record.time }}</h3>
            </div>

            <!-- 복약 정보가 있는 경우 -->
            <div v-if="record.medications" class="space-y-3">
              <div
                v-for="med in record.medications"
                :key="med.name"
                class="border-l-2 border-gray-200 pl-3"
              >
                <div class="flex items-center mb-1">
                  <span class="w-2 h-2 bg-gray-400 rounded-full mr-2"></span>
                  <span class="text-sm font-medium text-gray-700">{{
                    med.name
                  }}</span>
                </div>
                <p class="text-sm text-gray-600 ml-4">{{ med.description }}</p>
              </div>
            </div>

            <!-- 복약 정보가 없는 경우 -->
            <div v-else class="text-sm text-gray-500">
              {{ record.description }}
            </div>
          </div>
        </div>
      </div>

      <!-- 복약 의사 종합 의견 섹션 -->
      <div class="mb-6">
        <div class="flex items-center mb-4">
          <div
            class="w-6 h-6 bg-blue-500 rounded text-white flex items-center justify-center text-sm font-bold mr-3"
          >
            3
          </div>
          <h2 class="text-lg font-semibold text-gray-900">
            복약 의사 종합 의견
          </h2>
        </div>

        <div class="bg-white rounded-2xl p-4">
          <div class="text-sm text-gray-600 mb-4">복약 이행률</div>

          <!-- 그래프와 설명을 나란히 배치 -->
          <div class="flex items-center space-x-6 mb-4">
            <!-- 원형 프로그레스 바 -->
            <div class="relative w-20 h-20 flex-shrink-0">
              <svg class="w-20 h-20 transform -rotate-90" viewBox="0 0 100 100">
                <!-- 배경 원 -->
                <circle
                  cx="50"
                  cy="50"
                  r="40"
                  stroke="#e5e7eb"
                  stroke-width="8"
                  fill="none"
                />
                <!-- 진행률 원 (동적) -->
                <circle
                  cx="50"
                  cy="50"
                  r="40"
                  stroke="#3b82f6"
                  stroke-width="8"
                  fill="none"
                  :stroke-dasharray="progressCalculation.strokeDasharray"
                  :stroke-dashoffset="progressCalculation.strokeDashoffset"
                  stroke-linecap="round"
                  class="transition-all duration-1000 ease-out"
                />
              </svg>
              <!-- 중앙 퍼센트 표시 -->
              <div class="absolute inset-0 flex items-center justify-center">
                <span class="text-lg font-bold text-blue-500"
                  >{{ adherenceRate }}%</span
                >
              </div>
            </div>

            <!-- 설명 텍스트 -->
            <div class="flex-1">
              <div class="text-lg font-semibold text-gray-900 mb-1">
                {{ adherenceRate }}% 훌륭하고,
              </div>
              <div class="text-sm text-gray-600">건약한 날이 앞으로,</div>
              <div class="text-sm text-gray-600">더욱 늘어날 예정</div>
            </div>
          </div>

          <div class="space-y-3">
            <div>
              <div class="text-sm font-medium text-gray-700 mb-1">누락약품</div>
              <div class="text-sm text-gray-600">
                → 메인 아침 약과, 날마 아이템을 잊은 누락
              </div>
              <div class="text-sm text-gray-600">
                → 저녁 시간 용량과, 단기 계획을 잊은 누락
              </div>
            </div>
            <div>
              <div class="text-sm font-medium text-gray-700 mb-1">
                복약 실허
              </div>
              <div class="text-sm text-gray-600">
                혈약 시간에 용량, 식 접어놓 기 기타 실허
              </div>
              <div class="text-sm text-gray-600">
                → 혈약 엠콜 따뜨과오, 성차 및 컬레 바뜻
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 복약 의사 프로필 섹션 -->
      <div class="mb-6">
        <div class="flex items-center mb-4">
          <div
            class="w-6 h-6 bg-blue-500 rounded text-white flex items-center justify-center text-sm font-bold mr-3"
          >
            4
          </div>
          <h2 class="text-lg font-semibold text-gray-900">복약 의사 프로필</h2>
        </div>

        <div class="bg-white rounded-2xl p-4">
          <div class="flex items-center space-x-4 mb-4">
            <!-- 프로필 이미지 -->
            <div class="w-16 h-16 bg-gray-200 rounded-full overflow-hidden">
              <img
                src="https://images.unsplash.com/photo-1559839734-2b71ea197ec2?w=150&h=150&fit=crop&crop=face"
                alt="약사 프로필"
                class="w-full h-full object-cover"
              />
            </div>
            <div>
              <div class="flex items-center space-x-2 mb-1">
                <h3 class="font-semibold text-gray-900">
                  {{ pharmacistInfo.name }}
                </h3>
                <span class="text-sm text-gray-500">{{
                  pharmacistInfo.title
                }}</span>
                <span
                  class="text-xs bg-gray-100 text-gray-600 px-2 py-1 rounded"
                  >간증대상</span
                >
              </div>
              <div class="text-sm text-gray-600">
                <div>
                  {{ pharmacistInfo.license }} : {{ pharmacistInfo.number }}
                </div>
                <div>연락처: {{ pharmacistInfo.phone }}</div>
              </div>
            </div>
          </div>

          <div class="space-y-2 text-sm text-gray-600">
            <div>위치: {{ pharmacistInfo.location }}</div>
            <div>
              경력
              <ul class="list-disc ml-5">
                <li
                  v-for="(item, index) in pharmacistInfo.history"
                  :key="index"
                >
                  {{ item }}
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>

      <!-- AI 리포트 다운로드 버튼 -->
      <div class="mt-8">
        <button
          @click="downloadReport"
          class="w-full py-4 bg-blue-500 text-white rounded-2xl font-semibold text-lg hover:bg-blue-600 transition-all"
        >
          AI 리포트 열람(PDF)
        </button>
      </div>
    </div>
  </div>
</template>
