<template>
  <div class="student-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>学生信息管理</span>
          <el-button
            type="primary"
            @click="handleAdd"
            icon="Plus"
          >
            新增学生
          </el-button>
        </div>
      </template>

      <!-- 搜索表单 -->
      <SearchForm
        :initial-form="searchForm"
        @search="handleSearch"
        @reset="handleReset"
      >
        <el-form-item label="姓名">
          <el-input
            v-model="searchForm.name"
            placeholder="请输入姓名"
          />
        </el-form-item>
        <el-form-item label="班级">
          <el-input
            v-model="searchForm.className"
            placeholder="请输入班级"
          />
        </el-form-item>
        <el-form-item label="高考成绩">
          <el-input-number
            v-model="searchForm.minScore"
            placeholder="最低成绩"
            controls-position="right"
            :min="0"
            :max="750"
          />
          -
          <el-input-number
            v-model="searchForm.maxScore"
            placeholder="最高成绩"
            controls-position="right"
            :min="0"
            :max="750"
          />
        </el-form-item>
      </SearchForm>

      <!-- 学生表格 -->
      <el-table
        :data="studentList"
        border
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="examNumber" label="考号" width="120" />
        <el-table-column prop="name" label="姓名" width="100" />
        <el-table-column prop="gender" label="性别" width="80" />
        <el-table-column prop="birthDate" label="出生年月" width="120" />
        <el-table-column prop="className" label="班级" width="100" />
        <el-table-column prop="nativePlace" label="籍贯" />
        <el-table-column prop="gaokaoScore" label="高考成绩" width="100" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button
              size="small"
              @click="handleEdit(scope.row.examNumber)"
            >
              编辑
            </el-button>
            <el-popconfirm
              title="确定要删除这条记录吗？"
              @confirm="handleDelete(scope.row.examNumber)"
            >
              <template #reference>
                <el-button size="small" type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
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
import { useRouter } from 'vue-router'
import { ElMessage, ElPopconfirm } from 'element-plus'
import { getStudents, deleteStudent } from '../../api/student'
import SearchForm from '../../components/common/SearchForm.vue'
import Pagination from '../../components/common/Pagination.vue'

const router = useRouter()

// 数据
const studentList = ref([])
const loading = ref(false)
const searchForm = ref({
  name: '',
  className: '',
  minScore: null,
  maxScore: null
})

// 分页数据
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 获取学生列表
const fetchStudents = async () => {
  try {
    loading.value = true
    const params = {
      current: currentPage.value,
      size: pageSize.value,
      ...searchForm.value
    }

    const response = await getStudents(params)
    studentList.value = response.records || []
    total.value = response.total || 0
  } catch (error) {
    console.error('获取学生列表失败:', error)
    ElMessage.error('获取学生列表失败')
  } finally {
    loading.value = false
  }
}

// 处理搜索
const handleSearch = (form) => {
  currentPage.value = 1
  fetchStudents()
}

// 处理重置
const handleReset = (form) => {
  currentPage.value = 1
  fetchStudents()
}

// 处理分页改变
const handlePageChange = (page) => {
  currentPage.value = page
  fetchStudents()
}

// 处理每页大小改变
const handleSizeChange = (size) => {
  pageSize.value = size
  currentPage.value = 1
  fetchStudents()
}

// 处理新增
const handleAdd = () => {
  router.push('/students/add')
}

// 处理编辑
const handleEdit = (examNumber) => {
  router.push(`/students/edit/${examNumber}`)
}

// 处理删除
const handleDelete = async (examNumber) => {
  try {
    await deleteStudent(examNumber)
    ElMessage.success('删除成功')
    fetchStudents()
  } catch (error) {
    console.error('删除学生失败:', error)
    ElMessage.error('删除失败')
  }
}

// 初始化加载数据
onMounted(() => {
  fetchStudents()
})
</script>

<style scoped>
.student-list {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>