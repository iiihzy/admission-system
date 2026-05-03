<template>
  <div class="admission-edit">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>编辑录取信息</span>
          <el-button @click="handleBack">返回</el-button>
        </div>
      </template>

      <el-form
        :model="admissionForm"
        :rules="rules"
        ref="formRef"
        label-width="120px"
        style="max-width: 600px"
      >
        <el-form-item label="考号" prop="examNumber">
          <el-input
            v-model="admissionForm.examNumber"
            placeholder="请输入考号"
            disabled
          />
        </el-form-item>

        <el-form-item label="姓名" prop="name">
          <el-input
            v-model="admissionForm.name"
            placeholder="请输入姓名"
            disabled
          />
        </el-form-item>

        <el-form-item label="录取院校编号" prop="universityId">
          <el-select
            v-model="admissionForm.universityId"
            placeholder="请选择院校编号"
            filterable
            remote
            :remote-method="fetchUniversities"
            :loading="universitiesLoading"
          >
            <el-option
              v-for="university in universities"
              :key="university.universityId"
              :label="`${university.universityId} - ${university.universityName}`"
              :value="university.universityId"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="录取院校名称" prop="universityName">
          <el-input
            v-model="admissionForm.universityName"
            placeholder="请输入院校名称"
            disabled
          />
        </el-form-item>

        <el-form-item label="录取批次" prop="admissionBatch">
          <el-select
            v-model="admissionForm.admissionBatch"
            placeholder="请选择录取批次"
          >
            <el-option
              v-for="item in batchOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit">更新</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import request from '../../utils/request'

const router = useRouter()
const route = useRoute()
const formRef = ref()

// 录取批次选项
const batchOptions = [
  { value: '提前批', label: '提前批' },
  { value: '本科一批', label: '本科一批' },
  { value: '本科二批', label: '本科二批' },
  { value: '专科批', label: '专科批' },
  { value: '其他', label: '其他' }
]

const admissionForm = ref({
  id: null,
  examNumber: '',
  name: '',
  universityId: '',
  universityName: '',
  admissionBatch: ''
})

const rules = {
  universityId: [
    { required: true, message: '请选择院校编号', trigger: 'change' }
  ],
  admissionBatch: [
    { required: true, message: '请选择录取批次', trigger: 'change' }
  ]
}

// 院校数据
const universities = ref([])
const universitiesLoading = ref(false)

// 获取院校列表（用于远程搜索）
const fetchUniversities = async (query) => {
  if (!query) {
    universities.value = []
    return
  }

  try {
    universitiesLoading.value = true
    const response = await request.get('/universities/search', {
      params: { universityName: query }
    })
    universities.value = response
  } catch (error) {
    console.error('获取院校列表失败:', error)
  } finally {
    universitiesLoading.value = false
  }
}

// 获取录取详情
const fetchAdmission = async (id) => {
  try {
    const response = await request.get(`/admissions/${id}`)
    admissionForm.value = response
  } catch (error) {
    console.error('获取录取信息失败:', error)
    ElMessage.error('获取录取信息失败')
  }
}

// 当选择院校时，自动填充院校名称
const handleUniversityChange = (value) => {
  if (value) {
    const university = universities.value.find(u => u.universityId === value)
    if (university) {
      admissionForm.value.universityName = university.universityName
    }
  }
}

const handleSubmit = async () => {
  try {
    await formRef.value.validate()

    await request.put(`/admissions/${admissionForm.value.id}`, admissionForm.value)
    ElMessage.success('更新成功')
    router.push('/admissions')
  } catch (error) {
    console.error('更新录取信息失败:', error)
    ElMessage.error('更新失败')
  }
}

const handleReset = () => {
  formRef.value.resetFields()
}

const handleBack = () => {
  router.go(-1)
}

// 初始化加载数据
onMounted(() => {
  const id = route.params.id
  if (id) {
    fetchAdmission(id)
  }
})
</script>

<style scoped>
.admission-edit {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>