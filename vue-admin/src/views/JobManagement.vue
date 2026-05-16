<template>
  <SimpleCrud title="定时任务" :columns="columns" :fields="fields" :load-list="getJobList" :save-row="saveJob" :delete-row="deleteJob" />
</template>
<script setup>
import SimpleCrud from '../components/SimpleCrud.vue'
import { getJobList, saveJob, deleteJob } from '../api'
const columns = [
  { prop: 'jobName', label: '任务名' },
  { prop: 'jobGroup', label: '分组', width: 100 },
  { prop: 'cronExpression', label: 'Cron', width: 140 },
  { prop: 'status', label: '状态', width: 90 }
]
const fields = [
  { prop: 'jobName', label: '任务名' },
  { prop: 'jobGroup', label: '分组' },
  { prop: 'cronExpression', label: 'Cron' },
  { prop: 'beanName', label: 'Bean' },
  { prop: 'status', label: '状态' },
  { prop: 'remark', label: '备注', type: 'textarea' }
]
</script>
