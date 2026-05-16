<template>
  <div class="simple-crud">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>{{ title }}</span>
          <el-button v-if="showAdd" type="primary" @click="openDialog()">新增</el-button>
        </div>
      </template>
      <el-table :data="rows" border stripe v-loading="loading">
        <el-table-column
          v-for="col in columns"
          :key="col.prop"
          :prop="col.prop"
          :label="col.label"
          :width="col.width"
          :min-width="col.minWidth || 120"
          show-overflow-tooltip
        />
        <el-table-column v-if="showEdit || deleteRow" label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button v-if="showEdit" size="small" @click="openDialog(row)">编辑</el-button>
            <el-button v-if="deleteRow" size="small" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="visible" :title="dialogTitle" width="520px">
      <el-form :model="form" label-width="100px">
        <el-form-item v-for="f in fields" :key="f.prop" :label="f.label">
          <el-input v-if="!f.type || f.type === 'input'" v-model="form[f.prop]" />
          <el-input-number v-else-if="f.type === 'number'" v-model="form[f.prop]" style="width: 100%" />
          <el-input v-else-if="f.type === 'textarea'" v-model="form[f.prop]" type="textarea" :rows="3" />
          <el-select v-else-if="f.type === 'select'" v-model="form[f.prop]" style="width: 100%" clearable>
            <el-option v-for="o in f.options || []" :key="o.value" :label="o.label" :value="o.value" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const props = defineProps({
  title: { type: String, required: true },
  columns: { type: Array, required: true },
  fields: { type: Array, required: true },
  loadList: { type: Function, required: true },
  saveRow: { type: Function, required: true },
  deleteRow: { type: Function, default: null },
  showAdd: { type: Boolean, default: true },
  showEdit: { type: Boolean, default: true },
  idField: { type: String, default: 'id' }
})

const rows = ref([])
const loading = ref(false)
const visible = ref(false)
const dialogTitle = ref('')
const form = reactive({})

const resetForm = (row = {}) => {
  Object.keys(form).forEach(k => delete form[k])
  props.fields.forEach(f => {
    form[f.prop] = row[f.prop] ?? (f.type === 'number' ? 0 : '')
  })
  if (row[props.idField]) form[props.idField] = row[props.idField]
}

const load = async () => {
  loading.value = true
  try {
    const res = await props.loadList()
    rows.value = res.success ? (res.data || []) : []
  } finally {
    loading.value = false
  }
}

const openDialog = (row) => {
  dialogTitle.value = row ? '编辑' : '新增'
  resetForm(row || {})
  visible.value = true
}

const handleSave = async () => {
  const res = await props.saveRow({ ...form })
  if (res.success) {
    ElMessage.success('保存成功')
    visible.value = false
    load()
  } else {
    ElMessage.error(res.message || '保存失败')
  }
}

const handleDelete = async (row) => {
  if (!props.deleteRow) return
  await ElMessageBox.confirm('确认删除？', '提示', { type: 'warning' })
  const res = await props.deleteRow(row[props.idField])
  if (res.success) {
    ElMessage.success('已删除')
    load()
  }
}

onMounted(load)
defineExpose({ reload: load })
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
