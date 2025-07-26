<script setup lang="ts">
const hideNavigationRoutes = ['/', '/splash',]
</script>

<template>
  <div class="h-dvh w-dvw bg-white sm:bg-blue-50 sm:flex sm:justify-center sm:items-center">
    <!-- YakSense Logo Area (Desktop Only) -->
    <div class="hidden sm:flex flex-1 h-full justify-center items-center">
      <div class="text-center">
        <h1 class="text-5xl font-bold text-blue-600">YakSense</h1>
        <p class="text-xl text-gray-600 mt-2">내 손안의 스마트 복약 관리</p>
      </div>
    </div>

    <!-- Mobile/Desktop Screen Container -->
    <div class="h-full w-full sm:flex-1 sm:flex sm:justify-center sm:items-center sm:p-4">
      <!-- Phone Bezel (Desktop Only) -->
      <div
          class="h-full w-full sm:relative sm:mx-auto sm:border-gray-700 sm:bg-gray-900 sm:border-[12px] sm:rounded-[2.5rem] sm:h-[720px] sm:w-[360px] sm:shadow-2xl">
        <!-- Side Buttons (Desktop Only) -->
        <div class="hidden sm:block h-[40px] w-[4px] bg-gray-600 absolute -left-[16px] top-[120px] rounded-l-md"></div>
        <div class="hidden sm:block h-[40px] w-[4px] bg-gray-600 absolute -left-[16px] top-[170px] rounded-l-md"></div>
        <div class="hidden sm:block h-[60px] w-[4px] bg-gray-600 absolute -right-[16px] top-[140px] rounded-r-md"></div>

        <!-- Notch (Desktop Only) -->
        <div
            class="hidden sm:block w-[140px] h-[28px] bg-gray-900 top-0 rounded-b-[1rem] left-1/2 -translate-x-1/2 absolute">
          <div
              class="w-[60px] h-[6px] bg-gray-700 rounded-full absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2"></div>
        </div>

        <!-- Screen -->
        <div
            class="h-full w-full bg-white sm:overflow-hidden sm:rounded-[2rem] sm:ring-2 sm:ring-inset sm:ring-gray-800">
          <div class="h-full w-full flex flex-col relative">
            <!-- Main Content -->
            <div class="flex-1 overflow-y-auto">
              <slot/>
            </div>

             <BottomNavigation v-if="!hideNavigationRoutes.includes($route.path)" />

          </div>
        </div>
      </div>
    </div>
  </div>
</template>
