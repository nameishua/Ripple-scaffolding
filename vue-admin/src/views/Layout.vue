<template>
  <el-container class="layout-container">
    <el-aside width="220px">
      <div class="logo">Ripple Admin</div>
      <el-menu
        :default-active="activeMenu"
        class="el-menu-vertical"
        router
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
      >
        <el-menu-item index="/dashboard">
          <el-icon><HomeFilled /></el-icon>
          <span>首页</span>
        </el-menu-item>

        <el-sub-menu index="sys">
          <template #title>
            <el-icon><Setting /></el-icon>
            <span>系统管理</span>
          </template>
          <el-menu-item index="/user">用户管理</el-menu-item>
          <el-menu-item index="/role">角色管理</el-menu-item>
          <el-menu-item index="/menu">菜单管理</el-menu-item>
          <el-menu-item index="/permission">权限管理</el-menu-item>
          <el-menu-item index="/dept">部门管理</el-menu-item>
          <el-menu-item index="/dict">字典管理</el-menu-item>
          <el-menu-item index="/config">参数配置</el-menu-item>
          <el-menu-item index="/notice">通知公告</el-menu-item>
          <el-menu-item index="/file">文件管理</el-menu-item>
          <el-menu-item index="/job">定时任务</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="monitor">
          <template #title>
            <el-icon><Monitor /></el-icon>
            <span>监控中心</span>
          </template>
          <el-menu-item index="/oper-log">操作日志</el-menu-item>
          <el-menu-item index="/login-log">登录日志</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="biz">
          <template #title>
            <el-icon><Shop /></el-icon>
            <span>业务管理</span>
          </template>
          <el-menu-item index="/order">订单管理</el-menu-item>
          <el-menu-item index="/product">商品管理</el-menu-item>
          <el-menu-item index="/customer">客户管理</el-menu-item>
        </el-sub-menu>

        <el-sub-menu index="wf">
          <template #title>
            <el-icon><Document /></el-icon>
            <span>表单与工作流</span>
          </template>
          <el-menu-item index="/form-def">表单配置</el-menu-item>
          <el-menu-item index="/form-fill">表单填写</el-menu-item>
          <el-menu-item index="/workflow-def">流程配置</el-menu-item>
          <el-menu-item index="/workflow-instance">流程实例</el-menu-item>
          <el-menu-item index="/workflow-task">我的待办</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-content">
          <span class="title">Ripple 企业后台管理系统</span>
          <div class="user-info">
            <span>{{ userName }}</span>
            <el-button type="danger" size="small" @click="handleLogout">退出</el-button>
          </div>
        </div>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getUserInfo } from '../api'

const router = useRouter()
const route = useRoute()
const userName = ref('')

const activeMenu = computed(() => route.path)

onMounted(async () => {
  try {
    const res = await getUserInfo()
    if (res.success && res.data.user) {
      userName.value = res.data.user.name || res.data.user.account
    }
  } catch (error) {
    console.error('获取用户信息失败', error)
  }
})

const handleLogout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('userName')
  ElMessage.success('已退出登录')
  router.push('/login')
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.el-aside {
  background-color: #304156;
}

.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-size: 18px;
  font-weight: bold;
  background-color: #263445;
}

.el-menu {
  border: none;
}

.el-header {
  background-color: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  display: flex;
  align-items: center;
}

.header-content {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title {
  font-size: 18px;
  font-weight: bold;
  color: #333;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 15px;
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>
