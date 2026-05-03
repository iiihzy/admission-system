<template>
  <div class="admission-add">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>新增录取信息</span>
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
          <el-select
            v-model="admissionForm.examNumber"
            placeholder="请选择考生考号"
            filterable
            remote
            :remote-method="fetchStudents"
            :loading="studentsLoading"
            @change="handleStudentChange"
          >
            <el-option
              v-for="student in students"
              :key="student.examNumber"
              :label="`${student.examNumber} - ${student.name}`"
              :value="student.examNumber"
            />
          </el-select>
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
            @change="handleUniversityChange"
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
          <el-button type="primary" @click="handleSubmit">提交</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { addAdmission, searchStudents, searchUniversities } from '../../api/admission'

const router = useRouter()
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
  examNumber: '',
  name: '',
  universityId: '',
  universityName: '',
  admissionBatch: ''
})

const rules = {
  examNumber: [
    { required: true, message: '请选择考生考号', trigger: 'change' }
  ],
  universityId: [
    { required: true, message: '请选择院校编号', trigger: 'change' }
  ],
  admissionBatch: [
    { required: true, message: '请选择录取批次', trigger: 'change' }
  ]
}

// 学生和院校数据
const students = ref([])
const universities = ref([])
const studentsLoading = ref(false)
const universitiesLoading = ref(false)

// 获取学生列表（用于远程搜索）
const fetchStudents = async (query) => {
  if (!query) {
    students.value = []
    return
  }

  try {
    studentsLoading.value = true
    const response = await searchStudents({ name: query })
    students.value = response || []
  } catch (error) {
    console.error('获取学生列表失败:', error)
    students.value = []
  } finally {
    studentsLoading.value = false
  }
}

// 获取院校列表（用于远程搜索）
const fetchUniversities = async (query) => {
  if (!query) {
    universities.value = []
    return
  }

  try {
    universitiesLoading.value = true
    const response = await searchUniversities({ universityName: query })
    universities.value = response || []
  } catch (error) {
    console.error('获取院校列表失败:', error)
    universities.value = []
  } finally {
    universitiesLoading.value = false
  }
}

// 当选择学生时，自动填充姓名
const handleStudentChange = async (value) => {
  if (value) {
    try {
      // 获取学生详细信息
      const student = students.value.find(s => s.examNumber === value)
      if (student) {
        admissionForm.value.name = student.name
      }
    } catch (error) {
      console.error('获取学生信息失败:', error)
      ElMessage.error('获取学生信息失败')
    }
  } else {
    admissionForm.value.name = ''
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

    await addAdmission(admissionForm.value)
    ElMessage.success('添加成功')
    router.push('/admissions')
  } catch (error) {
    console.error('添加录取信息失败:', error)
    ElMessage.error('添加失败')
  }
}

const handleReset = () => {
  formRef.value.resetFields()
}

const handleBack = () => {
  router.go(-1)
}
</script>

<style scoped>
.admission-add {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>