INSERT INTO aseguradoras(id,nombre) VALUES (
    1,
    'Caser');
    INSERT INTO aseguradoras(id,nombre) VALUES (
    2,
    'NoCaser');


INSERT INTO polizas(id, aseguradora_id, name, precio, cobertura, duracion) VALUES (
    1,
    1,
    'Cobertura total 1',
    1000.50,
    'TOTAL',
    '2022-01-01');


INSERT INTO polizas(id, aseguradora_id, name, precio, cobertura, duracion) VALUES (
    2,
    1,
    'Cobertura parcial 1',
     500.25,
    'PARCIAL',
    '2022-02-02');

INSERT INTO historial (id) VALUES (
    1
);

INSERT INTO historial (id) VALUES (
    2
);

INSERT INTO historial (id) VALUES (
    3
);

INSERT INTO pacientes(id,aseguradora_id, poliza_id,  historial_id, first_name,last_name,dni,email,direccion,sexo,edad) VALUES (
    1,
    1, 
    1,
    1,
    'Rodrigo', 
    'García', 
    '00000000A', 
    'cosas@gmail.com', 
    'micasa', 
    'MASCULINO', 
    20);

INSERT INTO pacientes(id,aseguradora_id, poliza_id,  historial_id, first_name,last_name,dni,email,direccion,sexo,edad) VALUES (
    2,
    1, 
    1,
    2,
    'Rodriga', 
    'Garcío', 
    '00000000B', 
    'cosas@gmail.com', 
    'micasa', 
    'MASCULINO', 
    20);

INSERT INTO pacientes(id, aseguradora_id, poliza_id, historial_id, first_name, last_name, dni, email, direccion, sexo, edad) VALUES (
    3,
    1, 
    null,
    3,
    'Rodrige', 
    'Garcíe', 
    '00000000C', 
    'cosas@gmail.com', 
    'micasa', 
    'MASCULINO', 
    20);

INSERT INTO especialistas(id,first_name,last_name,dni,direccion,telefono,correo,especialidad) VALUES (
    1, 
    'Ceballo', 
    'Enriqueto', 
    '00000000E',
    'casoplon',
    '123456789',
    'especialistaaso@gmail.com',
    'MEDICINA_GENERAL');

INSERT INTO especialistas(id,first_name,last_name,dni,direccion,telefono,correo,especialidad) VALUES (
    2, 
    'Ceballa', 
    'Enriqueta', 
    '00000000F',
    'casoplon',
    '123456789',
    'especialistaaso@gmail.com',
    'MEDICINA_INTERNA');

 INSERT INTO citas(id, paciente_id, especialista_id, historial_id, formato, tipo, especialidad, fecha) VALUES (
     1,
     3,
     1,
     3,
     'PRESENCIAL',
     'ASEGURADO',
     'MEDICINA_GENERAL',
     '2019-01-27 22:00:00'); 

INSERT INTO citas(id, paciente_id,  especialista_id, historial_id, formato, tipo, especialidad, fecha) VALUES (
     2,
     3,
     2,
     3,
     'ONLINE',
     'ASEGURADO',
     'MEDICINA_GENERAL',
     '2019-01-27 22:00:00'); 

INSERT INTO aseguradoras_especialistas (aseguradora_id, especialista_id) VALUES (
    1,
    2);

INSERT INTO aseguradoras_especialistas (aseguradora_id, especialista_id) VALUES (
    1,
    1);

INSERT INTO aseguradoras_especialistas (aseguradora_id, especialista_id) VALUES (
    2,
    2);





