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

export const loginApi = (data) => api.post('/login/check', data)

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

export default api
