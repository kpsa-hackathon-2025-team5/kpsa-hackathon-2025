<script setup lang="ts">
const route = useRoute()

watch(() => route.path, () => {
  nextTick(() => {
    window.scrollTo(0, 0)
  })
})
</script>

<template>
  <NuxtLayout>
    <NuxtPage />
  </NuxtLayout>
</template>
