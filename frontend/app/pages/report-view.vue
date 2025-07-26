<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useApi } from "@/composable/useApi";

const { apiCall } = useApi();

const visitRecords = ref([]);
const isLoading = ref(false);
const patientName = ref("사용자");

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

    console.log("환자 정보 API 응답:", result);
    const name =
      result.name || result.patientName || result.memberName || "사용자";
    patientName.value = name;

    return result;
  } catch (error) {
    console.log("환자 정보 GET 요청 실패, POST 방식으로 재시도");

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
      console.error("환자 정보 API 호출 오류:", postError);
      patientName.value = "사용자";
    }
  }
};

// 방문 기록 가져오기
const getVisitRecords = async () => {
  try {
    isLoading.value = true;
    const patientId = 9;

    console.log("방문 기록 API 요청", { patientId });

    const result = await apiCall(`/api/v1/visits/patients/${patientId}`, {
      method: "GET",
    });

    console.log("방문 기록 API 응답:", result);

    // 실제 API 응답 구조에 따라 데이터 처리
    let records = [];

    if (Array.isArray(result)) {
      records = result;
    } else if (result.visits) {
      records = result.visits;
    } else if (result.data) {
      records = result.data;
    } else {
      // 단일 객체인 경우 배열로 변환
      records = [result];
    }

    // 날짜순으로 정렬 (최신순)
    visitRecords.value = records
      .filter((record) => record && record.createdAt) // null 체크
      .sort((a, b) => {
        const dateA = new Date(a.createdAt || a.visitDate || a.updatedAt);
        const dateB = new Date(b.createdAt || b.visitDate || b.updatedAt);
        return dateB.getTime() - dateA.getTime();
      });

    console.log("파싱된 방문 기록:", visitRecords.value);
  } catch (error) {
    console.error("방문 기록 API 호출 오류:", error);
    visitRecords.value = [];
  } finally {
    isLoading.value = false;
  }
};

// 날짜 포맷팅
const formatDate = (dateString: string) => {
  if (!dateString) return "날짜 정보 없음";

  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");

  return `${year}년 ${month}월 ${day}일`;
};

// 약사 이름 추출
const getPharmacistName = (record: any) => {
  return (
    record.pharmacist || record.name || record.pharmacistName || "담당 약사"
  );
};

// 상담 유형 추출
const getConsultationType = (record: any) => {
  return record.consultationType || record.type || "복약 상담";
};

// 리포트 상세 보기
const viewReportDetail = (record: any) => {
  console.log("리포트 상세 보기:", record);
  // 상세 페이지로 이동
  // navigateTo(`/report-detail/${record.id}`);
};

// 컴포넌트 마운트 시 데이터 가져오기
onMounted(async () => {
  await getPatientInfo();
  await getVisitRecords();
});
</script>

