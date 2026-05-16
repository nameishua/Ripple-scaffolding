-- Ripple MySQL 演示数据（角色 / 菜单 / 权限）
-- 可重复执行：先清理测试残留，再 INSERT IGNORE

SET NAMES utf8mb4;

DELETE FROM rp_role_menu WHERE role_id IN (SELECT id FROM rp_role WHERE code LIKE 'API_TEST_%');
DELETE FROM rp_role_permission WHERE role_id IN (SELECT id FROM rp_role WHERE code LIKE 'API_TEST_%');
DELETE FROM rp_role WHERE code LIKE 'API_TEST_%';
DELETE FROM rp_menu WHERE permission LIKE 'api_test_%' OR path = '/api-test';

INSERT IGNORE INTO rp_sequence(id, type, sequence, create_by, create_date, update_by, update_date, flag) VALUES
('07260013212290001', 'USER', 100, 'admin', '2019-07-26 00:13:21', 'admin', '2019-07-26 00:13:21', '新建');

INSERT IGNORE INTO rp_user(id, name, age, create_by, create_date, update_by, update_date, flag, password, account, code) VALUES
('07251713305620000', '管理员', 25, 'admin', '2019-07-25 17:13:31', 'admin', '2019-07-25 17:17:05', '新建', '202cb962ac59075b964b07152d234b70', 'admin', 'USER');

