import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/Login'
import AdminProfile from "@/views/dashboard/AdminProfile";

const routes = [
  {
    path: '/',
    name: 'Home',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '@/views/Home.vue')
  },

  {
    path: '/login',
    name: 'Login',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: Login
  },

  {
    path: '/DashSample',
    name: 'DashSample',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '@/views/dashboard/DashSample.vue')
  },

  {
    path: '/ChangePassword',
    name: 'ChangePassword',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '@/views/dashboard/ChangePassword.vue')
  },

  {
    path:'/Register',
    name:'Register',
    component:() => import('@/views/Register.vue')
  },
  {
    path:'/AdminProfile',
    name:'AdminProfile',
    component:AdminProfile
  },
  {
    path:'/MemberProfile',
    name:'MemberProfile',
    component:() => import('@/views/dashboard/MemberProfile.vue')
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
