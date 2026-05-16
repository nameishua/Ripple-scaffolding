# Ripple Scaffolding

[中文文档](README.zh-CN.md)

Ripple is an enterprise Java admin scaffolding built on Spring Boot, MyBatis, Shiro, Druid, Redis, and Kafka. This project ships a **full-featured Vue 3 admin console** with demo data for every major module.

## Features

### Backend

- JWT + Apache Shiro authentication
- RBAC: users, roles, menus, permissions
- System modules: departments, dictionaries, parameters, notices, files, scheduled jobs
- Audit: operation logs and login logs
- Business demo: orders, products, customers
- Low-code: drag-and-drop **form designer** and **workflow designer**
- Unified `ApiResult` responses and global exception handling
- Actuator health/info endpoints

### Frontend (`vue-admin`)

- Vue 3 + Vite + Element Plus
- Dashboard with cross-module statistics
- CRUD pages for all modules (reusable `SimpleCrud` component)
- Visual form & workflow builders

## Quick Start

### 1. Prerequisites

- JDK 8+
- Maven 3.6+
- MySQL 8+
- Node.js 18+ (for the admin UI)

### 2. Initialize database

```powershell
cd scripts
.\init-mysql.ps1
```

This creates database `ripple` and applies, in order:

| Script | Purpose |
|--------|---------|
| `schema-mysql.sql` | Core RBAC tables |
| `schema-workflow-form-mysql.sql` | Form & workflow tables |
| `schema-system-ext-mysql.sql` | Extended system & business tables |
| `seed-data-mysql.sql` | Users, roles, menus, permissions |
| `seed-workflow-form-mysql.sql` | Sample form & workflow |
| `seed-system-ext-mysql.sql` | Departments, dicts, logs, biz demo data |

Default admin account: **`admin` / `123`**

### 3. Configure datasource (optional)

```powershell
$env:RIPPLE_DATASOURCE_URL="jdbc:mysql://127.0.0.1:3306/ripple?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai"
$env:RIPPLE_DATASOURCE_USERNAME="root"
$env:RIPPLE_DATASOURCE_PASSWORD="your-password"
```

### 4. Start backend

```powershell
.\start-dev.ps1
# or: mvn spring-boot:run -Dspring.profiles.active=local
```

API base: `http://localhost:8883/ripple`

### 5. Start frontend

```powershell
cd vue-admin
npm install
npm run dev
```

Admin UI: `http://localhost:3000`

Or start both from the repo root:

```powershell
.\start-dev.ps1
```

## Module Map

| Menu (UI) | API prefix | Description |
|-----------|------------|-------------|
| Dashboard | `/admin/dashboard/stats` | Aggregated counts |
| Users | `/admin/user` | User CRUD & role assignment |
| Roles | `/admin/role` | Role CRUD, menu/permission assignment |
| Menus | `/admin/menu` | Menu tree CRUD |
| Permissions | `/admin/permission` | Permission CRUD |
| Departments | `/admin/dept` | Org structure |
| Dictionaries | `/admin/dict` | Dict types & data |
| Parameters | `/admin/config` | System key-value config |
| Notices | `/admin/notice` | Announcements |
| Files | `/admin/file` | File metadata |
| Jobs | `/admin/job` | Scheduled job definitions |
| Operation logs | `/admin/log/oper` | Audit trail (read/delete) |
| Login logs | `/admin/log/login` | Sign-in history |
| Orders / Products / Customers | `/admin/biz/*` | Business demo data |
| Form designer | `/admin/form` | Dynamic forms |
| Workflow designer | `/admin/workflow` | Approval flows |

## Project Layout

```
src/main/java/top/coderak/
  modules/admin/controller/   # Admin REST APIs
  modules/auth/               # Login & JWT
  entity/                     # Domain models
  service/                    # Business logic
src/main/resources/
  schema-*.sql / seed-*.sql   # Database scripts
vue-admin/                    # Vue 3 admin SPA
scripts/init-mysql.ps1        # One-shot DB setup
start-dev.ps1                 # Start backend + frontend
```

## Management Scripts

```powershell
.\manage-dev.ps1 status    # Check services
.\manage-dev.ps1 stop       # Stop all
.\manage-dev.ps1 restart    # Restart all
```

## Health Checks

- `GET /ripple/framework/info`
- `GET /ripple/actuator/health`

## License

See repository license file. Use and modify according to your project policy.
