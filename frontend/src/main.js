import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './util/router.js'
import Toast from "vue-toastification"
import "vue-toastification/dist/index.css"
import "./styles/admin/popup.css" // Custom CSS import

const app = createApp(App)
const pinia = createPinia()

app.use(pinia)
app.use(router)

// Vue Toastification 설정
app.use(Toast, {
  position: "top-center", // 위치 변경 가능
  timeout: 2000,
  closeOnClick: true,
  pauseOnFocusLoss: true,
  pauseOnHover: true,
  draggable: true,
  draggablePercent: 0.6,
  showCloseButtonOnHover: false,
  hideProgressBar: false,
  closeButton: "button",
  icon: true,
  rtl: false,
  transition: "Vue-Toastification__slideBlurred",
  maxToasts: 5,
  newestOnTop: true,
  enableBatching: true, // Added for button support
  filterBeforeCreate: (toast, toasts) => {
    if (toasts.filter(t => t.type === toast.type).length !== 0) {
      return false
    }
    return toast
  }
})

app.mount('#app')