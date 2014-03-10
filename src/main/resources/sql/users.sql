insert into users (id, gw2display_name, enabled) values(1, "Morhyn.8032", 0);
insert into roles (id, name, description) values(1, "admin", "Application Administrator");
insert into user_roles(user_id, role_id) values(1, 1);

insert into characters (name) values ("Semaj Srenmus");
insert into characters (name) values ("Krait Hate");
insert into characters (name) values ("Poncy Jigglebottom");

insert into user_characters (user_id, character_id) values(1, 1);
insert into user_characters (user_id, character_id) values(1, 2);
insert into user_characters (user_id, character_id) values(1, 3);

insert into users (id, gw2display_name, enabled) values(2, "Sanctum.7938", 0);
insert into user_roles (user_id, role_id) values(2, 1);
insert into characters (name) values("Sanctum Chrae");
insert into user_characters (user_id, character_id) values(2, 4);