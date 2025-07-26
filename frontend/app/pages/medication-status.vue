<script setup lang="ts">
const goBack = () => {
  window.history.back();
};

const selectUserType = (type: string) => {
  console.log("선택된 유형:", type);
  navigateTo("/login");
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
      <p class="absolute left-33 text-lg font-medium text-black">복약 현황</p>
    </div>

    <!-- 사용자 유형 wrap -->
    <div class="flex-1 px-6 py-8">
      <!-- 제목 -->
    </div>
  </div>
</template>
