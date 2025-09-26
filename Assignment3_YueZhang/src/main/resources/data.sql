INSERT INTO sec_user (userName, encryptedPassword, enabled)
VALUES 
('Jack', '{bcrypt}$2y$10$NJcv6KfAnt8XxOMOSWdToeZNcvGnjtTZLHH7qXYlLBaV6pxMKiSHW', 1),
('Rose', '{bcrypt}$2a$12$1e6TDCdhl.N2NP.nivFSt.KFRX18xeZJUMq4mhDuR8eAdjT4uVwMC', 1);
INSERT INTO sec_role (roleName)
VALUES 
('ROLE_USER'),
('ROLE_ADMIN');

INSERT INTO user_role (userId, roleId)
VALUES 
(1, 2),
(2, 1);


INSERT INTO COURSES (COURSE_TITLE, CATEGORY, INSTRUCTOR, PRICE, DURATION, PLATFORM, DESCRIPTION) VALUES
('Java Fundamentals', 'Technology', 'Alice Johnson', 89.99, 14.0, 'Udemy', 'Introductory Java programming course.'),
('Spring Boot in Action', 'Technology', 'Bob Smith', 119.00, 18.5, 'Coursera', 'Build enterprise apps with Spring Boot.'),
('Business Strategy Essentials', 'Business', 'Clara Zhang', 95.50, 10.0, 'LinkedIn Learning', 'Learn core business strategy concepts.'),
('Project Management Basics', 'Business', 'Daniel Lee', 79.00, 8.0, 'edX', 'Fundamentals of managing projects.'),
('Beginner Yoga Flow', 'Health & Fitness', 'Eva Patel', 49.99, 6.0, 'YouTube', 'Gentle yoga routine for beginners.'),
('Nutrition 101', 'Health & Fitness', 'Frank Green', 65.00, 9.5, 'Udemy', 'Understand the basics of healthy eating.');
