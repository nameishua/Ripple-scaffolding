import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000
})

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

api.interceptors.response.use(
  response => response.data,
  error => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

export const loginApi = (data) => {
  const params = new URLSearchParams()
  params.append('account', data.account)
  params.append('password', data.password)
  return api.post('/login/check', params)
}

export const getUserInfo = () => api.get('/admin/user/info')

export const getUserList = () => api.get('/admin/user/list')

export const getAllRoles = () => api.get('/admin/user/roles')

export const addUser = (data) => api.post('/admin/user/add', data)

export const updateUser = (data) => api.post('/admin/user/update', data)

export const deleteUser = (id) => api.post('/admin/user/delete', null, { params: { id } })

export const getUserById = (id) => api.get(`/admin/user/${id}`)

export const getRoleList = () => api.get('/admin/role/list')

export const getMenuTree = () => api.get('/admin/menu/tree')

export const getMenuList = () => api.get('/admin/menu/list')

export const addMenu = (data) => api.post('/admin/menu/add', data)

export const updateMenu = (data) => api.post('/admin/menu/update', data)

export const deleteMenu = (id) => api.post('/admin/menu/delete', null, { params: { id } })

export const addRole = (data) => api.post('/admin/role/add', data)

export const updateRole = (data) => api.post('/admin/role/update', data)

export const deleteRole = (id) => api.post('/admin/role/delete', null, { params: { id } })

export const assignMenus = (roleId, menuIds) => api.post('/admin/role/assignMenus', menuIds, { params: { roleId } })

export const getAllMenus = () => api.get('/admin/role/menus')

export const getAllPermissions = () => api.get('/admin/role/permissions')

export const assignPermissions = (roleId, permissionIds) => api.post('/admin/role/assignPermissions', permissionIds, { params: { roleId } })

export const getFormDefinitionList = () => api.get('/admin/form/definition/list')
export const getPublishedFormDefinitions = () => api.get('/admin/form/definition/published')
export const getFormDefinitionByCode = (code) => api.get(`/admin/form/definition/code/${code}`)
export const addFormDefinition = (data) => api.post('/admin/form/definition/add', data)
export const updateFormDefinition = (data) => api.post('/admin/form/definition/update', data)
export const deleteFormDefinition = (id) => api.post('/admin/form/definition/delete', null, { params: { id } })
export const publishFormDefinition = (id) => api.post('/admin/form/definition/publish', null, { params: { id } })
export const getFormDataList = (formCode) => api.get('/admin/form/data/list', { params: formCode ? { formCode } : {} })
export const submitFormData = (body) => api.post('/admin/form/data/submit', body)

export const getWorkflowDefinitionList = () => api.get('/admin/workflow/definition/list')
export const getPublishedWorkflowDefinitions = () => api.get('/admin/workflow/definition/published')
export const addWorkflowDefinition = (data) => api.post('/admin/workflow/definition/add', data)
export const updateWorkflowDefinition = (data) => api.post('/admin/workflow/definition/update', data)
export const deleteWorkflowDefinition = (id) => api.post('/admin/workflow/definition/delete', null, { params: { id } })
export const publishWorkflowDefinition = (id) => api.post('/admin/workflow/definition/publish', null, { params: { id } })
export const getWorkflowInstanceList = () => api.get('/admin/workflow/instance/list')
export const startWorkflow = (body) => api.post('/admin/workflow/instance/start', body)
export const getWorkflowTodoTasks = () => api.get('/admin/workflow/task/todo')
export const getWorkflowTasks = (instanceId) => api.get('/admin/workflow/task/list', { params: { instanceId } })
export const approveWorkflowTask = (taskId, comment) => api.post('/admin/workflow/task/approve', null, { params: { taskId, comment } })
export const rejectWorkflowTask = (taskId, comment) => api.post('/admin/workflow/task/reject', null, { params: { taskId, comment } })

export const getDashboardStats = () => api.get('/admin/dashboard/stats')

export const getDeptList = () => api.get('/admin/dept/list')
export const saveDept = (data) => api.post('/admin/dept/save', data)
export const deleteDept = (id) => api.post('/admin/dept/delete', null, { params: { id } })

export const getDictTypeList = () => api.get('/admin/dict/type/list')
export const saveDictType = (data) => api.post('/admin/dict/type/save', data)
export const deleteDictType = (id) => api.post('/admin/dict/type/delete', null, { params: { id } })
export const getDictDataList = () => api.get('/admin/dict/data/list')
export const saveDictData = (data) => api.post('/admin/dict/data/save', data)
export const deleteDictData = (id) => api.post('/admin/dict/data/delete', null, { params: { id } })

export const getOperLogList = () => api.get('/admin/log/oper/list')
export const deleteOperLog = (id) => api.post('/admin/log/oper/delete', null, { params: { id } })
export const getLoginLogList = () => api.get('/admin/log/login/list')
export const deleteLoginLog = (id) => api.post('/admin/log/login/delete', null, { params: { id } })

export const getConfigList = () => api.get('/admin/config/list')
export const saveConfig = (data) => api.post('/admin/config/save', data)
export const deleteConfig = (id) => api.post('/admin/config/delete', null, { params: { id } })

export const getNoticeList = () => api.get('/admin/notice/list')
export const saveNotice = (data) => api.post('/admin/notice/save', data)
export const deleteNotice = (id) => api.post('/admin/notice/delete', null, { params: { id } })

export const getFileList = () => api.get('/admin/file/list')
export const saveFile = (data) => api.post('/admin/file/save', data)
export const deleteFile = (id) => api.post('/admin/file/delete', null, { params: { id } })

export const getJobList = () => api.get('/admin/job/list')
export const saveJob = (data) => api.post('/admin/job/save', data)
export const deleteJob = (id) => api.post('/admin/job/delete', null, { params: { id } })

export const getOrderList = () => api.get('/admin/biz/order/list')
export const saveOrder = (data) => api.post('/admin/biz/order/save', data)
export const deleteOrder = (id) => api.post('/admin/biz/order/delete', null, { params: { id } })

export const getProductList = () => api.get('/admin/biz/product/list')
export const saveProduct = (data) => api.post('/admin/biz/product/save', data)
export const deleteProduct = (id) => api.post('/admin/biz/product/delete', null, { params: { id } })

export const getCustomerList = () => api.get('/admin/biz/customer/list')
export const saveCustomer = (data) => api.post('/admin/biz/customer/save', data)
export const deleteCustomer = (id) => api.post('/admin/biz/customer/delete', null, { params: { id } })

export const getPermissionList = () => api.get('/admin/permission/list')
export const savePermission = (data) => (data.id ? api.post('/admin/permission/update', data) : api.post('/admin/permission/add', data))
export const deletePermission = (id) => api.post('/admin/permission/delete', null, { params: { id } })

export default api
