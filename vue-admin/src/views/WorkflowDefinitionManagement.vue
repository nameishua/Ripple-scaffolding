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
            <el-button size="small" @click="handleEdit(row)">设计</el-button>
            <el-button size="small" type="success" v-if="row.status !== 'published'" @click="handlePublish(row)">发布</el-button>
            <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="1100px"
      top="4vh"
      destroy-on-close
      class="workflow-designer-dialog"
    >
      <el-form :model="form" label-width="90px" class="meta-form">
        <el-row :gutter="16">
          <el-col :span="6">
            <el-form-item label="编码">
              <el-input v-model="form.code" :disabled="!!form.id" placeholder="如 LEAVE_APPROVAL" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="名称">
              <el-input v-model="form.name" placeholder="流程名称" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="关联表单">
              <el-select v-model="form.formCode" filterable clearable placeholder="选择已发布表单" style="width: 100%">
                <el-option
                  v-for="f in publishedForms"
                  :key="f.code"
                  :label="`${f.name} (${f.code})`"
                  :value="f.code"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="描述">
              <el-input v-model="form.description" placeholder="可选" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <WorkflowDesigner ref="designerRef" v-model="form.definitionJson" />

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
import WorkflowDesigner from '../components/WorkflowDesigner.vue'
import { defaultDefinitionJson } from '../utils/workflowSchema.js'
import {
  getWorkflowDefinitionList,
  addWorkflowDefinition,
  updateWorkflowDefinition,
  deleteWorkflowDefinition,
  publishWorkflowDefinition,
  getPublishedFormDefinitions
} from '../api'

const tableData = ref([])
const publishedForms = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('新建工作流')
const designerRef = ref()

const form = reactive({
  id: '',
  code: '',
  name: '',
  description: '',
  formCode: 'LEAVE_FORM',
  definitionJson: defaultDefinitionJson(),
  version: 1,
  status: 'draft'
})

const loadData = async () => {
  const res = await getWorkflowDefinitionList()
  if (res.success) tableData.value = res.data || []
}

const loadPublishedForms = async () => {
  const res = await getPublishedFormDefinitions()
  if (res.success) publishedForms.value = res.data || []
}

const handleAdd = () => {
  dialogTitle.value = '新建工作流'
  Object.assign(form, {
    id: '',
    code: '',
    name: '',
    description: '',
    formCode: publishedForms.value[0]?.code || 'LEAVE_FORM',
    definitionJson: defaultDefinitionJson(),
    version: 1,
    status: 'draft'
  })
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = `设计工作流 · ${row.name}`
  Object.assign(form, row)
  form.definitionJson = row.definitionJson || defaultDefinitionJson()
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (!form.code?.trim() || !form.name?.trim()) {
    ElMessage.warning('请填写工作流编码和名称')
    return
  }
  const check = designerRef.value?.validate?.()
  if (check && !check.ok) {
    ElMessage.error(check.message)
    return
  }
  try {
    JSON.parse(form.definitionJson)
  } catch {
    ElMessage.error('流程配置不是合法 JSON')
    return
  }
  const payload = {
    ...form,
    definitionJson: JSON.stringify(JSON.parse(form.definitionJson))
  }
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

onMounted(async () => {
  await loadPublishedForms()
  loadData()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.meta-form {
  margin-bottom: 8px;
}

:deep(.workflow-designer-dialog .el-dialog__body) {
  padding-top: 12px;
  max-height: calc(92vh - 120px);
  overflow-y: auto;
}
</style>
