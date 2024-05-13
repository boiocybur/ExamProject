CREATE SCHEMA IF NOT EXISTS projects;

USE projects;

DROP TABLE IF EXISTS bruger;

CREATE TABLE bruger (
                        userid INTEGER AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(100),
                        userpassword VARCHAR(100)
);

INSERT INTO bruger (username, userpassword) VALUES ('Oskar', '1234');
INSERT INTO bruger (username, userpassword) VALUES ('Mikkel', '1234');
INSERT INTO bruger (username, userpassword) VALUES ('Jesper', '1234');
