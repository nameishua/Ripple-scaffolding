<template>
  <div class="dashboard">
    <h2>系统概览</h2>
    <el-row :gutter="16">
      <el-col v-for="item in statCards" :key="item.key" :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-icon" :style="{ background: item.color }">
            <el-icon :size="32"><component :is="item.icon" /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats[item.key] ?? 0 }}</div>
            <div class="stat-label">{{ item.label }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16" style="margin-top: 16px">
      <el-col :span="12">
        <el-card>
          <template #header><span>当前用户</span></template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="姓名">{{ userInfo.name }}</el-descriptions-item>
            <el-descriptions-item label="账号">{{ userInfo.account }}</el-descriptions-item>
            <el-descriptions-item label="角色">
              <el-tag v-for="role in userInfo.roles" :key="role.id" size="small" style="margin-right: 6px">{{ role.name }}</el-tag>
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header><span>模块导航</span></template>
          <el-space wrap>
            <el-tag v-for="m in modules" :key="m.path" type="info" effect="plain">{{ m.label }}</el-tag>
          </el-space>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getUserInfo, getDashboardStats } from '../api'

const stats = ref({})
const userInfo = ref({ name: '', account: '', roles: [] })

const statCards = [
  { key: 'userCount', label: '用户', icon: 'User', color: '#409EFF' },
  { key: 'roleCount', label: '角色', icon: 'Wallet', color: '#67C23A' },
  { key: 'orderCount', label: '订单', icon: 'List', color: '#E6A23C' },
  { key: 'productCount', label: '商品', icon: 'Goods', color: '#F56C6C' },
  { key: 'customerCount', label: '客户', icon: 'UserFilled', color: '#909399' },
  { key: 'deptCount', label: '部门', icon: 'OfficeBuilding', color: '#626aef' },
  { key: 'formDefCount', label: '表单', icon: 'Document', color: '#00bcd4' },
  { key: 'workflowDefCount', label: '流程', icon: 'Connection', color: '#9c27b0' },
  { key: 'todoTaskCount', label: '待办', icon: 'Bell', color: '#ff5722' }
]

const modules = [
  { path: '/user', label: '用户' }, { path: '/role', label: '角色' }, { path: '/dept', label: '部门' },
  { path: '/dict', label: '字典' }, { path: '/order', label: '订单' }, { path: '/form-def', label: '表单' },
  { path: '/workflow-task', label: '待办' }, { path: '/oper-log', label: '操作日志' }
]

onMounted(async () => {
  const [userRes, statRes] = await Promise.all([getUserInfo(), getDashboardStats()])
  if (userRes.success) {
    userInfo.value = userRes.data.user || {}
    userInfo.value.roles = userRes.data.roles || []
  }
  if (statRes.success && statRes.data) {
    stats.value = statRes.data
  }
})
</script>

<style scoped>
.dashboard h2 { margin-bottom: 20px; color: #333; }
.stat-card { display: flex; align-items: center; padding: 16px; margin-bottom: 16px; }
.stat-icon {
  width: 64px; height: 64px; border-radius: 8px;
  display: flex; align-items: center; justify-content: center;
  color: #fff; margin-right: 16px;
}
.stat-value { font-size: 28px; font-weight: bold; color: #333; }
.stat-label { font-size: 13px; color: #999; margin-top: 4px; }
</style>
