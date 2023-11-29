ALTER TABLE Drivers
ADD COLUMN patronymic VARCHAR(20);

ALTER TABLE Tickets
ADD COLUMN buyer_patronymic VARCHAR(20);