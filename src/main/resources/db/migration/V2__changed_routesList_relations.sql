-- noinspection SqlNoDataSourceInspectionForFile

-- noinspection SqlDialectInspectionForFile

ALTER TABLE RoutesList
DROP COLUMN destination_point;

ALTER TABLE RoutesList
ADD route_id INT;

ALTER TABLE RoutesList
ADD CONSTRAINT fk_routesList_route
FOREIGN KEY (route_id) REFERENCES Routes(id);
