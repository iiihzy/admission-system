<template>
  <div class="university-edit">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>编辑院校</span>
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
            disabled
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

const universityForm = ref({
  universityId: '',
  universityName: '',
  address: ''
})

const rules = {
  universityName: [
    { required: true, message: '请输入院校名称', trigger: 'blur' }
  ]
}

// 获取院校详情
const fetchUniversity = async (id) => {
  try {
    const response = await request.get(`/universities/${id}`)
    universityForm.value = response
  } catch (error) {
    console.error('获取院校信息失败:', error)
    ElMessage.error('获取院校信息失败')
  }
}

const handleSubmit = async () => {
  try {
    await formRef.value.validate()

    await request.put(`/universities/${universityForm.value.universityId}`, universityForm.value)
    ElMessage.success('更新成功')
    router.push('/universities')
  } catch (error) {
    console.error('更新院校失败:', error)
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
    fetchUniversity(id)
  }
})
</script>

<style scoped>
.university-edit {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>