CREATE TABLE IF NOT EXISTS `User`(
    `id`            int PRIMARY KEY AUTO_INCREMENT,
    `login`         VARCHAR(100) UNIQUE NOT NULL,
    `full_name`     VARCHAR(100) NOT NULL,
    `gender`        ENUM('male', 'female') NOT NULL,
    `birth_date`    DATE NOT NULL
);