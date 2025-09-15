import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')
    }
  },
  server: {
    port: 3000,
    open: true,
    proxy: {
      '/api': {
        target: 'http://localhost:8090',
        changeOrigin: true,
        secure: false
      }
    }
  },
  build: {
    // 빌드 결과물을 backend/src/main/resources/static 폴더에 출력
    outDir: '../backend/src/main/resources/static',
    // 빌드 시 public 폴더의 구조 유지
    copyPublicDir: true,
    // assets 폴더명 변경 (선택사항)
    assetsDir: 'assets'
  }
})
