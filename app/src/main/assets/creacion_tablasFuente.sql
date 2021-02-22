
CREATE TABLE IF NOT EXISTS 'Ciudad' (
id_ciudad INTEGER PRIMARY KEY NOT NULL,
nombre_ciudad TEXT NOT NULL
);


CREATE TABLE IF NOT EXISTS 'Area' (
id_area INTEGER PRIMARY KEY NOT NULL,
nombre_area TEXT NOT NULL,
ciudad_id INTEGER NOT NULL,
CONSTRAINT FK_CIUDAD FOREIGN KEY(ciudad_id) REFERENCES 'Ciudad'(id_ciudad)
);

CREATE TABLE IF NOT EXISTS 'Localizacion'(
id_loc INTEGER PRIMARY KEY NOT NULL,
descrip_loc TEXT,
coordenadas TEXT,
num_mapa INTEGER,
area_id INTEGER NOT NULL,
CONSTRAINT FK_AREA FOREIGN KEY(area_id) REFERENCES 'Area'(id_area)
);


CREATE TABLE IF NOT EXISTS 'Fuente' (
id_fuente INTEGER PRIMARY KEY NOT NULL,
descrip_fuente TEXT,
foto_fuente TEXT,
loc_id,
CONSTRAINT FK_LOCALIZACION FOREIGN KEY(loc_id) REFERENCES 'Localizacion'(id_loc)
);