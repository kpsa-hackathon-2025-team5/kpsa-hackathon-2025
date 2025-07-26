import tailwindcss from "@tailwindcss/vite"

export default defineNuxtConfig({
  compatibilityDate: '2025-07-15',
  devtools: {enabled: true},
  ssr: false,
  runtimeConfig: {
    public: {
      apiBaseUrl: process.env.API_BASE_URL || 'http://localhost:8080'
    }
  },
  vite: {
    plugins: [tailwindcss()],
    server: {
      allowedHosts: ['28918cfbe868.ngrok-free.app'],
    },
  },
  nitro: {
    output: {
      publicDir: '..\\src\\main\\resources\\static'
    }
  },
  css: ["~/assets/app.css"],
  modules: ['@pinia/nuxt'],
})
