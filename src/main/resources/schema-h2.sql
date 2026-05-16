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

INSERT INTO sequence(id, type, sequence, create_by, create_date, update_by, update_date, flag) VALUES 
('07260013212290001', 'USER', 1, 'admin', '2019-07-26 00:13:21', 'admin', '2019-07-26 00:13:21', '新建');

INSERT INTO user(id, name, age, create_by, create_date, update_by, update_date, flag, password, account, code) VALUES 
('07251713305620000', 'admin', 25, 'test', '2019-07-25 17:13:31', 'test', '2019-07-25 17:17:05', '新建', '202cb962ac59075b964b07152d234b70', 'admin', 'USER');