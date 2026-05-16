<template>
  <div>
    <SimpleCrud
      title="字典类型"
      :columns="typeColumns"
      :fields="typeFields"
      :load-list="getDictTypeList"
      :save-row="saveDictType"
      :delete-row="deleteDictType"
    />
    <SimpleCrud
      style="margin-top: 16px"
      title="字典数据"
      :columns="dataColumns"
      :fields="dataFields"
      :load-list="getDictDataList"
      :save-row="saveDictData"
      :delete-row="deleteDictData"
    />
  </div>
</template>
<script setup>
import SimpleCrud from '../components/SimpleCrud.vue'
import {
  getDictTypeList, saveDictType, deleteDictType,
  getDictDataList, saveDictData, deleteDictData
} from '../api'
const typeColumns = [
  { prop: 'name', label: '名称' },
  { prop: 'code', label: '编码', width: 140 },
  { prop: 'status', label: '状态', width: 90 }
]
const typeFields = [
  { prop: 'name', label: '名称' },
  { prop: 'code', label: '编码' },
  { prop: 'status', label: '状态' },
  { prop: 'remark', label: '备注', type: 'textarea' }
]
const dataColumns = [
  { prop: 'dictCode', label: '字典编码', width: 120 },
  { prop: 'label', label: '标签' },
  { prop: 'value', label: '值', width: 100 },
  { prop: 'sortNo', label: '排序', width: 80 }
]
const dataFields = [
  { prop: 'dictCode', label: '字典编码' },
  { prop: 'label', label: '标签' },
  { prop: 'value', label: '值' },
  { prop: 'sortNo', label: '排序', type: 'number' },
  { prop: 'status', label: '状态' }
]
</script>
