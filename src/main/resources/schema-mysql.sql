CREATE TABLE IF NOT EXISTS rp_sequence (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  type VARCHAR(255),
  sequence INT DEFAULT 0,
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS rp_user (
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

CREATE TABLE IF NOT EXISTS rp_menu (
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

CREATE TABLE IF NOT EXISTS rp_role (
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

CREATE TABLE IF NOT EXISTS rp_permission (
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

CREATE TABLE IF NOT EXISTS rp_user_role (
  user_id VARCHAR(64) NOT NULL,
  role_id VARCHAR(64) NOT NULL,
  PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS rp_role_menu (
  role_id VARCHAR(64) NOT NULL,
  menu_id VARCHAR(64) NOT NULL,
  PRIMARY KEY (role_id, menu_id)
);

CREATE TABLE IF NOT EXISTS rp_role_permission (
  role_id VARCHAR(64) NOT NULL,
  permission_id VARCHAR(64) NOT NULL,
  PRIMARY KEY (role_id, permission_id)
);

-- 演示数据见 seed-data-mysql.sql，执行 scripts/init-mysql.ps1 一键初始化
