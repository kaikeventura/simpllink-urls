create table link (
       id bigint not null auto_increment,
       create_date date,
       is_activated bit,
       link longtext,
       shorted_link varchar(20),
       number_of_days_active int,
       primary key (id)
)