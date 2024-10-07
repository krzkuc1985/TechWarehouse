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