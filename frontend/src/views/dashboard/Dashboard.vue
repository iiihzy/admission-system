<template>
  <div class="dashboard">
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="card-content">
            <i class="el-icon-user-solid card-icon"></i>
            <div class="card-info">
              <p class="card-title">学生总数</p>
              <p class="card-value">{{ studentCount }}</p>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="card-content">
            <i class="el-icon-school card-icon"></i>
            <div class="card-info">
              <p class="card-title">院校总数</p>
              <p class="card-value">{{ universityCount }}</p>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="card-content">
            <i class="el-icon-document-add card-icon"></i>
            <div class="card-info">
              <p class="card-title">录取总数</p>
              <p class="card-value">{{ admissionCount }}</p>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="card-content">
            <i class="el-icon-trend-charts card-icon"></i>
            <div class="card-info">
              <p class="card-title">平均成绩</p>
              <p class="card-value">{{ averageScore }}分</p>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>各批次录取人数统计</span>
            </div>
          </template>
          <div ref="batchChartRef" style="height: 300px;"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>各班级录取情况</span>
            </div>
          </template>
          <div ref="classChartRef" style="height: 300px;"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { ElCard, ElMessage } from 'element-plus'
import * as echarts from 'echarts'
import { getOverview, getBatchCount } from '../../api/statistics'
import { getStudents } from '../../api/student'

// 统计数据
const studentCount = ref(0)
const universityCount = ref(0)
const admissionCount = ref(0)
const averageScore = ref(0)

// 图表实例
const batchChartRef = ref(null)
const classChartRef = ref(null)
let batchChart = null
let classChart = null

// 获取统计数据
const fetchStatistics = async () => {
  try {
    // 获取概览数据
    const overview = await getOverview()

    studentCount.value = overview.totalStudents || 0
    universityCount.value = overview.totalUniversities || 0
    admissionCount.value = overview.totalAdmissions || 0

    // 计算平均成绩
    const studentsResponse = await getStudents({ current: 1, size: 1000 })
    const students = studentsResponse.records || []
    if (students.length > 0) {
      const totalScore = students.reduce((sum, student) => sum + (student.gaokaoScore || 0), 0)
      averageScore.value = (totalScore / students.length).toFixed(1)
    }

    // 获取批次统计数据
    const batchData = await getBatchCount()

    // 处理图表数据
    const batchCounts = {}
    const classCounts = {}

    batchData.forEach(item => {
      // 批次统计
      if (!batchCounts[item.admissionBatch]) {
        batchCounts[item.admissionBatch] = 0
      }
      batchCounts[item.admissionBatch] += item.count

      // 班级统计
      if (!classCounts[item.className]) {
        classCounts[item.className] = 0
      }
      classCounts[item.className] += item.count
    })

    // 初始化图表
    if (batchChartRef.value) {
      batchChart = echarts.init(batchChartRef.value)
    }
    if (classChartRef.value) {
      classChart = echarts.init(classChartRef.value)
    }

    // 批次图表配置
    const batchOption = {
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      legend: {
        data: ['录取人数']
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: [
        {
          type: 'category',
          data: Object.keys(batchCounts),
          axisTick: {
            alignWithLabel: true
          }
        }
      ],
      yAxis: [
        {
          type: 'value'
        }
      ],
      series: [
        {
          name: '录取人数',
          type: 'bar',
          barWidth: '60%',
          data: Object.values(batchCounts),
          itemStyle: {
            color: '#409EFF'
          }
        }
      ]
    }

    // 班级图表配置
    const classOption = {
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b}: {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: '班级分布',
          type: 'pie',
          radius: '50%',
          data: Object.entries(classCounts).map(([className, count]) => ({
            value: count,
            name: className
          })),
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          },
          itemStyle: {
            color: function(params) {
              const colors = ['#409EFF', '#67C23A', '#E6A23C', '#F56C6C', '#909399']
              return colors[params.dataIndex % colors.length]
            }
          }
        }
      ]
    }

    // 更新图表
    if (batchChart) {
      batchChart.setOption(batchOption)
    }
    if (classChart) {
      classChart.setOption(classOption)
    }
  } catch (error) {
    console.error('获取统计数据失败:', error)
    ElMessage.error('获取统计数据失败')
  }
}

// 处理窗口大小变化
const handleResize = () => {
  if (batchChart) {
    batchChart.resize()
  }
  if (classChart) {
    classChart.resize()
  }
}

onMounted(() => {
  fetchStatistics()
  window.addEventListener('resize', handleResize)
})

// 组件卸载时清理
onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  if (batchChart) {
    batchChart.dispose()
  }
  if (classChart) {
    classChart.dispose()
  }
})
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.stat-card {
  height: 120px;
}

.card-content {
  display: flex;
  align-items: center;
}

.card-icon {
  font-size: 48px;
  color: #409EFF;
  margin-right: 20px;
}

.card-info {
  flex: 1;
}

.card-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 5px;
}

.card-value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

</style>