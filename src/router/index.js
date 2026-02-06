import Vue from 'vue'
import VueRouter from 'vue-router'

import HomeView from '../views/HomeView.vue'
import RedesView from '@/views/redesView.vue'
import LecturasView from '@/views/LecturasView.vue'
import ContactoView from '@/views/ContactoView.vue'
import AdminView from '@/views/AdminView.vue' // 🔐 NUEVO

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/blog',
    name: 'about',
    component: () =>
      import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/redes',
    name: 'RedesView',
    component: RedesView
  },
  {
    path: '/lecturas',
    name: 'LecturasView',
    component: LecturasView
  },
  {
    path: '/contacto',
    name: 'ContactoView',
    component: ContactoView
  },

  /* ================= ADMIN PROTEGIDO ================= */
  {
    path: '/admin',
    name: 'AdminView',
    component: AdminView,
    meta: { requiresAdmin: true }
  }
]

const router = new VueRouter({
  mode: 'history',
  routes,
  scrollBehavior () {
    return { x: 0, y: 0 }
  }
})

/* ================= ROUTER GUARD ================= */
router.beforeEach((to, from, next) => {
  if (to.meta.requiresAdmin) {
    const isAdmin = localStorage.getItem('admin') === 'true'

    if (!isAdmin) {
      next('/') // fuera si no eres admin
      return
    }
  }

  next()
})

export default router
