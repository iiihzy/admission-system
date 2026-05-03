import request from '../utils/request'

export interface LoginForm {
  username: string
  password: string
}

export interface LoginResponse {
  token: string
  username: string
  realName: string
  role: string
}

export interface RegisterForm {
  username: string
  password: string
  realName: string
  role?: string
}

// 登录
export const login = (data: LoginForm) => {
  return request.post<LoginResponse>('/auth/login', data)
}

// 注册
export const register = (data: RegisterForm) => {
  return request.post('/auth/register', data)
}

// 登出
export const logout = () => {
  return request.post('/auth/logout')
}

// 检查用户名是否可用
export const checkUsername = (username: string) => {
  return request.get(`/auth/check-username/${username}`)
}