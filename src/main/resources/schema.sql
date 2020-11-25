CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE base_station (
	id uuid DEFAULT uuid_generate_v4 (),
	name VARCHAR(50) UNIQUE NOT NULL,
	x FLOAT NOT NULL,
	y FLOAT NOT NULL,
	detection_radius_in_meters FLOAT NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE mobile_station (
	id uuid DEFAULT uuid_generate_v4 (),
	last_known_x FLOAT NOT NULL,
	last_known_y FLOAT NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE report (
	id uuid DEFAULT uuid_generate_v4 (),
	base_station_id uuid NOT NULL,
	mobile_station_id uuid NOT NULL,
	distance FLOAT NOT NULL,
	timestamp TIMESTAMP NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (base_station_id) REFERENCES base_station(id) ON DELETE CASCADE
	FOREIGN KEY (mobile_station_id) REFERENCES mobile_station(id) ON DELETE CASCADE
);
