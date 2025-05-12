import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  host: true,
  plugins: [react()],
  server: {
    proxy: {
      '/ws': {
        target: '${import.meta.env.VITE_API_BASE_URL}',
        changeOrigin: true,
        ws: true, // ✅ WebSocket도 프록시
      },
      '/auth': {
        target: '${import.meta.env.VITE_API_BASE_URL}',
        changeOrigin: true,
      },
      '/login': {
        target: '${import.meta.env.VITE_API_BASE_URL}',
        changeOrigin: true,
      },
    },
  },
});