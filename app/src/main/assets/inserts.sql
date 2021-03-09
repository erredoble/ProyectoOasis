DELETE FROM FUENTE



/* INSERCIONES DE CIUDAD */
INSERT INTO ciudad VALUES(0, 'Logroño');

/* INSERCION DE AREAS */
INSERT INTO area VALUES(0, 'Parque del Ebro', 0);
INSERT INTO area VALUES(1, 'Plaza del Mercado', 0);
INSERT INTO area VALUES(2, 'Plaza del Espolon', 0);

/* INSERCION DE FUENTES */
/* Area del parque del ebro */
INSERT INTO fuente VALUES(0, 'Fuente cuarto puente', '42.4752, -2.4616', 0);
INSERT INTO fuente VALUES(1, 'Fuente de las escaleras', '42.4714, -2.4598', 0);
INSERT INTO fuente VALUES(2, 'Fuente cancha baloncesto', '42.4731, -2.4606', 0);
INSERT INTO fuente VALUES(3, 'Fuente del pino piñonero', '42.4698, -2.4572', 0);
INSERT INTO fuente VALUES(4, 'Fuente nueva de la antigua seccion femenina', '42.4696, -2.4551', 0);
INSERT INTO fuente VALUES(5, 'Fuente vieja de la antigua seccion femenina', '42.4692, -2.4548', 0);
INSERT INTO fuente VALUES(6, 'Fuente junto al antiguo muro del cuartel', '42.4685, -2.4523', 0);
INSERT INTO fuente VALUES(7, 'Fuente del frontón del Revellin', '42.4685, -2.4498', 0);
INSERT INTO fuente VALUES(8, 'Fuente del Puente de Hierro', '42.4688, -2.4474', 0);

/* Area de la plaza del mercado */
INSERT INTO fuente VALUES(9, 'Fuente de La Plaza del Mercado', '42.4670, -2.4454', 1);

-- Area del Espolon
INSERT INTO fuente VALUES(10, 'Fuente de Las Ranitas', '42.4647, -2.4463', 2);
INSERT INTO fuente VALUES(11, 'Fuente de La Plaza del Mercado', '42.4650, -2.4453', 2);