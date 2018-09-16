INSERT INTO fr_db.user (id, email, password, username) VALUES ('admin_id', 'admin@admin.hu', '$2a$10$TrDVeb16jK/Yl/0sjoSgkujTy0FLjU0IpYdWIf4vhPKAPbUSCQheC', 'admin')

INSERT INTO fr_db.role (id, name) VALUES (1, 'ROLE_ADMIN')
INSERT INTO fr_db.role (id, name) VALUES (2, 'ROLE_USER')
INSERT INTO fr_db.role (id, name) VALUES (3, 'ROLE_ANONYMOUS')

INSERT INTO fr_db.user_role (user_id, role_id) VALUES ('admin_id', 1)
INSERT INTO fr_db.user_role (user_id, role_id) VALUES ('admin_id', 2)