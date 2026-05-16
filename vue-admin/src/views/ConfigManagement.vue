<template>
  <SimpleCrud title="参数配置" :columns="columns" :fields="fields" :load-list="getConfigList" :save-row="saveConfig" :delete-row="deleteConfig" />
</template>
<script setup>
import SimpleCrud from '../components/SimpleCrud.vue'
import { getConfigList, saveConfig, deleteConfig } from '../api'
const columns = [
  { prop: 'name', label: '参数名' },
  { prop: 'configKey', label: '键', width: 160 },
  { prop: 'configValue', label: '值', minWidth: 160 },
  { prop: 'remark', label: '备注' }
]
const fields = [
  { prop: 'name', label: '参数名' },
  { prop: 'configKey', label: '键' },
  { prop: 'configValue', label: '值' },
  { prop: 'remark', label: '备注', type: 'textarea' }
]
</script>
