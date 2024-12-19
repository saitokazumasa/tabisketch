CREATE TABLE IF NOT EXISTS days
(
    id                                SERIAL PRIMARY KEY,
    day                               INT        NOT NULL,
    plan_id                           INT        NOT NULL,
    walk_threshold                    INT,
    prefer_transportation_list_binary VARCHAR(4) NOT NULL,
    use_toll_road                     BOOLEAN    NOT NULL DEFAULT TRUE,
    use_ferry                         BOOLEAN    NOT NULL DEFAULT TRUE,
    FOREIGN KEY (plan_id) REFERENCES plans (id)
);
