CREATE TABLE `course`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`created` datetime DEFAULT NULL,
	`modified` datetime DEFAULT NULL,
	`title` varchar(255) DEFAULT NULL,
	PRIMARY KEY (`id`)
)
