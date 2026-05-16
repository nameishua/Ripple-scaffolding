<template>
  <SimpleCrud title="订单管理" :columns="columns" :fields="fields" :load-list="getOrderList" :save-row="saveOrder" :delete-row="deleteOrder" />
</template>
<script setup>
import SimpleCrud from '../components/SimpleCrud.vue'
import { getOrderList, saveOrder, deleteOrder } from '../api'
const columns = [
  { prop: 'orderNo', label: '订单号', width: 150 },
  { prop: 'customerName', label: '客户' },
  { prop: 'productName', label: '商品' },
  { prop: 'amount', label: '金额', width: 100 },
  { prop: 'status', label: '状态', width: 100 }
]
const fields = [
  { prop: 'orderNo', label: '订单号' },
  { prop: 'customerName', label: '客户' },
  { prop: 'productName', label: '商品' },
  { prop: 'amount', label: '金额', type: 'number' },
  { prop: 'status', label: '状态' }
]
</script>
