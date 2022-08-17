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
    id               INT          NOT NULL AUTO_INCREMENT,
    name             VARCHAR(45)  NOT NULL,
    create_date      TIMESTAMP(3) NOT NULL,
    last_update_date TIMESTAMP(3) NOT NULL,
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

create table if not exists users
(
    id               int auto_increment
        primary key,
    first_name       varchar(25) not null,
    second_name      varchar(25) not null,
    login            varchar(25) not null,
    password         varchar(25) not null,
    phone_number     varchar(17) not null,
    create_date      timestamp   null,
    last_update_date timestamp   null,
    constraint user_UN
        unique (login)
);

create table if not exists orders_for_gift_certificates
(
    id               int auto_increment
        primary key,
    order_time       timestamp default current_timestamp() not null,
    user_id          int                                   not null,
    total_amount     double                                not null,
    create_date      timestamp                             null,
    last_update_date timestamp                             null,
    constraint order_FK
        foreign key (user_id) references users (id)
);

create table if not exists order_item
(
    id                  bigint auto_increment
        primary key,
    gift_certificate_id int       not null,
    order_id            int       not null,
    quantity            int       not null,
    create_date         timestamp null,
    last_update_date    timestamp null,
    constraint order_item_FK
        foreign key (gift_certificate_id) references gift_certificate (id),
    constraint order_item_FK_1
        foreign key (order_id) references orders_for_gift_certificates (id)
);






