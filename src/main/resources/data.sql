CREATE SCHEMA IF NOT EXISTS ProjectManager;
       SET SCHEMA ProjectManager;
drop table if exists ProjectManager.tasks;

           DROP TABLE IF EXISTS ProjectManager.projects;
-- Create the projects table
CREATE TABLE ProjectManager.projects (
                                        project_id INT AUTO_INCREMENT PRIMARY KEY,
                                        project_name VARCHAR(255) NOT NULL,
    project_description TEXT,
    project_start_date DATE,
    project_budget DOUBLE,
    budget_spent DOUBLE
    due_date DATE,
    completion_date DATE
    );

-- Create the tasks table
CREATE TABLE IF NOT EXISTS ProjectManager.tasks (
                                     task_id INT AUTO_INCREMENT PRIMARY KEY,
                                     project_id INT NOT NULL,
                                     task_description TEXT,
                                     task_start_date DATE,
                                     task_end_date DATE,
                                     status VARCHAR(50),
    FOREIGN KEY (project_id) REFERENCES projects(project_id)
    ON DELETE CASCADE ON UPDATE CASCADE
    );

-- Insert data into the projects table
-- Overdue projects
INSERT INTO projects
(project_name, project_description, project_start_date, project_budget, due_date, completion_date)
VALUES
    ('Website Redesign', 'Overdue website overhaul', '2022-01-01', 10000, '2023-04-01', NULL),
    ('Marketing Launch', 'Overdue launch of marketing campaign', '2022-02-01', 5000, '2023-04-10', NULL);

-- Imminent projects
INSERT INTO projects
(project_name, project_description, project_start_date, project_budget, due_date, completion_date)
VALUES
    ('New Software', 'Launch of new software version', '2023-01-01', 15000, '2024-05-16', NULL),
    ('Office Move', 'Relocation of office premises', '2023-02-01', 20000,'2024-05-15', NULL);

-- Completed projects
INSERT INTO projects
(project_name, project_description, project_start_date, project_budget, due_date, completion_date)
VALUES
    ('App Update', 'Update of mobile application', '2022-01-01', 8000, '2025-04-01', '2024-05-04'),
    ('HR System Overhaul', 'Complete revamp of HR system', '2022-02-01', 12000, '2025-04-20', '2024-05-07');

-- Insert data into the tasks table (example entries)
INSERT INTO tasks
(project_id, task_description, task_start_date, task_end_date, status)
VALUES
    (1, 'Design new layout', '2022-01-10', '2022-02-10', 'Completed'),
    (1, 'Implement responsive features', '2022-02-15', '2022-03-15', 'InProgress'),
    (2, 'Prepare marketing materials', '2022-02-05', '2022-03-05', 'Completed');
