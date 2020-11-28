INSERT INTO aseguradoras(id,nombre) VALUES (
    1,
    'Caser');


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

INSERT INTO pacientes(id,aseguradora_id,first_name,last_name,dni,email,direccion,sexo,edad) VALUES (
    1,
    1, 
    'Rodrigo', 
    'García', 
    '00000000A', 
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

 INSERT INTO citas(id,paciente_id,formato,tipo,especialidad,especialista,fecha) VALUES (
     1,
     1,
     'PRESENCIAL',
     'ASEGURADO',
     'MEDICINA_GENERAL',
     'manhattan',
     '2019-01-27 22:00:00'); 


