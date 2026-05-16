-- Ripple MySQL seed data (ASCII/UNHEX only, safe on Windows mysql client)
SET NAMES utf8mb4;
SET @enabled = CONVERT(UNHEX('E590AFE794A8') USING utf8mb4);
SET @new_flag = CONVERT(UNHEX('E696B0E5BBBA') USING utf8mb4);

DELETE FROM rp_role_menu WHERE role_id IN (SELECT id FROM rp_role WHERE code LIKE 'API_TEST_%');
DELETE FROM rp_role_permission WHERE role_id IN (SELECT id FROM rp_role WHERE code LIKE 'API_TEST_%');
DELETE FROM rp_role WHERE code LIKE 'API_TEST_%';
DELETE FROM rp_menu WHERE permission LIKE 'api_test_%' OR path = '/api-test';

DELETE FROM rp_role_permission WHERE role_id LIKE 'role_%' OR permission_id LIKE 'perm_%';
DELETE FROM rp_role_menu WHERE role_id LIKE 'role_%';
DELETE FROM rp_role WHERE id LIKE 'role_%';
DELETE FROM rp_menu WHERE id LIKE 'menu_%';
DELETE FROM rp_permission WHERE id LIKE 'perm_%';

INSERT INTO rp_sequence(id, type, sequence, create_by, create_date, update_by, update_date, flag) VALUES
('07260013212290001', 'USER', 100, 'admin', '2019-07-26 00:13:21', 'admin', '2019-07-26 00:13:21', @new_flag)
ON DUPLICATE KEY UPDATE sequence = 100;

INSERT INTO rp_user(id, name, age, create_by, create_date, update_by, update_date, flag, password, account, code) VALUES
('07251713305620000', CONVERT(UNHEX('E7AEA1E79086E59198') USING utf8mb4), 25, 'admin', '2019-07-25 17:13:31', 'admin', '2019-07-25 17:17:05', @new_flag, '202cb962ac59075b964b07152d234b70', 'admin', 'USER')
ON DUPLICATE KEY UPDATE name = VALUES(name), flag = VALUES(flag);

