const FIELD_TYPES = ['input', 'textarea', 'number', 'select', 'date', 'switch']

export const COMPONENT_PALETTE = [
  { type: 'input', label: '单行文本', icon: 'Edit' },
  { type: 'textarea', label: '多行文本', icon: 'Document' },
  { type: 'number', label: '数字', icon: 'Odometer' },
  { type: 'select', label: '下拉选择', icon: 'ArrowDown' },
  { type: 'date', label: '日期', icon: 'Calendar' },
  { type: 'switch', label: '开关', icon: 'Switch' }
]

let uid = 0
export const nextFieldId = () => `f_${Date.now()}_${++uid}`

export function parseSchemaJson(schemaJson) {
  try {
    const schema = JSON.parse(schemaJson || '{}')
    const fields = Array.isArray(schema.fields) ? schema.fields : []
    return fields
      .filter(f => f && f.key && FIELD_TYPES.includes(f.type))
      .map(f => normalizeField(f))
  } catch {
    return []
  }
}

export function serializeSchema(fields) {
  const clean = fields.map(({ _id, ...rest }) => normalizeField(rest))
  return JSON.stringify({ fields: clean })
}

export function normalizeField(field) {
  const base = {
    key: String(field.key || '').trim(),
    label: String(field.label || '未命名字段').trim(),
    type: FIELD_TYPES.includes(field.type) ? field.type : 'input',
    required: !!field.required
  }
  if (field.placeholder) base.placeholder = String(field.placeholder)
  if (base.type === 'select') {
    base.options = Array.isArray(field.options) && field.options.length
      ? field.options.map(o => ({ label: String(o.label), value: o.value }))
      : [{ label: '选项1', value: '1' }, { label: '选项2', value: '2' }]
  }
  return base
}

export function createField(type, existingKeys = []) {
  const palette = COMPONENT_PALETTE.find(p => p.type === type)
  const label = palette?.label || '字段'
  let key = `field_${existingKeys.length + 1}`
  let n = existingKeys.length + 1
  while (existingKeys.includes(key)) {
    n += 1
    key = `field_${n}`
  }
  const field = normalizeField({ key, label, type, required: false })
  if (type === 'input') field.placeholder = `请输入${label}`
  return { ...field, _id: nextFieldId() }
}

export function defaultSchemaJson() {
  const keys = []
  const fields = [
    { ...createField('input', keys), key: 'title', label: '标题', required: true },
    { ...createField('number', [...keys, 'title']), key: 'amount', label: '金额', required: true },
    { ...createField('textarea', [...keys, 'title', 'amount']), key: 'reason', label: '事由', required: false }
  ]
  return serializeSchema(fields)
}
