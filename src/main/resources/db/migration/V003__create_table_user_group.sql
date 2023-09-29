CREATE TABLE users_group
(
    id INT IDENTITY PRIMARY KEY NOT NULL,
    group_id INT REFERENCES group_list (id),
    user_id INT REFERENCES users (id)
);