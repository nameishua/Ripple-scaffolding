<template>
  <SimpleCrud title="文件管理" :columns="columns" :fields="fields" :load-list="getFileList" :save-row="saveFile" :delete-row="deleteFile" />
</template>
<script setup>
import SimpleCrud from '../components/SimpleCrud.vue'
import { getFileList, saveFile, deleteFile } from '../api'
const columns = [
  { prop: 'originalName', label: '原始名' },
  { prop: 'fileName', label: '存储名', width: 140 },
  { prop: 'filePath', label: '路径', minWidth: 180 },
  { prop: 'fileSize', label: '大小', width: 100 }
]
const fields = [
  { prop: 'originalName', label: '原始名' },
  { prop: 'fileName', label: '存储名' },
  { prop: 'filePath', label: '路径' },
  { prop: 'fileSize', label: '大小', type: 'number' },
  { prop: 'mimeType', label: 'MIME' }
]
</script>
