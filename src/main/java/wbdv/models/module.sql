CREATE TABLE `module`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`title` varchar(255) DEFAULT NULL,
	`course_id` int(11) DEFAULT NULL,
	PRIMARY KEY (`id`),
	KEY `FKfq09oddpwjoxcirvkh9vnfnsg` (`course_id`)
)