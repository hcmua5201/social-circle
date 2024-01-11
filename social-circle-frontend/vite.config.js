import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    host: '0.0.0.0',
    port: 5173,
    https: false,
    cors: true,
    changeOrigin: true,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,                    //�Ƿ����
        rewrite: (path) => path.replace(/^\/api/, ''),
      },

    },
  }
})
