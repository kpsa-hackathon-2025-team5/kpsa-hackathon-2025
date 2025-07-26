<script setup lang="ts">
const selectUserType = (type: string) => {
  console.log("선택된 유형:", type);
  // 다음 페이지로 이동 로직
};
</script>

<template>
  <!-- 사용자유형 페이지 전체 wrap -->
  <div class="h-full w-full bg-gray-100 flex flex-col">
    <!-- 뒤로가기 및 소형 타이틀 wrap -->
    <div class="relative flex items-center px-6 py-6 bg-gray-100 text-black">
      <button class="mr-4 p-2">
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
      <p class="absolute left-33 text-lg font-medium text-black">서비스 신청</p>
    </div>
    <!-- 사용자 유형 wrap -->
    <div class="flex-1 px-6 py-8">
      <!-- 제목 -->
      <div class="mb-16">
        <h1 class="text-3xl font-bold text-gray-900 leading-tight">
          원하시는 서비스를<br />선택해주세요.
        </h1>
      </div>

      <!-- 구독서비스? 출력할 곳 -->
      <div></div>
    </div>
  </div>
</template>
