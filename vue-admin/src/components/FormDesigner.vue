<template>
  <div class="form-designer">
    <div class="designer-body">
      <aside class="panel palette-panel">
        <div class="panel-title">组件库</div>
        <div
          v-for="item in palette"
          :key="item.type"
          class="palette-item"
          draggable="true"
          @dragstart="onPaletteDragStart($event, item.type)"
          @dragend="onDragEnd"
          @click="insertField(item.type, fields.length)"
        >
          <el-icon class="palette-icon"><component :is="item.icon" /></el-icon>
          <span>{{ item.label }}</span>
        </div>
      </aside>

      <main
        class="panel canvas-panel"
        @dragover.prevent
        @drop="onCanvasDrop($event, fields.length)"
      >
        <div class="panel-title">表单画布</div>
        <div v-if="!fields.length" class="canvas-empty">
          从左侧拖拽组件到此处
        </div>
        <div
          v-for="(field, index) in fields"
          :key="field._id"
          class="canvas-item"
          :class="{ active: selectedIndex === index, 'drag-over': dragOverIndex === index }"
          draggable="true"
          @click="selectField(index)"
          @dragstart="onCanvasDragStart($event, index)"
          @dragover.prevent="onCanvasDragOver(index)"
          @drop.stop="onCanvasDrop($event, index)"
          @dragend="onDragEnd"
        >
          <span class="drag-handle" title="拖动排序">⋮⋮</span>
          <div class="canvas-item-body">
            <span class="field-label">{{ field.label }}</span>
            <span class="field-meta">{{ typeLabel(field.type) }} · {{ field.key }}</span>
            <el-tag v-if="field.required" size="small" type="danger">必填</el-tag>
          </div>
          <el-button type="danger" link :icon="Delete" @click.stop="removeField(index)" />
        </div>
        <div
          v-if="fields.length"
          class="canvas-drop-tail"
          :class="{ 'drag-over': dragOverIndex === fields.length }"
          @dragover.prevent="onCanvasDragOver(fields.length)"
          @drop="onCanvasDrop($event, fields.length)"
        >
          拖放到此处追加字段
        </div>
      </main>

      <aside class="panel props-panel">
        <div class="panel-title">字段属性</div>
        <template v-if="selectedField">
          <el-form label-width="72px" size="small" @submit.prevent>
            <el-form-item label="字段标识">
              <el-input v-model="selectedField.key" placeholder="英文字母/数字/下划线" @change="emitSchema" />
            </el-form-item>
            <el-form-item label="显示名称">
              <el-input v-model="selectedField.label" @change="emitSchema" />
            </el-form-item>
            <el-form-item label="组件类型">
              <el-select v-model="selectedField.type" style="width: 100%" @change="onTypeChange">
                <el-option v-for="p in palette" :key="p.type" :label="p.label" :value="p.type" />
              </el-select>
            </el-form-item>
            <el-form-item label="必填">
              <el-switch v-model="selectedField.required" @change="emitSchema" />
            </el-form-item>
            <el-form-item v-if="selectedField.type === 'input'" label="占位提示">
              <el-input v-model="selectedField.placeholder" @change="emitSchema" />
            </el-form-item>
            <template v-if="selectedField.type === 'select'">
              <el-form-item label="选项">
                <div class="options-editor">
                  <div v-for="(opt, i) in selectedField.options" :key="i" class="option-row">
                    <el-input v-model="opt.label" placeholder="标签" @change="syncOptionValue(i)" />
                    <el-input v-model="opt.value" placeholder="值" @change="emitSchema" />
                    <el-button type="danger" link :icon="Delete" @click="removeOption(i)" />
                  </div>
                  <el-button size="small" @click="addOption">添加选项</el-button>
                </div>
              </el-form-item>
            </template>
          </el-form>
        </template>
        <el-empty v-else description="选中画布中的字段以编辑属性" :image-size="64" />
      </aside>
    </div>

    <el-divider />

    <div class="preview-section">
      <div class="panel-title">实时预览</div>
      <div class="preview-box">
        <DynamicFormRender :schema-json="previewSchema" v-model="previewModel" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { Delete } from '@element-plus/icons-vue'
