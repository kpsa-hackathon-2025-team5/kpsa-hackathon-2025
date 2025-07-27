<script setup lang="ts">
const route = useRoute()

const menus = [
  {
      name: '홈',
      path: '/',
      icon: '<svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" /></svg>'
    },
  {
    name: '리포트',
    path: '/consultationRecords',
    icon: '<svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 17v-2m3 2v-4m3 4v-6m2 10H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" /></svg>'
  },
  {
    name: '복약확인',
    path: '/takeMedicine',
    icon: '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="w-6 h-6"><path d="M10 3.5a.75.75 0 0 1 .75.75v2.5h2.5a.75.75 0 0 1 0 1.5h-2.5v2.5a.75.75 0 0 1-1.5 0v-2.5h-2.5a.75.75 0 0 1 0-1.5h2.5v-2.5A.75.75 0 0 1 10 3.5Z" /><path fill-rule="evenodd" d="M10 18a8 8 0 1 0 0-16 8 8 0 0 0 0 16Zm0-1.5a6.5 6.5 0 1 0 0-13 6.5 6.5 0 0 0 0 13Z" clip-rule="evenodd" /></svg>'
  },
  {
    name: '구독',
    path: '/serviceApply',
    icon: '<svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" viewBox="0 0 20 20" fill="currentColor"><path d="M4 4a2 2 0 00-2 2v1h16V6a2 2 0 00-2-2H4z" /><path fill-rule="evenodd" d="M18 9H2v5a2 2 0 002 2h12a2 2 0 002-2V9zM4 13a1 1 0 011-1h1a1 1 0 110 2H5a1 1 0 01-1-1zm5-1a1 1 0 100 2h1a1 1 0 100-2H9z" clip-rule="evenodd" /></svg>'
  },
]
</script>

<template>
  <nav class="absolute bottom-0 left-0 right-0 bg-white shadow-t-md border-t border-gray-200">
    <div class="flex justify-around">
      <NuxtLink v-for="menu in menus" :key="menu.name" :to="menu.path" class="flex flex-col items-center justify-center w-full pt-2 pb-1 text-sm transition-colors duration-200" :class="route.path === menu.path ? 'text-blue-600' : 'text-gray-500 hover:text-blue-500'">
        <div v-html="menu.icon"></div>
        <span class="mt-1">{{ menu.name }}</span>
      </NuxtLink>
    </div>
  </nav>
</template>
