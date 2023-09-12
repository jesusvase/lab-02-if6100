CREATE TABLE rooms (
                       id UUID PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       identifier VARCHAR(255) UNIQUE NOT NULL
);


