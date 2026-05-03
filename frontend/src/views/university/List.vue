<template>
  <div class="university-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>院校信息管理</span>
          <el-button
            type="primary"
            @click="handleAdd"
            icon="Plus"
          >
            新增院校
          </el-button>
        </div>
      </template>

      <!-- 搜索表单 -->
      <SearchForm
        :initial-form="searchForm"
        @search="handleSearch"
        @reset="handleReset"
      >
        <el-form-item label="院校名称">
          <el-input
            v-model="searchForm.universityName"
            placeholder="请输入院校名称"
          />
        </el-form-item>
        <el-form-item label="地址">
          <el-input
            v-model="searchForm.address"
            placeholder="请输入地址"
          />
        </el-form-item>
      </SearchForm>

      <!-- 院校表格 -->
      <el-table
        :data="universityList"
        border
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="universityId" label="院校编号" width="150" />
        <el-table-column prop="universityName" label="院校名称" />
        <el-table-column prop="address" label="地址" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button
              size="small"
              @click="handleEdit(scope.row.universityId)"
            >
              编辑
            </el-button>
            <el-popconfirm
              title="确定要删除这条记录吗？"
              @confirm="handleDelete(scope.row.universityId)"
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
import { getUniversities, deleteUniversity } from '../../api/university'
import SearchForm from '../../components/common/SearchForm.vue'
import Pagination from '../../components/common/Pagination.vue'

const router = useRouter()

// 数据
const universityList = ref([])
const loading = ref(false)
const searchForm = ref({
  universityName: '',
  address: ''
})

// 分页数据
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 获取院校列表
const fetchUniversities = async () => {
  try {
    loading.value = true
    const params = {
      current: currentPage.value,
      size: pageSize.value,
      ...searchForm.value
    }

    const response = await getUniversities(params)
    universityList.value = response.records || []
    total.value = response.total || 0
  } catch (error) {
    console.error('获取院校列表失败:', error)
    ElMessage.error('获取院校列表失败')
  } finally {
    loading.value = false
  }
}

// 处理搜索
const handleSearch = (form) => {
  currentPage.value = 1
  fetchUniversities()
}

// 处理重置
const handleReset = (form) => {
  currentPage.value = 1
  fetchUniversities()
}

// 处理分页改变
const handlePageChange = (page) => {
  currentPage.value = page
  fetchUniversities()
}

// 处理每页大小改变
const handleSizeChange = (size) => {
  pageSize.value = size
  currentPage.value = 1
  fetchUniversities()
}

// 处理新增
const handleAdd = () => {
  router.push('/universities/add')
}

// 处理编辑
const handleEdit = (id) => {
  router.push(`/universities/edit/${id}`)
}

// 处理删除
const handleDelete = async (id) => {
  try {
    await deleteUniversity(id)
    ElMessage.success('删除成功')
    fetchUniversities()
  } catch (error) {
    console.error('删除院校失败:', error)
    ElMessage.error('删除失败')
  }
}

// 初始化加载数据
onMounted(() => {
  fetchUniversities()
})
</script>

<style scoped>
.university-list {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>