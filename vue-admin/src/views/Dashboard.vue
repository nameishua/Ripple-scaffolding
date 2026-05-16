<template>
  <div class="dashboard">
    <h2>首页</h2>
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #409EFF;">
            <el-icon :size="40"><User /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.userCount }}</div>
            <div class="stat-label">用户数量</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #67C23A;">
            <el-icon :size="40"><Wallet /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.roleCount }}</div>
            <div class="stat-label">角色数量</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #E6A23C;">
            <el-icon :size="40"><Menu /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.menuCount }}</div>
            <div class="stat-label">菜单数量</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-icon" style="background: #F56C6C;">
            <el-icon :size="40"><Setting /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.permissionCount }}</div>
            <div class="stat-label">权限数量</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="info-card" style="margin-top: 20px;">
      <template #header>
        <span>系统信息</span>
      </template>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="系统名称">Ripple Scaffold</el-descriptions-item>
        <el-descriptions-item label="版本">1.2.2</el-descriptions-item>
        <el-descriptions-item label="当前用户">{{ userInfo.name }}</el-descriptions-item>
        <el-descriptions-item label="账号">{{ userInfo.account }}</el-descriptions-item>
        <el-descriptions-item label="角色">
          <el-tag v-for="role in userInfo.roles" :key="role.id" size="small" style="margin-right: 5px;">
            {{ role.name }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="拥有权限">
          <span v-for="perm in userInfo.permissions" :key="perm.id" class="permission-tag">
            {{ perm.code }}
          </span>
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getUserInfo, getUserList, getRoleList, getMenuList } from '../api'

const stats = ref({
  userCount: 0,
  roleCount: 0,
  menuCount: 0,
  permissionCount: 0
})

const userInfo = ref({
  name: '',
  account: '',
  roles: [],
  permissions: []
})

onMounted(async () => {
  try {
    const [userRes, userListRes, roleRes, menuRes] = await Promise.all([
      getUserInfo(),
      getUserList(),
      getRoleList(),
      getMenuList()
    ])

    if (userRes.success) {
      userInfo.value = userRes.data.user || {}
      userInfo.value.roles = userRes.data.roles || []
      userInfo.value.permissions = userRes.data.permissions || []
    }

    if (userListRes.success) {
      stats.value.userCount = userListRes.data?.length || 0
    }

    if (roleRes.success) {
      stats.value.roleCount = roleRes.data?.length || 0
    }

    if (menuRes.success) {
      stats.value.menuCount = menuRes.data?.length || 0
    }

    stats.value.permissionCount = userInfo.value.permissions?.length || 0
  } catch (error) {
    console.error('获取数据失败', error)
  }
})
</script>

<style scoped>
.dashboard h2 {
  margin-bottom: 20px;
  color: #333;
}

.stat-card {
  display: flex;
  align-items: center;
  padding: 20px;
}

.stat-icon {
  width: 80px;
  height: 80px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  margin-right: 20px;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 32px;
  font-weight: bold;
  color: #333;
}

.stat-label {
  font-size: 14px;
  color: #999;
  margin-top: 5px;
}

.permission-tag {
  display: inline-block;
  padding: 2px 8px;
  margin: 2px;
  background-color: #f0f2f5;
  border-radius: 4px;
  font-size: 12px;
  color: #666;
}
</style>
