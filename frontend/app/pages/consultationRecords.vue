<script setup lang="ts">
import { ref, computed, onMounted } from "vue";

const reportData = ref({
  date: "",
  recordId: "",
  pharmacist: "김지윤",
  type: "복약 상담",
});

const reportDate = computed(() => {
  if (!reportData.value.date) return "2025년5월15일";

  try {
    const date = new Date(reportData.value.date);

    if (isNaN(date.getTime())) {
      return "2025년5월15일";
    }

    const year = date.getFullYear();
    const month = String(date.getMonth() + 1);
    const day = String(date.getDate());

    return `${year}년${month}월${day}일`;
  } catch (error) {
    console.error("날짜 파싱 오류:", error);
    return "2025년5월15일";
  }
});

const goBack = () => {
  window.history.back();
};

const downloadReport = () => {
  console.log("AI 리포트 열람(PDF) 다운로드");
};

onMounted(() => {
  const savedReport = sessionStorage.getItem("selectedReport");
  if (savedReport) {
    try {
      reportData.value = JSON.parse(savedReport);
      console.log("불러온 리포트 데이터:", reportData.value);
    } catch (error) {
      console.error("리포트 데이터 파싱 오류:", error);
    }
  }
});

// 복약 이행률
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

// 이상 반응 데이터 (사진과 완전히 동일)
const medicationData = [
  {
    id: 1,
    time: "이상 반응 1",
    hasContent: true,
    description: "졸림 및 멍함",
    medications: [
      {
        name: "관련 약물",
        description: "스틸녹스정10mg + 트리티코정50mg",
        icon: "💊",
      },
      {
        name: "원인 및 위험 요인",
        description: "중추신경 억제 중복 → 낙상 위험 증가 가능성",
        icon: "ℹ️",
      },
      {
        name: "가이드",
        description:
          "→ 졸림 및 멍함 증상이 반복되므로 졸피뎀·트라조돈 중복 복용 여부를 병원에 보고하고, 필요 시 수면약 조정 또는 바이옵틱 수면요법 전환을 검토해야 합니다.",
        icon: "💬",
      },
    ],
  },
  {
    id: 2,
    time: "이상 반응 2",
    hasContent: true,
    description: "위장장애",
    medications: [
      {
        name: "관련 약물",
        description: "쎄레브렉스캡슐100mg + (커클랜드)글루코사민 + MSM",
        icon: "💊",
      },
      {
        name: "원인 및 위험 요인",
        description: "위 점막 자극 → 식사와 분리 복용 시 악화",
        icon: "ℹ️",
      },
      {
        name: "가이드",
        description:
          "→ 위장 불편감은 글루코사민을 반드시 식후 복용하고, 필요 시 용량을 조절하거나, 위장 보호제 추가 처방을 의논할 수 있습니다.",
        icon: "💬",
      },
    ],
  },
  {
    id: 3,
    time: "이상 반응 3",
    hasContent: true,
    description: "지목주스 병용",
    medications: [
      {
        name: "관련 약물",
        description: "리피토정20mg",
        icon: "💊",
      },
      {
        name: "원인 및 위험 요인",
        description: "CYP3A4 대사 저해 → 근육통·간독성 위험 증가",
        icon: "ℹ️",
      },
      {
        name: "가이드",
        description:
          "→ 리피토정20mg 복용 환자는 자몽주스 섭취를 즉시 중단하고, 대체 과일(예: 사과, 배)을 권장합니다.",
        icon: "💬",
      },
    ],
  },
  {
    id: 4,
    time: "이상 반응 4",
    hasContent: true,
    description: "출혈",
    medications: [
      {
        name: "관련 약물",
        description: "오메가3 + 트리티코정50mg",
        icon: "💊",
      },
      {
        name: "원인 및 위험 요인",
        description: "항응고 작용 중복 → 출혈성 부작용 가능성",
        icon: "ℹ️",
      },
      {
        name: "가이드",
        description:
          "→ 출혈 경향이 있는 경우에는 오메가3 복용 중단 여부, 또는 항우울제·항코피 복합 작용을 의사와 상담 후 조정해야 하며, 정기적인 출혈 증상(잇몸 출혈, 멍 등) 모니터링을 안내해야 합니다.",
        icon: "💬",
      },
    ],
  },
];

