CREATE TABLE drones
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    serial_number VARCHAR(255) UNIQUE,
    max_weight    INT       NOT NULL,
    battery       INT          NOT NULL,
    model         VARCHAR(255) NOT NULL,
    state         VARCHAR(255) NOT NULL
);

CREATE TABLE medicines
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    code   VARCHAR(255) UNIQUE,
    name   VARCHAR(255) NOT NULL,
    weight DOUBLE       NOT NULL,
    image  VARCHAR(255) NOT NULL
);

CREATE TABLE trips_history
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    drone_id     INT          NOT NULL,
    medicines_id INT          NOT NULL,
    trip_state   VARCHAR(255) NOT NULL
);