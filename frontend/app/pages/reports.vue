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
      <p class="absolute left-33 text-lg font-medium">레포트 열람하기</p>
    </div>

    <!-- 사용자 유형 wrap -->
    <div class="flex-1 px-6 py-8">
      <!-- 제목 -->
      <div class="mb-16">
        <div class="bg-red-100 w-[100px] h-[100px] border border-black">
          이미지 들어갈 곳
        </div>
      </div>
    </div>
  </div>
</template>
