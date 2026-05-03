import axios from 'axios'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../store'
import router from '../router'

// 创建axios实例
const service = axios.create({
  baseURL: '/api', // 基础URL，通过vite代理
  timeout: 10000 // 请求超时时间
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    const userStore = useUserStore()
    const token = userStore.token
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  error => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    const res = response.data

    // 根据后端返回的状态码进行处理
    if (res.code === 200) {
      return res.data
    } else {
      ElMessage({
        message: res.message || '请求失败',
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(new Error(res.message || '请求失败'))
    }
  },
  error => {
    console.error('响应错误:', error)

    if (error.response?.status === 401) {
      // Token过期或无效，清除本地存储并跳转到登录页
      const userStore = useUserStore()
      userStore.logout()
      router.push('/login')
      ElMessage({
        message: '登录已过期，请重新登录',
        type: 'error'
      })
    } else {
      const errorMessage = error.response?.data?.message || error.message || '网络错误'
      ElMessage({
        message: errorMessage,
        type: 'error',
        duration: 5 * 1000
      })
    }

    return Promise.reject(error)
  }
)

export default service