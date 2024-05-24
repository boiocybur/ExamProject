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
(projectID, taskName, taskDescription, taskStartDate, taskDueDate, userID, taskCompletionStatus)
VALUES
    -- Project 1
    (1, 'Website Performance Optimization', 'Optimize website performance for faster loading times and better user experience', '2024-05-21', '2024-05-25', 2, FALSE),
    (1, 'Security Audit', 'Conduct a security audit to identify and address vulnerabilities', '2024-07-15', '2024-07-30', 3, FALSE),
    (1, 'Content Localization', 'Localize website content for international audiences', '2024-07-20', '2024-08-04', 1, FALSE),
    (1, 'Customer Feedback Integration', 'Integrate customer feedback system for real-time feedback collection', '2024-07-25', '2024-08-09', 2, FALSE),
    (1, 'Marketing Campaign Analysis', 'Analyze the effectiveness of past marketing campaigns for insights', '2024-07-30', '2024-08-14', 3, FALSE),
    (1, 'User Interface Refinement', 'Refine user interface elements for improved usability', '2024-08-05', '2024-08-20', 1, FALSE),

    -- Project 2
    (2, 'Market Segmentation Analysis', 'Analyze market segments to identify target audiences', '2024-07-12', '2024-07-27', 3, FALSE),
    (2, 'Content Calendar Creation', 'Create a content calendar for organized content planning', '2024-07-17', '2024-08-01', 1, FALSE),
    (2, 'Email Automation Setup', 'Set up automated email campaigns for efficient marketing', '2024-07-22', '2024-08-06', 2, FALSE),
    (2, 'SEO Strategy Review', 'Review and refine SEO strategy for improved search rankings', '2024-07-27', '2024-08-11', 3, FALSE),
    (2, 'Social Media Engagement Analysis', 'Analyze social media engagement metrics for optimization', '2024-08-01', '2024-08-16', 1, FALSE),
    (2, 'Content Optimization Workshop', 'Organize a workshop to optimize marketing content', '2024-08-06', '2024-08-21', 2, FALSE),

    -- Project 3
    (3, 'Product Feature Prioritization', 'Prioritize product features based on market demand', '2024-07-14', '2024-07-29', 1, FALSE),
    (3, 'Competitor Analysis', 'Conduct a comprehensive analysis of competitors in the market', '2024-07-19', '2024-08-03', 2, FALSE),
    (3, 'Usability Testing', 'Conduct usability testing sessions to gather user feedback', '2024-07-24', '2024-08-08', 3, FALSE),
    (3, 'Product Demo Preparation', 'Prepare product demos for showcasing key features', '2024-07-29', '2024-08-13', 1, FALSE),
    (3, 'Sales Strategy Development', 'Develop a strategic sales plan to maximize revenue', '2024-08-03', '2024-08-18', 2, FALSE),
    (3, 'Customer Support Training', 'Provide training sessions for customer support representatives', '2024-08-08', '2024-08-23', 3, FALSE),

    -- Project 4
    (4, 'Inventory Management System Update', 'Update inventory management system for efficiency improvements', '2024-07-16', '2024-07-31', 2, FALSE),
    (4, 'Supplier Negotiations', 'Negotiate with suppliers for better terms and pricing', '2024-07-21', '2024-08-05', 3, FALSE),
    (4, 'Warehouse Organization', 'Organize warehouse layout for better inventory management', '2024-07-26', '2024-08-10', 1, FALSE),
    (4, 'Shipping Process Optimization', 'Optimize shipping processes for faster order fulfillment', '2024-07-31', '2024-08-15', 2, FALSE),
    (4, 'Customer Satisfaction Survey', 'Conduct a customer satisfaction survey for feedback collection', '2024-08-05', '2024-08-20', 3, FALSE),
    (4, 'Supplier Performance Evaluation', 'Evaluate supplier performance to ensure quality standards', '2024-08-10', '2024-08-25', 1, FALSE),

    -- Project 5
    (5, 'Financial Analysis Report', 'Prepare a detailed financial analysis report for stakeholders', '2024-07-18', '2024-08-02', 3, FALSE),
    (5, 'Budget Planning Meeting', 'Hold a meeting to plan budgets for upcoming projects', '2024-07-23', '2024-08-07', 1, FALSE),
    (5, 'Investment Portfolio Review', 'Review and optimize investment portfolios for maximum returns', '2024-07-28', '2024-08-12', 2, FALSE),
    (5, 'Tax Compliance Audit', 'Conduct an audit to ensure compliance with tax regulations', '2024-08-02', '2024-08-17', 3, FALSE),
    (5, 'Expense Tracking System Implementation', 'Implement a new expense tracking system for better financial management', '2024-08-12', '2024-08-27', 1, FALSE),
    (5, 'Financial Risk Assessment', 'Assess financial risks and develop mitigation strategies', '2024-08-17', '2024-09-01', 2, FALSE),
    (5, 'Profitability Analysis', 'Analyze profitability of different business units and products', '2024-08-22', '2024-09-06', 3, FALSE),

    -- Project 6
    (6, 'Customer Relationship Management (CRM) Integration', 'Integrate CRM system for improved customer interactions', '2024-08-14', '2024-08-29', 3, FALSE),
    (6, 'Supply Chain Optimization', 'Optimize supply chain processes for cost efficiency', '2024-08-19', '2024-09-03', 1, FALSE),
    (6, 'Quality Control System Upgrade', 'Upgrade quality control system for enhanced product quality', '2024-08-24', '2024-09-08', 2, FALSE),
    (6, 'Inventory Forecasting Model Development', 'Develop a predictive model for inventory forecasting', '2024-08-29', '2024-09-13', 3, FALSE),
    (6, 'Market Expansion Strategy Planning', 'Plan strategies for expanding into new markets', '2024-09-03', '2024-09-18', 1, FALSE),
    (6, 'Logistics Optimization', 'Optimize logistics operations for faster and cheaper deliveries', '2024-09-08', '2024-09-23', 2, FALSE),
    (6, 'Employee Training Program Development', 'Develop training programs to enhance employee skills', '2024-09-13', '2024-09-28', 3, FALSE),
    (6, 'Performance Evaluation System Implementation', 'Implement a system for employee performance evaluations', '2024-09-18', '2024-10-03', 1, FALSE);

(projectID, taskName, taskDescription, taskStartDate, taskDueDate, estimatedHours, actualHours, userID)
VALUES
    (1, 'layout task', 'Design new layout', '2022-01-10', '2022-02-10', 50, 60, 1),
    (1,'implement features' ,'Implement responsive features', '2022-02-15', '2022-03-15', 100, 120, 1),
    (2, 'market materials','Prepare marketing materials', '2022-02-05', '2022-03-05', 40, 30, 2),
    (1, 'refactoring', 'refactor backend code', '2022-01-10', '2022-03-11', 80, 90, 1);


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

