import request from '../utils/request'
import { searchStudents } from './student'
import { searchUniversities } from './university'

export interface Admission {
  id?: number
  examNumber: string
  name: string
  universityId: string
  universityName: string
  admissionBatch: string
  admissionTime?: string
  createTime?: string
  updateTime?: string
  className?: string
  gaokaoScore?: number
}

export interface AdmissionSearchParams {
  admissionBatch?: string
  universityName?: string
}

// 获取录取列表（分页，包含学生信息）
export const getAdmissions = (params: {
  current: number
  size: number
} & AdmissionSearchParams = { current: 1, size: 10 }) => {
  return request.get('/admissions', { params })
}

// 添加录取
export const addAdmission = (data: Admission) => {
  return request.post('/admissions', data)
}

// 更新录取
export const updateAdmission = (id: number, data: Partial<Admission>) => {
  return request.put(`/admissions/${id}`, data)
}

// 删除录取
export const deleteAdmission = (id: number) => {
  return request.delete(`/admissions/${id}`)
}

// 搜索录取
export const searchAdmissions = (params: AdmissionSearchParams) => {
  return request.get('/admissions/search', { params })
}

// 获取批次学生列表
export const getBatchStudents = () => {
  return request.get('/admissions/batch-students')
}

// 重新导出搜索函数
export { searchStudents, searchUniversities }