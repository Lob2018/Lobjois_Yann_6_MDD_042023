DROP TABLE IF EXISTS `COMMENTS`;
DROP TABLE IF EXISTS `POSTS`;
DROP TABLE IF EXISTS `SUBSCRIPTIONS`;
DROP TABLE IF EXISTS `SUBJECTS`;
DROP TABLE IF EXISTS `USERS`;

CREATE TABLE `USERS` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
);

CREATE UNIQUE INDEX `USERSMAIL_index` ON `USERS` (`email`);

CREATE TABLE `SUBJECTS` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `topic` varchar(255),
  `description` varchar(2000)
);
CREATE UNIQUE INDEX `SUBJECTTOPIC_index` ON `SUBJECTS` (`topic`);

CREATE TABLE `SUBSCRIPTIONS` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `user_id` integer NOT NULL,
  `subject_id` integer NOT NULL,
  FOREIGN KEY (`user_id`) REFERENCES `USERS`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`subject_id`) REFERENCES `SUBJECTS`(`id`) ON DELETE CASCADE
);

CREATE TABLE `POSTS` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `author_id` integer NOT NULL,
  `subject_id` integer NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` varchar(2000) NOT NULL,
  `created_at` timestamp NOT NULL,
  FOREIGN KEY (`author_id`) REFERENCES `USERS`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`subject_id`) REFERENCES `SUBJECTS`(`id`) ON DELETE CASCADE
);

CREATE TABLE `COMMENTS` (
  `id` integer PRIMARY KEY AUTO_INCREMENT,
  `commentator_id` integer NOT NULL,
  `post_id` integer NOT NULL,
  `created_at` timestamp NOT NULL,
  `comment` varchar(2000) NOT NULL,
  FOREIGN KEY (`commentator_id`) REFERENCES `USERS`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`post_id`) REFERENCES `POSTS`(`id`) ON DELETE CASCADE
);

INSERT INTO `SUBJECTS`
VALUES (1,'Java','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.');
INSERT INTO `SUBJECTS`
VALUES (2,'JavaScript','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.');
INSERT INTO `SUBJECTS`
VALUES (3,'Python','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.');
INSERT INTO `SUBJECTS`
VALUES (4,'Web3','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.');