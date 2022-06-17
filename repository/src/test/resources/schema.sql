CREATE TABLE IF NOT EXISTS gift_certificate
(
    id               INT          NOT NULL AUTO_INCREMENT,
    name             VARCHAR(45)  NOT NULL,
    description      VARCHAR(255) NOT NULL,
    price            DOUBLE       NOT NULL,
    duration         INT          NOT NULL,
    create_date      TIMESTAMP(3) NOT NULL,
    last_update_date TIMESTAMP(3) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS tag
(
    id   INT         NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    PRIMARY KEY (id),
    unique (name)
);

CREATE TABLE IF NOT EXISTS gift_certificate_has_tag
(
    gift_certificate_id INT NOT NULL,
    tag_id              INT NOT NULL,
    PRIMARY KEY (gift_certificate_id, tag_id),
    CONSTRAINT fk_gift_certificate_has_tag_gift_certificate
        FOREIGN KEY (gift_certificate_id)
            REFERENCES gift_certificate (id)
            ON DELETE CASCADE
            ON UPDATE NO ACTION,
    CONSTRAINT fk_gift_certificate_has_tag_tag1
        FOREIGN KEY (tag_id)
            REFERENCES tag (id)
            ON DELETE CASCADE
            ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS users
(
    id          int auto_increment
        primary key,
    first_name  varchar(25) not null,
    second_name varchar(25) not null,
    user_name   varchar(25) not null,
    password    varchar(25) not null,
    unique (user_name)
);