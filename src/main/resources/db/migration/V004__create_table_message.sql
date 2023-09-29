CREATE TABLE message
(
    id INT IDENTITY PRIMARY KEY NOT NULL,
    user_id INT REFERENCES users (id),
    group_id INT REFERENCES group_list (id),
    title VARCHAR(100),
    body VARCHAR(1000),
    time_stamp VARCHAR(100)
);