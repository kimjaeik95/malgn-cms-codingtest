CREATE TABLE contents (
    id                  BIGINT PRIMARY      KEY AUTO_INCREMENT,
    title               VARCHAR(100)        NOT NULL,
    description         TEXT,
    view_count          BIGINT              NOT NULL    DEFAULT 0,
    created_date        TIMESTAMP,
    created_by          VARCHAR(50)         NOT NULL,
    last_modified_date  TIMESTAMP,
    last_modified_by    VARCHAR(50)
)