<template>
  <div class="workflow-designer">
    <div class="designer-body">
      <aside class="panel palette-panel">
        <div class="panel-title">节点库</div>
        <div
          class="palette-item approval"
          draggable="true"
          @dragstart="onPaletteDragStart"
          @dragend="onDragEnd"
          @click="addApproval(approvalNodes.length)"
        >
          <el-icon class="palette-icon"><User /></el-icon>
          <span>审批节点</span>
        </div>
        <p class="palette-hint">拖拽或点击添加到流程中</p>
      </aside>

      <main
        class="panel canvas-panel"
        @dragover.prevent
        @drop="onDropAt(approvalNodes.length)"
      >
        <div class="panel-title">流程画布</div>

        <div class="flow-chain">
          <div class="flow-node fixed start">
            <el-icon><VideoPlay /></el-icon>
            <span>开始</span>
          </div>
          <div class="flow-arrow">↓</div>

          <template v-if="!approvalNodes.length">
            <div
              class="flow-drop-zone"
              :class="{ 'drag-over': dragOverIndex === 0 }"
              @dragover.prevent="dragOverIndex = 0"
              @drop="onDropAt(0)"
            >
              将审批节点拖放到此处
            </div>
            <div class="flow-arrow">↓</div>
          </template>

          <template v-for="(node, index) in approvalNodes" :key="node._id">
            <div
              class="flow-node approval selectable"
              :class="{ active: selectedIndex === index, 'drag-over': dragOverIndex === index }"
              draggable="true"
              @click="selectNode(index)"
              @dragstart="onNodeDragStart($event, index)"
              @dragover.prevent="dragOverIndex = index"
              @drop.stop="onDropAt(index)"
              @dragend="onDragEnd"
            >
              <span class="drag-handle" title="拖动排序">⋮⋮</span>
              <el-icon><User /></el-icon>
              <div class="node-text">
                <strong>{{ node.name }}</strong>
                <small>{{ node.key }} · {{ node.assignee }}</small>
              </div>
              <el-button type="danger" link :icon="Delete" @click.stop="removeNode(index)" />
            </div>
            <div class="flow-arrow">↓</div>
            <div
              v-if="index === approvalNodes.length - 1"
              class="flow-drop-zone tail"
              :class="{ 'drag-over': dragOverIndex === approvalNodes.length }"
              @dragover.prevent="dragOverIndex = approvalNodes.length"
              @drop="onDropAt(approvalNodes.length)"
            >
              拖放到此处追加节点
            </div>
            <div v-if="index === approvalNodes.length - 1" class="flow-arrow">↓</div>
          </template>

          <div class="flow-node fixed end">
            <el-icon><CircleCheck /></el-icon>
            <span>结束</span>
          </div>
        </div>

        <div class="edge-legend">
          <span><el-tag size="small" type="success">通过</el-tag> 进入下一节点或结束</span>
          <span><el-tag size="small" type="danger">驳回</el-tag> 直接结束流程</span>
        </div>
      </main>

      <aside class="panel props-panel">
        <div class="panel-title">节点属性</div>
        <template v-if="selectedNode">
          <el-form label-width="72px" size="small">
            <el-form-item label="节点标识">
              <el-input v-model="selectedNode.key" @change="emitDefinition" />
            </el-form-item>
            <el-form-item label="节点名称">
              <el-input v-model="selectedNode.name" @change="emitDefinition" />
            </el-form-item>
            <el-form-item label="审批人">
              <el-input v-model="selectedNode.assignee" placeholder="账号，如 admin" @change="emitDefinition" />
            </el-form-item>
          </el-form>
        </template>
        <el-empty v-else description="选中审批节点以编辑属性" :image-size="64" />
      </aside>
    </div>

    <el-divider />

    <div class="preview-section">
      <div class="panel-title">流程预览</div>
      <div class="preview-diagram">
        <template v-for="(step, i) in previewSteps" :key="step.key">
          <div class="preview-step" :class="step.type">
            <span class="preview-name">{{ step.name }}</span>
            <span v-if="step.sub" class="preview-sub">{{ step.sub }}</span>
          </div>
          <div v-if="i < previewSteps.length - 1" class="preview-link">
            <span v-if="step.type === 'approval'" class="link-label">通过 ↓ / 驳回 → 结束</span>
            <span v-else>↓</span>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { Delete, User, VideoPlay, CircleCheck } from '@element-plus/icons-vue'
