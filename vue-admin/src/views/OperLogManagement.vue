<template>
  <SimpleCrud title="操作日志" :columns="columns" :fields="[]" :load-list="getOperLogList" :save-row="noopSave" :delete-row="deleteOperLog" :show-add="false" :show-edit="false" />
</template>
<script setup>
import SimpleCrud from '../components/SimpleCrud.vue'
import { getOperLogList, deleteOperLog } from '../api'
const noopSave = async () => ({ success: false, message: '只读' })
const columns = [
  { prop: 'module', label: '模块', width: 100 },
  { prop: 'action', label: '操作', width: 90 },
  { prop: 'operator', label: '操作人', width: 90 },
  { prop: 'operIp', label: 'IP', width: 120 },
  { prop: 'status', label: '状态', width: 80 },
  { prop: 'operTime', label: '时间', minWidth: 160 }
]
</script>
