<template>
  <div class="admission-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>录取信息管理</span>
          <el-button
            type="primary"
            @click="handleAdd"
            icon="Plus"
          >
            新增录取
          </el-button>
        </div>
      </template>

      <!-- 搜索表单 -->
      <SearchForm
        :initial-form="searchForm"
        @search="handleSearch"
        @reset="handleReset"
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
        <el-form-item label="院校名称">
          <el-input
            v-model="searchForm.universityName"
            placeholder="请输入院校名称"
          />
        </el-form-item>
      </SearchForm>

      <!-- 录取表格 -->
      <el-table
        :data="admissionList"
        border
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="examNumber" label="考号" width="120" />
        <el-table-column prop="name" label="姓名" width="100" />
        <el-table-column prop="className" label="班级" width="100" />
        <el-table-column prop="universityName" label="录取院校" />
        <el-table-column prop="admissionBatch" label="录取批次" width="120" />
        <el-table-column prop="gaokaoScore" label="高考成绩" width="100" />
        <el-table-column prop="admissionTime" label="录取时间" width="180" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button
              size="small"
              @click="handleEdit(scope.row.id)"
            >
              编辑
            </el-button>
            <el-popconfirm
              title="确定要删除这条记录吗？"
              @confirm="handleDelete(scope.row.id)"
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
import { getAdmissions, deleteAdmission } from '../../api/admission'
import SearchForm from '../../components/common/SearchForm.vue'
import Pagination from '../../components/common/Pagination.vue'

const router = useRouter()

// 数据
const admissionList = ref([])
const loading = ref(false)
const searchForm = ref({
  admissionBatch: '',
  universityName: ''
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

// 获取录取列表
const fetchAdmissions = async () => {
  try {
    loading.value = true
    const params = {
      current: currentPage.value,
      size: pageSize.value,
      ...searchForm.value
    }

    const response = await getAdmissions(params)
    admissionList.value = response.records || []
    total.value = response.total || 0
  } catch (error) {
    console.error('获取录取列表失败:', error)
    ElMessage.error('获取录取列表失败')
  } finally {
    loading.value = false
  }
}

// 处理搜索
const handleSearch = (form) => {
  currentPage.value = 1
  fetchAdmissions()
}

// 处理重置
const handleReset = (form) => {
  currentPage.value = 1
  fetchAdmissions()
}

// 处理分页改变
const handlePageChange = (page) => {
  currentPage.value = page
  fetchAdmissions()
}

// 处理每页大小改变
const handleSizeChange = (size) => {
  pageSize.value = size
  currentPage.value = 1
  fetchAdmissions()
}

// 处理新增
const handleAdd = () => {
  router.push('/admissions/add')
}

// 处理编辑
const handleEdit = (id) => {
  router.push(`/admissions/edit/${id}`)
}

// 处理删除
const handleDelete = async (id) => {
  try {
    await deleteAdmission(id)
    ElMessage.success('删除成功')
    fetchAdmissions()
  } catch (error) {
    console.error('删除录取信息失败:', error)
    ElMessage.error('删除失败')
  }
}

// 初始化加载数据
onMounted(() => {
  fetchAdmissions()
})
</script>

<style scoped>
.admission-list {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>