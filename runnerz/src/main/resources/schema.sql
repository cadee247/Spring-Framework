DROP TABLE IF EXISTS run;

CREATE TABLE run (
                     id INT PRIMARY KEY,
                     title VARCHAR(255),
                     started_on TIMESTAMP,
                     completed_on TIMESTAMP,
                     miles INT,
                     location VARCHAR(50)
);