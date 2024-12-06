CREATE TABLE IF NOT EXISTS mail_authentication_tokens
(
    id         SERIAL PRIMARY KEY,
    token      UUID UNIQUE NOT NULL DEFAULT GEN_RANDOM_UUID(),
    new_mail   VARCHAR(254) UNIQUE,
    user_id    INT         NOT NULL REFERENCES users (id),
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);
