<template>
  <div class="user-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>用户管理</span>
          <el-button type="primary" @click="handleAdd">添加用户</el-button>
        </div>
      </template>

      <el-table :data="tableData" border stripe>
        <el-table-column prop="id" label="ID" width="220" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="account" label="账号" />
        <el-table-column prop="age" label="年龄" width="80" />
        <el-table-column prop="flag" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.flag === '启用' ? 'success' : 'danger'">
              {{ row.flag }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="账号" prop="account">
          <el-input v-model="form.account" :disabled="!!form.id" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" show-password />
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input-number v-model="form.age" :min="1" :max="150" />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="form.roleIds" multiple placeholder="选择角色" style="width: 100%">
            <el-option v-for="role in roleList" :key="role.id" :label="role.name" :value="role.id" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getUserList, getAllRoles, addUser, updateUser, deleteUser } from '../api'

const tableData = ref([])
const roleList = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('添加用户')
const formRef = ref()

const form = reactive({
  id: '',
  name: '',
  account: '',
  password: '',
  age: 18,
  roleIds: []
})

const rules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  account: [{ required: true, message: '请输入账号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const loadData = async () => {
  try {
    const [userRes, roleRes] = await Promise.all([getUserList(), getAllRoles()])
    if (userRes.success) {
      tableData.value = userRes.data || []
    }
    if (roleRes.success) {
      roleList.value = roleRes.data || []
    }
  } catch (error) {
    ElMessage.error('加载数据失败')
  }
}

const handleAdd = () => {
  form.id = ''
  form.name = ''
  form.account = ''
  form.password = ''
  form.age = 18
  form.roleIds = []
  dialogTitle.value = '添加用户'
  dialogVisible.value = true
}

const handleEdit = async (row) => {
  form.id = row.id
  form.name = row.name
  form.account = row.account
  form.password = ''
  form.age = row.age
  form.roleIds = row.roles?.map(r => r.id) || []
  dialogTitle.value = '编辑用户'
  dialogVisible.value = true
}

const handleSubmit = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  try {
    const data = { ...form }
    delete data.roleIds
    data.roleIds = form.roleIds

    const res = form.id ? await updateUser(data) : await addUser(data)
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
    await ElMessageBox.confirm('确定删除该用户吗？', '提示', {
      type: 'warning'
    })
    const res = await deleteUser(row.id)
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
.user-management {
  width: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
