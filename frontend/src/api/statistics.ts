import request from '../utils/request'

export interface BatchCountItem {
  className: string
  admissionBatch: string
  count: number
}

export interface OverviewStats {
  totalStudents: number
  totalUniversities: number
  totalAdmissions: number
}

// 获取批次统计
export const getBatchCount = () => {
  return request.get<BatchCountItem[]>('/statistics/batch-count')
}

// 获取概览统计
export const getOverview = () => {
  return request.get<OverviewStats>('/statistics/overview')
}

// 获取批次学生视图
export const getBatchStudents = (params?: {
  page?: number
  size?: number
  admissionBatch?: string
  className?: string
}) => {
  return request.get('/statistics/batch-students', { params })
}