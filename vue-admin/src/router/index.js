import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../views/Layout.vue'
import Dashboard from '../views/Dashboard.vue'
import UserManagement from '../views/UserManagement.vue'
import RoleManagement from '../views/RoleManagement.vue'
import MenuManagement from '../views/MenuManagement.vue'
import PermissionManagement from '../views/PermissionManagement.vue'
import DeptManagement from '../views/DeptManagement.vue'
import DictManagement from '../views/DictManagement.vue'
import ConfigManagement from '../views/ConfigManagement.vue'
import NoticeManagement from '../views/NoticeManagement.vue'
import FileManagement from '../views/FileManagement.vue'
import JobManagement from '../views/JobManagement.vue'
import OperLogManagement from '../views/OperLogManagement.vue'
import LoginLogManagement from '../views/LoginLogManagement.vue'
import OrderManagement from '../views/OrderManagement.vue'
import ProductManagement from '../views/ProductManagement.vue'
import CustomerManagement from '../views/CustomerManagement.vue'
import FormDefinitionManagement from '../views/FormDefinitionManagement.vue'
import FormFill from '../views/FormFill.vue'
import WorkflowDefinitionManagement from '../views/WorkflowDefinitionManagement.vue'
import WorkflowInstanceManagement from '../views/WorkflowInstanceManagement.vue'
import WorkflowTaskManagement from '../views/WorkflowTaskManagement.vue'

const routes = [
  { path: '/login', name: 'Login', component: Login },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', name: 'Dashboard', component: Dashboard },
      { path: 'user', name: 'UserManagement', component: UserManagement },
      { path: 'role', name: 'RoleManagement', component: RoleManagement },
      { path: 'menu', name: 'MenuManagement', component: MenuManagement },
      { path: 'permission', name: 'PermissionManagement', component: PermissionManagement },
      { path: 'dept', name: 'DeptManagement', component: DeptManagement },
      { path: 'dict', name: 'DictManagement', component: DictManagement },
      { path: 'config', name: 'ConfigManagement', component: ConfigManagement },
      { path: 'notice', name: 'NoticeManagement', component: NoticeManagement },
      { path: 'file', name: 'FileManagement', component: FileManagement },
      { path: 'job', name: 'JobManagement', component: JobManagement },
      { path: 'oper-log', name: 'OperLogManagement', component: OperLogManagement },
      { path: 'login-log', name: 'LoginLogManagement', component: LoginLogManagement },
      { path: 'order', name: 'OrderManagement', component: OrderManagement },
      { path: 'product', name: 'ProductManagement', component: ProductManagement },
      { path: 'customer', name: 'CustomerManagement', component: CustomerManagement },
      { path: 'form-def', name: 'FormDefinitionManagement', component: FormDefinitionManagement },
      { path: 'form-fill', name: 'FormFill', component: FormFill },
      { path: 'workflow-def', name: 'WorkflowDefinitionManagement', component: WorkflowDefinitionManagement },
      { path: 'workflow-instance', name: 'WorkflowInstanceManagement', component: WorkflowInstanceManagement },
      { path: 'workflow-task', name: 'WorkflowTaskManagement', component: WorkflowTaskManagement }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
