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
  
  // Learning Routes
  {
    path: '/learning',
    children: [
      {
        path: 'login',
        name: 'LearningLogin',
        component: LearningLogin
      },
      {
        path: 'board',
        name: 'LearningBoard',
        component: LearningBoard
      }
    ]
  },
  
  // Mall Routes
  {
    path: '/mall',
    children: [
      {
        path: 'login',
        name: 'MallLogin',
        component: MallLogin
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
