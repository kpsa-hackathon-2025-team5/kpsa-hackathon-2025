<script setup lang="ts">

import { VPdfViewer } from '@vue-pdf-viewer/viewer'

definePageMeta({
  layout: 'pdf-layout'
})
</script>

<template>
  <div class="w-screen h-screen">
    <VPdfViewer
        src="/ai-report.pdf"
    />
  </div>
</template>

<style scoped>

</style>
