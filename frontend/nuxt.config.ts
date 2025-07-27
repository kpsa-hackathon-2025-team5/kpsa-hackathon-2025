import tailwindcss from "@tailwindcss/vite"

const isBackendBuild = process.argv.includes('--backend')


export default defineNuxtConfig({
  compatibilityDate: '2025-07-15',
  devtools: {enabled: true},
  ssr: false,
  runtimeConfig: {
    public: {
      apiBaseUrl: process.env.API_BASE_URL || 'https://fe7558cd5f46.ngrok-free.app/'
    }
  },
  vite: {
    plugins: [tailwindcss()],
    server: {
      allowedHosts: ['28918cfbe868.ngrok-free.app'],
    },
  },
  nitro: {
    output: isBackendBuild ? {
      publicDir: '..\\backend\\src\\main\\resources\\static'
    } : undefined
  },
  css: ["~/assets/app.css"],
  modules: ['@pinia/nuxt'],
})