-- 菜单树
INSERT IGNORE INTO rp_menu(id, parent_id, name, path, component, icon, sort_no, type, permission, create_by, create_date, update_by, update_date, flag) VALUES
('menu_005', NULL, '首页', '/dashboard', 'views/dashboard', 'dashboard', 0, 'menu', NULL, 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('menu_001', NULL, '系统管理', '/system', NULL, 'setting', 1, 'dir', NULL, 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('menu_002', 'menu_001', '用户管理', '/system/user', 'views/system/user', 'user', 1, 'menu', 'user:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('menu_003', 'menu_001', '角色管理', '/system/role', 'views/system/role', 'role', 2, 'menu', 'role:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('menu_004', 'menu_001', '菜单管理', '/system/menu', 'views/system/menu', 'menu', 3, 'menu', 'menu:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('menu_006', 'menu_001', '权限管理', '/system/permission', 'views/system/permission', 'lock', 4, 'menu', 'permission:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('menu_010', NULL, '业务管理', '/business', NULL, 'shop', 2, 'dir', NULL, 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('menu_011', 'menu_010', '订单管理', '/business/order', 'views/business/order', 'order', 1, 'menu', 'order:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('menu_012', 'menu_010', '商品管理', '/business/product', 'views/business/product', 'goods', 2, 'menu', 'product:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('menu_013', 'menu_010', '客户管理', '/business/customer', 'views/business/customer', 'team', 3, 'menu', 'customer:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('menu_020', NULL, '数据中心', '/report', NULL, 'chart', 3, 'dir', NULL, 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('menu_021', 'menu_020', '访问统计', '/report/visit', 'views/report/visit', 'line-chart', 1, 'menu', 'report:visit', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('menu_022', 'menu_020', '操作日志', '/report/audit', 'views/report/audit', 'file-text', 2, 'menu', 'report:audit', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用');

-- 角色
INSERT IGNORE INTO rp_role(id, name, code, description, create_by, create_date, update_by, update_date, flag) VALUES
('role_001', '超级管理员', 'SUPER_ADMIN', '拥有系统全部权限', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('role_002', '普通用户', 'NORMAL_USER', '仅首页与基础查看', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('role_003', '运营管理员', 'OPS_ADMIN', '业务模块管理与报表查看', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('role_004', '内容编辑', 'CONTENT_EDITOR', '商品与客户资料维护', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('role_005', '访客', 'GUEST', '只读访问部分页面', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用');

-- 权限按钮
INSERT IGNORE INTO rp_permission(id, name, code, type, description, create_by, create_date, update_by, update_date, flag) VALUES
('perm_001', '用户列表', 'user:list', 'button', '查看用户列表', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('perm_002', '用户新增', 'user:add', 'button', '新增用户', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('perm_003', '用户编辑', 'user:edit', 'button', '编辑用户', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('perm_004', '用户删除', 'user:delete', 'button', '删除用户', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('perm_005', '角色列表', 'role:list', 'button', '查看角色列表', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('perm_006', '角色新增', 'role:add', 'button', '新增角色', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('perm_007', '角色编辑', 'role:edit', 'button', '编辑角色', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('perm_008', '角色删除', 'role:delete', 'button', '删除角色', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('perm_009', '菜单列表', 'menu:list', 'button', '查看菜单列表', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('perm_010', '菜单新增', 'menu:add', 'button', '新增菜单', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('perm_011', '菜单编辑', 'menu:edit', 'button', '编辑菜单', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('perm_012', '菜单删除', 'menu:delete', 'button', '删除菜单', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('perm_013', '权限列表', 'permission:list', 'button', '查看权限列表', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('perm_014', '订单列表', 'order:list', 'button', '查看订单', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('perm_015', '商品列表', 'product:list', 'button', '查看商品', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('perm_016', '客户列表', 'customer:list', 'button', '查看客户', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('perm_017', '访问统计', 'report:visit', 'button', '查看访问统计', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('perm_018', '操作日志', 'report:audit', 'button', '查看操作日志', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用');

INSERT IGNORE INTO rp_user_role(user_id, role_id) VALUES
('07251713305620000', 'role_001');

-- 超级管理员：全部菜单
INSERT IGNORE INTO rp_role_menu(role_id, menu_id) VALUES
('role_001', 'menu_005'), ('role_001', 'menu_001'), ('role_001', 'menu_002'), ('role_001', 'menu_003'),
('role_001', 'menu_004'), ('role_001', 'menu_006'), ('role_001', 'menu_010'), ('role_001', 'menu_011'),
('role_001', 'menu_012'), ('role_001', 'menu_013'), ('role_001', 'menu_020'), ('role_001', 'menu_021'),
('role_001', 'menu_022');

-- 普通用户：首页
INSERT IGNORE INTO rp_role_menu(role_id, menu_id) VALUES
('role_002', 'menu_005');

-- 运营管理员：首页 + 业务 + 报表
INSERT IGNORE INTO rp_role_menu(role_id, menu_id) VALUES
('role_003', 'menu_005'), ('role_003', 'menu_010'), ('role_003', 'menu_011'), ('role_003', 'menu_012'),
('role_003', 'menu_013'), ('role_003', 'menu_020'), ('role_003', 'menu_021'), ('role_003', 'menu_022');

-- 内容编辑：首页 + 商品/客户
INSERT IGNORE INTO rp_role_menu(role_id, menu_id) VALUES
('role_004', 'menu_005'), ('role_004', 'menu_010'), ('role_004', 'menu_012'), ('role_004', 'menu_013');

-- 访客：首页 + 访问统计
INSERT IGNORE INTO rp_role_menu(role_id, menu_id) VALUES
('role_005', 'menu_005'), ('role_005', 'menu_020'), ('role_005', 'menu_021');

INSERT IGNORE INTO rp_role_permission(role_id, permission_id) VALUES
('role_001', 'perm_001'), ('role_001', 'perm_002'), ('role_001', 'perm_003'), ('role_001', 'perm_004'),
('role_001', 'perm_005'), ('role_001', 'perm_006'), ('role_001', 'perm_007'), ('role_001', 'perm_008'),
('role_001', 'perm_009'), ('role_001', 'perm_010'), ('role_001', 'perm_011'), ('role_001', 'perm_012'),
('role_001', 'perm_013'), ('role_001', 'perm_014'), ('role_001', 'perm_015'), ('role_001', 'perm_016'),
('role_001', 'perm_017'), ('role_001', 'perm_018'),
('role_003', 'perm_014'), ('role_003', 'perm_015'), ('role_003', 'perm_016'), ('role_003', 'perm_017'), ('role_003', 'perm_018'),
('role_004', 'perm_015'), ('role_004', 'perm_016'),
('role_005', 'perm_017');
