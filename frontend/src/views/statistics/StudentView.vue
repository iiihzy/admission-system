<template>
  <div class="student-view">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>批次学生视图</span>
          <el-button type="primary" @click="fetchStudentView">刷新</el-button>
        </div>
      </template>

      <!-- 搜索表单 -->
      <el-form
        :model="searchForm"
        :inline="true"
        label-width="auto"
        class="search-form"
      >
        <el-form-item label="录取批次">
          <el-select
            v-model="searchForm.admissionBatch"
            placeholder="请选择录取批次"
            clearable
          >
            <el-option
              v-for="item in batchOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="班级">
          <el-input
            v-model="searchForm.className"
            placeholder="请输入班级"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 学生视图表表 -->
      <el-table
        :data="studentViewList"
        border
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="examNumber" label="考号" width="120" />
        <el-table-column prop="name" label="姓名" width="100" />
        <el-table-column prop="gender" label="性别" width="80" />
        <el-table-column prop="className" label="班级" width="100" />
        <el-table-column prop="universityName" label="录取院校" />
        <el-table-column prop="gaokaoScore" label="高考成绩" width="100" />
        <el-table-column prop="admissionBatch" label="录取批次" width="120" />
      </el-table>

      <!-- 分页 -->
      <Pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        @current-change="handlePageChange"
        @size-change="handleSizeChange"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getBatchStudents } from '../../api/statistics'
import Pagination from '../../components/common/Pagination.vue'

// 数据
const studentViewList = ref([])
const loading = ref(false)
const searchForm = ref({
  admissionBatch: '',
  className: ''
})

// 录取批次选项
const batchOptions = [
  { value: '提前批', label: '提前批' },
  { value: '本科一批', label: '本科一批' },
  { value: '本科二批', label: '本科二批' },
  { value: '专科批', label: '专科批' },
  { value: '其他', label: '其他' }
]

// 分页数据
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 获取学生视图数据
const fetchStudentView = async () => {
  try {
    loading.value = true
    const params = {
      page: currentPage.value,
      size: pageSize.value,
      admissionBatch: searchForm.value.admissionBatch,
      className: searchForm.value.className
    }

    const response = await getBatchStudents(params)
    studentViewList.value = response || []
    total.value = response.length || 0
  } catch (error) {
    console.error('获取学生视图数据失败:', error)
    ElMessage.error('获取学生视图数据失败')
  } finally {
    loading.value = false
  }
}

// 处理搜索
const handleSearch = () => {
  currentPage.value = 1
  fetchStudentView()
}

// 处理重置
const handleReset = () => {
  searchForm.value = {
    admissionBatch: '',
    className: ''
  }
  currentPage.value = 1
  fetchStudentView()
}

// 处理分页改变
const handlePageChange = (page) => {
  currentPage.value = page
  fetchStudentView()
}

// 处理每页大小改变
const handleSizeChange = (size) => {
  pageSize.value = size
  currentPage.value = 1
  fetchStudentView()
}

// 初始化加载数据
onMounted(() => {
  fetchStudentView()
})
</script>

<style scoped>
.student-view {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  padding: 20px;
  background-color: #fff;
  border-radius: 4px;
  margin-bottom: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>