-- ASCII-safe fix: restore Chinese text via UTF-8 hex (Windows mysql client encoding)
SET NAMES utf8mb4;
SET @enabled = CONVERT(UNHEX('E590AFE794A8') USING utf8mb4);

DELETE FROM rp_role_permission WHERE role_id LIKE 'role_%';
DELETE FROM rp_role_menu WHERE role_id LIKE 'role_%';
DELETE FROM rp_role WHERE id LIKE 'role_%';
DELETE FROM rp_menu WHERE id LIKE 'menu_%';
DELETE FROM rp_permission WHERE id LIKE 'perm_%';

INSERT INTO rp_menu(id, parent_id, name, path, component, icon, sort_no, type, permission, create_by, create_date, update_by, update_date, flag) VALUES
('menu_005', NULL, CONVERT(UNHEX('E9A696E987B5') USING utf8mb4), '/dashboard', 'views/dashboard', 'dashboard', 0, 'menu', NULL, 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_001', NULL, CONVERT(UNHEX('E7B3BBE7BB9FE7AEA1E79086') USING utf8mb4), '/system', NULL, 'setting', 1, 'dir', NULL, 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_002', 'menu_001', CONVERT(UNHEX('E794A8E688B7E7AEA1E79086') USING utf8mb4), '/system/user', 'views/system/user', 'user', 1, 'menu', 'user:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_003', 'menu_001', CONVERT(UNHEX('E8A792E889B2E7AEA1E79086') USING utf8mb4), '/system/role', 'views/system/role', 'role', 2, 'menu', 'role:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_004', 'menu_001', CONVERT(UNHEX('E88F9CE58D95E7AEA1E79086') USING utf8mb4), '/system/menu', 'views/system/menu', 'menu', 3, 'menu', 'menu:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_006', 'menu_001', CONVERT(UNHEX('E69D83E99990E7AEA1E79086') USING utf8mb4), '/system/permission', 'views/system/permission', 'lock', 4, 'menu', 'permission:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_010', NULL, CONVERT(UNHEX('E4B89AE58AA1E7AEA1E79086') USING utf8mb4), '/business', NULL, 'shop', 2, 'dir', NULL, 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('menu_011', 'menu_010', CONVERT(UNHEX('E8A282E58D95E7AEA1E79086') USING utf8mb4), '/business/order', 'views/business/order', 'order', 1, 'menu', 'order:list', 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
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
('perm_005', CONVERT(UNHEX('E8A792E889B2E58897E8A1A8') USING utf8mb4), 'role:list', 'button', CONVERT(UNHEX('E69FA5E79C8BE8A792E889B2E58897E8A1A8') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled),
('perm_009', CONVERT(UNHEX('E88F9CE58D95E58897E8A1A8') USING utf8mb4), 'menu:list', 'button', CONVERT(UNHEX('E69FA5E79C8BE88F9CE58D95E58897E8A1A8') USING utf8mb4), 'admin', '2019-07-26 00:00:00', 'admin', '2019-07-26 00:00:00', @enabled);

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
('role_001', 'perm_001'), ('role_001', 'perm_005'), ('role_001', 'perm_009');
