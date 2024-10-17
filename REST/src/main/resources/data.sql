-- INSERT INTO measure_unit
INSERT INTO measurement_unit (version, symbol)
VALUES (1, 'mm'),
       (1, 'cm'),
       (1, 'm'),
       (1, 'km'),
       (1, 'mg'),
       (1, 'g'),
       (1, 'kg'),
       (1, 'ml'),
       (1, 'l'),
       (1, 'szt');

-- INSERT INTO permission
INSERT INTO permission (version, name, category)
VALUES (1, 'VIEW_PERMISSION', 'PERMISSION'),
       (1, 'VIEW_MEASUREMENT_UNIT', 'MEASUREMENT_UNIT'),
       (1, 'ADD_MEASUREMENT_UNIT', 'MEASUREMENT_UNIT'),
       (1, 'EDIT_MEASUREMENT_UNIT', 'MEASUREMENT_UNIT'),
       (1, 'DELETE_MEASUREMENT_UNIT', 'MEASUREMENT_UNIT'),
       (1, 'VIEW_ROLE', 'ROLE'),
       (1, 'ADD_ROLE', 'ROLE'),
       (1, 'EDIT_ROLE', 'ROLE'),
       (1, 'DELETE_ROLE', 'ROLE');

-- INSERT INTO role
INSERT INTO role (version, name)
VALUES (1, 'Administrator'),
       (1, 'Manager'),
       (1, 'WarehouseWorker'),
       (1, 'MaintenanceWorker');

-- INSERT INTO role_permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, a.id
FROM role r,
     permission a
WHERE r.name = 'Administrator';

INSERT INTO role_permissions (role_id, permission_id)
VALUES (2, 2);

-- INSERT INTO employee
INSERT INTO employee (version, first_name, last_name, phone_number, email, country, city, street, street_number, flat_number, postal_code,
                      login, password)
VALUES (1, 'Jan', 'Kowalski', '+48111222333','jan.kowalski@example.com', 'Poland', 'Warsaw', 'Główna', '1', '1A', '00-001', 'jk',
        '$2a$12$a89eFUCJqs2P6SRuTZIKh.M/dSYWO0UqrwbJq85xGGMCP8nklLNWi'),                --pass
       (1, 'Anna', 'Nowak', '+48222333444', 'anna.nowak@example.com', 'Poland', 'Krakow', 'Śmieszna', '2', '2B', '00-002', 'an',
        '$2a$12$a89eFUCJqs2P6SRuTZIKh.M/dSYWO0UqrwbJq85xGGMCP8nklLNWi'),                --pass
       (1, 'Piotr', 'Wiśniewski', '+48333444555', 'piotr.wisniewski@example.com', 'Poland', 'Gdansk', 'Kinowa', '3', '3C', '00-003',
        'pwisniewski', '$2a$12$a89eFUCJqs2P6SRuTZIKh.M/dSYWO0UqrwbJq85xGGMCP8nklLNWi'), --pass
       (1, 'Katarzyna', 'Wójcik', '+48333444556','katarzyna.wojcik@example.com', 'Poland', 'Wroclaw', 'Niska', '4', NULL, '00-004',
        'kwojcik', '$2a$12$a89eFUCJqs2P6SRuTZIKh.M/dSYWO0UqrwbJq85xGGMCP8nklLNWi'); --pass

-- INSERT INTO employee_roles
INSERT INTO employee_roles (employee_id, role_id)
VALUES (1, 1),
       (2, 2);

-- INSERT INTO item_category
INSERT INTO item_category (name)
VALUES ('Mechanics'),
       ('Pneumatics'),
       ('Hydraulics'),
       ('Electrics'),
       ('Automation');

-- INSERT INTO work_order_status
INSERT INTO work_order_status (status)
VALUES ('New'),
       ('InProgress'),
       ('Completed');