CREATE DATABASE hr;

\connect hr;

CREATE SCHEMA hr;

CREATE TABLE hr.employee (
    id VARCHAR(50) PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(50),
    salary VARCHAR(5)
);