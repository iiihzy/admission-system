<template>
  <div class="student-add">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>新增学生</span>
          <el-button @click="handleBack">返回</el-button>
        </div>
      </template>

      <el-form
        :model="studentForm"
        :rules="rules"
        ref="formRef"
        label-width="100px"
        style="max-width: 600px"
      >
        <el-form-item label="考号" prop="examNumber">
          <el-input
            v-model="studentForm.examNumber"
            placeholder="请输入考号"
          />
        </el-form-item>

        <el-form-item label="姓名" prop="name">
          <el-input
            v-model="studentForm.name"
            placeholder="请输入姓名"
          />
        </el-form-item>

        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="studentForm.gender">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="出生年月" prop="birthDate">
          <el-date-picker
            v-model="studentForm.birthDate"
            type="date"
            placeholder="请选择出生日期"
            value-format="yyyy-MM-dd"
          />
        </el-form-item>

        <el-form-item label="班级" prop="className">
          <el-input
            v-model="studentForm.className"
            placeholder="请输入班级"
          />
        </el-form-item>

        <el-form-item label="籍贯" prop="nativePlace">
          <el-input
            v-model="studentForm.nativePlace"
            placeholder="请输入籍贯"
          />
        </el-form-item>

        <el-form-item label="高考成绩" prop="gaokaoScore">
          <el-input-number
            v-model="studentForm.gaokaoScore"
            placeholder="请输入高考成绩"
            controls-position="right"
            :min="0"
            :max="750"
          />
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
import { addStudent } from '../../api/student'

const router = useRouter()
const formRef = ref()

const studentForm = ref({
  examNumber: '',
  name: '',
  gender: '男',
  birthDate: '',
  className: '',
  nativePlace: '',
  gaokaoScore: null
})

const rules = {
  examNumber: [
    { required: true, message: '请输入考号', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  className: [
    { required: true, message: '请输入班级', trigger: 'blur' }
  ],
  gaokaoScore: [
    { required: true, message: '请输入高考成绩', trigger: 'blur' }
  ]
}

const handleSubmit = async () => {
  try {
    await formRef.value.validate()

    await addStudent(studentForm.value)
    ElMessage.success('添加成功')
    router.push('/students')
  } catch (error) {
    console.error('添加学生失败:', error)
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
.student-add {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>