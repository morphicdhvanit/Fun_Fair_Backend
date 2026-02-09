INSERT INTO `account_role` 
(`created_by`, `created_on`, `is_active`, `role_description`, `role_id`, `role_name`, `updated_by`, `updated_on`)
VALUES
('system', NOW(), b'1', 'Administrator role', 'ADMIN', 'admin', 'system', NOW()),
('system', NOW(), b'1', 'Salesperson role', 'SALESPERSON', 'salesperson', 'system', NOW()),
('system', NOW(), b'1', 'doormanager role', 'DOORMANAGER', 'doormanager', 'system', NOW()),
('system', NOW(), b'1', 'organizer role', 'ORGANIZER', 'organizer', 'system', NOW()),
('system', NOW(), b'1', 'customer role', 'CUSTOMER', 'customer', 'system', NOW());
