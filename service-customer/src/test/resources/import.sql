INSERT INTO `identification_type` VALUES (1,'CC','Cédula de ciudadanía'),(2,'TI','Tarjeta de identifidad'),(3,'TP','Tarjeta pasaporte'),(4,'RC','Registro civil'),(5,'CE','Cédula de extranjería');

INSERT INTO `city` VALUES (1,'Bogotá'),(2,'Medellín'),(3,'Cali'),(4,'Barranquilla'),(5,'Cartagena'),(6,'Cúcuta'),(7,'Soledad'),(8,'Bucaramanga');

INSERT INTO `customer` (`city_of_birth_id`, `identification_type_id`, `identification`, `name`, `last_name`, `age`, `create_at`, `update_at`, `photo_id`) VALUES ('1', '2', '123456781', 'Yessika', 'Lopez', '15', '2021-07-01', '2021-07-01', '123456');
INSERT INTO `customer` (`city_of_birth_id`, `identification_type_id`, `identification`, `name`, `last_name`, `age`, `create_at`, `update_at`, `photo_id`) VALUES ('1', '2', '123456782', 'Juan', 'Mantilla', '16', '2021-07-01', '2021-07-01', '123457');
INSERT INTO `customer` (`city_of_birth_id`, `identification_type_id`, `identification`, `name`, `last_name`, `age`, `create_at`, `update_at`, `photo_id`) VALUES ('1', '2', '123456783', 'Andrea', 'Perez', '32', '2021-07-01', '2021-07-01',null);
INSERT INTO `customer` (`city_of_birth_id`, `identification_type_id`, `identification`, `name`, `last_name`, `age`, `create_at`, `update_at`, `photo_id`) VALUES ('1', '2', '123456784', 'Diana', 'Martinez', '12', '2021-07-01', '2021-07-01', '123458');
INSERT INTO `customer` (`city_of_birth_id`, `identification_type_id`, `identification`, `name`, `last_name`, `age`, `create_at`, `update_at`, `photo_id`) VALUES ('1', '2', '123456785', 'Carolina', 'Herrera', '50', '2021-07-01', '2021-07-01',null);

