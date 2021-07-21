drop table if exists membership;

create table membership
(
    membership_idx    bigint           not null auto_increment,
    start_date        datetime(6),
    membership_status varchar(255)     not null,
    membership_id     varchar(255)     not null,
    membership_name   varchar(255)     not null,
    rate              double precision not null,
    point             decimal(19, 2)   not null,
    user_id           varchar(255)     not null,
    primary key (membership_idx)
) engine=InnoDB;

alter table membership
    add constraint UK9cxyc3gdk575d5wjlbjuel12o unique (user_id, membership_id);

CREATE INDEX IDX_Membersip_Membership_id ON `membership` (`membership_id`);