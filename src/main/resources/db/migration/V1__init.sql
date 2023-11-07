-- noinspection SqlNoDataSourceInspectionForFile

-- noinspection SqlDialectInspectionForFile

CREATE TABLE Transports
(
    id           SERIAL PRIMARY KEY,
    state_number VARCHAR(28) NOT NULL,
    brand        VARCHAR(20) NOT NULL,
    capacity     INT         NOT NULL
);

CREATE TABLE Drivers
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(18) NOT NULL,
    last_name  VARCHAR(18) NOT NULL
);

CREATE TABLE Routes
(
    id                 SERIAL PRIMARY KEY,
    departure_point    VARCHAR(100) NOT NULL,
    destination_point  VARCHAR(100) NOT NULL,
    travel_time        INT          NOT NULL,
    frequency          VARCHAR(18)  NOT NULL,
    adult_ticket_price INT          NOT NULL,
    child_ticket_price INT          NOT NULL
);

CREATE TABLE Tickets
(
    id              SERIAL PRIMARY KEY,
    route_id        INT,
    ticket_number   INT         NOT NULL,
    purchase_date   DATE        NOT NULL,
    ticket_type     VARCHAR(20) NOT NULL,
    ticket_price    INT         NOT NULL,
    buyer_firstname VARCHAR(20) NOT NULL,
    buyer_lastname  VARCHAR(20) NOT NULL,
    FOREIGN KEY (route_id) REFERENCES Routes (id)
);

CREATE TABLE RoutesList
(
    id                SERIAL PRIMARY KEY,
    departure_date    DATE        NOT NULL,
    destination_date  DATE        NOT NULL,
    purchased_tickets INT         NOT NULL,
    remaining_tickets INT         NOT NULL,
    transport_id      INT,
    driver_id         INT,
    travel_time       INT         NOT NULL,
    routes_per_day    INT         NOT NULL,
    date              DATE        NOT NULL,
    destination_point VARCHAR(30) NOT NULL,
    FOREIGN KEY (transport_id) REFERENCES Transports (id),
    FOREIGN KEY (driver_id) REFERENCES Drivers (id)
);