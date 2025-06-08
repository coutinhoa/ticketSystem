CREATE TABLE T_USER(
    id bigserial primary key,
    name character VARYING NOT NULL,
    password character VARYING NOT NULL,
    email character VARYING NOT NULL
);

INSERT INTO T_USER (name, password, email) values ('Admin', 'admin', 'admin@email.com');
INSERT INTO T_USER (name, password, email) values ('Aaa', 'password1', 'a@email.com');
INSERT INTO T_USER (name, password, email) values ('Bbb', 'password2', 'b@email.com');
INSERT INTO T_USER (name, password, email) values ('Ccc', 'password3', 'c@email.com');
INSERT INTO T_USER (name, password, email) values ('Ddd', 'password4', 'd@email.com');
INSERT INTO T_USER (name, password, email) values ('Eee', 'password5', 'e@email.com');
INSERT INTO T_USER (name, password, email) values ('Fff', 'password6', 'f@email.com');



