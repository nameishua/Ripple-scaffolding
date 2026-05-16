<template>
  <div class="form-fill">
    <el-card>
      <template #header><span>填写并提交表单</span></template>
      <el-form label-width="100px">
        <el-form-item label="选择表单">
          <el-select v-model="selectedCode" placeholder="已发布表单" style="width: 320px" @change="loadForm">
            <el-option v-for="f in publishedForms" :key="f.code" :label="f.name" :value="f.code" />
          </el-select>
        </el-form-item>
      </el-form>
      <DynamicFormRender v-if="currentSchema" ref="dynamicRef" v-model="formModel" :schema-json="currentSchema" />
      <div style="margin-top: 16px">
        <el-button type="primary" :disabled="!selectedCode" @click="handleSubmit">提交</el-button>
        <el-button @click="handleStartWorkflow" :disabled="!lastFormDataId">发起关联流程</el-button>
      </div>
    </el-card>

    <el-card style="margin-top: 16px">
      <template #header><span>我的提交记录</span></template>
      <el-table :data="dataList" border stripe>
        <el-table-column prop="id" label="ID" width="200" show-overflow-tooltip />
        <el-table-column prop="formCode" label="表单编码" width="140" />
        <el-table-column prop="submitter" label="提交人" width="100" />
        <el-table-column prop="status" label="状态" width="100" />
        <el-table-column prop="createDate" label="提交时间" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import DynamicFormRender from '../components/DynamicFormRender.vue'
import {
  getPublishedFormDefinitions, getFormDefinitionByCode, submitFormData,
  getFormDataList, startWorkflow, getPublishedWorkflowDefinitions
} from '../api'

const publishedForms = ref([])
const selectedCode = ref('')
const currentSchema = ref('')
const formModel = ref({})
const dynamicRef = ref()
const dataList = ref([])
const lastFormDataId = ref('')

const loadPublished = async () => {
  const res = await getPublishedFormDefinitions()
  if (res.success) publishedForms.value = res.data || []
}

const loadForm = async () => {
  if (!selectedCode.value) return
  const res = await getFormDefinitionByCode(selectedCode.value)
  if (res.success && res.data) {
    currentSchema.value = res.data.schemaJson
    formModel.value = {}
  }
}

const loadDataList = async () => {
  const res = await getFormDataList(selectedCode.value || undefined)
  if (res.success) dataList.value = res.data || []
}

const handleSubmit = async () => {
  const valid = await dynamicRef.value?.validate()
  if (!valid) return
  const res = await submitFormData({ formCode: selectedCode.value, data: formModel.value })
  if (res.success) {
    ElMessage.success('提交成功')
    lastFormDataId.value = res.data.id
    loadDataList()
  } else {
    ElMessage.error(res.message || '提交失败')
  }
}

const handleStartWorkflow = async () => {
  const wf = publishedWorkflows.value?.find(w => w.code === 'LEAVE_APPROVAL') || publishedWorkflows.value?.[0]
  if (!wf) {
    ElMessage.warning('请先发布工作流 LEAVE_APPROVAL')
    return
  }
  const res = await startWorkflow({ workflowCode: wf.code, formDataId: lastFormDataId.value })
  if (res.success) {
    ElMessage.success('流程已发起')
  } else {
    ElMessage.error(res.message || '发起失败')
  }
}

const publishedWorkflows = ref([])
onMounted(async () => {
  await loadPublished()
  const wfRes = await getPublishedWorkflowDefinitions()
  if (wfRes.success) publishedWorkflows.value = wfRes.data || []
  loadDataList()
})
</script>
