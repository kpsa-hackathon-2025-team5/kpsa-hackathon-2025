<script setup lang="ts">
import { ref } from "vue";

// 스토어 import (경로 확인 필요)
const authStore = useAuthStore();

const userId = ref("");
const password = ref("");
const showPassword = ref(false);
const isLoading = ref(false);
const errorMessage = ref("");

const goBack = () => {
  window.history.back();
};

const login = async () => {
  if (!userId.value || !password.value) {
    errorMessage.value = "아이디와 비밀번호를 입력해주세요.";
    return;
  }

  isLoading.value = true;
  errorMessage.value = "";

  try {
    console.log("로그인 시도:", { username: userId.value });
    await authStore.login(userId.value, password.value);
    console.log("로그인 성공:", authStore.member);

    // 홈 페이지가 없다면 일단 콘솔로 확인
    console.log("로그인 완료! 홈으로 이동 시도");
    navigateTo("/takerSelect");
  } catch (error: any) {
    console.error("로그인 오류:", error);

    // 더 자세한 에러 정보 표시
    if (error.message?.includes("Failed to fetch")) {
      errorMessage.value = "서버 연결 실패: 네트워크를 확인해주세요.";
    } else if (error.status === 401) {
      errorMessage.value = "아이디 또는 비밀번호가 올바르지 않습니다.";
    } else {
      errorMessage.value = error.message || "로그인에 실패했습니다.";
    }
  } finally {
    isLoading.value = false;
  }
};

const findId = () => {
  console.log("아이디 찾기");
};

const findPassword = () => {
  console.log("비밀번호 찾기");
};

const signup = () => {
  navigateTo("/signup");
};
</script>

<template>
  <!-- 로그인 페이지 전체 wrap -->
  <div class="h-full w-full bg-gray-100 flex flex-col">
    <!-- 뒤로가기 및 소형 타이틀 wrap -->
    <div class="relative flex items-center px-6 py-6 bg-gray-100 text-black">
      <!-- @click="goBack" 추가! -->
      <button @click="goBack" class="p-2">
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
        로그인
      </p>
    </div>

    <!-- 나머지는 그대로 유지 -->
    <!-- 로그인 정보 입력 -->
    <div class="flex-1 px-6 py-8">
      <!-- 제목 -->
      <div class="mb-12">
        <h1 class="text-3xl font-bold text-gray-900 leading-tight mb-4">
          안녕하세요.<br />
          <span class="text-blue-600">YakSense</span> 입니다.
        </h1>
      </div>

      <!-- 에러 메시지 -->
      <div
        v-if="errorMessage"
        class="mb-4 p-3 bg-red-100 border border-red-400 text-red-700 rounded"
      >
        {{ errorMessage }}
      </div>

      <div class="space-y-4 mb-8">
        <!-- 아이디 입력 -->
        <div>
          <input
            v-model="userId"
            type="text"
            placeholder="아이디 입력"
            :disabled="isLoading"
            class="w-full px-4 py-4 bg-white rounded-lg border-none text-lg placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50 text-black"
          />
        </div>

        <!-- 비밀번호 입력 -->
        <div class="relative">
          <input
            v-model="password"
            :type="showPassword ? 'text' : 'password'"
            placeholder="비밀번호 입력"
            :disabled="isLoading"
            @keyup.enter="login"
            class="w-full px-4 py-4 bg-white rounded-lg border-none text-lg placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-500 pr-12 disabled:opacity-50 text-black"
          />
          <!-- 비밀번호 보기/숨기기 버튼 -->
          <button
            @click="showPassword = !showPassword"
            class="absolute right-4 top-1/2 transform -translate-y-1/2 p-1"
            type="button"
            :disabled="isLoading"
          >
            <svg
              v-if="!showPassword"
              class="w-5 h-5 text-gray-400"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"
              />
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"
              />
            </svg>
            <svg
              v-else
              class="w-5 h-5 text-gray-400"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.878 9.878L3 3m6.878 6.878L21 21"
              />
            </svg>
          </button>
        </div>
      </div>

      <!-- 로그인 버튼 -->
      <button
        @click="login"
        :disabled="isLoading || !userId || !password"
        class="w-full bg-blue-600 text-white py-4 rounded-lg text-lg font-medium hover:bg-blue-700 transition-colors mb-6 disabled:opacity-50 disabled:cursor-not-allowed flex items-center justify-center"
      >
        <svg
          v-if="isLoading"
          class="animate-spin -ml-1 mr-3 h-5 w-5 text-white"
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
        >
          <circle
            class="opacity-25"
            cx="12"
            cy="12"
            r="10"
            stroke="currentColor"
            stroke-width="4"
          ></circle>
          <path
            class="opacity-75"
            fill="currentColor"
            d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
          ></path>
        </svg>
        {{ isLoading ? "로그인 중..." : "로그인" }}
      </button>

      <!-- 아이디 찾기, 비밀번호 찾기, 회원가입 -->
      <div class="flex justify-center space-x-4 text-gray-600 mb-12">
        <button @click="findId" class="text-sm hover:text-gray-800">
          아이디 찾기
        </button>
        <span class="text-gray-300">|</span>
        <button @click="findPassword" class="text-sm hover:text-gray-800">
          비밀번호 찾기
        </button>
        <span class="text-gray-300">|</span>
        <button @click="signup" class="text-sm hover:text-gray-800">
          회원가입
        </button>
      </div>
    </div>
  </div>
</template>
