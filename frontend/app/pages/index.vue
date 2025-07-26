<script setup lang="ts">

// x초 후 로그인 페이지로 자동 이동
    onMounted(() => {
        setTimeout(() => {
        navigateTo('/login')
        }, 2000)
    })



</script>

<template>
  <!-- default.vue 레이아웃의 slot 영역에 들어갈 내용 -->
  <div class="flex justify-center items-center h-full" style="background-color: #2869FE;">
    <img
      src="@/assets/yacksenseLogo.png"
      alt="logo"
      class="h-32"
    >
  </div>
</template>