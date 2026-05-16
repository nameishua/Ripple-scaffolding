-- Extended system & business tables for full admin console

CREATE TABLE IF NOT EXISTS rp_sys_dept (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  parent_id VARCHAR(64),
  name VARCHAR(100) NOT NULL,
  leader VARCHAR(64),
  phone VARCHAR(32),
  sort_no INT DEFAULT 0,
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS rp_sys_dict_type (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  code VARCHAR(64) NOT NULL UNIQUE,
  status VARCHAR(20) DEFAULT 'enabled',
  remark VARCHAR(255),
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS rp_sys_dict_data (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  dict_code VARCHAR(64) NOT NULL,
  label VARCHAR(100) NOT NULL,
  value VARCHAR(100) NOT NULL,
  sort_no INT DEFAULT 0,
  status VARCHAR(20) DEFAULT 'enabled',
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS rp_sys_oper_log (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  module VARCHAR(64),
  action VARCHAR(64),
  method VARCHAR(16),
  request_url VARCHAR(500),
  operator VARCHAR(64),
  oper_ip VARCHAR(64),
  status VARCHAR(20),
  detail VARCHAR(1000),
  oper_time TIMESTAMP,
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS rp_sys_login_log (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  account VARCHAR(64),
  login_ip VARCHAR(64),
  location VARCHAR(128),
  browser VARCHAR(128),
  status VARCHAR(20),
  message VARCHAR(255),
  login_time TIMESTAMP,
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS rp_sys_config (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  config_key VARCHAR(100) NOT NULL UNIQUE,
  config_value VARCHAR(500),
  name VARCHAR(100),
  remark VARCHAR(255),
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS rp_sys_notice (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  title VARCHAR(200) NOT NULL,
  content TEXT,
  notice_type VARCHAR(32),
  status VARCHAR(20) DEFAULT 'draft',
  publish_time TIMESTAMP,
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS rp_sys_file (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  file_name VARCHAR(255),
  original_name VARCHAR(255),
  file_path VARCHAR(500),
  file_size BIGINT,
  mime_type VARCHAR(128),
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS rp_sys_job (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  job_name VARCHAR(100) NOT NULL,
  job_group VARCHAR(64),
  cron_expression VARCHAR(64),
  bean_name VARCHAR(128),
  status VARCHAR(20) DEFAULT 'paused',
  remark VARCHAR(255),
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS rp_biz_order (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  order_no VARCHAR(64) NOT NULL,
  customer_name VARCHAR(100),
  product_name VARCHAR(100),
  amount DECIMAL(12,2),
  status VARCHAR(32),
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS rp_biz_product (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  sku VARCHAR(64) NOT NULL,
  name VARCHAR(200) NOT NULL,
  price DECIMAL(12,2),
  stock INT DEFAULT 0,
  status VARCHAR(32),
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS rp_biz_customer (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  phone VARCHAR(32),
  email VARCHAR(128),
  level VARCHAR(32),
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);
