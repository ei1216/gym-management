import { createRouter, createWebHistory } from 'vue-router'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: () => import('@/views/MainView.vue'),
      children: [
        {
          path: '/equipment',
          name: 'equipment',
          component: () => import('@/views/EquipmentView.vue'),
        },
        {
          path: '/coach',
          name: 'coach',
          component: () => import('@/views/CoachView.vue'),
        },
        {
          path: '/member',
          name: 'member',
          component: () => import('@/views/MemberView.vue'),
        },
        {
          path: '/course',
          name: 'course',
          component: () => import('@/views/CourseView.vue'),
        },
        {
          path: '/courseRegistration',
          name: 'courseRegistration',
          component: () => import('@/views/CourseRegistrationView.vue'),
        },
      ],
      redirect: '/equipment'   //当访问'/'时重定向到'/equipment'

    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue'),

    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/RegisterView.vue'),

    }

  ]
})



export default router
