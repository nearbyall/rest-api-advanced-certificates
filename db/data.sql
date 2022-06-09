INSERT INTO certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('Game Store', 'Gift certificate for purchase in the game store', 50.0, 30,
        '2021-01-26T10:00:00.0', '2021-01-26T10:00:00.0');

INSERT INTO certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('Kids Clothes', 'Gift certificate for kids clothing purchasing', 40.0, 45,
        '2021-01-26T10:00:00.0', '2021-01-26T10:00:00.0');

INSERT INTO certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('Beauty Store', 'Gift certificate for purchase in the beauty store', 60.0, 60,
        '2021-01-26T10:00:00.0', '2021-01-26T10:00:00.0');

INSERT INTO tags (name)
VALUES ('game');
INSERT INTO tags (name)
VALUES ('videogame');
INSERT INTO tags (name)
VALUES ('games');
INSERT INTO tags (name)
VALUES ('playstation');

INSERT INTO tags (name)
VALUES ('kids');
INSERT INTO tags (name)
VALUES ('children');
INSERT INTO tags (name)
VALUES ('kid');
INSERT INTO tags (name)
VALUES ('clothes');

INSERT INTO tags (name)
VALUES ('beauty');
INSERT INTO tags (name)
VALUES ('makeup');
INSERT INTO tags (name)
VALUES ('cosmetics');
INSERT INTO tags (name)
VALUES ('skincare');


INSERT INTO certificates_tags (certificate_id, tag_id)
VALUES (1, 1);
INSERT INTO certificates_tags (certificate_id, tag_id)
VALUES (1, 2);
INSERT INTO certificates_tags (certificate_id, tag_id)
VALUES (1, 3);
INSERT INTO certificates_tags (certificate_id, tag_id)
VALUES (1, 4);

INSERT INTO certificates_tags (certificate_id, tag_id)
VALUES (2, 5);
INSERT INTO certificates_tags (certificate_id, tag_id)
VALUES (2, 6);
INSERT INTO certificates_tags (certificate_id, tag_id)
VALUES (2, 7);
INSERT INTO certificates_tags (certificate_id, tag_id)
VALUES (2, 8);

INSERT INTO certificates_tags (certificate_id, tag_id)
VALUES (3, 9);
INSERT INTO certificates_tags (certificate_id, tag_id)
VALUES (3, 10);
INSERT INTO certificates_tags (certificate_id, tag_id)
VALUES (3, 11);
INSERT INTO certificates_tags (certificate_id, tag_id)
VALUES (3, 12);