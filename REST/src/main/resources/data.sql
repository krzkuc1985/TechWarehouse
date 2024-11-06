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
       (1, 'pcs');

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
INSERT INTO item_category (version, name)
VALUES (1, 'Mechanics'),
       (1, 'Pneumatics'),
       (1, 'Hydraulics'),
       (1, 'Electrics'),
       (1, 'Automation');

-- INSERT INTO work_order_status
INSERT INTO work_order_status (version, status)
VALUES (1, 'New'),
       (1, 'InProgress'),
       (1, 'Completed');

-- INSERT INTO work_order_type
INSERT INTO work_order_type (version, type)
VALUES (1, 'Repair'),
       (1, 'Modernization'),
       (1, 'Tests');

-- INSERT INTO location
INSERT INTO location (version, rack, shelf)
VALUES (1, 'A', '1'),
       (1, 'A', '2'),
       (1, 'A', '3'),
       (1, 'A', '4'),
       (1, 'B', '1'),
       (1, 'B', '2'),
       (1, 'B', '3'),
       (1, 'B', '4'),
       (1, 'C', '1'),
       (1, 'C', '2'),
       (1, 'C', '3'),
       (1, 'C', '4'),
       (1, 'D', '1'),
       (1, 'D', '2'),
       (1, 'D', '3'),
       (1, 'D', '4');

INSERT INTO item (version, name, order_number, description, quantity, measurement_unit_id, item_category_id, archive, max_quantity, min_quantity, location_id)
VALUES (1, 'Ball bearing 6204', '6204-ZZ-C3-SKF', 'Closed ball bearing, 20 mm diameter bearing', 15, 10, 1, false, 50, 5, 1),
       (1, 'Pneumatic cylinder FESTO', 'DSNU-32-100-P-A', 'Pneumatic cylinder with 100 mm stroke, 32 mm diameter', 8, 10, 2, false, 20, 5, 2),
       (1, 'Hydraulic pump Bosch Rexroth', '0510-356-928-231', 'Hydraulic pump with a capacity of 60 l/min, 230V power supply', 5, 10, 3, false, 10, 2, 3),
       (1, 'Electromagnetic relay 12V', 'G2R-1-SN-12VDC-OMRON', 'Electromagnetic relay SPDT, control voltage 12V', 50, 10, 4, false, 100, 20, 4),
       (1, 'Inverter Siemens SINAMICS', '6SL3210-1PE11-8UL0', 'Inverter for three-phase motors, 5 kW power', 3, 10, 5, true, 5, 1, 5),
       (1, 'Inductive sensor IFM', 'IF5177-IFM', 'Inductive proximity sensor, 10 mm range', 25, 10, 5, false, 50, 10, 6),
       (1, 'Control cable 3x2.5 mm²', 'YY-JZ-3G2.5-50M', 'Cable for powering electrical devices, 50m', 10, 3, 4, true, 20, 5, 7),
       (1, 'Ball valve DN50', 'VB50-16-BSP-DN50', 'Steel ball valve for water installation, 50 mm diameter', 20, 10, 2, false, 50, 10, 8),
       (1, 'Screw M12x60', 'DIN933-M12x60-8.8', 'Metric screw M12, 60 mm length, strength class 8.8', 100, 10, 1, false, 200, 50, 9),
       (1, 'PLC controller Siemens S7-300', '6ES7312-1AE14-0AB0', 'PLC controller with 32 KB memory', 2, 10, 5, false, 5, 1, 10),
       (1, 'PLC controller Siemens S7-1200', '6ES7212-1AE40-0XB0', 'PLC controller with 100KB memory, 8DI and 6DO', 2, 10, 5, false, 5, 1, 11);

-- INSERT INTO work_order
INSERT INTO work_order (version, description, start_date, end_date, type_id, status_id, employee_id)
VALUES (1, 'Replacement of faulty sensor', '2021-01-01', '2021-01-10', 1, 3, 1),
       (1, 'Production line modernization', '2021-02-01', '2021-03-01', 2, 2, 2),
       (1, 'PLC software testing', '2021-03-01', '2021-03-10', 3, 1, 3);