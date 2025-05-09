import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  host: true,
  plugins: [react()],
  server: {
    proxy: {
      '/ws': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        ws: true, // ✅ WebSocket도 프록시
      },
      '/auth': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/login': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
});