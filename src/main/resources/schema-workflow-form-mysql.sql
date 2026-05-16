CREATE TABLE IF NOT EXISTS rp_form_definition (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  code VARCHAR(64) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(500),
  schema_json MEDIUMTEXT NOT NULL,
  version INT DEFAULT 1,
  status VARCHAR(20) DEFAULT 'draft',
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS rp_form_data (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  form_code VARCHAR(64) NOT NULL,
  business_key VARCHAR(64),
  data_json MEDIUMTEXT NOT NULL,
  status VARCHAR(20) DEFAULT 'submitted',
  submitter VARCHAR(64),
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS rp_workflow_definition (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  code VARCHAR(64) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(500),
  form_code VARCHAR(64),
  definition_json MEDIUMTEXT NOT NULL,
  version INT DEFAULT 1,
  status VARCHAR(20) DEFAULT 'draft',
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS rp_workflow_instance (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  workflow_code VARCHAR(64) NOT NULL,
  business_key VARCHAR(64),
  form_data_id VARCHAR(64),
  current_node VARCHAR(64),
  status VARCHAR(20) DEFAULT 'running',
  starter VARCHAR(64),
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);

CREATE TABLE IF NOT EXISTS rp_workflow_task (
  id VARCHAR(64) NOT NULL PRIMARY KEY,
  instance_id VARCHAR(64) NOT NULL,
  node_key VARCHAR(64) NOT NULL,
  node_name VARCHAR(100),
  assignee VARCHAR(64),
  status VARCHAR(20) DEFAULT 'pending',
  action VARCHAR(20),
  comment VARCHAR(500),
  completed_date TIMESTAMP,
  create_by VARCHAR(64),
  create_date TIMESTAMP,
  update_by VARCHAR(64),
  update_date TIMESTAMP,
  flag VARCHAR(64)
);
