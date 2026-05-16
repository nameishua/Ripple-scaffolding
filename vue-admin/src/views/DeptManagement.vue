<template>
  <SimpleCrud
    title="部门管理"
    :columns="columns"
    :fields="fields"
    :load-list="getDeptList"
    :save-row="saveDept"
    :delete-row="deleteDept"
  />
</template>

<script setup>
import SimpleCrud from '../components/SimpleCrud.vue'
import { getDeptList, saveDept, deleteDept } from '../api'

const columns = [
  { prop: 'name', label: '部门名称' },
  { prop: 'leader', label: '负责人', width: 100 },
  { prop: 'phone', label: '电话', width: 120 },
  { prop: 'sortNo', label: '排序', width: 80 }
]
const fields = [
  { prop: 'name', label: '部门名称' },
  { prop: 'parentId', label: '上级ID' },
  { prop: 'leader', label: '负责人' },
  { prop: 'phone', label: '电话' },
  { prop: 'sortNo', label: '排序', type: 'number' }
]
</script>
