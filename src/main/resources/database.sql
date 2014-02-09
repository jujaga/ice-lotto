
-- Table: prize_items
create table prize_items ( 
  id          integer primary key
                      unique,
  name        varchar,
  description varchar 
);


-- Table: prize_tiers
create table prize_tiers ( 
  id      integer primary key autoincrement
                  not null
                  default ( 0 ),
  item_1  integer references prize_items ( id ),
  item_2  integer references prize_items ( id ),
  item_3  integer references prize_items ( id ),
  item_4  integer references prize_items ( id ),
  item_5  integer references prize_items ( id ),
  item_6  integer references prize_items ( id ),
  item_7  integer references prize_items ( id ),
  item_8  integer references prize_items ( id ),
  item_9  integer references prize_items ( id ),
  item_10 integer references prize_items ( id ) 
);


-- Table: prize_pools
create table prize_pools ( 
  id      integer primary key autoincrement
                  not null
                  unique
                  default ( 0 ),
  tier_1  integer references prize_tiers ( id ),
  tier_2  integer references prize_tiers ( id ),
  tier_3  integer references prize_tiers ( id ),
  tier_4  integer references prize_tiers ( id ),
  tier_5  integer references prize_tiers ( id ),
  tier_6  integer references prize_tiers ( id ),
  tier_7  integer references prize_tiers ( id ),
  tier_8  integer references prize_tiers ( id ),
  tier_9  integer references prize_tiers ( id ),
  tier_10 integer references prize_tiers ( id ) 
);


-- Table: users
create table users ( 
  id               integer primary key autoincrement
                           not null
                           unique
                           default ( 0 ),
  email            varchar,
  display_name     varchar,
  gw2_display_name varchar,
  password         varchar,
  salt             varchar 
);


-- Table: characters
create table characters ( 
  user_id integer not null
                  references users ( id ),
  name    varchar,
  primary key ( user_id, name ) 
);


-- Table: drawings
create table drawings ( 
  id         integer  primary key autoincrement
                      not null,
  scheduled  datetime,
  held       datetime,
  prize_pool integer  references prize_pools ( id ),
  name       varchar 
);


-- Table: drawing_results
create table drawing_results ( 
  id          integer primary key autoincrement
                      not null
                      unique
                      default ( 0 ),
  drawing_id  integer not null
                      references drawings ( id ),
  winner_id   integer,
  prize_pool  integer not null
                      references prize_pools ( id ),
  prize_tier  integer not null
                      references prize_pools ( id ),
  item_number integer 
);


-- Table: roles
create table roles ( 
  id          integer primary key autoincrement
                      not null
                      unique
                      default ( 0 ),
  name        varchar not null
                      unique,
  description varchar not null 
);


-- Table: user_roles
create table user_roles ( 
  role_id integer not null
                  references roles ( id ),
  user_id integer not null
                  references users ( id ),
  primary key ( role_id, user_id ) 
);

