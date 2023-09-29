INSERT group_list VALUES ('School Chat');
INSERT group_list VALUES ('Gaming Chat');
INSERT group_list VALUES ('Sport Chat');

INSERT users VALUES ('Vegard','vegard@gmail.com','90432921');
INSERT users VALUES ('Marius','sander@gmail.com','20583431');
INSERT users VALUES ('Bjørnar', N'Bjørnar@gmail.com', '90583921');

-- groupid userid
INSERT INTO users_group VALUES (1,1);
INSERT INTO users_group VALUES (1,3);
INSERT INTO users_group VALUES (1,3);
INSERT INTO users_group VALUES (2,2);
INSERT INTO users_group VALUES (2,3);
INSERT INTO users_group VALUES (2,3);
INSERT INTO users_group VALUES (2,3);
INSERT INTO users_group VALUES (3,1);


-- userid Groupid
INSERT INTO message VALUES (1,1,'Exam Start','What does the Backend Exam Start?','November 20 2022 16:13');
INSERT INTO message VALUES (3,1,'Exam Start','It was Due last week...','November 20 2022 16:13');
INSERT INTO message VALUES (2,2,'CSGO','Anyone wanna play csgo?','November 20 2022 16:13');
INSERT INTO message VALUES (3,2,'League','Im playing lol atm','November 20 2022 16:13');
INSERT INTO message VALUES (1,1,'UFC','Anyone watching UFC Next week?','November 20 2022 16:13');