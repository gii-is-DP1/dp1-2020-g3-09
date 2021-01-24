
INSERT INTO users(username,password,enabled) VALUES ('admin1','1234',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');

INSERT INTO users(username,password,enabled) VALUES ('especialista1','1234',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'especialista1','especialista');

INSERT INTO users(username,password,enabled) VALUES ('paciente1','1234',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'paciente1','paciente');

INSERT INTO users(username,password,enabled) VALUES ('aseguradora1','1234',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'aseguradora1','aseguradora');

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

INSERT INTO pacientes(id,aseguradora_id, poliza_id, first_name,last_name,dni,email,direccion,sexo,edad) VALUES (
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

INSERT INTO pacientes(id,aseguradora_id, poliza_id, first_name,last_name,dni,email,direccion,sexo,edad) VALUES (
    2,
    1, 
    1,
    'Rodriga', 
    'Garcío', 
    '00000000B', 
    'cosas@gmail.com', 
    'micasa', 
    'MASCULINO', 
    20);

INSERT INTO pacientes(id, aseguradora_id, poliza_id, first_name, last_name, dni, email, direccion, sexo, edad) VALUES (
    3,
    1, 
    null,
    'Rodrige', 
    'Garcíe', 
    '00000000C', 
    'cosas@gmail.com', 
    'micasa', 
    'MASCULINO', 
    20);

INSERT INTO especialistas(id, first_name,last_name,dni,direccion,telefono,correo,especialidad) VALUES (
    1, 
    'Ceballo', 
    'Enriqueto', 
    '00000000E',
    'casoplon',
    '123456789',
    'especialistaaso@gmail.com',
    'MEDICINA_GENERAL');

INSERT INTO especialistas(id, first_name,last_name,dni,direccion,telefono,correo,especialidad) VALUES (
    2, 
    'Ceballa', 
    'Enriqueta', 
    '00000000F',
    'casoplon',
    '123456789',
    'especialistaaso@gmail.com',
    'MEDICINA_INTERNA');

 INSERT INTO citas(id, paciente_id, especialista_id, formato, tipo, especialidad, fecha) VALUES (
     1,
     3,
     1,
     'PRESENCIAL',
     'ASEGURADO',
     'MEDICINA_GENERAL',
     '2021-01-27 22:00:00'); 

INSERT INTO citas(id, paciente_id,  especialista_id, formato, tipo, especialidad, fecha) VALUES (
     2,
     3,
     2,
     'ONLINE',
     'ASEGURADO',
     'MEDICINA_GENERAL',
     '2019-01-27 22:00:00');
     


INSERT INTO actas(id, especialista_id, descripcion, exploracion, diagnostico, cita_id) VALUES(
    1,
    1,
    'esufsiufensoif',
    'esufsiufensoif',
    'esufsiufensoif',
    1
);

INSERT INTO actas(id, especialista_id, descripcion, exploracion, diagnostico, cita_id) VALUES(
    2,
    2,
    'esufsiufensoif',
    'esufsiufensoif',
    'esufsiufensoif',
    2
);

INSERT INTO aseguradoras_especialistas (aseguradora_id, especialista_id) VALUES (
    1,
    2);

INSERT INTO aseguradoras_especialistas (aseguradora_id, especialista_id) VALUES (
    1,
    1);

INSERT INTO aseguradoras_especialistas (aseguradora_id, especialista_id) VALUES (
    2,
    2);

INSERT INTO tratamientos (id, poliza_id, descripcion, duracion, acta_id) VALUES (
    1,
    2,
    'esufsiufensoif',
    3,
    1
);     




INSERT INTO justificantes(id, motivo, cita_id) VALUES (
    1,
    'Trabajo',
    1);


INSERT INTO alarmas(id, dias, cita_id) VALUES(
    1,
    13,
    1);

INSERT INTO alarmas(id, dias, cita_id) VALUES(
    2,
    28,
    2);

INSERT INTO calculadora(id,peso,altura,imc,resultado,paciente_id) VALUES(
    1,
    72.3,
    1.80,
    20.0004,
    'Peso normal',
    1);