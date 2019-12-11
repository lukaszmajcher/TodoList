DROP TABLE IF EXISTS tasks;

CREATE TABLE tasks(
    id IDENTITY,
    title VARCHAR(100),
    description VARCHAR(1024),
    created_at TIMESTAMP
);
