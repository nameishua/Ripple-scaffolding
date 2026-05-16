<template>
  <SimpleCrud title="客户管理" :columns="columns" :fields="fields" :load-list="getCustomerList" :save-row="saveCustomer" :delete-row="deleteCustomer" />
</template>
<script setup>
import SimpleCrud from '../components/SimpleCrud.vue'
import { getCustomerList, saveCustomer, deleteCustomer } from '../api'
const columns = [
  { prop: 'name', label: '姓名' },
  { prop: 'phone', label: '电话', width: 130 },
  { prop: 'email', label: '邮箱', minWidth: 160 },
  { prop: 'level', label: '等级', width: 90 }
]
const fields = [
  { prop: 'name', label: '姓名' },
  { prop: 'phone', label: '电话' },
  { prop: 'email', label: '邮箱' },
  { prop: 'level', label: '等级' }
]
</script>
