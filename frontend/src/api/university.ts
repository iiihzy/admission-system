import request from '../utils/request'

export interface University {
  universityId: string
  universityName: string
  address: string
  createTime?: string
  updateTime?: string
}

export interface UniversitySearchParams {
  universityName?: string
  address?: string
}

// 获取院校列表（分页）
export const getUniversities = (params: {
  current: number
  size: number
} & UniversitySearchParams = { current: 1, size: 10 }) => {
  return request.get('/universities', { params })
}

// 添加院校
export const addUniversity = (data: University) => {
  return request.post('/universities', data)
}

// 更新院校
export const updateUniversity = (universityId: string, data: Partial<University>) => {
  return request.put(`/universities/${universityId}`, data)
}

// 删除院校
export const deleteUniversity = (universityId: string) => {
  return request.delete(`/universities/${universityId}`)
}

// 搜索院校
export const searchUniversities = (params: UniversitySearchParams) => {
  return request.get('/universities/search', { params })
}