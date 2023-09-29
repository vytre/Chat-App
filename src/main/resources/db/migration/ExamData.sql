INSERT group_list VALUES ('School Chat');
INSERT group_list VALUES ('Gaming Chat');
INSERT group_list VALUES ('Sport Chat');

INSERT users VALUES ('Vegard','vegard@gmail.com','90432921');
INSERT users VALUES ('Sander','sander@gmail.com','20583431');
INSERT users VALUES ('Havard','havard@gmail.com','80583921');
INSERT users VALUES ('Bogdan','bogdan@gmail.com','90111921');
INSERT users VALUES ('Bjørnar', N'Bjørnar@gmail.com', '90583921');

-- groupid userid
INSERT INTO users_group VALUES (1,1);
INSERT INTO users_group VALUES (1,4);
INSERT INTO users_group VALUES (1,5);
INSERT INTO users_group VALUES (2,2);
INSERT INTO users_group VALUES (2,3);
INSERT INTO users_group VALUES (2,4);
INSERT INTO users_group VALUES (2,5);
INSERT INTO users_group VALUES (3,1);


-- userid Groupid
INSERT INTO message VALUES (1,1,'Exam Start','What does the Backend Exam Start?','November 20 2022 16:13');
INSERT INTO message VALUES (4,1,'Exam Start','It was Due last week...','November 20 2022 16:13');
INSERT INTO message VALUES (2,2,'CSGO','Anyone wanna play csgo?','November 20 2022 16:13');
INSERT INTO message VALUES (5,2,'League','Im playing lol atm','November 20 2022 16:13');
INSERT INTO message VALUES (1,1,'UFC','Anyone watching UFC Next week?','November 20 2022 16:13');