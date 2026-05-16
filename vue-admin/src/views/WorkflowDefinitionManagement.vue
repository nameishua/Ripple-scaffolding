<template>
  <div class="workflow-def">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>工作流配置</span>
          <el-button type="primary" @click="handleAdd">新建工作流</el-button>
        </div>
      </template>
      <el-table :data="tableData" border stripe>
        <el-table-column prop="code" label="编码" width="160" />
        <el-table-column prop="name" label="名称" />
        <el-table-column prop="formCode" label="关联表单" width="140" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 'published' ? 'success' : 'info'">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="260" fixed="right">
          <template #default="{ row }">
            <el-button size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button size="small" type="success" v-if="row.status !== 'published'" @click="handlePublish(row)">发布</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="760px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="编码"><el-input v-model="form.code" :disabled="!!form.id" /></el-form-item>
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="关联表单"><el-input v-model="form.formCode" placeholder="如 LEAVE_FORM" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" type="textarea" /></el-form-item>
        <el-form-item label="流程JSON">
          <el-input v-model="form.definitionJson" type="textarea" :rows="14" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  getWorkflowDefinitionList, addWorkflowDefinition, updateWorkflowDefinition,
  deleteWorkflowDefinition, publishWorkflowDefinition
} from '../api'

const tableData = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('新建工作流')
const defaultDef = JSON.stringify({
  nodes: [
    { key: 'start', name: '开始', type: 'start' },
    { key: 'mgr', name: '经理审批', type: 'approval', assignee: 'admin' },
    { key: 'end', name: '结束', type: 'end' }
  ],
  edges: [
    { from: 'start', to: 'mgr' },
    { from: 'mgr', to: 'end', on: 'approve' },
    { from: 'mgr', to: 'end', on: 'reject' }
  ]
}, null, 2)

const form = reactive({
  id: '', code: '', name: '', description: '', formCode: 'LEAVE_FORM',
  definitionJson: defaultDef, version: 1, status: 'draft'
})

const loadData = async () => {
  const res = await getWorkflowDefinitionList()
  if (res.success) tableData.value = res.data || []
}

const handleAdd = () => {
  dialogTitle.value = '新建工作流'
  Object.assign(form, { id: '', code: '', name: '', description: '', formCode: 'LEAVE_FORM', definitionJson: defaultDef, version: 1, status: 'draft' })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑工作流'
  Object.assign(form, row)
  try {
    form.definitionJson = JSON.stringify(JSON.parse(row.definitionJson), null, 2)
  } catch {
    form.definitionJson = row.definitionJson
  }
  dialogVisible.value = true
}

const handleSubmit = async () => {
  try {
    JSON.parse(form.definitionJson)
  } catch {
    ElMessage.error('definitionJson 不是合法 JSON')
    return
  }
  const payload = { ...form, definitionJson: JSON.stringify(JSON.parse(form.definitionJson)) }
  const res = form.id ? await updateWorkflowDefinition(payload) : await addWorkflowDefinition(payload)
  if (res.success) {
    ElMessage.success('保存成功')
    dialogVisible.value = false
    loadData()
  } else {
    ElMessage.error(res.message || '保存失败')
  }
}

const handlePublish = async (row) => {
  const res = await publishWorkflowDefinition(row.id)
  if (res.success) {
    ElMessage.success('已发布')
    loadData()
  }
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确认删除？', '提示', { type: 'warning' })
  const res = await deleteWorkflowDefinition(row.id)
  if (res.success) {
    ElMessage.success('已删除')
    loadData()
  }
}

onMounted(loadData)
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
