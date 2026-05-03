<template>
  <div class="university-add">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>新增院校</span>
          <el-button @click="handleBack">返回</el-button>
        </div>
      </template>

      <el-form
        :model="universityForm"
        :rules="rules"
        ref="formRef"
        label-width="100px"
        style="max-width: 600px"
      >
        <el-form-item label="院校编号" prop="universityId">
          <el-input
            v-model="universityForm.universityId"
            placeholder="请输入院校编号"
          />
        </el-form-item>

        <el-form-item label="院校名称" prop="universityName">
          <el-input
            v-model="universityForm.universityName"
            placeholder="请输入院校名称"
          />
        </el-form-item>

        <el-form-item label="地址" prop="address">
          <el-input
            v-model="universityForm.address"
            placeholder="请输入地址"
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
import request from '../../utils/request'

const router = useRouter()
const formRef = ref()

const universityForm = ref({
  universityId: '',
  universityName: '',
  address: ''
})

const rules = {
  universityId: [
    { required: true, message: '请输入院校编号', trigger: 'blur' }
  ],
  universityName: [
    { required: true, message: '请输入院校名称', trigger: 'blur' }
  ]
}

const handleSubmit = async () => {
  try {
    await formRef.value.validate()

    await request.post('/universities', universityForm.value)
    ElMessage.success('添加成功')
    router.push('/universities')
  } catch (error) {
    console.error('添加院校失败:', error)
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
.university-add {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>