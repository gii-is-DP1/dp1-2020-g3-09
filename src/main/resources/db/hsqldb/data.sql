
INSERT INTO pacientes(id, first_name, last_name, dni, email, direccion, sexo, edad) VALUES (1, 'Rodrigo', 'García', '00000000A', 'cosas@gmail.com', 'micasa', 'MASCULINO', 20);
INSERT INTO pacientes(id, first_name, last_name, dni, email, direccion, sexo, edad) VALUES (2, 'Antonio', 'Fernández', '00000000B','cosas1@gmail.com', 'sucasa', 'FEMENINO', 20);



INSERT INTO citas(id,paciente_id,tipo,formato,especialidad,especialista,fecha) VALUES (
    1,
    1,
    'ASEGURADO',
    'PRESENCIAL',
    'TUS MUERTO',
    'especialista_1',
    '2018-01-27 22:00:00');

INSERT INTO citas(id,paciente_id,tipo,formato,especialidad,especialista,fecha) VALUES (
    2,
    1,
    'ASEGURADO',
    'PRESENCIAL',
    'TUS MUERTO 2',
    'especialista_1',
    '2019-01-27 22:00:00');

INSERT INTO citas(id,paciente_id,tipo,formato,especialidad,especialista,fecha) VALUES (
    3,
    1,
    'ASEGURADO',
    'PRESENCIAL',
    'TUS MUERTO 3',
    'especialista_1',
    '2020-01-27 22:00:00');