INSERT INTO rp_menu(id, parent_id, name, path, component, icon, sort_no, type, permission, create_by, create_date, update_by, update_date, flag) VALUES
('menu_005', NULL, CONVERT(UNHEX('E9A696E987B5') USING utf8mb4), '/dashboard', 'views/dashboard', 'dashboard', 0, 'menu', NULL, 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_001', NULL, CONVERT(UNHEX('E7B3BBE7BB9FE7AEA1E79086') USING utf8mb4), '/system', NULL, 'setting', 1, 'dir', NULL, 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_002', 'menu_001', CONVERT(UNHEX('E794A8E688B7E7AEA1E79086') USING utf8mb4), '/system/user', 'views/system/user', 'user', 1, 'menu', 'user:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_003', 'menu_001', CONVERT(UNHEX('E8A792E889B2E7AEA1E79086') USING utf8mb4), '/system/role', 'views/system/role', 'role', 2, 'menu', 'role:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_004', 'menu_001', CONVERT(UNHEX('E88F9CE58D95E7AEA1E79086') USING utf8mb4), '/system/menu', 'views/system/menu', 'menu', 3, 'menu', 'menu:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_006', 'menu_001', CONVERT(UNHEX('E69D83E99990E7AEA1E79086') USING utf8mb4), '/system/permission', 'views/system/permission', 'lock', 4, 'menu', 'permission:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_010', NULL, CONVERT(UNHEX('E4B89AE58AA1E7AEA1E79086') USING utf8mb4), '/business', NULL, 'shop', 2, 'dir', NULL, 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_011', 'menu_010', CONVERT(UNHEX('E8AEA2E58D95E7AEA1E79086') USING utf8mb4), '/business/order', 'views/business/order', 'order', 1, 'menu', 'order:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_012', 'menu_010', CONVERT(UNHEX('E59586E59381E7AEA1E79086') USING utf8mb4), '/business/product', 'views/business/product', 'goods', 2, 'menu', 'product:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_013', 'menu_010', CONVERT(UNHEX('E5AEA2E688B7E7AEA1E79086') USING utf8mb4), '/business/customer', 'views/business/customer', 'team', 3, 'menu', 'customer:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_020', NULL, CONVERT(UNHEX('E695B0E68DAEE4B8ADE5BF83') USING utf8mb4), '/report', NULL, 'chart', 3, 'dir', NULL, 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_021', 'menu_020', CONVERT(UNHEX('E8AEBFE9979EE7BB9FE8AEA1') USING utf8mb4), '/report/visit', 'views/report/visit', 'line-chart', 1, 'menu', 'report:visit', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_022', 'menu_020', CONVERT(UNHEX('E6938DE4BD9CE697A5E5BF97') USING utf8mb4), '/report/audit', 'views/report/audit', 'file-text', 2, 'menu', 'report:audit', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled);

INSERT INTO rp_role(id, name, code, description, create_by, create_date, update_by, update_date, flag) VALUES
('role_001', CONVERT(UNHEX('E8B685E7BAA7E7AEA1E79086E59198') USING utf8mb4), 'SUPER_ADMIN', CONVERT(UNHEX('E68BA5E69C89E7B3BBE7BB9FE585A8E983A8E69D83E99990') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('role_002', CONVERT(UNHEX('E699AEE9809AE794A8E688B7') USING utf8mb4), 'NORMAL_USER', CONVERT(UNHEX('E4BB85E9A696E9A1B5E4B88EE59FBAE7A180E69FA5E79C8B') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('role_003', CONVERT(UNHEX('E8BF90E890A5E7AEA1E79086E59198') USING utf8mb4), 'OPS_ADMIN', CONVERT(UNHEX('E4B89AE58A1E6A8A1E59D97E7AEA1E79086E4B88E62A5E8A1A8E69FA5E79C8B') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('role_004', CONVERT(UNHEX('E58685E5AEB9E7BC96E8BE91') USING utf8mb4), 'CONTENT_EDITOR', CONVERT(UNHEX('E59586E59381E4B88EE5AEA2E688B7E8B584E69699E7BBB4E68AA4') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('role_005', CONVERT(UNHEX('E8AEBFE5AEA2') USING utf8mb4), 'GUEST', CONVERT(UNHEX('E58FAAE8AFBBE8AEBFE9979EE983A8E58886E9A1B5E99DA2') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled);

INSERT INTO rp_permission(id, name, code, type, description, create_by, create_date, update_by, update_date, flag) VALUES
('perm_001', CONVERT(UNHEX('E794A8E688B7E58897E8A1A8') USING utf8mb4), 'user:list', 'button', CONVERT(UNHEX('E69FA5E79C8BE794A8E688B7E58897E8A1A8') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('perm_002', CONVERT(UNHEX('E794A8E688B7E696B0E5A29E') USING utf8mb4), 'user:add', 'button', CONVERT(UNHEX('E696B0E5A29EE794A8E688B7') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('perm_003', CONVERT(UNHEX('E794A8E688B7E7BC96E8BE91') USING utf8mb4), 'user:edit', 'button', CONVERT(UNHEX('E7BC96E8BE91E794A8E688B7') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('perm_004', CONVERT(UNHEX('E794A8E688B7E588A0E999A4') USING utf8mb4), 'user:delete', 'button', CONVERT(UNHEX('E588A0E999A4E794A8E688B7') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('perm_005', CONVERT(UNHEX('E8A792E889B2E58897E8A1A8') USING utf8mb4), 'role:list', 'button', CONVERT(UNHEX('E69FA5E79C8BE8A792E889B2E58897E8A1A8') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('perm_006', CONVERT(UNHEX('E8A792E889B2E696B0E5A29E') USING utf8mb4), 'role:add', 'button', CONVERT(UNHEX('E696B0E5A29EE8A792E889B2') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('perm_007', CONVERT(UNHEX('E8A792E889B2E7BC96E8BE91') USING utf8mb4), 'role:edit', 'button', CONVERT(UNHEX('E7BC96E8BE91E8A792E889B2') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('perm_008', CONVERT(UNHEX('E8A792E889B2E588A0E999A4') USING utf8mb4), 'role:delete', 'button', CONVERT(UNHEX('E588A0E999A4E8A792E889B2') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('perm_009', CONVERT(UNHEX('E88F9CE58D95E58897E8A1A8') USING utf8mb4), 'menu:list', 'button', CONVERT(UNHEX('E69FA5E79C8BE88F9CE58D95E58897E8A1A8') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('perm_010', CONVERT(UNHEX('E88F9CE58D95E696B0E5A29E') USING utf8mb4), 'menu:add', 'button', CONVERT(UNHEX('E696B0E5A29EE88F9CE58D95') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('perm_011', CONVERT(UNHEX('E88F9CE58D95E7BC96E8BE91') USING utf8mb4), 'menu:edit', 'button', CONVERT(UNHEX('E7BC96E8BE91E88F9CE58D95') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('perm_012', CONVERT(UNHEX('E88F9CE58D95E588A0E999A4') USING utf8mb4), 'menu:delete', 'button', CONVERT(UNHEX('E588A0E999A4E88F9CE58D95') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('perm_013', CONVERT(UNHEX('E69D83E99990E58897E8A1A8') USING utf8mb4), 'permission:list', 'button', CONVERT(UNHEX('E69FA5E79C8BE69D83E99990E58897E8A1A8') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('perm_014', CONVERT(UNHEX('E8AEA2E58D95E58897E8A1A8') USING utf8mb4), 'order:list', 'button', CONVERT(UNHEX('E69FA5E79C8BE8AEA2E58D95') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('perm_015', CONVERT(UNHEX('E59586E59381E58897E8A1A8') USING utf8mb4), 'product:list', 'button', CONVERT(UNHEX('E69FA5E79C8BE59586E59381') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('perm_016', CONVERT(UNHEX('E5AEA2E688B7E58897E8A1A8') USING utf8mb4), 'customer:list', 'button', CONVERT(UNHEX('E69FA5E79C8BE5AEA2E688B7') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('perm_017', CONVERT(UNHEX('E8AEBFE997AEE7BB9FE8AEA1') USING utf8mb4), 'report:visit', 'button', CONVERT(UNHEX('E69FA5E79C8BE8AEBFE997AEE7BB9FE8AEA1') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('perm_018', CONVERT(UNHEX('E6938DE4BD9CE697A5E5BF97') USING utf8mb4), 'report:audit', 'button', CONVERT(UNHEX('E69FA5E79C8BE6938DE4BD9CE697A5E5BF97') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled);

INSERT IGNORE INTO rp_user_role(user_id, role_id) VALUES ('07251713305620000', 'role_001');

INSERT INTO rp_role_menu(role_id, menu_id) VALUES
('role_001', 'menu_005'), ('role_001', 'menu_001'), ('role_001', 'menu_002'), ('role_001', 'menu_003'),
('role_001', 'menu_004'), ('role_001', 'menu_006'), ('role_001', 'menu_010'), ('role_001', 'menu_011'),
('role_001', 'menu_012'), ('role_001', 'menu_013'), ('role_001', 'menu_020'), ('role_001', 'menu_021'),
('role_001', 'menu_022'),
('role_002', 'menu_005'),
('role_003', 'menu_005'), ('role_003', 'menu_010'), ('role_003', 'menu_011'), ('role_003', 'menu_012'),
('role_003', 'menu_013'), ('role_003', 'menu_020'), ('role_003', 'menu_021'), ('role_003', 'menu_022'),
('role_004', 'menu_005'), ('role_004', 'menu_010'), ('role_004', 'menu_012'), ('role_004', 'menu_013'),
('role_005', 'menu_005'), ('role_005', 'menu_020'), ('role_005', 'menu_021');

INSERT INTO rp_role_permission(role_id, permission_id) VALUES
('role_001', 'perm_001'), ('role_001', 'perm_002'), ('role_001', 'perm_003'), ('role_001', 'perm_004'),
('role_001', 'perm_005'), ('role_001', 'perm_006'), ('role_001', 'perm_007'), ('role_001', 'perm_008'),
('role_001', 'perm_009'), ('role_001', 'perm_010'), ('role_001', 'perm_011'), ('role_001', 'perm_012'),
('role_001', 'perm_013'), ('role_001', 'perm_014'), ('role_001', 'perm_015'), ('role_001', 'perm_016'),
('role_001', 'perm_017'), ('role_001', 'perm_018'),
('role_003', 'perm_014'), ('role_003', 'perm_015'), ('role_003', 'perm_016'), ('role_003', 'perm_017'), ('role_003', 'perm_018'),
('role_004', 'perm_015'), ('role_004', 'perm_016'),
('role_005', 'perm_017');
