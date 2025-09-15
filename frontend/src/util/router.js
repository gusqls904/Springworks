import { createRouter, createWebHistory } from 'vue-router'
import Login from '/src/views/common/login/Login.vue'
import LearningLogin from '/src/views/learning/login/LearningLogin.vue'
import MallLogin from '/src/views/mall/login/MallLogin.vue'
import LearningBoard from '/src/views/learning/board/LearningBoard.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Login
  },
  {
    path: '/learning-login',
    name: 'LearningLogin',
    component: LearningLogin
  },
  {
    path: '/mall-login',
    name: 'MallLogin',
    component: MallLogin
  },
  {
    path: '/board',
    name: 'LearningBoard',
    component: LearningBoard
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
