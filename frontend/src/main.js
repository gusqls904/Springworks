import { createApp } from 'vue'
import App from './App.vue'
import router from './util/router.js'
import Toast from "vue-toastification"
import "vue-toastification/dist/index.css"
import "./styles/popup-custom.css" // Custom CSS import

const app = createApp(App)

app.use(router)

// Vue Toastification 설정
app.use(Toast, {
  position: "top-right",
  timeout: 3000,
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