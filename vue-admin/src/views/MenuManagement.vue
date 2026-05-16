<template>
  <div class="menu-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>菜单管理</span>
          <el-button type="primary" @click="handleAdd">添加菜单</el-button>
        </div>
      </template>

      <el-table :data="tableData" border stripe row-key="id" :tree-props="{ children: 'children' }">
        <el-table-column prop="name" label="菜单名称" />
        <el-table-column prop="path" label="路径" />
        <el-table-column prop="component" label="组件" />
        <el-table-column prop="icon" label="图标" width="120">
          <template #default="{ row }">
            <el-icon><component :is="row.icon" /></el-icon>
          </template>
        </el-table-column>
        <el-table-column prop="type" label="类型" width="100">
          <template #default="{ row }">
            <el-tag :type="row.type === 'dir' ? 'warning' : 'success'">
              {{ row.type === 'dir' ? '目录' : '菜单' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="permission" label="权限标识" />
        <el-table-column prop="sortNo" label="排序" width="80" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="上级菜单" prop="parentId">
          <el-select v-model="form.parentId" placeholder="选择上级菜单" clearable style="width: 100%">
            <el-option label="无（顶级菜单）" value="" />
            <el-option v-for="menu in flatMenuList" :key="menu.id" :label="menu.name" :value="menu.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="路径" prop="path">
          <el-input v-model="form.path" />
        </el-form-item>
        <el-form-item label="组件" prop="component">
          <el-input v-model="form.component" placeholder="如: views/system/user" />
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-input v-model="form.icon" placeholder="Element Plus图标名称" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-radio-group v-model="form.type">
            <el-radio label="dir">目录</el-radio>
            <el-radio label="menu">菜单</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="权限标识" prop="permission">
          <el-input v-model="form.permission" placeholder="如: user:list" />
        </el-form-item>
        <el-form-item label="排序" prop="sortNo">
          <el-input-number v-model="form.sortNo" :min="0" />
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
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMenuTree, getMenuList, addMenu, updateMenu, deleteMenu } from '../api'

const tableData = ref([])
const menuList = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('添加菜单')
const formRef = ref()

const form = reactive({
  id: '',
  parentId: '',
  name: '',
  path: '',
  component: '',
  icon: '',
  type: 'menu',
  permission: '',
  sortNo: 0
})

const rules = {
  name: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
  path: [{ required: true, message: '请输入路径', trigger: 'blur' }],
  type: [{ required: true, message: '请选择类型', trigger: 'change' }]
}

const flatMenuList = computed(() => {
  const flatten = (menus, result = []) => {
    menus.forEach(menu => {
      result.push(menu)
      if (menu.children && menu.children.length) {
        flatten(menu.children, result)
      }
    })
    return result
  }
  return flatten(menuList.value)
})

const loadData = async () => {
  try {
    const res = await getMenuTree()
    if (res.success) {
      tableData.value = res.data || []
    }
    const listRes = await getMenuList()
    if (listRes.success) {
      menuList.value = listRes.data || []
    }
  } catch (error) {
    ElMessage.error('加载数据失败')
  }
}

const handleAdd = () => {
  form.id = ''
  form.parentId = ''
  form.name = ''
  form.path = ''
  form.component = ''
  form.icon = ''
  form.type = 'menu'
  form.permission = ''
  form.sortNo = 0
  dialogTitle.value = '添加菜单'
  dialogVisible.value = true
}

const handleEdit = (row) => {
  form.id = row.id
  form.parentId = row.parentId || ''
  form.name = row.name
  form.path = row.path
  form.component = row.component
  form.icon = row.icon
  form.type = row.type
  form.permission = row.permission
  form.sortNo = row.sortNo
  dialogTitle.value = '编辑菜单'
  dialogVisible.value = true
}

const handleSubmit = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  try {
    const res = form.id ? await updateMenu(form) : await addMenu(form)
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
    await ElMessageBox.confirm('确定删除该菜单吗？', '提示', {
      type: 'warning'
    })
    const res = await deleteMenu(row.id)
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
.menu-management {
  width: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
