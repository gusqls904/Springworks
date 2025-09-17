import { createRouter, createWebHistory } from 'vue-router'
import Home from '/src/views/Home.vue'
import learningRoutes from './routes/learningRouter.js'
import mallRoutes from './routes/mallRouter.js'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  
  // Learning Routes
  ...learningRoutes,
  
  // Mall Routes
  ...mallRoutes
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
