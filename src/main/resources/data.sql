CREATE SCHEMA if not exists alphaSolutions;
use alphasolutions;


ALTER TABLE project DROP FOREIGN KEY project_ibfk_1;

drop table if exists projectList;
CREATE TABLE projectList (
                            projectListID INTEGER auto_increment,
                            projectListName VARCHAR(50) NOT NULL,
                            primary key(projectListID)
    );

drop table if exists project;
CREATE TABLE project (
                            projectID INTEGER AUTO_INCREMENT PRIMARY KEY,
                            projectName VARCHAR(255) NOT NULL,
                            projectDescription TEXT,
                            projectStartDate DATE,
                            projectBudget DOUBLE,
                            dueDate DATE,
                            completionDate DATE,
                            projectListID INTEGER,
                            primary key(projectID),
                            FOREIGN KEY(projectListID) REFERENCES projectList(projectListID)
    );

drop table if exists task;
CREATE TABLE task (
                            taskID INTEGER AUTO_INCREMENT PRIMARY KEY,
                            projectID INTEGER NOT NULL,
                            taskDescription TEXT,
                            taskStartDate DATE,
                            taskEndDate DATE,
                            status VARCHAR(50),
                            FOREIGN KEY (project_id) REFERENCES projects(project_id)
                            ON DELETE CASCADE ON UPDATE CASCADE
    );


drop table if exists user;
CREATE TABLE user (
                        userID INTEGER AUTO_INCREMENT PRIMARY KEY,
                        userName VARCHAR(100),
                        userPassword VARCHAR(100)
);

-- Overdue projects
INSERT INTO projects
(projectName, projectDescription, projectStartDate, projectBudget, dueDate, completionDate)
VALUES
    ('Website Redesign', 'Overdue website overhaul', '2022-01-01', 10000, '2023-04-01', NULL),
    ('Marketing Launch', 'Overdue launch of marketing campaign', '2022-02-01', 5000, '2023-04-10', NULL);

-- Imminent projects
INSERT INTO projects
(projectName, projectDescription, projectStartDate, projectBudget, dueDate, completionDate)
VALUES
    ('New Software', 'Launch of new software version', '2023-01-01', 15000, '2024-05-10', NULL),
    ('Office Move', 'Relocation of office premises', '2023-02-01', 20000, '2024-05-15', NULL);

-- Completed projects
INSERT INTO projects
(projectName, projectDescription, projectStartDate, projectBudget, dueDate, completionDate)
VALUES
    ('App Update', 'Update of mobile application', '2022-01-01', 8000, '2025-04-01', '2024-05-04'),
    ('HR System Overhaul', 'Complete revamp of HR system', '2022-02-01', 12000, '2025-04-20', '2024-05-07');

-- Insert data into the tasks table (example entries)
INSERT INTO tasks
(projectID, taskDescription, taskStartDate, taskEndDate, status)
VALUES
    (1, 'Design new layout', '2022-01-10', '2022-02-10', 'Completed'),
    (1, 'Implement responsive features', '2022-02-15', '2022-03-15', 'InProgress'),
    (2, 'Prepare marketing materials', '2022-02-05', '2022-03-05', 'Completed');

INSERT INTO projectlist (projectListName)
VALUES
    ('Per Olesen'),
    ('Rasmus Hansen');

INSERT INTO bruger (userName, userPassword)
VALUES
    ('Oskar', '1234'),
    ('Mikkel', '1234'),
    ('Jesper', '1234');
