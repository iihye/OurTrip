
create database `ourtrip`;

use `ourtrip`;

create table `user`
(
	`user_id` varchar(255) NOT NULL PRIMARY KEY ,
    `user_pw` varchar(255) NOT NULL,
    `user_name` varchar(255) NOT NULL,
    `token` varchar(255) NULL
);

create table `list`
(
	`list_no` integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `list_name` text NOT NULL,
    `list_img` integer NOT NULL,
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
    `place_name` text NOT NULL,
    `place_url` varchar(255) NOT NULL,
    `place_address_name` text NOT NULL,
    `place_road_address_name` text NOT NULL,
    `place_phone` text NOT NULL,
    `place_x` decimal NOT NULL,
    `place_y` decimal NOT NULL,
    `list_no` integer NOT NULL,
     CONSTRAINT `place_to_list_list_no_fk` FOREIGN KEY (`list_no`) REFERENCES `list` (`list_no`) ON DELETE CASCADE
);
