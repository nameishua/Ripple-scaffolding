<template>
  <SimpleCrud title="商品管理" :columns="columns" :fields="fields" :load-list="getProductList" :save-row="saveProduct" :delete-row="deleteProduct" />
</template>
<script setup>
import SimpleCrud from '../components/SimpleCrud.vue'
import { getProductList, saveProduct, deleteProduct } from '../api'
const columns = [
  { prop: 'sku', label: 'SKU', width: 120 },
  { prop: 'name', label: '名称' },
  { prop: 'price', label: '价格', width: 100 },
  { prop: 'stock', label: '库存', width: 80 },
  { prop: 'status', label: '状态', width: 100 }
]
const fields = [
  { prop: 'sku', label: 'SKU' },
  { prop: 'name', label: '名称' },
  { prop: 'price', label: '价格', type: 'number' },
  { prop: 'stock', label: '库存', type: 'number' },
  { prop: 'status', label: '状态' }
]
</script>
