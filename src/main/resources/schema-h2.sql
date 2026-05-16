CREATE TABLE IF NOT EXISTS sequence (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  type VARCHAR(255),
  sequence INT DEFAULT 0,
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS user (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  name VARCHAR(255),
  age INT,
  create_by VARCHAR(255),
  create_date TIMESTAMP,
  update_by VARCHAR(255),
  update_date TIMESTAMP,
  flag VARCHAR(255),
  sort INT,
  password VARCHAR(64),
  account VARCHAR(64) NOT NULL UNIQUE,
  code VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS menu (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  parent_id VARCHAR(64),
  name VARCHAR(100) NOT NULL,
  path VARCHAR(200),
  component VARCHAR(200),
  icon VARCHAR(50),
  sort_no INT DEFAULT 0,
  type VARCHAR(20),
  permission VARCHAR(100),
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS role (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  code VARCHAR(50) NOT NULL UNIQUE,
  description VARCHAR(200),
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS permission (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  code VARCHAR(50) NOT NULL UNIQUE,
  type VARCHAR(20),
  description VARCHAR(200),
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS user_role (
  user_id VARCHAR(64) NOT NULL,
  role_id VARCHAR(64) NOT NULL,
  PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS role_menu (
  role_id VARCHAR(64) NOT NULL,
  menu_id VARCHAR(64) NOT NULL,
  PRIMARY KEY (role_id, menu_id)
);

CREATE TABLE IF NOT EXISTS role_permission (
  role_id VARCHAR(64) NOT NULL,
  permission_id VARCHAR(64) NOT NULL,
  PRIMARY KEY (role_id, permission_id)
);

INSERT INTO sequence(id, type, sequence, create_by, create_date, update_by, update_date, flag) VALUES
('07260013212290001', 'USER', 1, 'admin', '2019-07-26 00:13:21', 'admin', '2019-07-26 00:13:21', '新建');

INSERT INTO user(id, name, age, create_by, create_date, update_by, update_date, flag, password, account, code) VALUES
('07251713305620000', '管理员', 25, 'admin', '2019-07-25 17:13:31', 'admin', '2019-07-25 17:17:05', '新建', '202cb962ac59075b964b07152d234b70', 'admin', 'USER');

INSERT INTO menu(id, parent_id, name, path, component, icon, sort_no, type, permission, create_by, create_date, update_by, update_date, flag) VALUES
('menu_001', NULL, '系统管理', '/system', NULL, 'setting', 1, 'dir', NULL, 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('menu_002', 'menu_001', '用户管理', '/system/user', 'views/system/user', 'user', 1, 'menu', 'user:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('menu_003', 'menu_001', '角色管理', '/system/role', 'views/system/role', 'role', 2, 'menu', 'role:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('menu_004', 'menu_001', '菜单管理', '/system/menu', 'views/system/menu', 'menu', 3, 'menu', 'menu:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('menu_005', NULL, '首页', '/dashboard', 'views/dashboard', 'dashboard', 0, 'menu', NULL, 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用');

INSERT INTO role(id, name, code, description, create_by, create_date, update_by, update_date, flag) VALUES
('role_001', '超级管理员', 'SUPER_ADMIN', '拥有所有权限', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用'),
('role_002', '普通用户', 'NORMAL_USER', '普通用户权限', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用');

INSERT INTO permission(id, name, code, type, description, create_by, create_date, update_by, update_date, flag) VALUES
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
('perm_012', '菜单删除', 'menu:delete', 'button', '删除菜单', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', '启用');

INSERT INTO user_role(user_id, role_id) VALUES
('07251713305620000', 'role_001');

INSERT INTO role_menu(role_id, menu_id) VALUES
('role_001', 'menu_001'),
('role_001', 'menu_002'),
('role_001', 'menu_003'),
('role_001', 'menu_004'),
('role_001', 'menu_005');

INSERT INTO role_permission(role_id, permission_id) VALUES
('role_001', 'perm_001'),
('role_001', 'perm_002'),
('role_001', 'perm_003'),
('role_001', 'perm_004'),
('role_001', 'perm_005'),
('role_001', 'perm_006'),
('role_001', 'perm_007'),
('role_001', 'perm_008'),
('role_001', 'perm_009'),
('role_001', 'perm_010'),
('role_001', 'perm_011'),
('role_001', 'perm_012');
