-- 1. Tipos de mascota (IDs 1 a 6)
INSERT INTO types (name) VALUES ('cat');     -- id 1
INSERT INTO types (name) VALUES ('dog');     -- id 2
INSERT INTO types (name) VALUES ('lizard');  -- id 3
INSERT INTO types (name) VALUES ('snake');   -- id 4
INSERT INTO types (name) VALUES ('bird');    -- id 5
INSERT INTO types (name) VALUES ('hamster'); -- id 6

-- 2. Especialidades (IDs 1 a 3)
INSERT INTO specialties (name) VALUES ('radiology');  -- id 1
INSERT INTO specialties (name) VALUES ('surgery');    -- id 2
INSERT INTO specialties (name) VALUES ('dentistry');  -- id 3

-- 3. Veterinarios (IDs 1 a 3)
INSERT INTO vets (first_name, last_name) VALUES ('James', 'Carter');
INSERT INTO vets (first_name, last_name) VALUES ('Helen', 'Leary');
INSERT INTO vets (first_name, last_name) VALUES ('Linda', 'Douglas');

-- 4. Propietarios (IDs 1 y 2)
INSERT INTO owners (first_name, last_name, address, city, telephone)
VALUES ('George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023');

INSERT INTO owners (first_name, last_name, address, city, telephone)
VALUES ('Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');

-- 5. Mascotas (IDs 1 y 2)
-- Leo: type_id=1 (cat), owner_id=1 (George)
INSERT INTO pets (name, birth_date, type_id, owner_id)
VALUES ('Leo', '2010-09-07', 1, 1);

-- Basil: type_id=2 (dog), owner_id=2 (Betty)
INSERT INTO pets (name, birth_date, type_id, owner_id)
VALUES ('Basil', '2012-08-06', 2, 2);

-- 6. Visitas (IDs 1 y 2)
INSERT INTO visits (pet_id, visit_date, description)
VALUES (1, '2013-01-01', 'rabies shot');

INSERT INTO visits (pet_id, visit_date, description)
VALUES (2, '2013-01-04', 'neutered');
