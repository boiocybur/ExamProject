CREATE SCHEMA if not exists alphaSolutions;
use alphasolutions;

ALTER TABLE project DROP FOREIGN KEY project_ibfk_1;

drop table if exists projectList;
CREATE TABLE projectList(
                            projectListID INT auto_increment,
                            projectListName VARCHAR(50) NOT NULL,
                            primary key(projectListID)
);

drop table if exists project;
CREATE TABLE if not exists project(
                                      projectID INT auto_increment,
                                      projectName VARCHAR(50) NOT NULL,
    projectDescription VARCHAR(100),
    projectListID INT NOT NULL,
    primary key(projectID),
    FOREIGN KEY(projectListID) REFERENCES projectList(projectListID)
    );

INSERT INTO projectlist (projectListName)
VALUES
    ('Per Olesen'),
    ('Rasmus Hansen');

INSERT INTO project (projectName, projectDescription, projectListID)
VALUES
    ('Maersk project B', 'Software system to monitor the traffic of an offshore fuel station for Maersk', 1),
    ('Imerco project A', 'Software system to monitor warehouse activity and to service supply and demand', 1),
    ('Test1', 'Test1', 1),
    ('Test2', 'Test2', 1),
    ('Test3', 'Test3', 1),
    ('Test4', 'Test4', 1),
    ('Test5', 'Test5', 1),
    ('Test1', 'Test1', 2),
    ('Test2', 'Test2', 2),
    ('Test3', 'Test3', 2),
    ('Test4', 'Test4', 2);