<template>
  <!-- 리포트 열람하기 페이지 전체 wrap -->
  <div class="min-h-screen w-full bg-gray-100 flex flex-col">
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
        리포트 열람하기
      </p>
    </div>

    <!-- 메인 컨텐츠 -->
    <div class="flex-1 px-6 py-8 overflow-y-auto pb-24">
      <!-- 프로필 및 아이콘 섹션 -->
      <div class="flex items-center justify-center mb-8">
        <!-- 프로필 아이콘 (조 대신 첫 글자 사용) -->
        <div
          class="w-16 h-16 bg-orange-500 rounded-full flex items-center justify-center mr-4"
        >
          <span class="text-white text-xl font-bold">{{
            patientName.charAt(0) || "P"
          }}</span>
        </div>

        <!-- 의료 리포트 아이콘 -->
        <div class="relative">
          <div
            class="w-24 h-24 bg-blue-100 rounded-2xl flex items-center justify-center"
          >
            <svg
              class="w-12 h-12 text-blue-600"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
              />
            </svg>
          </div>
          <!-- 청진기 아이콘 -->
          <div
            class="absolute -bottom-2 -right-2 w-8 h-8 bg-blue-600 rounded-full flex items-center justify-center"
          >
            <svg
              class="w-4 h-4 text-white"
              fill="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                d="M19 14c1.49-1.46 3-3.21 3-5.5A5.5 5.5 0 0016.5 3c-1.76 0-3.22.79-4.21 2.09C11.28 3.79 9.76 3 8 3A5.5 5.5 0 002.5 8.5c0 2.29 1.51 4.04 3 5.5l6.5 6.5 7-7z"
              />
            </svg>
          </div>
        </div>
      </div>

      <!-- 제목 섹션 -->
      <div class="mb-6">
        <div class="flex items-center justify-between">
          <h2 class="text-xl font-bold text-gray-900">방문 복약 상담 기록</h2>
          <span class="text-sm text-gray-500">최신순</span>
        </div>
      </div>

      <!-- 로딩 상태 -->
      <div v-if="isLoading" class="flex justify-center items-center py-12">
        <div
          class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"
        ></div>
      </div>

      <!-- 방문 기록이 없는 경우 -->
      <div
        v-else-if="visitRecords.length === 0"
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
              d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
            />
          </svg>
        </div>
        <p class="text-gray-600 mb-2">방문 기록이 없습니다</p>
        <p class="text-sm text-gray-500">첫 약사 방문을 예약해보세요</p>
      </div>

      <!-- 방문 기록 리스트 -->
      <div v-else class="space-y-4">
        <div
          v-for="record in visitRecords"
          :key="record.id"
          @click="viewReportDetail(record)"
          class="bg-white rounded-2xl p-6 shadow-sm border border-gray-100 cursor-pointer hover:shadow-md transition-all active:scale-95"
        >
          <div class="flex items-center justify-between">
            <div class="flex-1">
              <h3 class="text-lg font-bold text-gray-900 mb-2">
                {{
                  formatDate(
                    record.createdAt || record.visitDate || record.updatedAt
                  )
                }}
              </h3>

              <!-- 상담 정보 -->
              <div class="space-y-1 mb-3">
                <p class="text-sm text-gray-600">
                  👨‍⚕️ {{ getPharmacistName(record) }}
                </p>
                <p class="text-sm text-gray-600">
                  📋 {{ getConsultationType(record) }}
                </p>
                <p v-if="record.pharmacyName" class="text-sm text-gray-600">
                  🏥 {{ record.pharmacyName }}
                </p>
                <div
                  v-if="record.medications && record.medications.length > 0"
                  class="text-sm text-gray-600"
                >
                  💊 {{ record.medications.join(", ") }}
                </div>
              </div>

              <!-- 상담 내용 미리보기 -->
              <p
                v-if="record.notes || record.summary || record.memberType"
                class="text-sm text-gray-700 line-clamp-2"
              >
                {{
                  record.notes ||
                  record.summary ||
                  `${record.memberType} 복약 상담 진행`
                }}
              </p>

              <!-- API 데이터가 있는 경우 기본 메시지 -->
              <p v-else-if="record.id" class="text-sm text-gray-700">
                복약 상담이 진행되었습니다.
              </p>
            </div>

            <!-- 화살표 아이콘 -->
            <svg
              class="w-5 h-5 text-gray-400 flex-shrink-0 ml-4"
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
      </div>

      <!-- 도움말 섹션 -->
      <div class="mt-8 bg-blue-50 rounded-2xl p-4">
        <h3 class="font-semibold text-blue-900 mb-2">📊 리포트 안내</h3>
        <ul class="text-sm text-blue-800 space-y-1">
          <li>• 방문 시마다 복약 상담 기록이 자동으로 저장됩니다</li>
          <li>• 각 기록을 터치하면 상세 내용을 확인할 수 있습니다</li>
          <li>• 복약 이력을 통해 건강 관리 현황을 파악하세요</li>
        </ul>
      </div>
    </div>

    <!-- 하단 인디케이터 -->
    <div class="flex justify-center pb-8">
      <div class="w-32 h-1 bg-black rounded-full"></div>
    </div>
  </div>
</template>

<style scoped>
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
