import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'
import { useUserStore } from '../store/user'

// 路由守卫
const checkAuth = (to, from, next) => {
  const userStore = useUserStore()

  if (!to.meta.requiresAuth) {
    next()
    return
  }

  if (!userStore.isAuthenticated) {
    next('/login')
    return
  }

  next()
}

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/login/Login.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/login/Register.vue'),
    meta: { title: '注册' }
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    meta: { requiresAuth: true },
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('../views/dashboard/Dashboard.vue'),
        meta: {
          title: '首页仪表盘',
          requiresAuth: true
        }
      },
      {
        path: 'student',
        name: 'Student',
        meta: {
          title: '学生管理',
          requiresAuth: true
        },
        children: [
          {
            path: '',
            name: 'StudentList',
            component: () => import('../views/student/List.vue'),
            meta: { subtitle: '学生列表' }
          },
          {
            path: 'add',
            name: 'StudentAdd',
            component: () => import('../views/student/Add.vue'),
            meta: { subtitle: '添加学生' }
          },
          {
            path: 'edit/:examNumber',
            name: 'StudentEdit',
            component: () => import('../views/student/Edit.vue'),
            meta: { subtitle: '编辑学生' },
            props: true
          }
        ]
      },
      {
        path: 'university',
        name: 'University',
        meta: {
          title: '院校管理',
          requiresAuth: true
        },
        children: [
          {
            path: '',
            name: 'UniversityList',
            component: () => import('../views/university/List.vue'),
            meta: { subtitle: '院校列表' }
          },
          {
            path: 'add',
            name: 'UniversityAdd',
            component: () => import('../views/university/Add.vue'),
            meta: { subtitle: '添加院校' }
          },
          {
            path: 'edit/:id',
            name: 'UniversityEdit',
            component: () => import('../views/university/Edit.vue'),
            meta: { subtitle: '编辑院校' },
            props: true
          }
        ]
      },
      {
        path: 'admission',
        name: 'Admission',
        meta: {
          title: '录取管理',
          requiresAuth: true
        },
        children: [
          {
            path: '',
            name: 'AdmissionList',
            component: () => import('../views/admission/List.vue'),
            meta: { subtitle: '录取列表' }
          },
          {
            path: 'add',
            name: 'AdmissionAdd',
            component: () => import('../views/admission/Add.vue'),
            meta: { subtitle: '添加录取' }
          },
          {
            path: 'edit/:id',
            name: 'AdmissionEdit',
            component: () => import('../views/admission/Edit.vue'),
            meta: { subtitle: '编辑录取' },
            props: true
          }
        ]
      },
      {
        path: 'statistics',
        name: 'Statistics',
        meta: {
          title: '统计分析',
          requiresAuth: true
        },
        children: [
          {
            path: 'batch-count',
            name: 'BatchCount',
            component: () => import('../views/statistics/BatchCount.vue'),
            meta: { subtitle: '批次统计' }
          },
          {
            path: 'student-view',
            name: 'StudentView',
            component: () => import('../views/statistics/StudentView.vue'),
            meta: { subtitle: '学生视图' }
          }
        ]
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 全局前置守卫
router.beforeEach(checkAuth)

export default router