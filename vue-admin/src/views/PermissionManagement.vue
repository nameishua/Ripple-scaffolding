<template>
  <SimpleCrud
    title="权限管理"
    :columns="columns"
    :fields="fields"
    :load-list="getPermissionList"
    :save-row="savePermission"
    :delete-row="deletePermission"
  />
</template>

<script setup>
import SimpleCrud from '../components/SimpleCrud.vue'
import { getPermissionList, savePermission, deletePermission } from '../api'

const columns = [
  { prop: 'name', label: '权限名称' },
  { prop: 'code', label: '权限编码', width: 160 },
  { prop: 'type', label: '类型', width: 100 },
  { prop: 'description', label: '说明', minWidth: 160 }
]
const fields = [
  { prop: 'name', label: '权限名称' },
  { prop: 'code', label: '权限编码' },
  { prop: 'type', label: '类型' },
  { prop: 'description', label: '说明', type: 'textarea' }
]
</script>
