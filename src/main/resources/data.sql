CREATE SCHEMA if not exists alphaSolutions;
use alphasolutions;

drop table if exists taskAcceptCriteria;
drop table if exists tasks;
drop table if exists projects;
drop table if exists users;



CREATE TABLE users (
                       userID INTEGER AUTO_INCREMENT,
                       userName VARCHAR(100),
                       userPassword VARCHAR(100),
                       userEmail VARCHAR(100),
                       userRank varchar(100),
                       primary key (userID)
);

CREATE TABLE projects (
                          projectID INTEGER AUTO_INCREMENT,
                          projectName VARCHAR(255) NOT NULL,
                          projectDescription TEXT,
                          projectStartDate DATE,
                          projectDueDate DATE,
                          projectBudget DOUBLE,
                          projectCompletionDate DATE,
                          userID INTEGER NOT NULL,
                          FOREIGN KEY (userID) REFERENCES users(userID) ON DELETE CASCADE ON UPDATE CASCADE,
                          primary key (projectID)

);

CREATE TABLE tasks (
                       taskID INTEGER AUTO_INCREMENT,
                       taskName varchar(50),
                       taskDescription TEXT,
                       taskStartDate DATE,
                       taskDueDate DATE,
                       estimatedHours DOUBLE,
                       actualHours DOUBLE,
                       projectID INTEGER NOT NULL,
                       userID INTEGER NOT NULL,
                       FOREIGN KEY (projectID) REFERENCES projects(projectID)
                           ON DELETE CASCADE ON UPDATE CASCADE,
                       FOREIGN KEY (userID) REFERENCES users(userID)
                           ON DELETE CASCADE ON UPDATE CASCADE,
                       primary key(taskID)

);

CREATE TABLE taskAcceptCriteria (
                                    criteriaID INTEGER AUTO_INCREMENT,
                                    taskID INTEGER,
                                    taskAcceptCriteriaTEXT TEXT,
                                    taskStatus BOOLEAN DEFAULT FALSE,
                                    FOREIGN KEY (taskID) REFERENCES tasks(taskID)
                                        ON DELETE CASCADE ON UPDATE CASCADE,
                                    PRIMARY KEY (criteriaID)
);


INSERT INTO users (userName, userPassword, userEmail, userRank)
VALUES
    ('Oskar', '1234', 'Oskar@mail.dk', 'admin'),
    ('Mikkel', '1234', 'Mikkel@mail.dk', 'admin'),
    ('Jesper', '1234', 'Jesper@mail.dk', 'generic');

-- Overdue projects
INSERT INTO projects
(projectName, projectDescription, projectStartDate, projectDueDate, projectBudget, projectCompletionDate, userID)
VALUES
    ('Website Redesign', 'Overdue website overhaul', '2022-01-01', '2023-04-01', 10000, NULL, 1),
    ('Marketing Launch', 'Overdue launch of marketing campaign', '2022-02-01', '2023-04-10', 5000, NULL, 2);

-- Imminent projects
INSERT INTO projects
(projectName, projectDescription, projectStartDate, projectDueDate, projectBudget, projectCompletionDate, userID)
VALUES
    ('New Software', 'Launch of new software version', '2023-01-01', '2024-05-10', 15000, NULL, 1),
    ('Office Move', 'Relocation of office premises', '2023-02-01', '2024-05-15', 20000, NULL, 3);

-- Completed projects
INSERT INTO projects
(projectName, projectDescription, projectStartDate, projectDueDate, projectBudget, projectCompletionDate, userID)
VALUES
    ('App Update', 'Update of mobile application', '2022-01-01', '2025-04-01', 8000, '2024-05-04', 1),
    ('HR System Overhaul', 'Complete revamp of HR system', '2022-02-01', '2025-04-20', 12000, '2024-05-07', 2);

-- Insert data into the tasks table
INSERT INTO tasks
(projectID, taskName, taskDescription, taskStartDate, taskDueDate, estimatedHours, actualHours, userID)
VALUES
    (1, 'layout task', 'Design new layout', '2022-01-10', '2022-02-10', 50, 60, 1),
    (1,'implement features' ,'Implement responsive features', '2022-02-15', '2022-03-15', 100, 120, 1),
    (2, 'market materials','Prepare marketing materials', '2022-02-05', '2022-03-05', 40, 30, 2),
    (1, 'refactoring', 'refactor backend code', '2022-01-10', '2022-03-11', 80, 90, 1);

INSERT INTO taskAcceptCriteria
(taskID, taskAcceptCriteriaTEXT, taskStatus)
VALUES
    (1, 'Unit tests', FALSE),
    (1, 'UI tests', FALSE);