import DynamicFormRender from './DynamicFormRender.vue'
import {
  COMPONENT_PALETTE,
  parseSchemaJson,
  serializeSchema,
  createField,
  normalizeField
} from '../utils/formSchema.js'

const props = defineProps({
  modelValue: { type: String, default: '{"fields":[]}' }
})

const emit = defineEmits(['update:modelValue'])

const palette = COMPONENT_PALETTE
const fields = ref([])
const selectedIndex = ref(-1)
const previewModel = ref({})
const dragSource = ref(null)
const dragFromIndex = ref(-1)
const dragOverIndex = ref(-1)

const PALETTE_PREFIX = 'palette:'

const selectedField = computed(() => {
  if (selectedIndex.value < 0 || selectedIndex.value >= fields.value.length) return null
  return fields.value[selectedIndex.value]
})

const previewSchema = computed(() => serializeSchema(fields.value))

const typeLabel = (type) => palette.find(p => p.type === type)?.label || type

const loadFromSchema = (schemaJson) => {
  const parsed = parseSchemaJson(schemaJson)
  fields.value = parsed.map(f => ({
    ...f,
    _id: f._id || `${f.key}_${Math.random().toString(36).slice(2, 8)}`
  }))
  if (selectedIndex.value >= fields.value.length) {
    selectedIndex.value = fields.value.length ? 0 : -1
  }
}

watch(() => props.modelValue, (val) => {
  if (serializeSchema(fields.value) === val) return
  loadFromSchema(val)
}, { immediate: true })

const emitSchema = () => {
  fields.value = fields.value.map(f => ({ ...normalizeField(f), _id: f._id }))
  emit('update:modelValue', serializeSchema(fields.value))
}

const selectField = (index) => {
  selectedIndex.value = index
}

const removeField = (index) => {
  fields.value.splice(index, 1)
  if (selectedIndex.value === index) selectedIndex.value = -1
  else if (selectedIndex.value > index) selectedIndex.value -= 1
  emitSchema()
}

const insertField = (type, index) => {
  const keys = fields.value.map(f => f.key)
  const field = createField(type, keys)
  fields.value.splice(index, 0, field)
  selectedIndex.value = index
  emitSchema()
}

const moveField = (from, to) => {
  if (from === to || from < 0 || from >= fields.value.length) return
  const list = [...fields.value]
  const [item] = list.splice(from, 1)
  const target = to > from ? to - 1 : to
  list.splice(target, 0, item)
  fields.value = list
  selectedIndex.value = target
  emitSchema()
}

const onPaletteDragStart = (e, type) => {
  dragSource.value = 'palette'
  e.dataTransfer.effectAllowed = 'copy'
  e.dataTransfer.setData('text/plain', `${PALETTE_PREFIX}${type}`)
}

const onCanvasDragStart = (e, index) => {
  dragSource.value = 'canvas'
  dragFromIndex.value = index
  e.dataTransfer.effectAllowed = 'move'
  e.dataTransfer.setData('text/plain', `canvas:${index}`)
}

const onCanvasDragOver = (index) => {
  dragOverIndex.value = index
}

const onCanvasDrop = (e, index) => {
  e.preventDefault()
  const raw = e.dataTransfer.getData('text/plain')
  if (raw.startsWith(PALETTE_PREFIX)) {
    insertField(raw.slice(PALETTE_PREFIX.length), index)
  } else if (raw.startsWith('canvas:') && dragSource.value === 'canvas') {
    moveField(dragFromIndex.value, index)
  }
  onDragEnd()
}

const onDragEnd = () => {
  dragSource.value = null
  dragFromIndex.value = -1
  dragOverIndex.value = -1
}

const onTypeChange = () => {
  const f = selectedField.value
  if (!f) return
  if (f.type === 'select' && !f.options) {
    f.options = [{ label: '选项1', value: '1' }, { label: '选项2', value: '2' }]
  }
  if (f.type !== 'input') delete f.placeholder
  emitSchema()
}

