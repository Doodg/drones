CREATE TABLE drones
(
    id             INT  PRIMARY KEY,
    serial_number   VARCHAR(255) UNIQUE,
    max_weight DOUBLE       NOT NULL,
    battery        INT          NOT NULL,
    model          VARCHAR(255) NOT NULL,
    state          VARCHAR(255) NOT NULL
);

INSERT INTO drones(id, serial_number, max_weight, battery, model, state)
VALUES (1, "a23z2-4vds-2c", 500.0, 100, "HEAVYWEIGHT", "IDLE");


CREATE TABLE medicines
(
    id     INT PRIMARY KEY,
    code   VARCHAR(255) UNIQUE,
    name   VARCHAR(255) NOT NULL,
    weight DOUBLE       NOT NULL,
    image  VARCHAR(255) NOT NULL
);

CREATE TABLE trips_history
(
    id           INT PRIMARY KEY,
    drone_id     INT          NOT NULL,
    medicines_id INT          NOT NULL,
    trip_state   VARCHAR(255) NOT NULL
);
