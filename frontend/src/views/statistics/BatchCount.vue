<template>
  <div class="batch-count">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>班级批次录取统计</span>
          <el-button type="primary" @click="fetchBatchCount">刷新</el-button>
        </div>
      </template>

      <!-- 统计表格 -->
      <el-table
        :data="batchCountList"
        border
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="className" label="班级" width="120" />
        <el-table-column prop="admissionBatch" label="录取批次" width="120" />
        <el-table-column prop="count" label="录取人数" width="120" />
      </el-table>

      <!-- 统计图表 -->
      <el-row :gutter="20" style="margin-top: 30px;">
        <el-col :span="12">
          <el-card>
            <template #header>
              <div class="card-header">
                <span>各批次录取人数分布</span>
              </div>
            </template>
            <el-chart
              :option="batchChartOption"
              style="height: 300px;"
              autoresize
            />
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card>
            <template #header>
              <div class="card-header">
                <span>各班级录取人数分布</span>
              </div>
            </template>
            <el-chart
              :option="classChartOption"
              style="height: 300px;"
              autoresize
            />
          </el-card>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getBatchCount } from '../../api/statistics'

// 数据
const batchCountList = ref([])
const loading = ref(false)

// 图表配置
const batchChartOption = ref({})
const classChartOption = ref({})

// 获取统计信息
const fetchBatchCount = async () => {
  try {
    loading.value = true
    const response = await getBatchCount()

    batchCountList.value = response || []

    // 设置图表数据
    setChartData(response || [])
  } catch (error) {
    console.error('获取统计信息失败:', error)
    ElMessage.error('获取统计信息失败')
  } finally {
    loading.value = false
  }
}

// 设置图表数据
const setChartData = (data) => {
  // 按批次分类数据
  const batchData = {}
  data.forEach(item => {
    if (!batchData[item.admissionBatch]) {
      batchData[item.admissionBatch] = 0
    }
    batchData[item.admissionBatch] += item.count
  })

  // 按班级分类数据
  const classData = {}
  data.forEach(item => {
    if (!classData[item.className]) {
      classData[item.className] = 0
    }
    classData[item.className] += item.count
  })

  // 批次图表配置
  batchChartOption.value = {
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
        data: Object.keys(batchData),
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
        data: Object.values(batchData)
      }
    ]
  }

  // 班级图表配置
  classChartOption.value = {
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
        data: Object.entries(classData).map(([className, count]) => ({
          value: count,
          name: className
        })),
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }
}

// 初始化加载数据
onMounted(() => {
  fetchBatchCount()
})
</script>

<style scoped>
.batch-count {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>