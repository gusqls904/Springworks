import Login from '/src/views/admin/login/Login.vue'
import Main from '/src/views/admin/MainLayout.vue'

const adminRoutes = [
  {
    path: '/admin',
    children: [
      {
        path: 'login',
        name: 'Login',
        component: Login
      },
      {
        path: 'main',
        name: 'Main',
        component: Main
      }
    ]
  }
]

export default adminRoutes
