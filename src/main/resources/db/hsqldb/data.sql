
INSERT INTO pacientes(id, first_name, last_name, dni, email, direccion, sexo, edad) VALUES (1, 'Rodrigo', 'García', '00000000A', 'cosas@gmail.com', 'micasa', 'MASCULINO', 20);
INSERT INTO pacientes(id, first_name, last_name, dni, email, direccion, sexo, edad) VALUES (2, 'Antonio', 'Fernández', '00000000B','cosas1@gmail.com', 'sucasa', 'FEMENINO', 20);

INSERT INTO citas(id, paciente_id, tipo, formato, especialidad, especialista) VALUES (1, 1, 'ASEGURADO', 'PRESENCIAL', 'TU MUERTOS' , 'especialista_1');
INSERT INTO citas(id, paciente_id, tipo, formato, especialidad, especialista) VALUES (2, 1, 'PRIVADO', 'ONLINE', 'TU MUERTOS11' , 'especialista_2');