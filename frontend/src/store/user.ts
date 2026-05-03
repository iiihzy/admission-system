import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login, logout, register, checkUsername, type LoginForm, type RegisterForm } from '../api/auth'
import { ElMessage } from 'element-plus'
import router from '../router'

export interface UserInfo {
  username: string
  realName: string
  role: string
}

export const useUserStore = defineStore('user', () => {
  // State
  const token = ref<string>(localStorage.getItem('token') || '')
  const userInfo = ref<UserInfo | null>(null)
  const isAuthenticated = computed(() => !!token.value)

  // Actions
  const setToken = (newToken: string) => {
    token.value = newToken
    localStorage.setItem('token', newToken)
  }

  const setUserInfo = (info: UserInfo) => {
    userInfo.value = info
  }

  const clearAuth = () => {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
  }

  const handleLogin = async (loginForm: LoginForm) => {
    try {
      const response = await login(loginForm)
      setToken(response.token)
      setUserInfo({
        username: response.username,
        realName: response.realName,
        role: response.role
      })
      ElMessage.success('登录成功')
      return true
    } catch (error) {
      console.error('登录失败:', error)
      return false
    }
  }

  const handleRegister = async (registerForm: RegisterForm) => {
    try {
      await register(registerForm)
      ElMessage.success('注册成功，请登录')
      router.push('/login')
      return true
    } catch (error) {
      console.error('注册失败:', error)
      return false
    }
  }

  const handleLogout = async () => {
    try {
      await logout()
    } catch (error) {
      console.error('登出请求失败:', error)
    } finally {
      clearAuth()
      ElMessage.success('已退出登录')
      router.push('/login')
    }
  }

  const checkUsernameAvailable = async (username: string) => {
    try {
      await checkUsername(username)
      return true
    } catch (error) {
      return false
    }
  }

  return {
    // State
    token,
    userInfo,
    isAuthenticated,

    // Getters
    username: computed(() => userInfo.value?.username || ''),
    realName: computed(() => userInfo.value?.realName || ''),
    role: computed(() => userInfo.value?.role || ''),

    // Actions
    setToken,
    setUserInfo,
    clearAuth,
    handleLogin,
    handleRegister,
    handleLogout,
    checkUsernameAvailable
  }
})