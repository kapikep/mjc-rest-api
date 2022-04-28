insert into tag values(default, 'Sport');
insert into tag values(default, 'Water');
insert into tag values(default, 'Photo-session');
insert into tag values(default, 'Cafe');
insert into tag values(default, 'Auto');
insert into tag values(default, 'Romantic');
insert into tag values(default, 'Health');


insert into gift_certificate values(default,'Water skiing', 'Certificate for 200 balloons', 20, 50, NOW(), NOW());
insert into gift_certificate values(default,'Car wash', 'Complex for cars with washing and body treatment from "KlinArt"', 100, 180, NOW(), NOW());
insert into gift_certificate values(default,'Relaxing massage "Antistress"', 'Relaxing massage in Minsk will help to get rid of both nervous and muscle tension', 50, 60, NOW(), NOW());
insert into gift_certificate values(default,'Bowling for the company', 'Bowling will be an excellent option for outdoor activities for a large company', 45, 60, NOW(), NOW());
insert into gift_certificate values(default,'Karting on a two-storey highway', 'Gift certificate for skating in the renovated club of Minsk "Kartland"', 20, 10, NOW(), NOW());


insert into gift_certificate_has_tag values(1,1);
insert into gift_certificate_has_tag values(1,2);
insert into gift_certificate_has_tag values(1,3);
insert into gift_certificate_has_tag values(2,6);
insert into gift_certificate_has_tag values(3,7);
insert into gift_certificate_has_tag values(4,7);
insert into gift_certificate_has_tag values(4,5);