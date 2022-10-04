CREATE TABLE IF NOT EXISTS drones
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    serial_number VARCHAR(255) UNIQUE,
    max_weight    INT          NOT NULL,
    battery       INT          NOT NULL,
    model         VARCHAR(255) NOT NULL,
    state         VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS medicines
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    code   VARCHAR(255) UNIQUE,
    name   VARCHAR(255) NOT NULL,
    weight DOUBLE       NOT NULL,
    image  VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS trips_history
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    drone_id     INT NOT NULL,
    medicines_id INT NOT NULL,
    trip_state   VARCHAR(255) NOT NULL,
    destination_location VARCHAR(255)  NOT NULL,
    start_point_location VARCHAR(255)  NOT NULL,
    start_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
ALTER TABLE trips_history
    ADD CONSTRAINT FK_DRONE_ID FOREIGN KEY (drone_id) REFERENCES drones (id);

ALTER TABLE trips_history
    ADD CONSTRAINT FK_MEDICINES_ID FOREIGN KEY (medicines_id) REFERENCES medicines (id);
