SET NAMES utf8mb4;
SET @enabled = CONVERT(UNHEX('E590AFE794A8') USING utf8mb4);

-- Extra users (password md5: 123 -> 202cb962ac59075b964b07152d234b70)
INSERT INTO rp_user(id, name, age, create_by, create_date, update_by, update_date, flag, password, account, code) VALUES
('user_002', CONVERT(UNHEX('E5BCA0E4B889') USING utf8mb4), 28, 'admin', NOW(), 'admin', NOW(), @enabled, '202cb962ac59075b964b07152d234b70', 'zhangsan', 'USER'),
('user_003', CONVERT(UNHEX('E69D8EE59B9B') USING utf8mb4), 30, 'admin', NOW(), 'admin', NOW(), @enabled, '202cb962ac59075b964b07152d234b70', 'lisi', 'USER'),
('user_004', CONVERT(UNHEX('E78E8BE4BC8D') USING utf8mb4), 32, 'admin', NOW(), 'admin', NOW(), @enabled, '202cb962ac59075b964b07152d234b70', 'wangwu', 'USER')
ON DUPLICATE KEY UPDATE name = VALUES(name), flag = @enabled;

INSERT IGNORE INTO rp_user_role(user_id, role_id) VALUES
('user_002', 'role_003'), ('user_003', 'role_004'), ('user_004', 'role_005');

