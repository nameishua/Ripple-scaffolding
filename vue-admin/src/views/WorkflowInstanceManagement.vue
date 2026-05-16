<template>
  <div>
    <el-card>
      <template #header>
        <div class="card-header">
          <span>流程实例</span>
          <el-button type="primary" @click="startDialog = true">发起流程</el-button>
        </div>
      </template>
      <el-table :data="instances" border stripe>
        <el-table-column prop="id" label="实例ID" width="200" show-overflow-tooltip />
        <el-table-column prop="workflowCode" label="流程编码" width="160" />
        <el-table-column prop="starter" label="发起人" width="100" />
        <el-table-column prop="currentNode" label="当前节点" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag>{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createDate" label="创建时间" />
        <el-table-column label="操作" width="120">
          <template #default="{ row }">
            <el-button size="small" @click="viewTasks(row)">任务</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="startDialog" title="发起流程" width="480px">
      <el-form label-width="100px">
        <el-form-item label="工作流">
          <el-select v-model="startForm.workflowCode" style="width: 100%">
            <el-option v-for="w in workflows" :key="w.code" :label="w.name" :value="w.code" />
          </el-select>
        </el-form-item>
        <el-form-item label="表单数据ID">
          <el-input v-model="startForm.formDataId" placeholder="先提交表单后填入 data id" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="startDialog = false">取消</el-button>
        <el-button type="primary" @click="doStart">发起</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="taskDialog" title="任务历史" width="640px">
      <el-table :data="taskList" border>
        <el-table-column prop="nodeName" label="节点" />
        <el-table-column prop="assignee" label="处理人" width="100" />
        <el-table-column prop="status" label="状态" width="100" />
        <el-table-column prop="action" label="动作" width="80" />
        <el-table-column prop="comment" label="意见" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getWorkflowInstanceList, getPublishedWorkflowDefinitions, startWorkflow, getWorkflowTasks } from '../api'

const instances = ref([])
const workflows = ref([])
const startDialog = ref(false)
const taskDialog = ref(false)
const taskList = ref([])
const startForm = reactive({ workflowCode: '', formDataId: '' })

const loadInstances = async () => {
  const res = await getWorkflowInstanceList()
  if (res.success) instances.value = res.data || []
}

const doStart = async () => {
  const res = await startWorkflow({ ...startForm })
  if (res.success) {
    ElMessage.success('流程已发起')
    startDialog.value = false
    loadInstances()
  } else {
    ElMessage.error(res.message || '发起失败')
  }
}

const viewTasks = async (row) => {
  const res = await getWorkflowTasks(row.id)
  if (res.success) {
    taskList.value = res.data || []
    taskDialog.value = true
  }
}

onMounted(async () => {
  await loadInstances()
  const wf = await getPublishedWorkflowDefinitions()
  if (wf.success) {
    workflows.value = wf.data || []
    if (workflows.value.length) startForm.workflowCode = workflows.value[0].code
  }
})
</script>

<style scoped>
.card-header { display: flex; justify-content: space-between; align-items: center; }
</style>
