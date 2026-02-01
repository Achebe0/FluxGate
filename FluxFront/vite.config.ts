import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    host: true, // Listen on all addresses
    port: 5173,
    proxy: {
      // Proxy API requests to the backend on port 8081
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false,
      }
    }
  }
})