const addOption = () => {
  const f = selectedField.value
  if (!f?.options) return
  const n = f.options.length + 1
  f.options.push({ label: `选项${n}`, value: String(n) })
  emitSchema()
}

const removeOption = (i) => {
  const f = selectedField.value
  if (!f?.options || f.options.length <= 1) return
  f.options.splice(i, 1)
  emitSchema()
}

const syncOptionValue = (i) => {
  const f = selectedField.value
  if (f?.options?.[i] && !f.options[i].value) {
    f.options[i].value = String(i + 1)
  }
  emitSchema()
}

defineExpose({
  getFields: () => fields.value,
  validate: () => {
    const keys = new Set()
    for (const f of fields.value) {
      if (!f.key || !/^[a-zA-Z_][a-zA-Z0-9_]*$/.test(f.key)) {
        return { ok: false, message: `字段「${f.label}」的标识不合法` }
      }
      if (keys.has(f.key)) return { ok: false, message: `字段标识重复: ${f.key}` }
      keys.add(f.key)
    }
    if (!fields.value.length) return { ok: false, message: '请至少添加一个表单字段' }
    return { ok: true }
  }
})
</script>

<style scoped>
.form-designer {
  width: 100%;
}

.designer-body {
  display: grid;
  grid-template-columns: 160px 1fr 260px;
  gap: 12px;
  min-height: 360px;
}

.panel {
  border: 1px solid var(--el-border-color-lighter);
  border-radius: 8px;
  background: var(--el-fill-color-blank);
  overflow: hidden;
}

.panel-title {
  padding: 10px 12px;
  font-size: 13px;
  font-weight: 600;
  color: var(--el-text-color-primary);
  border-bottom: 1px solid var(--el-border-color-lighter);
  background: var(--el-fill-color-light);
}

.palette-panel {
  padding-bottom: 8px;
}

.palette-item {
  display: flex;
  align-items: center;
  gap: 8px;
  margin: 8px 10px 0;
  padding: 10px 12px;
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: grab;
  font-size: 13px;
  transition: border-color 0.15s, background 0.15s;
}

.palette-item:hover {
  border-color: var(--el-color-primary);
  background: var(--el-color-primary-light-9);
}

.palette-icon {
  color: var(--el-color-primary);
}

.canvas-panel {
  display: flex;
  flex-direction: column;
  min-height: 360px;
}

.canvas-empty {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--el-text-color-secondary);
  font-size: 13px;
  padding: 24px;
}

.canvas-item {
  display: flex;
  align-items: center;
  gap: 8px;
  margin: 8px 12px 0;
  padding: 10px 12px;
  border: 1px solid var(--el-border-color-lighter);
  border-radius: 6px;
  cursor: pointer;
  transition: border-color 0.15s, box-shadow 0.15s;
}

.canvas-item.active,
.canvas-item.drag-over {
  border-color: var(--el-color-primary);
  box-shadow: 0 0 0 1px var(--el-color-primary-light-7);
}

.canvas-item-body {
  flex: 1;
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 8px;
  min-width: 0;
}

.field-label {
  font-weight: 500;
}

.field-meta {
  font-size: 12px;
  color: var(--el-text-color-secondary);
}

.drag-handle {
  cursor: grab;
  color: var(--el-text-color-placeholder);
  user-select: none;
  font-size: 12px;
  letter-spacing: -2px;
}

.canvas-drop-tail {
  margin: 8px 12px 12px;
  padding: 12px;
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  text-align: center;
  font-size: 12px;
  color: var(--el-text-color-secondary);
}

.canvas-drop-tail.drag-over {
  border-color: var(--el-color-primary);
  background: var(--el-color-primary-light-9);
}

.props-panel {
  padding-bottom: 12px;
}

.props-panel .el-form {
  padding: 12px;
}

.options-editor {
  width: 100%;
}

.option-row {
  display: flex;
  gap: 6px;
  margin-bottom: 6px;
  align-items: center;
}

.preview-section {
  margin-top: 4px;
}

.preview-box {
  padding: 16px 20px;
  border: 1px solid var(--el-border-color-lighter);
  border-radius: 8px;
  background: #fafafa;
  max-width: 640px;
}
</style>
