CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       alias VARCHAR(255) UNIQUE NOT NULL,
                       room_id UUID NOT NULL,
                       FOREIGN KEY (room_id) REFERENCES rooms(id)
);

