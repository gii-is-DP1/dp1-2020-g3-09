INSERT INTO pacientes(id,first_name,last_name,dni,email,direccion,sexo,edad,aseguradora) VALUES (
    1, 
    'Rodrigo', 
    'García', 
    '00000000A', 
    'cosas@gmail.com', 
    'micasa', 
    'MASCULINO', 
    20,
    'caser');
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

