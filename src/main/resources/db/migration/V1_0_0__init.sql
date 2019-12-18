CREATE TABLE `users` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `tasks` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `assignee_user_id` int(10) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `reviewers` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `task_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `users` (`id`, `name`) VALUES
(1, 'Alice'),
(2, 'Bob'),
(3, 'Charlie')
;

INSERT INTO `tasks` (`id`, `name`, `assignee_user_id`) VALUES
(1, 'Task1', 1),
(2, 'Task2', 2),
(3, 'Task3', 2)
;

INSERT INTO `reviewers` (`task_id`, `user_id`) VALUES
(1, 2),
(1, 3),
(2, 3)
;