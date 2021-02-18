drop table if exists `bag` CASCADE;
drop table if exists `item` CASCADE;

create table bag (id bigint PRIMARY KEY AUTO_INCREMENT, listName varchar(255) not null);
create table item (id bigint PRIMARY KEY AUTO_INCREMENT, itemName varchar(255) not null, price double not null, bag_id bigint);
