import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
import { existsSync, rmSync } from 'fs'

// 빌드 전에 기존 static 폴더 내용 삭제하는 플러그인
const cleanStaticDir = () => {
  return {
    name: 'clean-static-dir',
    buildStart() {
      // 빌드 모드일 때만 실행 (개발 모드에서는 실행하지 않음)
      if (process.env.NODE_ENV === 'production') {
        const staticDir = resolve(__dirname, '../backend/src/main/resources/static')
        if (existsSync(staticDir)) {
          console.log('🧹 기존 static 폴더 내용 삭제 중...')
          rmSync(staticDir, { recursive: true, force: true })
          console.log('✅ static 폴더 정리 완료')
        }
      }
    }
  }
}

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(), cleanStaticDir()],
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
