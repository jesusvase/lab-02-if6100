CREATE TABLE messages (
                          id UUID PRIMARY KEY,
                          message TEXT NOT NULL,
                          fecha DATE NOT NULL,
                          user_id UUID NOT NULL,
                          FOREIGN KEY (user_id) REFERENCES users(id)
);