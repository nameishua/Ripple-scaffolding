<template>
  <SimpleCrud title="通知公告" :columns="columns" :fields="fields" :load-list="getNoticeList" :save-row="saveNotice" :delete-row="deleteNotice" />
</template>
<script setup>
import SimpleCrud from '../components/SimpleCrud.vue'
import { getNoticeList, saveNotice, deleteNotice } from '../api'
const columns = [
  { prop: 'title', label: '标题', minWidth: 180 },
  { prop: 'noticeType', label: '类型', width: 100 },
  { prop: 'status', label: '状态', width: 100 }
]
const fields = [
  { prop: 'title', label: '标题' },
  { prop: 'noticeType', label: '类型' },
  { prop: 'status', label: '状态' },
  { prop: 'content', label: '内容', type: 'textarea' }
]
</script>
