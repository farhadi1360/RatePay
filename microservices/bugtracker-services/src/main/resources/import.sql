

-- *********************************************************Insert to Role *************************************************
INSERT INTO roles (ID, CREATED_DATE, CREATED_BY, MODIFIED_BY,MODIFIED_DATE,VERSION, role) VALUES  (1, CURRENT_TIMESTAMP, 'RatePaySystem','RatePaySystem', CURRENT_TIMESTAMP, 0,'ROLE_USER');
INSERT INTO roles (ID, CREATED_DATE, CREATED_BY, MODIFIED_BY,MODIFIED_DATE,VERSION, role) VALUES  (2, CURRENT_TIMESTAMP, 'RatePaySystem','RatePaySystem', CURRENT_TIMESTAMP, 0,'ROLE_MANAGER');
INSERT INTO roles (ID, CREATED_DATE, CREATED_BY, MODIFIED_BY,MODIFIED_DATE,VERSION, role) VALUES  (3, CURRENT_TIMESTAMP, 'RatePaySystem','RatePaySystem', CURRENT_TIMESTAMP, 0,'ROLE_DEVELOPER');


-- *********************************************************Insert to Users *************************************************

INSERT INTO users(ID, CREATED_DATE, CREATED_BY, MODIFIED_BY,MODIFIED_DATE,VERSION, email, password, username)
            VALUES(1, CURRENT_TIMESTAMP, 'RatePaySystem','RatePaySystem', CURRENT_TIMESTAMP, 0, 'farhadi.kam@gmail.com', '$2a$10$ZTX73MnjSclVRV/l/dshAeCCqGYoWvEoyx6.QrJeSr0B5IoMuU6dW', 'm.farhadi');
INSERT INTO users(ID, CREATED_DATE, CREATED_BY, MODIFIED_BY,MODIFIED_DATE,VERSION, email, password, username)
            VALUES(2, CURRENT_TIMESTAMP, 'RatePaySystem','RatePaySystem', CURRENT_TIMESTAMP, 0, 'ratepay-jobs@m.personio.de', '$2a$10$ZTX73MnjSclVRV/l/dshAeCCqGYoWvEoyx6.QrJeSr0B5IoMuU6dW', 'ratepay');


-- *********************************************************Insert to User_Roles *************************************************
insert into user_roles (USER_ID, ROLE_ID) values (1, 1);
insert into user_roles (USER_ID, ROLE_ID) values (1, 2);
insert into user_roles (USER_ID, ROLE_ID) values (1, 3);
insert into user_roles (USER_ID, ROLE_ID) values (2, 1);
insert into user_roles (USER_ID, ROLE_ID) values (2, 2);
insert into user_roles (USER_ID, ROLE_ID) values (2, 3);

-- *********************************************************Insert to Projects *************************************************
insert into projects (ID, CREATED_DATE, CREATED_BY, MODIFIED_BY,MODIFIED_DATE,VERSION,CODE, NAME, PROJECT_MANAGER_ID) values (1, CURRENT_TIMESTAMP, 'RatePaySystem','RatePaySystem', CURRENT_TIMESTAMP, 0,10, 'Bug Tracker', 1);
insert into projects (ID, CREATED_DATE, CREATED_BY, MODIFIED_BY,MODIFIED_DATE,VERSION,CODE, NAME, PROJECT_MANAGER_ID) values (2, CURRENT_TIMESTAMP, 'RatePaySystem','RatePaySystem', CURRENT_TIMESTAMP, 0,11, 'Blog', 2);

-- *********************************************************Insert to Project_Developer *************************************************
insert into project_developer (project_id, developer_id) values (1, 1);
insert into project_developer (project_id, developer_id) values (1, 2);
insert into project_developer (project_id, developer_id) values (2, 2);
