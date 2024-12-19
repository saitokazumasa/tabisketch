CREATE TABLE IF NOT EXISTS plans
(
    id               SERIAL PRIMARY KEY,
    uuid             UUID UNIQUE NOT NULL DEFAULT GEN_RANDOM_UUID(),
    title            VARCHAR(64) NOT NULL,
    user_id          INT         NOT NULL REFERENCES users (id),
    editable         BOOLEAN     NOT NULL DEFAULT TRUE,
    publicAccessible BOOLEAN     NOT NULL DEFAULT FALSE
);
