INSERT INTO mobile."user"
(id, address, company_name, created_at, created_by, email, "name", occupation, "password", phone_number, status, surname, tc_number, updated_at, updated_by, user_name, reset_key, is_admin)
VALUES(1, NULL, NULL, '2018-10-16 11:01:26.920', 'admin32', 'yzbasbug@gmail.com', 'admin32', NULL, 'c4ca4238a0b923820dcc509a6f75849b', NULL, 0, 'admin32', '64887511358', '2019-07-28 10:59:17.366', 'admin32', 'admin32', NULL, 1);

INSERT INTO mobile."user"
(id, address, company_name, created_at, created_by, email, "name", occupation, "password", phone_number, status, surname, tc_number, updated_at, updated_by, user_name, reset_key, is_admin)
VALUES(2, NULL, NULL, '2018-10-16 11:01:26.920', 'admin4', 'turqay@gmail.com', 'admin4', NULL, 'c4ca4238a0b923820dcc509a6f75849b', NULL, 0, 'admin4', '64887511358', '2019-07-28 10:59:17.366', 'admin4', 'admin4', NULL, 2);


INSERT INTO mobile."role" (id, role_name, short_name) VALUES(1, 'ADMIN', 'ADMN');
INSERT INTO mobile."role" (id, role_name, short_name) VALUES(2, 'USER', 'USR');
INSERT INTO mobile."role" (id, role_name, short_name) VALUES(3, 'SUPERVISOR', 'SPRVSR');
INSERT INTO mobile."role" (id, role_name, short_name) VALUES(4, 'REPORTER', 'RPRTR');



INSERT INTO mobile.userrole (role_id, user_id) VALUES(1, 1);
INSERT INTO mobile.userrole (role_id, user_id) VALUES(4, 1);
INSERT INTO mobile.userrole (role_id, user_id) VALUES(2, 1);
INSERT INTO mobile.userrole (role_id, user_id) VALUES(3, 1);
INSERT INTO mobile.userrole (role_id, user_id) VALUES(1, 65);

INSERT INTO mobile.homepage (id, number_of_forms,number_of_users, number_of_completed_forms) VALUES(1,0,2,0);


Select * from mobile.userrole where user_id= 1;

Select * from mobile.userrole where to_tsvector('simple',"role_id" || ' ' || "user_id") @@ to_tsquery('simple','1&91');

Select COUNT(*) From mobile.user where deleted = false