-- Menus (paths match vue-admin routes)
INSERT INTO rp_menu(id, parent_id, name, path, component, icon, sort_no, type, permission, create_by, create_date, update_by, update_date, flag) VALUES
('menu_040', 'menu_001', CONVERT(UNHEX('E983A8E997A8E7AEA1E79086') USING utf8mb4), '/dept', 'views/DeptManagement', 'office-building', 5, 'menu', 'dept:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_041', 'menu_001', CONVERT(UNHEX('E5AD97E585B8E7AEA1E79086') USING utf8mb4), '/dict', 'views/DictManagement', 'collection', 6, 'menu', 'dict:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_042', 'menu_001', CONVERT(UNHEX('E69D83E99990E7AEA1E79086') USING utf8mb4), '/permission', 'views/PermissionManagement', 'lock', 4, 'menu', 'permission:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_043', 'menu_001', CONVERT(UNHEX('E58F82E695B0E9858DE7BDAE') USING utf8mb4), '/config', 'views/ConfigManagement', 'tools', 7, 'menu', 'config:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_044', 'menu_001', CONVERT(UNHEX('E585ACE5X91CAE7AEA1E79086') USING utf8mb4), '/notice', 'views/NoticeManagement', 'bell', 8, 'menu', 'notice:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_045', 'menu_001', CONVERT(UNHEX('E69687E4BBB6E7AEA1E79086') USING utf8mb4), '/file', 'views/FileManagement', 'folder', 9, 'menu', 'file:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_046', 'menu_001', CONVERT(UNHEX('E5AE9AE697B6E4BBBBB') USING utf8mb4), '/job', 'views/JobManagement', 'timer', 10, 'menu', 'job:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_050', NULL, CONVERT(UNHEX('E79B91E68EA7E4B8ADE5BF83') USING utf8mb4), '/monitor', NULL, 'monitor', 4, 'dir', NULL, 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_051', 'menu_050', CONVERT(UNHEX('E6938DE4BD9CE697A5E5BF97') USING utf8mb4), '/oper-log', 'views/OperLogManagement', 'document', 1, 'menu', 'operlog:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_052', 'menu_050', CONVERT(UNHEX('E799BBE5BD95E697A5E5BF97') USING utf8mb4), '/login-log', 'views/LoginLogManagement', 'key', 2, 'menu', 'loginlog:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_011', 'menu_010', CONVERT(UNHEX('E8AEA2E58D95E7AEA1E79086') USING utf8mb4), '/order', 'views/OrderManagement', 'order', 1, 'menu', 'order:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_012', 'menu_010', CONVERT(UNHEX('E59586E59381E7AEA1E79086') USING utf8mb4), '/product', 'views/ProductManagement', 'goods', 2, 'menu', 'product:list', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_013', 'menu_010', CONVERT(UNHEX('E5AEA2E688B7E7AEA1E79086') USING utf8mb4), '/customer', 'views/CustomerManagement', 'team', 3, 'menu', 'customer:list', 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE path = VALUES(path), component = VALUES(component), flag = @enabled;

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
('dept_001', NULL, CONVERT(UNHEX('E7A68AE5BDB8') USING utf8mb4), 'admin', '13800000001', 1, 'admin', NOW(), 'admin', NOW(), @enabled),
('dept_002', 'dept_001', CONVERT(UNHEX('E78294E58F91E983A8') USING utf8mb4), 'zhangsan', '13800000002', 1, 'admin', NOW(), 'admin', NOW(), @enabled),
('dept_003', 'dept_001', CONVERT(UNHEX('E8BF90E890A5E983A8') USING utf8mb4), 'lisi', '13800000003', 2, 'admin', NOW(), 'admin', NOW(), @enabled),
('dept_004', 'dept_001', CONVERT(UNHEX('E4BABA4E8B584E983A8') USING utf8mb4), 'wangwu', '13800000004', 3, 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- Dict
INSERT INTO rp_sys_dict_type(id, name, code, status, remark, create_by, create_date, update_by, update_date, flag) VALUES
('dict_t_01', CONVERT(UNHEX('E794A8E688B7E78AB6E68081') USING utf8mb4), 'user_status', 'enabled', NULL, 'admin', NOW(), 'admin', NOW(), @enabled),
('dict_t_02', CONVERT(UNHEX('E8AEA2E58D95E78AB6E68081') USING utf8mb4), 'order_status', 'enabled', NULL, 'admin', NOW(), 'admin', NOW(), @enabled),
('dict_t_03', CONVERT(UNHEX('E585ACE5X91CAE7B1BBE59E8B') USING utf8mb4), 'notice_type', 'enabled', NULL, 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO rp_sys_dict_data(id, dict_code, label, value, sort_no, status, create_by, create_date, update_by, update_date, flag) VALUES
('dict_d_01', 'user_status', CONVERT(UNHEX('E590AFE794A8') USING utf8mb4), 'enabled', 1, 'enabled', 'admin', NOW(), 'admin', NOW(), @enabled),
('dict_d_02', 'user_status', CONVERT(UNHEX('E5819CE794A8') USING utf8mb4), 'disabled', 2, 'enabled', 'admin', NOW(), 'admin', NOW(), @enabled),
('dict_d_03', 'order_status', CONVERT(UNHEX('E5BE85E4BB98E6ACBE') USING utf8mb4), 'pending', 1, 'enabled', 'admin', NOW(), 'admin', NOW(), @enabled),
('dict_d_04', 'order_status', CONVERT(UNHEX('E5B7B2E5AE8CE68890') USING utf8mb4), 'paid', 2, 'enabled', 'admin', NOW(), 'admin', NOW(), @enabled),
('dict_d_05', 'order_status', CONVERT(UNHEX('E5B7B2E58F96E6B688') USING utf8mb4), 'cancelled', 3, 'enabled', 'admin', NOW(), 'admin', NOW(), @enabled),
('dict_d_06', 'notice_type', CONVERT(UNHEX('E9809AE7F59A') USING utf8mb4), 'notice', 1, 'enabled', 'admin', NOW(), 'admin', NOW(), @enabled),
('dict_d_07', 'notice_type', CONVERT(UNHEX('E585ACE5X91CA') USING utf8mb4), 'announcement', 2, 'enabled', 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE label = VALUES(label);

-- Config
INSERT INTO rp_sys_config(id, config_key, config_value, name, remark, create_by, create_date, update_by, update_date, flag) VALUES
('cfg_01', 'site.name', 'Ripple Admin', CONVERT(UNHEX('E7AB99E782B9E5908D') USING utf8mb4), NULL, 'admin', NOW(), 'admin', NOW(), @enabled),
('cfg_02', 'site.logo', '/logo.png', CONVERT(UNHEX('E7AB99E782B9Logo') USING utf8mb4), NULL, 'admin', NOW(), 'admin', NOW(), @enabled),
('cfg_03', 'security.token.ttl', '86400', CONVERT(UNHEX('TokenE69C89E69588E69C9F') USING utf8mb4), 'seconds', 'admin', NOW(), 'admin', NOW(), @enabled),
('cfg_04', 'upload.maxSize', '10485760', CONVERT(UNHEX('E4B88AE4BCA0E99990E588B6') USING utf8mb4), 'bytes', 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE config_value = VALUES(config_value);

-- Notices
INSERT INTO rp_sys_notice(id, title, content, notice_type, status, publish_time, create_by, create_date, update_by, update_date, flag) VALUES
('notice_01', CONVERT(UNHEX('E7B3BBE7BB9FE7BBB4E68AA4E585ACE5X91CA') USING utf8mb4), CONVERT(UNHEX('E4BB8AE591A823:00-01:00E8BF9BE8A18C7CFB7EDFDC4BF') USING utf8mb4), 'announcement', 'published', NOW(), 'admin', NOW(), 'admin', NOW(), @enabled),
('notice_02', CONVERT(UNHEX('E696B0E58A9FE4B88AE7BABBF') USING utf8mb4), CONVERT(UNHEX('E5B7A5E4BD9CE6B581E7A88BE6A8A1E58D97E5B7B2E4B88AE7BABF') USING utf8mb4), 'notice', 'published', NOW(), 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE title = VALUES(title);

-- Files
INSERT INTO rp_sys_file(id, file_name, original_name, file_path, file_size, mime_type, create_by, create_date, update_by, update_date, flag) VALUES
('file_01', 'avatar_admin.png', 'admin.png', '/upload/2026/avatar_admin.png', 20480, 'image/png', 'admin', NOW(), 'admin', NOW(), @enabled),
('file_02', 'manual.pdf', 'manual.pdf', '/upload/2026/manual.pdf', 1048576, 'application/pdf', 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE file_name = VALUES(file_name);

-- Jobs
INSERT INTO rp_sys_job(id, job_name, job_group, cron_expression, bean_name, status, remark, create_by, create_date, update_by, update_date, flag) VALUES
('job_01', CONVERT(UNHEX('E6B8E985E79086E4B8B4E697B6E69687E4BBB6') USING utf8mb4), 'SYSTEM', '0 0 2 * * ?', 'tempFileCleanJob', 'running', NULL, 'admin', NOW(), 'admin', NOW(), @enabled),
('job_02', CONVERT(UNHEX('E5908CE6AD5E6B58AE8A1A8') USING utf8mb4), 'REPORT', '0 0 8 * * ?', 'dailyReportJob', 'paused', NULL, 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE job_name = VALUES(job_name);

-- Oper / login logs
INSERT INTO rp_sys_oper_log(id, module, action, method, request_url, operator, oper_ip, status, detail, oper_time, create_by, create_date, update_by, update_date, flag) VALUES
('olog_01', CONVERT(UNHEX('E794A8E688B7E7AEA1E79086') USING utf8mb4), CONVERT(UNHEX('E696B0E5A29E') USING utf8mb4), 'POST', '/admin/user/add', 'admin', '127.0.0.1', 'success', 'add user zhangsan', NOW(), 'admin', NOW(), 'admin', NOW(), @enabled),
('olog_02', CONVERT(UNHEX('E8A792E889B2E7AEA1E79086') USING utf8mb4), CONVERT(UNHEX('E58886E9858D') USING utf8mb4), 'POST', '/admin/role/assignMenus', 'admin', '127.0.0.1', 'success', 'assign menus', NOW(), 'admin', NOW(), 'admin', NOW(), @enabled),
('olog_03', CONVERT(UNHEX('E8A1A8E58D95E7AEA1E79086') USING utf8mb4), CONVERT(UNHEX('E58F91E5B883') USING utf8mb4), 'POST', '/admin/form/definition/publish', 'admin', '127.0.0.1', 'success', 'publish LEAVE_FORM', NOW(), 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE module = VALUES(module);

INSERT INTO rp_sys_login_log(id, account, login_ip, location, browser, status, message, login_time, create_by, create_date, update_by, update_date, flag) VALUES
('llog_01', 'admin', '127.0.0.1', CONVERT(UNHEX('E69CACE59CB0') USING utf8mb4), 'Chrome', 'success', 'login ok', NOW(), 'admin', NOW(), 'admin', NOW(), @enabled),
('llog_02', 'zhangsan', '192.168.1.10', 'LAN', 'Edge', 'success', 'login ok', DATE_SUB(NOW(), INTERVAL 1 DAY), 'admin', NOW(), 'admin', NOW(), @enabled),
('llog_03', 'guest', '10.0.0.8', 'LAN', 'Firefox', 'fail', 'bad password', DATE_SUB(NOW(), INTERVAL 2 DAY), 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE account = VALUES(account);

-- Business demo
INSERT INTO rp_biz_customer(id, name, phone, email, level, create_by, create_date, update_by, update_date, flag) VALUES
('cust_01', CONVERT(UNHEX('E58C97E4B889') USING utf8mb4), '13900001111', 'zhang@example.com', 'VIP', 'admin', NOW(), 'admin', NOW(), @enabled),
('cust_02', CONVERT(UNHEX('E69D8EE59B9B') USING utf8mb4), '13900002222', 'li@example.com', 'normal', 'admin', NOW(), 'admin', NOW(), @enabled),
('cust_03', CONVERT(UNHEX('E78E8BE59B9B') USING utf8mb4), '13900003333', 'wang@example.com', 'normal', 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO rp_biz_product(id, sku, name, price, stock, status, create_by, create_date, update_by, update_date, flag) VALUES
('prod_01', 'SKU-001', CONVERT(UNHEX('E4BC81E4B9A1E69C8DE69C8D') USING utf8mb4), 5999.00, 120, 'on_sale', 'admin', NOW(), 'admin', NOW(), @enabled),
('prod_02', 'SKU-002', CONVERT(UNHEX('E699BAE883BDE6898B') USING utf8mb4), 199.00, 500, 'on_sale', 'admin', NOW(), 'admin', NOW(), @enabled),
('prod_03', 'SKU-003', CONVERT(UNHEX('E4B9A6E69C8DE69C8D') USING utf8mb4), 8999.00, 30, 'off_sale', 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO rp_biz_order(id, order_no, customer_name, product_name, amount, status, create_by, create_date, update_by, update_date, flag) VALUES
('ord_01', 'ORD20260517001', CONVERT(UNHEX('E58C97E4B889') USING utf8mb4), CONVERT(UNHEX('E4BC81E4B9A1E69C8DE69C8D') USING utf8mb4), 5999.00, 'paid', 'admin', NOW(), 'admin', NOW(), @enabled),
('ord_02', 'ORD20260517002', CONVERT(UNHEX('E69D8EE59B9B') USING utf8mb4), CONVERT(UNHEX('E699BAE883BDE6898B') USING utf8mb4), 398.00, 'pending', 'admin', NOW(), 'admin', NOW(), @enabled),
('ord_03', 'ORD20260516003', CONVERT(UNHEX('E78E8BE59B9B') USING utf8mb4), CONVERT(UNHEX('E4B9A6E69C8DE69C8D') USING utf8mb4), 8999.00, 'cancelled', 'admin', DATE_SUB(NOW(), INTERVAL 1 DAY), 'admin', DATE_SUB(NOW(), INTERVAL 1 DAY), @enabled)
ON DUPLICATE KEY UPDATE order_no = VALUES(order_no);

-- Sample form submissions
INSERT INTO rp_form_data(id, form_code, data_json, submitter, status, create_by, create_date, update_by, update_date, flag) VALUES
('fd_01', 'LEAVE_FORM', '{"title":"annual leave","days":3,"reason":"family"}', 'zhangsan', 'submitted', 'zhangsan', NOW(), 'zhangsan', NOW(), @enabled)
ON DUPLICATE KEY UPDATE data_json = VALUES(data_json);
