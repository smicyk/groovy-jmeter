CREATE DATABASE hr;

\connect hr;

CREATE SCHEMA hr;

CREATE TABLE hr.employee (
    id VARCHAR(50) PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name VARCHAR(50)  NOT NULL,
    email VARCHAR(50)  NOT NULL,
    salary BIGINT  NOT NULL,
    active BOOLEAN NOT NULL
);