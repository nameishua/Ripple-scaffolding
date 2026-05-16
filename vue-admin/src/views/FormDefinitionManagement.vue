<template>
  <div class="form-def-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>动态表单配置</span>
          <el-button type="primary" @click="handleAdd">新建表单</el-button>
        </div>
      </template>
      <el-table :data="tableData" border stripe>
        <el-table-column prop="code" label="编码" width="140" />
        <el-table-column prop="name" label="名称" />
        <el-table-column prop="version" label="版本" width="80" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 'published' ? 'success' : 'info'">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="280" fixed="right">
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
      class="form-designer-dialog"
    >
      <el-form :model="form" label-width="80px" class="meta-form">
        <el-row :gutter="16">
          <el-col :span="8">
            <el-form-item label="编码">
              <el-input v-model="form.code" :disabled="!!form.id" placeholder="如 LEAVE_FORM" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="名称">
              <el-input v-model="form.name" placeholder="表单显示名称" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="描述">
              <el-input v-model="form.description" placeholder="可选" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <FormDesigner ref="designerRef" v-model="form.schemaJson" />

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
import FormDesigner from '../components/FormDesigner.vue'
import { defaultSchemaJson } from '../utils/formSchema.js'
import { getFormDefinitionList, addFormDefinition, updateFormDefinition, deleteFormDefinition, publishFormDefinition } from '../api'

const tableData = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('新建表单')
const designerRef = ref()
const form = reactive({
  id: '',
  code: '',
  name: '',
  description: '',
  schemaJson: defaultSchemaJson(),
  version: 1,
  status: 'draft'
})

const loadData = async () => {
  const res = await getFormDefinitionList()
  if (res.success) tableData.value = res.data || []
}

const handleAdd = () => {
  dialogTitle.value = '新建表单'
  form.id = ''
  form.code = ''
  form.name = ''
  form.description = ''
  form.schemaJson = defaultSchemaJson()
  form.version = 1
  form.status = 'draft'
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = `设计表单 · ${row.name}`
  Object.assign(form, row)
  form.schemaJson = row.schemaJson || defaultSchemaJson()
  dialogVisible.value = true
}

const handleSubmit = async () => {
  if (!form.code?.trim() || !form.name?.trim()) {
    ElMessage.warning('请填写表单编码和名称')
    return
  }
  const check = designerRef.value?.validate?.()
  if (check && !check.ok) {
    ElMessage.error(check.message)
    return
  }
  try {
    JSON.parse(form.schemaJson)
  } catch {
    ElMessage.error('表单配置不是合法 JSON')
    return
  }
  const payload = {
    ...form,
    schemaJson: JSON.stringify(JSON.parse(form.schemaJson))
  }
  const res = form.id ? await updateFormDefinition(payload) : await addFormDefinition(payload)
  if (res.success) {
    ElMessage.success('保存成功')
    dialogVisible.value = false
    loadData()
  } else {
    ElMessage.error(res.message || '保存失败')
  }
}

const handlePublish = async (row) => {
  const res = await publishFormDefinition(row.id)
  if (res.success) {
    ElMessage.success('已发布')
    loadData()
  }
}

const handleDelete = async (row) => {
  await ElMessageBox.confirm('确认删除？', '提示', { type: 'warning' })
  const res = await deleteFormDefinition(row.id)
  if (res.success) {
    ElMessage.success('已删除')
    loadData()
  }
}

onMounted(loadData)
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

:deep(.form-designer-dialog .el-dialog__body) {
  padding-top: 12px;
  max-height: calc(92vh - 120px);
  overflow-y: auto;
}
</style>
