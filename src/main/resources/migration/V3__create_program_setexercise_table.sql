CREATE TABLE set_reservation (
    id SERIAL PRIMARY KEY,
    table_id INT NOT NULL,
    user_id INT NOT NULL,
    reservation_time INT NOT NULL
);


