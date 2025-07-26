<script setup lang="ts">
const goBack = () => {
  navigateTo(-1)
}

const selectUserType = (type: string) => {
  console.log('선택된 유형:', type)
  // 다음 페이지로 이동 로직
}


const userId = ref('')
const password = ref('')
const showPassword = ref(false)

const login = () => {
  console.log('로그인:', { userId: userId.value, password: password.value })
  // 로그인 로직
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
      <p class="absolute text-lg font-medium">로그인</p>
    </div>

    <!-- 로그인 정보 입력 -->
    <div class="flex-1 px-6 py-8">
      <!-- 제목 -->

        <div class="mb-12">
          <h1 class="text-3xl font-bold text-gray-900 leading-tight mb-4">
            안녕하세요.<br>
            <span class="text-blue-600">YakSense</span> 입니다.
          </h1>
        </div>

          <div class="space-y-4 mb-8">
            <!-- 아이디 입력 -->
            <div>
              <input
                v-model="userId"
                type="text"
                placeholder="아이디 입력"
                class="w-full px-4 py-4 bg-white rounded-lg border-none text-lg placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-500"
              >
            </div>

            <!-- 비밀번호 입력 -->
            <div class="relative">
              <input
                v-model="password"
                :type="showPassword ? 'text' : 'password'"
                placeholder="비밀번호 입력"
                class="w-full px-4 py-4 bg-white rounded-lg border-none text-lg placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-500 pr-12"
              >
              <!-- 비밀번호 보기/숨기기 버튼 -->
              <button
                @click="showPassword = !showPassword"
                class="absolute right-4 top-1/2 transform -translate-y-1/2 p-1"
                type="button"
              >
                <svg v-if="!showPassword" class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
                </svg>
                <svg v-else class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.878 9.878L3 3m6.878 6.878L21 21"/>
                </svg>
              </button>
            </div>


        <button
        @click="login"
        class="w-full bg-blue-600 text-white py-3 rounded-lg text-lg font-medium hover:bg-blue-700 transition-colors mb-6"
      >
        로그인
      </button>

    <!-- 아이디 찾기, 비밀번호 찾기, 회원가입 -->
       <div class="flex justify-center space-x-4 text-gray-600 mb-12">
              <button @click="findId" class="text-sm hover:text-gray-800">아이디 찾기</button>
              <span class="text-gray-300">|</span>
              <button @click="findPassword" class="text-sm hover:text-gray-800">비밀번호 찾기</button>
              <span class="text-gray-300">|</span>
              <button @click="signup" class="text-sm hover:text-gray-800">회원가입</button>
            </div>

    </div> <!-- class="mb-12" -->





    </div>
  </div>
</template>