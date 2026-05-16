<template>
  <SimpleCrud title="登录日志" :columns="columns" :fields="[]" :load-list="getLoginLogList" :save-row="noopSave" :delete-row="deleteLoginLog" :show-add="false" :show-edit="false" />
</template>
<script setup>
import SimpleCrud from '../components/SimpleCrud.vue'
import { getLoginLogList, deleteLoginLog } from '../api'
const noopSave = async () => ({ success: false, message: '只读' })
const columns = [
  { prop: 'account', label: '账号', width: 100 },
  { prop: 'loginIp', label: 'IP', width: 120 },
  { prop: 'browser', label: '浏览器', width: 100 },
  { prop: 'status', label: '状态', width: 80 },
  { prop: 'loginTime', label: '时间', minWidth: 160 }
]
</script>
