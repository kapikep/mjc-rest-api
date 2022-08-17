insert into tag values(default, 'Sport', '2022-04-27 04:43:55.000','2022-04-27 04:43:55.000');
insert into tag values(default, 'Water', '2022-04-27 04:43:55.000','2022-04-27 04:43:55.000');
insert into tag values(default, 'Photo-session', '2022-04-27 04:43:55.000','2022-04-27 04:43:55.000');
insert into tag values(default, 'Cafe', '2022-04-27 04:43:55.000','2022-04-27 04:43:55.000');
insert into tag values(default, 'Auto', '2022-04-27 04:43:55.000','2022-04-27 04:43:55.000');
insert into tag values(default, 'Romantic', '2022-04-27 04:43:55.000','2022-04-27 04:43:55.000');
insert into tag values(default, 'Health', '2022-04-27 04:43:55.000','2022-04-27 04:43:55.000');

insert into gift_certificate values(default,'Water skiing', 'Water skiing on Minsk sea', 20, 50, '2022-04-27 04:43:55.000', '2022-04-27 04:43:55.000');
insert into gift_certificate values(default,'Car wash', 'Complex for cars with washing and body treatment from KlinArt', 100, 180, '2022-04-27 04:43:55.000', '2022-04-27 04:43:55.000');
insert into gift_certificate values(default,'Relaxing massage "Antistress"', 'Relaxing massage in Minsk will help to get rid of both nervous and muscle tension', 50, 60, '2022-04-27 04:43:55.000', '2022-04-27 04:43:55.000');
insert into gift_certificate values(default,'Bowling for the company', 'Bowling will be an excellent option for outdoor activities for a large company', 45, 60, '2022-04-27 04:43:55.000', '2022-04-27 04:43:55.000');
insert into gift_certificate values(default,'Karting on a two-storey highway', 'Gift certificate for skating in the renovated club of Minsk "Kartland"', 20, 10, '2022-04-27 04:43:55.000', '2022-04-27 04:43:55.000');

insert into gift_certificate_has_tag values(1,1);
insert into gift_certificate_has_tag values(1,2);
insert into gift_certificate_has_tag values(1,7);
insert into gift_certificate_has_tag values(2,5);
insert into gift_certificate_has_tag values(3,7);
insert into gift_certificate_has_tag values(4,5);
insert into gift_certificate_has_tag values(4,7);
insert into gift_certificate_has_tag values(5,5);

insert into users values(default, 'Vasilij', 'Pupkin', 'Vasilij', 'fasfq3wfw', '+375292629988', '2022-04-27 04:43:55.000', '2022-04-27 04:43:55.000');
insert into users values (default, 'Svetlana', 'Vasilievna', 'Svetlana', 'dewqewqr44', '+375292629995', '2022-04-27 04:43:55.000', '2022-04-27 04:43:55.000');
insert into users values (default, 'Kirill', 'Ivanovich', 'Kirill', 'dew88wqr44', '+375292629969', '2022-04-27 04:43:55.000', '2022-04-27 04:43:55.000');
insert into users values (default, 'Vladimir', 'Ponamarev', 'Vladimir', 'ewrr3w88wqr44', '+375292629944', '2022-04-27 04:43:55.000', '2022-04-27 04:43:55.000');
insert into users values (default, 'Igor', 'Vasiliev', 'Igor', 'ewqawerasdqr44', '+375292628844', '2022-04-27 04:43:55.000', '2022-04-27 04:43:55.000');

insert into orders_for_gift_certificates values (default, '2022-05-27 04:43:55.000', 3, 50, '2022-05-27 04:43:55.000', '2022-05-27 04:43:55.000');
insert into orders_for_gift_certificates values (default, '2022-06-27 04:43:55.000', 3, 150, '2022-06-27 04:43:55.000', '2022-06-27 04:43:55.000');
insert into orders_for_gift_certificates values (default, '2022-07-27 04:43:55.000', 3, 150, '2022-07-27 04:43:55.000', '2022-07-27 04:43:55.000');
insert into orders_for_gift_certificates values (default, '2022-06-27 04:43:55.000', 4, 150, '2022-06-27 04:43:55.000', '2022-06-27 04:43:55.000');
insert into orders_for_gift_certificates values (default, '2022-06-27 04:43:55.000', 4, 70, '2022-06-27 04:43:55.000', '2022-06-27 04:43:55.000');
insert into orders_for_gift_certificates values (default, '2022-06-27 04:43:55.000', 2, 100, '2022-06-27 04:43:55.000', '2022-06-27 04:43:55.000');
insert into orders_for_gift_certificates values (default, '2022-06-27 04:43:55.000', 1, 30, '2022-06-27 04:43:55.000', '2022-06-27 04:43:55.000');

insert into order_item values(default, 1, 1, 2, '2022-05-27 04:43:55.000', '2022-05-27 04:43:55.000');
insert into order_item values(default, 4, 2, 1, '2022-05-27 04:43:55.000', '2022-05-27 04:43:55.000');
insert into order_item values(default, 5, 3, 2, '2022-05-27 04:43:55.000', '2022-05-27 04:43:55.000');
insert into order_item values(default, 1, 7, 5, '2022-05-27 04:43:55.000', '2022-05-27 04:43:55.000');
insert into order_item values(default, 1, 4, 2, '2022-05-27 04:43:55.000', '2022-05-27 04:43:55.000');
insert into order_item values(default, 2, 4, 3, '2022-05-27 04:43:55.000', '2022-05-27 04:43:55.000');
insert into order_item values(default, 3, 5, 1, '2022-05-27 04:43:55.000', '2022-05-27 04:43:55.000');