// 의약사 정보
const pharmacistInfo = {
  name: "김지윤",
  age: "34세",
  gender: "여성",
  title: "연락처",
  phone: "010-1234-5678",
  location: "경력",
  history: [
    "신중앙 약국 근무 (2025.05 ~ 현재)",
    "중앙대 병원 근무 (2021.05 ~ 2025.04)",
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
        {{ reportDate }}
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

        <!-- 이상 반응 목록 -->
        <div class="space-y-4">
          <div
            v-for="record in medicationData"
            :key="record.id"
            class="bg-white rounded-2xl p-4"
          >
            <div class="flex items-center mb-3">
              <span class="text-2xl mr-3">⚠️</span>
              <h3 class="font-semibold text-gray-900">{{ record.time }}</h3>
            </div>
            <div class="text-sm text-gray-600 mb-3">
              {{ record.description }}
            </div>

            <!-- 내용이 있는 경우 -->
            <div v-if="record.hasContent" class="space-y-4">
              <div
                v-for="med in record.medications"
                :key="med.name"
                class="mb-3"
              >
                <div class="flex items-center mb-1">
                  <span class="text-lg mr-2">{{ med.icon }}</span>
                  <span class="text-sm font-medium text-gray-700">{{
                    med.name
                  }}</span>
                </div>
                <p class="text-sm text-gray-600 ml-6 leading-relaxed">
                  {{ med.description }}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 방문 약사 종합 의견 섹션 -->
      <div class="mb-6">
        <div class="flex items-center mb-4">
          <div
            class="w-6 h-6 bg-blue-500 rounded text-white flex items-center justify-center text-sm font-bold mr-3"
          >
            3
          </div>
          <h2 class="text-lg font-semibold text-gray-900">
            방문 약사 종합 의견
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
                <!-- 진행률 원 -->
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
                {{ adherenceRate }}% 복용했어요,
              </div>
              <div class="text-sm text-gray-600">전반적 이행 양호하나,</div>
              <div class="text-sm text-gray-600">취침 전 약 반복 누락</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 누락약물 섹션 -->
      <div class="mb-6">
        <div class="text-sm font-medium text-gray-700 mb-3">누락약물</div>
        <div class="bg-white rounded-2xl p-4 space-y-2">
          <div class="flex space-x-2">
            <span
              class="px-3 py-1 bg-blue-100 text-blue-700 rounded-full text-sm"
              >졸피뎀</span
            >
            <span
              class="px-3 py-1 bg-blue-100 text-blue-700 rounded-full text-sm"
              >트라조돈</span
            >
          </div>
          <div class="text-sm text-gray-600">
            수면 시간 불규칙, 인지 저하로 인한 누락
          </div>
          <div class="text-sm text-gray-600">
            → 수면 시간 불규칙, 인지 저하로 인한 누락
          </div>
        </div>
      </div>

      <!-- 복약 상황 섹션 -->
      <div class="mb-6">
        <div class="text-sm font-medium text-gray-700 mb-3">복약 상황</div>
        <div class="bg-white rounded-2xl p-4 space-y-2">
          <div class="text-sm text-gray-600">
            복약 시간대 불규칙, 약 복용 후 기억 흐림
          </div>
          <div class="text-sm text-gray-600">
            → 약 복용 루틴 미형성, 위치 설정 미흡
          </div>
        </div>
      </div>

      <!-- 방문 약사 프로필 섹션 -->
      <div class="mb-6">
        <div class="flex items-center mb-4">
          <div
            class="w-6 h-6 bg-blue-500 rounded text-white flex items-center justify-center text-sm font-bold mr-3"
          >
            4
          </div>
          <h2 class="text-lg font-semibold text-gray-900">방문 약사 프로필</h2>
        </div>

        <div class="bg-white rounded-2xl p-4">
          <div class="flex items-start space-x-4 mb-4">
            <!-- 프로필 이미지 -->
            <div
              class="w-16 h-16 bg-gray-200 rounded-lg overflow-hidden flex-shrink-0"
            >
              <img
                src="https://images.unsplash.com/photo-1559839734-2b71ea197ec2?w=150&h=150&fit=crop&crop=face"
                alt="약사 프로필"
                class="w-full h-full object-cover"
              />
            </div>

            <!-- 프로필 정보 -->
            <div class="flex-1">
              <!-- 이름과 전체보기 -->
              <div class="flex items-center justify-between mb-1">
                <h3 class="font-semibold text-gray-900">
                  {{ pharmacistInfo.name }}
                </h3>
                <div class="flex items-center text-gray-400 text-sm">
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
                </div>
              </div>

              <!-- 나이, 성별 -->
              <div class="text-sm text-gray-500 mb-2">
                ({{ pharmacistInfo.age }}, {{ pharmacistInfo.gender }})
              </div>

              <!-- 연락처 -->
              <div class="flex items-center text-sm text-gray-600">
                <span class="font-medium mr-2">{{ pharmacistInfo.title }}</span>
                <span>{{ pharmacistInfo.phone }}</span>
                <svg
                  class="w-4 h-4 ml-2 text-gray-400"
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
              </div>
            </div>
          </div>

          <!-- 경력 섹션 (전체 너비) -->
          <div class="border-t border-gray-100 pt-3">
            <div class="text-sm font-medium text-gray-700 mb-2">
              {{ pharmacistInfo.location }}
            </div>
            <ul class="text-sm text-gray-600 space-y-1">
              <li
                v-for="(item, index) in pharmacistInfo.history"
                :key="index"
                class="flex items-start"
              >
                <span
                  class="w-1 h-1 bg-gray-400 rounded-full mt-2 mr-2 flex-shrink-0"
                ></span>
                <span>{{ item }}</span>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <!-- AI 리포트 다운로드 버튼 -->
      <div class="mt-8">
        <button
          @click="downloadReport"
          class="w-full py-4 bg-blue-500 text-white rounded-2xl font-semibold text-lg hover:bg-blue-600 transition-all flex items-center justify-center space-x-2"
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
              d="M12 10v6m0 0l-3-3m3 3l3-3m2 8H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
            />
          </svg>
          <span>AI 리포트 열람(PDF)</span>
        </button>
      </div>
    </div>
  </div>
</template>
