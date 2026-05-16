export const START_NODE = { key: 'start', name: '开始', type: 'start' }
export const END_NODE = { key: 'end', name: '结束', type: 'end' }

let uid = 0
export const nextNodeId = () => `n_${Date.now()}_${++uid}`

export function parseDefinitionJson(definitionJson) {
  try {
    const def = JSON.parse(definitionJson || '{}')
    const nodes = Array.isArray(def.nodes) ? def.nodes : []
    const approvals = nodes
      .filter(n => n && n.type === 'approval')
      .map(n => normalizeApprovalNode(n))
    return { approvals, raw: def }
  } catch {
    return { approvals: [], raw: { nodes: [], edges: [] } }
  }
}

export function normalizeApprovalNode(node) {
  return {
    key: String(node.key || '').trim() || `node_${nextNodeId()}`,
    name: String(node.name || '审批').trim(),
    type: 'approval',
    assignee: String(node.assignee || 'admin').trim()
  }
}

export function createApprovalNode(existingKeys = []) {
  let n = existingKeys.length + 1
  let key = `step_${n}`
  while (existingKeys.includes(key)) {
    n += 1
    key = `step_${n}`
  }
  return {
    _id: nextNodeId(),
    key,
    name: `审批节点${n}`,
    type: 'approval',
    assignee: 'admin'
  }
}

/** 根据审批链自动生成引擎可用的边 */
export function buildEdges(approvalNodes) {
  const edges = []
  const list = approvalNodes.map(n => normalizeApprovalNode(n))
  if (!list.length) {
    edges.push({ from: START_NODE.key, to: END_NODE.key })
    return edges
  }
  edges.push({ from: START_NODE.key, to: list[0].key })
  list.forEach((node, i) => {
    const next = list[i + 1]
    if (next) {
      edges.push({ from: node.key, to: next.key, on: 'approve' })
      edges.push({ from: node.key, to: END_NODE.key, on: 'reject' })
    } else {
      edges.push({ from: node.key, to: END_NODE.key, on: 'approve' })
      edges.push({ from: node.key, to: END_NODE.key, on: 'reject' })
    }
  })
  return edges
}

export function serializeDefinition(approvalNodes) {
  const approvals = approvalNodes.map(n => {
    const { _id, ...rest } = n
    return normalizeApprovalNode(rest)
  })
  const nodes = [START_NODE, ...approvals, END_NODE]
  const edges = buildEdges(approvals)
  return JSON.stringify({ nodes, edges })
}

export function defaultDefinitionJson() {
  return serializeDefinition([
    {
      _id: nextNodeId(),
      key: 'mgr',
      name: '经理审批',
      type: 'approval',
      assignee: 'admin'
    }
  ])
}

export function validateDefinition(approvalNodes) {
  if (!approvalNodes.length) {
    return { ok: false, message: '请至少添加一个审批节点' }
  }
  const keys = new Set()
  for (const n of approvalNodes) {
    if (!n.key || !/^[a-zA-Z_][a-zA-Z0-9_]*$/.test(n.key)) {
      return { ok: false, message: `节点「${n.name}」的标识不合法` }
    }
    if (keys.has(n.key)) return { ok: false, message: `节点标识重复: ${n.key}` }
    keys.add(n.key)
    if (n.key === 'start' || n.key === 'end') {
      return { ok: false, message: '审批节点不能使用 start / end 作为标识' }
    }
    if (!n.assignee?.trim()) {
      return { ok: false, message: `请为「${n.name}」指定审批人` }
    }
  }
  return { ok: true }
}
