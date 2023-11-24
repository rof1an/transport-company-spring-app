ALTER TABLE Tickets
ADD route_list_id INT;

ALTER TABLE Tickets
ADD CONSTRAINT fk_routelist_obj
FOREIGN KEY (route_list_id) REFERENCES RoutesList(id);

