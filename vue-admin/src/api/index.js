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

export default api
