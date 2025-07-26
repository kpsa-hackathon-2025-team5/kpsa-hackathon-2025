<script setup lang="ts">
const goBack = () => {
  navigateTo(-1)
}

const selectUserType = (type: string) => {
  console.log('선택된 유형:', type)
  // 다음 페이지로 이동 로직
}
</script>

<template>
  <!-- 사용자유형 페이지 전체 wrap -->
  <div class="h-full w-full bg-gray-100 flex flex-col">

    <!-- 뒤로가기 및 소형 타이틀 wrap -->
    <div class="relative flex items-center px-6 py-6 bg-gray-100">
      <button @click="goBack" class="mr-4 p-2">
        <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"/>
        </svg>
      </button>
      <p class=" absolute left-35  text-lg font-medium">사용자 유형</p>
    </div>

    <!-- 사용자 유형 wrap -->
    <div class="flex-1 px-6 py-8">
      <!-- 제목 -->
      <div class="mb-16">
        <h1 class="text-3xl font-bold text-gray-900 leading-tight">
          사용자 유형을<br>선택해주세요.
        </h1>
      </div>

      <!-- 버튼들 -->
      <div class="space-y-5">
        <button
          @click="selectUserType('약사용')"
          class="w-full bg-blue-600 text-white py-5 px-6 rounded-full text-xl font-medium hover:bg-blue-700 transition-all duration-200 flex items-center justify-between shadow-lg hover:shadow-xl transform hover:scale-[1.02]"
        >
          <span>약사용</span>
          <div class="w-12 h-0.5 bg-white rounded-full relative">
            <div class="absolute -right-2 -top-1 w-0 h-0 border-l-4 border-l-white border-t-2 border-b-2 border-t-transparent border-b-transparent"></div>
          </div>
        </button>

        <button
          @click="selectUserType('보호자용')"
          class="w-full bg-blue-600 text-white py-5 px-6 rounded-full text-xl font-medium hover:bg-blue-700 transition-all duration-200 flex items-center justify-between shadow-lg hover:shadow-xl transform hover:scale-[1.02]"
        >
          <span>보호자용</span>
          <div class="w-12 h-0.5 bg-white rounded-full relative">
            <div class="absolute -right-2 -top-1 w-0 h-0 border-l-4 border-l-white border-t-2 border-b-2 border-t-transparent border-b-transparent"></div>
          </div>
        </button>

        <button
          @click="selectUserType('복용자용')"
          class="w-full bg-blue-600 text-white py-5 px-6 rounded-full text-xl font-medium hover:bg-blue-700 transition-all duration-200 flex items-center justify-between shadow-lg hover:shadow-xl transform hover:scale-[1.02]"
        >
          <span>복용자용</span>
          <div class="w-12 h-0.5 bg-white rounded-full relative">
            <div class="absolute -right-2 -top-1 w-0 h-0 border-l-4 border-l-white border-t-2 border-b-2 border-t-transparent border-b-transparent"></div>
          </div>
        </button>
      </div>
    </div>
  </div>
</template>