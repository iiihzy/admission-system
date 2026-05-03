// 存储token
export function setToken(token) {
  localStorage.setItem('token', token)
}

// 获取token
export function getToken() {
  return localStorage.getItem('token')
}

// 清除token
export function removeToken() {
  localStorage.removeItem('token')
}

// 存储用户信息
export function setUserInfo(userInfo) {
  localStorage.setItem('userInfo', JSON.stringify(userInfo))
}

// 获取用户信息
export function getUserInfo() {
  const userInfo = localStorage.getItem('userInfo')
  return userInfo ? JSON.parse(userInfo) : null
}

// 清除用户信息
export function removeUserInfo() {
  localStorage.removeItem('userInfo')
}