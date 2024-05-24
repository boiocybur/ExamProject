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
                        taskCompletionStatus BOOLEAN DEFAULT FALSE,
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
(projectID, taskName, taskDescription, taskStartDate, taskDueDate, estimatedHours, actualHours, userID, taskCompletionStatus)
VALUES
    -- Project 1
    (1, 'layout task', 'Design new layout', '2022-01-10', '2022-02-10', 50, 60, 1, TRUE),
    (1, 'implement features', 'Implement responsive features', '2022-02-15', '2022-03-15', 100, 120, 1, FALSE),
    (1, 'refactoring', 'Refactor backend code', '2022-01-10', '2022-03-11', 80, 90, 1, FALSE),
    (1, 'UI design', 'Create UI components', '2022-03-01', '2022-04-01', 60, 65, 1, TRUE),
    (1, 'API integration', 'Integrate third-party APIs', '2022-04-05', '2022-05-05', 70, 75, 1, FALSE),
    (1, 'testing', 'Perform unit testing', '2022-05-10', '2022-06-10', 50, 55, 1, TRUE),

    -- Project 2
    (2, 'market materials', 'Prepare marketing materials', '2022-02-05', '2022-03-05', 40, 30, 1, TRUE),
    (2, 'campaign planning', 'Plan marketing campaign', '2022-03-10', '2022-04-10', 50, 45, 1, FALSE),
    (2, 'content creation', 'Create social media content', '2022-04-15', '2022-05-15', 60, 70, 1, TRUE),
    (2, 'SEO optimization', 'Optimize website for SEO', '2022-05-20', '2022-06-20', 30, 35, 1, FALSE),
    (2, 'email marketing', 'Develop email marketing strategy', '2022-06-25', '2022-07-25', 40, 50, 1, TRUE),
    (2, 'analytics review', 'Review campaign analytics', '2022-08-01', '2022-09-01', 20, 25, 1, FALSE),

    -- Project 3
    (3, 'database design', 'Design database schema', '2022-03-01', '2022-04-01', 50, 60, 1, TRUE),
    (3, 'data migration', 'Migrate data to new system', '2022-04-05', '2022-05-05', 80, 85, 1, FALSE),
    (3, 'backup setup', 'Setup backup system', '2022-05-10', '2022-06-10', 30, 35, 1, TRUE),
    (3, 'security audit', 'Perform security audit', '2022-06-15', '2022-07-15', 40, 45, 1, FALSE),
    (3, 'performance tuning', 'Tune database performance', '2022-07-20', '2022-08-20', 50, 55, 1, TRUE),
    (3, 'index optimization', 'Optimize database indexes', '2022-09-01', '2022-10-01', 30, 35, 1, FALSE),

    -- Project 4
    (4, 'initial research', 'Conduct initial research', '2022-01-15', '2022-02-15', 40, 45, 2, TRUE),
    (4, 'prototype development', 'Develop prototype', '2022-02-20', '2022-03-20', 70, 75, 2, FALSE),
    (4, 'user testing', 'Conduct user testing', '2022-03-25', '2022-04-25', 60, 65, 2, TRUE),
    (4, 'feedback analysis', 'Analyze user feedback', '2022-05-01', '2022-06-01', 50, 55, 2, FALSE),
    (4, 'final adjustments', 'Make final adjustments', '2022-06-05', '2022-07-05', 40, 45, 2, TRUE),
    (4, 'documentation', 'Prepare project documentation', '2022-07-10', '2022-08-10', 30, 35, 2, FALSE),

    -- Project 5
    (5, 'market research', 'Conduct market research', '2022-02-01', '2022-03-01', 50, 55, 3, TRUE),
    (5, 'strategy development', 'Develop marketing strategy', '2022-03-05', '2022-04-05', 60, 65, 3, FALSE),
    (5, 'branding', 'Create branding materials', '2022-04-10', '2022-05-10', 70, 75, 3, TRUE),
    (5, 'advertising', 'Plan advertising campaign', '2022-05-15', '2022-06-15', 80, 85, 3, FALSE),
    (5, 'partnerships', 'Establish partnerships', '2022-06-20', '2022-07-20', 50, 55, 3, TRUE),
    (5, 'evaluation', 'Evaluate campaign effectiveness', '2022-08-01', '2022-09-01', 40, 45, 3, FALSE),

    -- Project 6
    (6, 'conceptual design', 'Create conceptual design', '2022-03-01', '2022-04-01', 60, 65, 3, TRUE),
    (6, 'technical specs', 'Write technical specifications', '2022-04-05', '2022-05-05', 70, 75, 3, FALSE),
    (6, 'development', 'Develop software', '2022-05-10', '2022-06-10', 90, 95, 3, TRUE),
    (6, 'testing phase', 'Execute testing phase', '2022-06-15', '2022-07-15', 80, 85, 3, FALSE),
    (6, 'deployment', 'Deploy to production', '2022-07-20', '2022-08-20', 60, 65, 3, TRUE),
    (6, 'maintenance', 'Perform maintenance', '2022-09-01', '2022-10-01', 50, 55, 3, FALSE);


INSERT INTO taskAcceptCriteria
(taskID, taskAcceptCriteriaTEXT, taskStatus)
VALUES
    -- For taskID 1
    (1, 'Integration tests', TRUE),
    (1, 'Code review completed', FALSE),
    (1, 'Documentation updated', FALSE),

    -- For taskID 2
    (2, 'Functional tests', TRUE),
    (2, 'User acceptance testing (UAT)', TRUE),
    (2, 'Performance testing', FALSE),

    -- For taskID 3
    (3, 'Data validation tests', FALSE),
    (3, 'Security testing', TRUE),
    (3, 'Accessibility testing', FALSE),

    -- For taskID 4
    (4, 'Load testing', FALSE),
    (4, 'Cross-browser compatibility testing', FALSE),
    (4, 'Localization testing', TRUE),

    -- For taskID 5
    (5, 'Regression testing', FALSE),
    (5, 'Stress testing', FALSE),
    (5, 'End-to-end testing', FALSE),

    -- For taskID 6
    (6, 'Usability testing', TRUE),
    (6, 'Scalability testing', FALSE),
    (6, 'Configuration testing', TRUE),

    -- For taskID 7
    (7, 'API testing', TRUE),
    (7, 'Error handling testing', FALSE),
    (7, 'Compatibility testing', FALSE),

    -- For taskID 8
    (8, 'Performance tuning', FALSE),
    (8, 'Backup and recovery testing', TRUE),
    (8, 'Interoperability testing', FALSE),

    -- For taskID 9
    (9, 'GUI testing', FALSE),
    (9, 'Concurrency testing', FALSE),
    (9, 'Version compatibility testing', TRUE),

    -- For taskID 10
    (10, 'Acceptance criteria met', TRUE),
    (10, 'Regression testing passed', FALSE),
    (10, 'Documentation verified', FALSE);

