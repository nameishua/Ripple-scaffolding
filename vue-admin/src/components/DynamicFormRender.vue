<template>
  <el-form ref="formRef" :model="model" :rules="readonly ? {} : formRules" label-width="100px" :disabled="readonly">
    <el-form-item
      v-for="field in fields"
      :key="field.key"
      :label="field.label"
      :prop="field.key"
    >
      <el-input v-if="field.type === 'input'" v-model="model[field.key]" :placeholder="field.placeholder" />
      <el-input-number v-else-if="field.type === 'number'" v-model="model[field.key]" style="width: 100%" />
      <el-input v-else-if="field.type === 'textarea'" v-model="model[field.key]" type="textarea" :rows="3" />
      <el-select v-else-if="field.type === 'select'" v-model="model[field.key]" style="width: 100%" clearable>
        <el-option v-for="opt in field.options || []" :key="opt.value" :label="opt.label" :value="opt.value" />
      </el-select>
      <el-date-picker
        v-else-if="field.type === 'date'"
        v-model="model[field.key]"
        type="date"
        value-format="YYYY-MM-DD"
        style="width: 100%"
      />
      <el-switch v-else-if="field.type === 'switch'" v-model="model[field.key]" />
      <el-input v-else v-model="model[field.key]" />
    </el-form-item>
    <el-empty v-if="!fields.length" description="暂无表单字段" :image-size="48" />
  </el-form>
</template>

<script setup>
import { ref, reactive, watch, computed } from 'vue'

const props = defineProps({
  schemaJson: { type: String, default: '{"fields":[]}' },
  modelValue: { type: Object, default: () => ({}) },
  readonly: { type: Boolean, default: false }
})

const emit = defineEmits(['update:modelValue'])

const formRef = ref()
const model = reactive({})

const fields = computed(() => {
  try {
    const schema = JSON.parse(props.schemaJson || '{}')
    return schema.fields || []
  } catch {
    return []
  }
})

const formRules = computed(() => {
  const rules = {}
  fields.value.forEach(f => {
    if (f.required) {
      rules[f.key] = [{ required: true, message: `请填写${f.label}`, trigger: 'blur' }]
    }
  })
  return rules
})

watch(() => props.modelValue, (val) => {
  Object.keys(model).forEach(k => delete model[k])
  Object.assign(model, val || {})
}, { immediate: true, deep: true })

watch(model, () => {
  emit('update:modelValue', { ...model })
}, { deep: true })

watch(fields, (list) => {
  list.forEach(f => {
    if (model[f.key] === undefined) {
      model[f.key] = f.type === 'number' ? 0 : f.type === 'switch' ? false : ''
    }
  })
}, { immediate: true })

const validate = async () => {
  return formRef.value.validate().catch(() => false)
}

defineExpose({ validate })
</script>
