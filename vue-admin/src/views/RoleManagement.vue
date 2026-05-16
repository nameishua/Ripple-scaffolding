<template>
  <div class="role-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>角色管理</span>
          <el-button type="primary" @click="handleAdd">添加角色</el-button>
        </div>
      </template>

      <el-table :data="tableData" border stripe>
        <el-table-column prop="id" label="ID" width="220" />
        <el-table-column prop="name" label="角色名称" />
        <el-table-column prop="code" label="角色代码" />
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="flag" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.flag === '启用' ? 'success' : 'danger'">
              {{ row.flag }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="300" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="warning" size="small" @click="handleAssignMenu(row)">分配菜单</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="角色代码" prop="code">
          <el-input v-model="form.code" :disabled="!!form.id" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="menuDialogVisible" title="分配菜单" width="500px">
      <el-tree
        ref="menuTreeRef"
        :data="menuTree"
        :props="{ label: 'name', children: 'children' }"
        node-key="id"
        show-checkbox
        check-strictly
      />
      <template #footer>
        <el-button @click="menuDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleMenuSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getRoleList, getMenuTree, getMenuList, addRole, updateRole, deleteRole, assignMenus } from '../api'

const tableData = ref([])
const menuTree = ref([])
const dialogVisible = ref(false)
const menuDialogVisible = ref(false)
const dialogTitle = ref('添加角色')
const formRef = ref()
const menuTreeRef = ref()
const currentRoleId = ref('')

const form = reactive({
  id: '',
  name: '',
  code: '',
  description: ''
})

const rules = {
  name: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
  code: [{ required: true, message: '请输入角色代码', trigger: 'blur' }]
}

const loadData = async () => {
  try {
    const res = await getRoleList()
    if (res.success) {
      tableData.value = res.data || []
    }
    const menuRes = await getMenuTree()
    if (menuRes.success) {
      menuTree.value = menuRes.data || []
    }
  } catch (error) {
    ElMessage.error('加载数据失败')
  }
}

const handleAdd = () => {
  form.id = ''
  form.name = ''
  form.code = ''
  form.description = ''
  dialogTitle.value = '添加角色'
  dialogVisible.value = true
}

const handleEdit = (row) => {
  form.id = row.id
  form.name = row.name
  form.code = row.code
  form.description = row.description
  dialogTitle.value = '编辑角色'
  dialogVisible.value = true
}

const handleAssignMenu = async (row) => {
  currentRoleId.value = row.id
  menuDialogVisible.value = true
  const res = await getMenuList()
  if (res.success) {
    const checkedIds = res.data?.filter(m => m.flag === '启用').map(m => m.id) || []
    setTimeout(() => {
      menuTreeRef.value?.setCheckedKeys(checkedIds)
    }, 100)
  }
}

const handleMenuSubmit = async () => {
  const checkedNodes = menuTreeRef.value?.getCheckedNodes() || []
  const menuIds = checkedNodes.map(n => n.id)
  try {
    const res = await assignMenus(currentRoleId.value, menuIds)
    if (res.success) {
      ElMessage.success('分配成功')
      menuDialogVisible.value = false
    } else {
      ElMessage.error(res.message || '分配失败')
    }
  } catch (error) {
    ElMessage.error('分配失败')
  }
}

const handleSubmit = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  try {
    const res = form.id ? await updateRole(form) : await addRole(form)
    if (res.success) {
      ElMessage.success(form.id ? '更新成功' : '添加成功')
      dialogVisible.value = false
      loadData()
    } else {
      ElMessage.error(res.message || '操作失败')
    }
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定删除该角色吗？', '提示', {
      type: 'warning'
    })
    const res = await deleteRole(row.id)
    if (res.success) {
      ElMessage.success('删除成功')
      loadData()
    } else {
      ElMessage.error(res.message || '删除失败')
    }
  } catch {
    // 用户取消
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.role-management {
  width: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
