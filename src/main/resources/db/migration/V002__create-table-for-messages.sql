create table message (
       id bigint not null auto_increment,
       email varchar(40),
       message varchar(240),
       message_date date,
       name varchar(40),
       primary key (id)
)