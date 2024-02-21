CREATE TABLE reservation (
    id SERIAL PRIMARY KEY,
    tableNumber INT NOT NULL,
    tableCapacity INT NOT NULL,
    user_id INT NOT NULL,
    reservationTime TIMESTAMP NOT NULL
);
