<script setup lang="ts">

const {isAuthenticated} = storeToRefs(useAuthStore())
const {login} = useAuthStore()
</script>

<template>
  <header class="flex items-center justify-between p-4 bg-white shadow-sm">
    <div>
      <h1 class="text-sm font-bold text-gray-800">안녕하세요, 고객님</h1>
      <div v-if="isAuthenticated">
        <span class="bg-black"> 로그인 완료</span>
      </div>
      <div v-else>
        <button @click="login('patients', '1234')" class="btn btn-xs btn-primary">Login</button>
      </div>

    </div>
  </header>
</template>
