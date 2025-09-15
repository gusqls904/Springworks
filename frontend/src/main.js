import { createApp } from 'vue'
import App from './App.vue'
import router from './util/router.js'

// CSS imports - 순서 중요
import './views/common/styles/common.css'

// Font Awesome CDN (index.html에서 로드)
// 또는 npm 패키지 사용 시: import '@fortawesome/fontawesome-free/css/all.min.css'

const app = createApp(App)

app.use(router)

app.mount('#app')
