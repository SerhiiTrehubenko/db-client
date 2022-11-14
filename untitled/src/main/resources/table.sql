USE db_jpwh;
CREATE TABLE IF NOT EXISTS user (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(100),
    user_salary DOUBLE,
    user_specialization VARCHAR(100)
)