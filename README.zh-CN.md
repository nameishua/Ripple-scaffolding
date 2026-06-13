# Ripple 脚手架

[English](README.md)

Ripple 是基于 Spring Boot、MyBatis、Shiro、Druid、Redis、Kafka 的企业级 Java 后台脚手架。本项目提供 **功能完整的 Vue 3 管理端**，各业务模块均附带演示数据，可直接体验。

## 功能概览

### 后端

- JWT + Apache Shiro 认证
- RBAC：用户、角色、菜单、权限
- 系统模块：部门、字典、参数、公告、文件、定时任务
- 审计：操作日志、登录日志
- 业务演示：订单、商品、客户
- 低代码：拖拽式 **表单设计器**、**工作流设计器**
- 统一 `ApiResult` 响应与全局异常处理
- Actuator 健康检查

### 前端（`vue-admin`）

- Vue 3 + Vite + Element Plus
- 首页仪表盘（各模块统计）
- 全模块 CRUD 页面（通用 `SimpleCrud` 组件）
- 可视化表单与工作流配置

## 快速开始

### 1. 环境要求

- JDK 8+
- Maven 3.6+
- MySQL 8+
- Node.js 18+（管理端）

### 2. 初始化数据库

```powershell
cd scripts
.\init-mysql.ps1
```

将依次执行：

| 脚本 | 说明 |
|------|------|
| `schema-mysql.sql` | RBAC 核心表 |
| `schema-workflow-form-mysql.sql` | 表单与工作流表 |
| `schema-system-ext-mysql.sql` | 扩展系统与业务表 |
| `seed-data-mysql.sql` | 用户、角色、菜单、权限 |
| `seed-workflow-form-mysql.sql` | 请假表单与审批流示例 |
| `seed-system-ext-mysql.sql` | 部门、字典、日志、业务演示数据 |

默认管理员：**`admin` / `123`**

### 3. 数据源配置（可选）

```powershell
$env:RIPPLE_DATASOURCE_URL="jdbc:mysql://127.0.0.1:3306/ripple?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai"
$env:RIPPLE_DATASOURCE_USERNAME="root"
$env:RIPPLE_DATASOURCE_PASSWORD="你的密码"
```

### 4. 启动服务

所有脚本已整合到 `scripts/` 目录：

```powershell
cd scripts

# 一键启动前后端（推荐）
.\manage-dev.ps1 start

# 或单独启动后端
.\manage-backend.ps1 start -Profile local

# 或单独启动前端
.\manage-frontend.ps1 start
```

**服务地址**：
- 后端：`http://localhost:8883/ripple`
- 前端：`http://localhost:3000`

### 5. 管理命令

```powershell
cd scripts

# 查看状态
.\manage-dev.ps1 status

# 停止服务
.\manage-dev.ps1 stop

# 重启服务
.\manage-dev.ps1 restart

# 查看日志
.\manage-dev.ps1 logs
.\manage-dev.ps1 logs -Target frontend
```

### 6. 构建项目

```powershell
cd scripts

# 构建后端
.\build-backend.ps1

# 构建前端
.\build-frontend.ps1
```

### 7. 测试接口

```powershell
cd scripts
.\test-apis.ps1
```

## 模块对照

| 菜单 | 接口前缀 | 说明 |
|------|----------|------|
| 首页 | `/admin/dashboard/stats` | 各模块数量统计 |
| 用户管理 | `/admin/user` | 用户 CRUD、分配角色 |
| 角色管理 | `/admin/role` | 角色 CRUD、分配菜单/权限 |
| 菜单管理 | `/admin/menu` | 菜单树维护 |
| 权限管理 | `/admin/permission` | 权限项维护 |
| 部门管理 | `/admin/dept` | 组织架构 |
| 字典管理 | `/admin/dict` | 字典类型与字典项 |
| 参数配置 | `/admin/config` | 系统键值参数 |
| 通知公告 | `/admin/notice` | 公告发布 |
| 文件管理 | `/admin/file` | 文件元数据 |
| 定时任务 | `/admin/job` | 任务定义（演示） |
| 操作日志 | `/admin/log/oper` | 操作审计（可查删） |
| 登录日志 | `/admin/log/login` | 登录记录 |
| 订单/商品/客户 | `/admin/biz/*` | 业务演示数据 |
| 表单配置 | `/admin/form` | 动态表单 |
| 流程配置 | `/admin/workflow` | 审批流程 |

## 目录结构

```
src/main/java/top/coderak/
  modules/admin/controller/   # 管理端 REST API
  modules/auth/               # 登录与 JWT
  entity/                     # 实体
  service/                    # 业务层
src/main/resources/
  schema-*.sql / seed-*.sql   # 数据库脚本
vue-admin/                    # Vue 3 管理端
scripts/init-mysql.ps1        # 一键初始化数据库
start-dev.ps1                 # 一键启动前后端
```

## 运维脚本

```powershell
.\manage-dev.ps1 status     # 查看运行状态
.\manage-dev.ps1 stop        # 停止服务
.\manage-dev.ps1 restart     # 重启服务
```

## 健康检查

- `GET /ripple/framework/info`
- `GET /ripple/actuator/health`

## 说明

- 业务、报表类菜单为演示用途，数据通过种子脚本预置。
- 定时任务模块仅维护任务元数据，不含真实调度执行器。
- 文件模块记录文件信息，上传接口可按需扩展。

## 许可证

请参阅仓库中的许可证文件，按项目政策使用与修改。
