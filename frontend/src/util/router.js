import { createRouter, createWebHistory } from 'vue-router'
import Home from '/src/views/Home.vue'
import adminRoutes from './routes/adminRouter.js'
import mallRoutes from './routes/mallRouter.js'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  
  // Admin Routes
  ...adminRoutes,
  
  // Mall Routes
  ...mallRoutes
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
