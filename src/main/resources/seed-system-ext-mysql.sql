SET NAMES utf8mb4;
SET @enabled = '启用';

-- Extra users (password md5: 123 -> 202cb962ac59075b964b07152d234b70)
INSERT INTO rp_user(id, name, age, create_by, create_date, update_by, update_date, flag, password, account, code) VALUES
('user_002', '张三', 28, 'admin', NOW(), 'admin', NOW(), @enabled, '202cb962ac59075b964b07152d234b70', 'zhangsan', 'USER'),
('user_003', '李四', 30, 'admin', NOW(), 'admin', NOW(), @enabled, '202cb962ac59075b964b07152d234b70', 'lisi', 'USER'),
('user_004', '王五', 32, 'admin', NOW(), 'admin', NOW(), @enabled, '202cb962ac59075b964b07152d234b70', 'wangwu', 'USER')
ON DUPLICATE KEY UPDATE name = VALUES(name), flag = @enabled;

INSERT IGNORE INTO rp_user_role(user_id, role_id) VALUES
('user_002', 'role_003'), ('user_003', 'role_004'), ('user_004', 'role_005');

-- Menus (paths match vue-admin routes; UTF-8 literals)
INSERT INTO rp_menu(id, parent_id, name, path, component, icon, sort_no, type, permission, create_by, create_date, update_by, update_date, flag) VALUES
('menu_040', 'menu_001', '部门管理', '/dept', 'views/DeptManagement', 'office-building', 5, 'menu', 'dept:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_041', 'menu_001', '字典管理', '/dict', 'views/DictManagement', 'collection', 6, 'menu', 'dict:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_042', 'menu_001', '权限管理', '/permission', 'views/PermissionManagement', 'lock', 4, 'menu', 'permission:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_043', 'menu_001', '参数配置', '/config', 'views/ConfigManagement', 'tools', 7, 'menu', 'config:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_044', 'menu_001', '通知公告', '/notice', 'views/NoticeManagement', 'bell', 8, 'menu', 'notice:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_045', 'menu_001', '文件管理', '/file', 'views/FileManagement', 'folder', 9, 'menu', 'file:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_046', 'menu_001', '定时任务', '/job', 'views/JobManagement', 'timer', 10, 'menu', 'job:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_050', NULL, '监控中心', '/monitor', NULL, 'monitor', 4, 'dir', NULL, 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_051', 'menu_050', '操作日志', '/oper-log', 'views/OperLogManagement', 'document', 1, 'menu', 'operlog:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_052', 'menu_050', '登录日志', '/login-log', 'views/LoginLogManagement', 'key', 2, 'menu', 'loginlog:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_011', 'menu_010', '订单管理', '/order', 'views/OrderManagement', 'order', 1, 'menu', 'order:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_012', 'menu_010', '商品管理', '/product', 'views/ProductManagement', 'goods', 2, 'menu', 'product:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_013', 'menu_010', '客户管理', '/customer', 'views/CustomerManagement', 'team', 3, 'menu', 'customer:list', 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE path = VALUES(path), component = VALUES(component), name = VALUES(name), flag = @enabled;

UPDATE rp_menu SET path = '/user', component = 'views/UserManagement' WHERE id = 'menu_002';
UPDATE rp_menu SET path = '/role', component = 'views/RoleManagement' WHERE id = 'menu_003';
UPDATE rp_menu SET path = '/menu', component = 'views/MenuManagement' WHERE id = 'menu_004';
UPDATE rp_menu SET path = '/dashboard', component = 'views/Dashboard' WHERE id = 'menu_005';

INSERT IGNORE INTO rp_role_menu(role_id, menu_id) VALUES
('role_001', 'menu_040'), ('role_001', 'menu_041'), ('role_001', 'menu_042'), ('role_001', 'menu_043'),
('role_001', 'menu_044'), ('role_001', 'menu_045'), ('role_001', 'menu_046'),
('role_001', 'menu_050'), ('role_001', 'menu_051'), ('role_001', 'menu_052'),
('role_001', 'menu_030'), ('role_001', 'menu_031'), ('role_001', 'menu_032'),
('role_001', 'menu_033'), ('role_001', 'menu_034'), ('role_001', 'menu_035');

-- Departments
INSERT INTO rp_sys_dept(id, parent_id, name, leader, phone, sort_no, create_by, create_date, update_by, update_date, flag) VALUES
('dept_001', NULL, '集团总部', 'admin', '13800000001', 1, 'admin', NOW(), 'admin', NOW(), @enabled),
('dept_002', 'dept_001', '研发部', 'zhangsan', '13800000002', 1, 'admin', NOW(), 'admin', NOW(), @enabled),
('dept_003', 'dept_001', '运营部', 'lisi', '13800000003', 2, 'admin', NOW(), 'admin', NOW(), @enabled),
('dept_004', 'dept_001', '人事部', 'wangwu', '13800000004', 3, 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Dict
INSERT INTO rp_sys_dict_type(id, name, code, status, remark, create_by, create_date, update_by, update_date, flag) VALUES
('dict_t_01', '用户状态', 'user_status', 'enabled', NULL, 'admin', NOW(), 'admin', NOW(), @enabled),
('dict_t_02', '订单状态', 'order_status', 'enabled', NULL, 'admin', NOW(), 'admin', NOW(), @enabled),
('dict_t_03', '公告类型', 'notice_type', 'enabled', NULL, 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO rp_sys_dict_data(id, dict_code, label, value, sort_no, status, create_by, create_date, update_by, update_date, flag) VALUES
('dict_d_01', 'user_status', '启用', 'enabled', 1, 'enabled', 'admin', NOW(), 'admin', NOW(), @enabled),
('dict_d_02', 'user_status', '禁用', 'disabled', 2, 'enabled', 'admin', NOW(), 'admin', NOW(), @enabled),
('dict_d_03', 'order_status', '待付款', 'pending', 1, 'enabled', 'admin', NOW(), 'admin', NOW(), @enabled),
('dict_d_04', 'order_status', '已付款', 'paid', 2, 'enabled', 'admin', NOW(), 'admin', NOW(), @enabled),
('dict_d_05', 'order_status', '已取消', 'cancelled', 3, 'enabled', 'admin', NOW(), 'admin', NOW(), @enabled),
('dict_d_06', 'notice_type', '通知', 'notice', 1, 'enabled', 'admin', NOW(), 'admin', NOW(), @enabled),
('dict_d_07', 'notice_type', '公告', 'announcement', 2, 'enabled', 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE label = VALUES(label);

-- Config
INSERT INTO rp_sys_config(id, config_key, config_value, name, remark, create_by, create_date, update_by, update_date, flag) VALUES
('cfg_01', 'site.name', 'Ripple Admin', '站点名称', NULL, 'admin', NOW(), 'admin', NOW(), @enabled),
('cfg_02', 'site.logo', '/logo.png', '站点Logo', NULL, 'admin', NOW(), 'admin', NOW(), @enabled),
('cfg_03', 'security.token.ttl', '86400', 'Token有效期', 'seconds', 'admin', NOW(), 'admin', NOW(), @enabled),
('cfg_04', 'upload.maxSize', '10485760', '上传限制', 'bytes', 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE config_value = VALUES(config_value);

-- Notices
INSERT INTO rp_sys_notice(id, title, content, notice_type, status, publish_time, create_by, create_date, update_by, update_date, flag) VALUES
('notice_01', '系统维护公告', '今晚 23:00-01:00 进行系统维护。', 'announcement', 'published', NOW(), 'admin', NOW(), 'admin', NOW(), @enabled),
('notice_02', '新功能上线', '工作流模块已上线，欢迎使用。', 'notice', 'published', NOW(), 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE title = VALUES(title);

-- Files
INSERT INTO rp_sys_file(id, file_name, original_name, file_path, file_size, mime_type, create_by, create_date, update_by, update_date, flag) VALUES
('file_01', 'avatar_admin.png', 'admin.png', '/upload/2026/avatar_admin.png', 20480, 'image/png', 'admin', NOW(), 'admin', NOW(), @enabled),
('file_02', 'manual.pdf', 'manual.pdf', '/upload/2026/manual.pdf', 1048576, 'application/pdf', 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE file_name = VALUES(file_name);

-- Jobs
INSERT INTO rp_sys_job(id, job_name, job_group, cron_expression, bean_name, status, remark, create_by, create_date, update_by, update_date, flag) VALUES
('job_01', '清理临时文件', 'SYSTEM', '0 0 2 * * ?', 'tempFileCleanJob', 'running', NULL, 'admin', NOW(), 'admin', NOW(), @enabled),
('job_02', '同步日报表', 'REPORT', '0 0 8 * * ?', 'dailyReportJob', 'paused', NULL, 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE job_name = VALUES(job_name);

-- Oper / login logs
INSERT INTO rp_sys_oper_log(id, module, action, method, request_url, operator, oper_ip, status, detail, oper_time, create_by, create_date, update_by, update_date, flag) VALUES
('olog_01', '用户管理', '新增', 'POST', '/admin/user/add', 'admin', '127.0.0.1', 'success', 'add user zhangsan', NOW(), 'admin', NOW(), 'admin', NOW(), @enabled),
('olog_02', '角色管理', '分配', 'POST', '/admin/role/assignMenus', 'admin', '127.0.0.1', 'success', 'assign menus', NOW(), 'admin', NOW(), 'admin', NOW(), @enabled),
('olog_03', '表单管理', '发布', 'POST', '/admin/form/definition/publish', 'admin', '127.0.0.1', 'success', 'publish LEAVE_FORM', NOW(), 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE module = VALUES(module);

INSERT INTO rp_sys_login_log(id, account, login_ip, location, browser, status, message, login_time, create_by, create_date, update_by, update_date, flag) VALUES
('llog_01', 'admin', '127.0.0.1', '本机', 'Chrome', 'success', 'login ok', NOW(), 'admin', NOW(), 'admin', NOW(), @enabled),
('llog_02', 'zhangsan', '192.168.1.10', '内网', 'Edge', 'success', 'login ok', DATE_SUB(NOW(), INTERVAL 1 DAY), 'admin', NOW(), 'admin', NOW(), @enabled),
('llog_03', 'guest', '10.0.0.8', '内网', 'Firefox', 'fail', 'bad password', DATE_SUB(NOW(), INTERVAL 2 DAY), 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE account = VALUES(account);

-- Business demo
INSERT INTO rp_biz_customer(id, name, phone, email, level, create_by, create_date, update_by, update_date, flag) VALUES
('cust_01', '张三', '13900001111', 'zhang@example.com', 'VIP', 'admin', NOW(), 'admin', NOW(), @enabled),
('cust_02', '李四', '13900002222', 'li@example.com', 'normal', 'admin', NOW(), 'admin', NOW(), @enabled),
('cust_03', '王五', '13900003333', 'wang@example.com', 'normal', 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO rp_biz_product(id, sku, name, price, stock, status, create_by, create_date, update_by, update_date, flag) VALUES
('prod_01', 'SKU-001', '笔记本电脑', 5999.00, 120, 'on_sale', 'admin', NOW(), 'admin', NOW(), @enabled),
('prod_02', 'SKU-002', '蓝牙耳机', 199.00, 500, 'on_sale', 'admin', NOW(), 'admin', NOW(), @enabled),
('prod_03', 'SKU-003', '智能手机', 8999.00, 30, 'off_sale', 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO rp_biz_order(id, order_no, customer_name, product_name, amount, status, create_by, create_date, update_by, update_date, flag) VALUES
('ord_01', 'ORD20260517001', '张三', '笔记本电脑', 5999.00, 'paid', 'admin', NOW(), 'admin', NOW(), @enabled),
('ord_02', 'ORD20260517002', '李四', '蓝牙耳机', 398.00, 'pending', 'admin', NOW(), 'admin', NOW(), @enabled),
('ord_03', 'ORD20260516003', '王五', '智能手机', 8999.00, 'cancelled', 'admin', DATE_SUB(NOW(), INTERVAL 1 DAY), 'admin', DATE_SUB(NOW(), INTERVAL 1 DAY), @enabled)
ON DUPLICATE KEY UPDATE order_no = VALUES(order_no);

-- Sample form submissions
INSERT INTO rp_form_data(id, form_code, data_json, submitter, status, create_by, create_date, update_by, update_date, flag) VALUES
('fd_01', 'LEAVE_FORM', '{"title":"annual leave","days":3,"reason":"family"}', 'zhangsan', 'submitted', 'zhangsan', NOW(), 'zhangsan', NOW(), @enabled)
ON DUPLICATE KEY UPDATE data_json = VALUES(data_json);
