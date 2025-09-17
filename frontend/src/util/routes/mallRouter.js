import MallLogin from '/src/views/mall/login/MallLogin.vue'

const mallRoutes = [
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

export default mallRoutes