import {
  parseDefinitionJson,
  serializeDefinition,
  createApprovalNode,
  validateDefinition,
  buildEdges,
  START_NODE,
  END_NODE
} from '../utils/workflowSchema.js'

const props = defineProps({
  modelValue: { type: String, default: '{"nodes":[],"edges":[]}' }
})

const emit = defineEmits(['update:modelValue'])

const PALETTE_PREFIX = 'wf-approval'

const approvalNodes = ref([])
const selectedIndex = ref(-1)
const dragSource = ref(null)
const dragFromIndex = ref(-1)
const dragOverIndex = ref(-1)

const selectedNode = computed(() => {
  if (selectedIndex.value < 0 || selectedIndex.value >= approvalNodes.value.length) return null
  return approvalNodes.value[selectedIndex.value]
})

const previewSteps = computed(() => {
  const steps = [{ key: 'start', name: '开始', type: 'start' }]
  approvalNodes.value.forEach(n => {
    steps.push({ key: n.key, name: n.name, type: 'approval', sub: `审批人: ${n.assignee}` })
  })
  steps.push({ key: 'end', name: '结束', type: 'end' })
  return steps
})

const loadFromJson = (json) => {
  const { approvals } = parseDefinitionJson(json)
  approvalNodes.value = approvals.map(n => ({
    ...n,
    _id: n._id || `${n.key}_${Math.random().toString(36).slice(2, 8)}`
  }))
  if (selectedIndex.value >= approvalNodes.value.length) {
    selectedIndex.value = approvalNodes.value.length ? 0 : -1
  }
}

watch(() => props.modelValue, (val) => {
  if (serializeDefinition(approvalNodes.value) === val) return
  loadFromJson(val)
}, { immediate: true })

const emitDefinition = () => {
  emit('update:modelValue', serializeDefinition(approvalNodes.value))
}

const selectNode = (index) => {
  selectedIndex.value = index
}

const addApproval = (index) => {
  const keys = approvalNodes.value.map(n => n.key)
  const node = createApprovalNode(keys)
  approvalNodes.value.splice(index, 0, node)
  selectedIndex.value = index
  emitDefinition()
}

const removeNode = (index) => {
  approvalNodes.value.splice(index, 1)
  if (selectedIndex.value === index) selectedIndex.value = -1
  else if (selectedIndex.value > index) selectedIndex.value -= 1
  emitDefinition()
}

const moveNode = (from, to) => {
  if (from === to || from < 0 || from >= approvalNodes.value.length) return
  const list = [...approvalNodes.value]
  const [item] = list.splice(from, 1)
  const target = to > from ? to - 1 : to
  list.splice(target, 0, item)
  approvalNodes.value = list
  selectedIndex.value = target
  emitDefinition()
}

const onPaletteDragStart = (e) => {
  dragSource.value = 'palette'
  e.dataTransfer.effectAllowed = 'copy'
  e.dataTransfer.setData('text/plain', PALETTE_PREFIX)
}

const onNodeDragStart = (e, index) => {
  dragSource.value = 'canvas'
  dragFromIndex.value = index
  e.dataTransfer.effectAllowed = 'move'
  e.dataTransfer.setData('text/plain', `canvas:${index}`)
}

const onDropAt = (index) => {
  if (dragSource.value === 'palette') {
    addApproval(index)
  } else if (dragSource.value === 'canvas') {
    moveNode(dragFromIndex.value, index)
  }
  onDragEnd()
}

const onDragEnd = () => {
  dragSource.value = null
  dragFromIndex.value = -1
  dragOverIndex.value = -1
}

defineExpose({
  validate: () => validateDefinition(approvalNodes.value),
  getEdges: () => buildEdges(approvalNodes.value),
  getNodes: () => [START_NODE, ...approvalNodes.value, END_NODE]
})
</script>

