<template>
  <div class="workflow-task">
    <el-card>
      <template #header><span>我的待办</span></template>
      <el-table :data="todoList" border stripe>
        <el-table-column prop="nodeName" label="节点" width="140" />
        <el-table-column prop="workflowCode" label="流程" width="160" />
        <el-table-column prop="formDataId" label="表单数据ID" show-overflow-tooltip />
        <el-table-column prop="createDate" label="到达时间" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="success" size="small" @click="handleApprove(row)">通过</el-button>
            <el-button type="danger" size="small" @click="handleReject(row)">驳回</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getWorkflowTodoTasks, approveWorkflowTask, rejectWorkflowTask } from '../api'

const todoList = ref([])

const loadTodo = async () => {
  const res = await getWorkflowTodoTasks()
  if (res.success) todoList.value = res.data || []
}

const handleApprove = async (row) => {
  const { value } = await ElMessageBox.prompt('审批意见', '通过', { inputValue: '同意' }).catch(() => ({ value: null }))
  if (value === null) return
  const res = await approveWorkflowTask(row.id, value)
  if (res.success) {
    ElMessage.success('已通过')
    loadTodo()
  } else {
    ElMessage.error(res.message || '操作失败')
  }
}

const handleReject = async (row) => {
  const { value } = await ElMessageBox.prompt('驳回意见', '驳回', { inputValue: '不同意' }).catch(() => ({ value: null }))
  if (value === null) return
  const res = await rejectWorkflowTask(row.id, value)
  if (res.success) {
    ElMessage.success('已驳回')
    loadTodo()
  } else {
    ElMessage.error(res.message || '操作失败')
  }
}

onMounted(loadTodo)
</script>
