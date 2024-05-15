CREATE SCHEMA if not exists alphaSolutions;
use alphasolutions;

drop table if exists projectLists;
drop table if exists tasks;
drop table if exists projects;
drop table if exists users;


CREATE TABLE users (
                       userID INTEGER AUTO_INCREMENT,
                       userName VARCHAR(100),
                       userPassword VARCHAR(100),
                       userEmail VARCHAR(100),
                       primary key (userID)
);

CREATE TABLE projects (
                          projectID INTEGER AUTO_INCREMENT,
                          projectName VARCHAR(255) NOT NULL,
                          projectDescription TEXT,
                          projectStartDate DATE,
                          projectBudget DOUBLE,
                          dueDate DATE,
                          completionDate DATE,
                          userID INTEGER NOT NULL,
                          FOREIGN KEY (userID) REFERENCES users(userID),
                          primary key (projectID)
);

CREATE TABLE tasks (
                       taskID INTEGER AUTO_INCREMENT,
                       taskDescription TEXT,
                       taskStartDate DATE,
                       taskEndDate DATE,
                       status VARCHAR(50),
                       projectID INTEGER NOT NULL,
                       FOREIGN KEY (projectID) REFERENCES projects(projectID)
                           ON DELETE CASCADE ON UPDATE CASCADE,
                       primary key(taskID)

);

CREATE TABLE projectLists (
                              projectListID INTEGER AUTO_INCREMENT,
                              projectListName VARCHAR(50),
                              userID INTEGER,
                              FOREIGN KEY (userID) REFERENCES users(userID),
                              primary key(projectListID)
);


INSERT INTO users (userName, userPassword, userEmail)
VALUES
    ('Oskar', '1234', 'Oskar@mail.dk'),
    ('Mikkel', '1234', 'Mikkel@mail.dk'),
    ('Jesper', '1234', 'Jesper@mail.dk');

-- Overdue projects
INSERT INTO projects
(projectName, projectDescription, projectStartDate, projectBudget, dueDate, completionDate, userID)
VALUES
    ('Website Redesign', 'Overdue website overhaul', '2022-01-01', 10000, '2023-04-01', NULL, 1),
    ('Marketing Launch', 'Overdue launch of marketing campaign', '2022-02-01', 5000, '2023-04-10', NULL, 2);

-- Imminent projects
INSERT INTO projects
(projectName, projectDescription, projectStartDate, projectBudget, dueDate, completionDate, userID)
VALUES
    ('New Software', 'Launch of new software version', '2023-01-01', 15000, '2024-05-10', NULL, 1),
    ('Office Move', 'Relocation of office premises', '2023-02-01', 20000, '2024-05-15', NULL, 2);

-- Completed projects
INSERT INTO projects
(projectName, projectDescription, projectStartDate, projectBudget, dueDate, completionDate, userID)
VALUES
    ('App Update', 'Update of mobile application', '2022-01-01', 8000, '2025-04-01', '2024-05-04', 1),
    ('HR System Overhaul', 'Complete revamp of HR system', '2022-02-01', 12000, '2025-04-20', '2024-05-07', 2);

-- Insert data into the tasks table (example entries)
INSERT INTO tasks
(projectID, taskDescription, taskStartDate, taskEndDate, status)
VALUES
    (1, 'Design new layout', '2022-01-10', '2022-02-10', 'Completed'),
    (1, 'Implement responsive features', '2022-02-15', '2022-03-15', 'InProgress'),
    (2, 'Prepare marketing materials', '2022-02-05', '2022-03-05', 'Completed');

INSERT INTO projectLists
(projectListName, userID)
VALUES
    ('Oskar', 1),
    ('Mikkel', 2),
    ('Jesper', 3);

