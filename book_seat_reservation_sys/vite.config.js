import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  server: {
    host: '127.0.0.1',
    port: 5173,
    proxy: {
      '/api': {			//请求前缀
        target: 'http://localhost:8080',	//目标地址
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''),	//把实际请求路径中的api换为空串
      },
    }
  },
  plugins: [vue()],
})
