import request from '../utils/request'

export interface Student {
  examNumber: string
  name: string
  gender: string
  birthDate: string
  className: string
  nativePlace: string
  gaokaoScore: number
  createTime?: string
  updateTime?: string
}

export interface StudentSearchParams {
  name?: string
  className?: string
  minScore?: number
  maxScore?: number
}

// 获取学生列表（分页）
export const getStudents = (params: {
  current: number
  size: number
} & StudentSearchParams = { current: 1, size: 10 }) => {
  return request.get('/students', { params })
}

// 添加学生
export const addStudent = (data: Student) => {
  return request.post('/students', data)
}

// 更新学生
export const updateStudent = (examNumber: string, data: Partial<Student>) => {
  return request.put(`/students/${examNumber}`, data)
}

// 删除学生
export const deleteStudent = (examNumber: string) => {
  return request.delete(`/students/${examNumber}`)
}

// 搜索学生
export const searchStudents = (params: StudentSearchParams) => {
  return request.get('/students/search', { params })
}