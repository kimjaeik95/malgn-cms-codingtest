CREATE TABLE users (
    id              BIGINT PRIMARY  KEY AUTO_INCREMENT,
    username        varchar(30)     NOT NULL,
    password        varchar(255)    NOT NULL,
    role            varchar(10)     NOT NULL,
    created_date    TIMESTAMP
);