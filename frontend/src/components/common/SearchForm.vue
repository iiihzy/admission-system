<template>
  <el-form
    :model="searchForm"
    :inline="true"
    label-width="auto"
    class="search-form"
  >
    <slot></slot>
    <el-form-item>
      <el-button type="primary" @click="handleSearch">搜索</el-button>
      <el-button @click="handleReset">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup>
import { reactive } from 'vue'

const props = defineProps({
  initialForm: {
    type: Object,
    default: () => ({})
  }
})

const emit = defineEmits(['search', 'reset'])

const searchForm = reactive({ ...props.initialForm })

const handleSearch = () => {
  emit('search', searchForm)
}

const handleReset = () => {
  Object.keys(searchForm).forEach(key => {
    searchForm[key] = props.initialForm[key] || ''
  })
  emit('reset', searchForm)
}
</script>

<style scoped>
.search-form {
  padding: 20px;
  background-color: #fff;
  border-radius: 4px;
  margin-bottom: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>