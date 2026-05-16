SET NAMES utf8mb4;
SET @enabled = CONVERT(UNHEX('E590AFE794A8') USING utf8mb4);

INSERT INTO rp_form_definition (id, code, name, description, schema_json, version, status, create_by, create_date, update_by, update_date, flag) VALUES
('form_def_001', 'LEAVE_FORM', CONVERT(UNHEX('E8AFB7E58187E794B3E8AFB7') USING utf8mb4), CONVERT(UNHEX('E7A4BAE4BC91E7A4BAE8AFB7') USING utf8mb4),
'{"fields":[{"key":"title","label":"\u6807\u9898","type":"input","required":true},{"key":"days","label":"\u5929\u6570","type":"number","required":true},{"key":"reason","label":"\u4e8b\u7531","type":"textarea","required":true}]}',
1, 'published', 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE schema_json = VALUES(schema_json), status = 'published', flag = @enabled;

INSERT INTO rp_workflow_definition (id, code, name, description, form_code, definition_json, version, status, create_by, create_date, update_by, update_date, flag) VALUES
('wf_def_001', 'LEAVE_APPROVAL', CONVERT(UNHEX('E8AFB7E58187E5AEA1E689B9E6B581E7A88B') USING utf8mb4), CONVERT(UNHEX('E8AFB7E58187E794B3E8AFB7E5AEA1E689B9') USING utf8mb4), 'LEAVE_FORM',
'{"nodes":[{"key":"start","name":"\u5f00\u59cb","type":"start"},{"key":"mgr","name":"\u7ecf\u7406\u5ba1\u6279","type":"approval","assignee":"admin"},{"key":"end","name":"\u7ed3\u675f","type":"end"}],"edges":[{"from":"start","to":"mgr"},{"from":"mgr","to":"end","on":"approve"},{"from":"mgr","to":"end","on":"reject"}]}',
1, 'published', 'admin', NOW(), 'admin', NOW(), @enabled)
ON DUPLICATE KEY UPDATE definition_json = VALUES(definition_json), status = 'published', flag = @enabled;

INSERT IGNORE INTO rp_menu (id, parent_id, name, path, component, icon, sort_no, type, permission, create_by, create_date, update_by, update_date, flag) VALUES
('menu_030', NULL, CONVERT(UNHEX('E4B89AE58AA1E6B581E7A88B') USING utf8mb4), '/workflow', NULL, 'connection', 4, 'dir', NULL, 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_031', 'menu_030', CONVERT(UNHEX('E8A1A8E58D95E9858DE7BDAE') USING utf8mb4), '/form-def', 'views/FormDefinitionManagement', 'document', 1, 'menu', 'form:def', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_032', 'menu_030', CONVERT(UNHEX('E8A1A8E58D95E5A1ABE58699') USING utf8mb4), '/form-fill', 'views/FormFill', 'edit', 2, 'menu', 'form:fill', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_033', 'menu_030', CONVERT(UNHEX('E6B581E7A88BE9858DE7BDAE') USING utf8mb4), '/workflow-def', 'views/WorkflowDefinitionManagement', 'setting', 3, 'menu', 'wf:def', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_034', 'menu_030', CONVERT(UNHEX('E6B581E7A8B8BE5AE9E4BE8') USING utf8mb4), '/workflow-instance', 'views/WorkflowInstanceManagement', 'list', 4, 'menu', 'wf:instance', 'admin', NOW(), 'admin', NOW(), @enabled),
('menu_035', 'menu_030', CONVERT(UNHEX('E68891E79A84E5BE85E58A9E') USING utf8mb4), '/workflow-task', 'views/WorkflowTaskManagement', 'bell', 5, 'menu', 'wf:task', 'admin', NOW(), 'admin', NOW(), @enabled);

INSERT IGNORE INTO rp_role_menu (role_id, menu_id) VALUES
('role_001', 'menu_030'), ('role_001', 'menu_031'), ('role_001', 'menu_032'),
('role_001', 'menu_033'), ('role_001', 'menu_034'), ('role_001', 'menu_035');