<style scoped>
.workflow-designer {
  width: 100%;
}

.designer-body {
  display: grid;
  grid-template-columns: 140px 1fr 240px;
  gap: 12px;
  min-height: 380px;
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
  margin: 10px;
  padding: 12px;
  border: 1px dashed var(--el-border-color);
  border-radius: 8px;
  cursor: grab;
  font-size: 13px;
}

.palette-item.approval {
  border-color: var(--el-color-warning-light-5);
  background: var(--el-color-warning-light-9);
}

.palette-item:hover {
  border-color: var(--el-color-primary);
}

.palette-icon {
  color: var(--el-color-warning);
}

.palette-hint {
  margin: 8px 12px 0;
  font-size: 12px;
  color: var(--el-text-color-secondary);
  line-height: 1.4;
}

.canvas-panel {
  padding-bottom: 12px;
}

.flow-chain {
  padding: 16px 24px 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.flow-node {
  display: flex;
  align-items: center;
  gap: 10px;
  min-width: 280px;
  max-width: 420px;
  width: 100%;
  padding: 12px 14px;
  border-radius: 8px;
  border: 1px solid var(--el-border-color-lighter);
  background: #fff;
  font-size: 13px;
}

.flow-node.fixed {
  justify-content: center;
  font-weight: 600;
  min-width: 120px;
  width: auto;
  padding: 10px 24px;
}

.flow-node.fixed.start {
  border-color: var(--el-color-success-light-5);
  background: var(--el-color-success-light-9);
  color: var(--el-color-success-dark-2);
}

.flow-node.fixed.end {
  border-color: var(--el-color-info-light-5);
  background: var(--el-fill-color-light);
}

.flow-node.approval {
  cursor: pointer;
  border-color: var(--el-color-warning-light-5);
}

.flow-node.approval.active,
.flow-node.approval.drag-over {
  border-color: var(--el-color-primary);
  box-shadow: 0 0 0 2px var(--el-color-primary-light-7);
}

.node-text {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 2px;
  min-width: 0;
}

.node-text small {
  color: var(--el-text-color-secondary);
  font-size: 12px;
}

.drag-handle {
  cursor: grab;
  color: var(--el-text-color-placeholder);
  user-select: none;
}

.flow-arrow {
  color: var(--el-text-color-secondary);
  font-size: 18px;
  line-height: 1;
  padding: 4px 0;
}

.flow-drop-zone {
  min-width: 280px;
  padding: 14px;
  border: 1px dashed var(--el-border-color);
  border-radius: 8px;
  text-align: center;
  font-size: 12px;
  color: var(--el-text-color-secondary);
}

.flow-drop-zone.drag-over {
  border-color: var(--el-color-primary);
  background: var(--el-color-primary-light-9);
}

.edge-legend {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
  justify-content: center;
  padding: 12px 16px 4px;
  font-size: 12px;
  color: var(--el-text-color-secondary);
}

.props-panel .el-form {
  padding: 12px;
}

.preview-section {
  margin-top: 4px;
}

.preview-diagram {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 8px;
  padding: 12px 16px;
  border: 1px solid var(--el-border-color-lighter);
  border-radius: 8px;
  background: #fafafa;
}

.preview-step {
  padding: 8px 14px;
  border-radius: 6px;
  border: 1px solid var(--el-border-color);
  background: #fff;
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.preview-step.start {
  border-color: var(--el-color-success-light-5);
  background: var(--el-color-success-light-9);
}

.preview-step.approval {
  border-color: var(--el-color-warning-light-5);
}

.preview-step.end {
  background: var(--el-fill-color-light);
}

.preview-name {
  font-weight: 500;
  font-size: 13px;
}

.preview-sub {
  font-size: 11px;
  color: var(--el-text-color-secondary);
}

.preview-link {
  font-size: 11px;
  color: var(--el-text-color-secondary);
  padding: 0 4px;
}

.link-label {
  white-space: nowrap;
}
</style>
