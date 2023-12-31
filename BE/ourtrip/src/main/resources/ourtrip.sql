drop database if exists `ourtrip`;

create database `ourtrip`;

use `ourtrip`;


create table `user`
(
	`user_id` varchar(255) NOT NULL PRIMARY KEY ,
    `user_pw` varchar(255) NOT NULL,
    `user_name` varchar(255) NOT NULL,
    `token` varchar(255) NULL
);

insert into `user`
values ("test", "test", "test", "test");
insert into `user`
values ("admin", "admin", "admin", "admin");

create table `list`
(
	`list_no` integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `list_name` varchar(255) NOT NULL,
    `list_img`  varchar(255) NULL,
	`list_open` boolean NOT NULL default false,
    `user_id` varchar(255) NOT NULL,
	CONSTRAINT `list_to_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);




create table `share`
(
	`list_no` integer NOT NULL,
    `user_id` varchar(255) NOT NULL,
    CONSTRAINT `share_to_list_list_no_fk` FOREIGN KEY (`list_no`) REFERENCES `list` (`list_no`) ON DELETE CASCADE,
    CONSTRAINT `share_to_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
    
);


create table `place`
(
	`place_no` integer NOT NULL primary key AUTO_INCREMENT,
    `place_name` varchar(255) NOT NULL,
    `place_url` varchar(255) NOT NULL,
    `place_address_name` varchar(255) NOT NULL,
    `place_road_address_name` varchar(255) NOT NULL,
    `place_phone` varchar(255) NOT NULL,
    `place_x` varchar(255) NOT NULL,
    `place_y` varchar(255) NOT NULL,
    `place_id` varchar(255) NOT NULL,
    `list_no` integer NOT NULL,
     CONSTRAINT `place_to_list_list_no_fk` FOREIGN KEY (`list_no`) REFERENCES `list` (`list_no`) ON DELETE CASCADE
);

create table `reply`
(
	`reply_no` integer NOT NULL primary key AUTO_INCREMENT,
    `reply_content` varchar(512) NOT NULL,
    `reply_datetime` datetime default current_timestamp,    
    `reply_like` integer default 0,
    `list_no` integer NOT NULL,
    `user_id` varchar(255) NOT NULL,
     CONSTRAINT `reply_to_list_list_no_fk` FOREIGN KEY (`list_no`) REFERENCES `list` (`list_no`) ON DELETE CASCADE,
     CONSTRAINT `reply_to_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);

create table `replylike`
(
	`reply_no` integer NOT NULL,
    `user_id` varchar(255) NOT NULL,
	primary key (`reply_no`, `user_id`),
    CONSTRAINT `replylike_to_reply_reply_no_fk` FOREIGN KEY (`reply_no`) REFERENCES `reply` (`reply_no`) ON DELETE CASCADE,
    CONSTRAINT `replylike_to_user_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
);