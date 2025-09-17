import Board from '/src/views/learning/board/Board.vue'
import Login from '/src/views/learning/login/Login.vue'
import Main from '/src/views/learning/MainLayout.vue'

const learningRoutes = [
  {
    path: '/learning',
    children: [
      {
        path: 'login',
        name: 'Login',
        component: Login
      },
      {
        path: 'board',
        name: 'Board',
        component: Board
      },
      {
        path: 'main',
        name: 'Main',
        component: Main
      }
    ]
  }
]

export default learningRoutes
