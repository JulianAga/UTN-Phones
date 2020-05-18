CREATE DATABASE utn_phones;
drop database utn_phones;
USE utn_phones;

select * from USERS;
SELECT * FROM PROVINCES;
SELECT * FROM CITIES;
select * from user_types;
select * from line_types;
select * from bills;
select * from phone_lines;


CREATE TABLE provinces(
	id INT AUTO_INCREMENT,
    name VARCHAR(30) UNIQUE,
    CONSTRAINT pk_id_province PRIMARY KEY (id)
);

CREATE TABLE cities(
	id INT AUTO_INCREMENT,
    prefix INT(5) UNIQUE,
    name VARCHAR(30) UNIQUE,
    province INT,
    CONSTRAINT pk_id_city PRIMARY KEY (id),
    CONSTRAINT fk_id_province FOREIGN KEY (province) REFERENCES provinces (id)
);

CREATE TABLE user_types(
	id INT AUTO_INCREMENT,
    type VARCHAR(30),
    CONSTRAINT pk_id_user_type PRIMARY KEY (id)
);

CREATE TABLE users(
	id INT AUTO_INCREMENT,
    dni VARCHAR(9) UNIQUE,
    username VARCHAR(30) UNIQUE,
    password VARCHAR(30),
    name VARCHAR(30),
    surname VARCHAR(30),
    city INT,
    province INT,
    user_type INT,
    CONSTRAINT pk_id_user PRIMARY KEY (id),
    CONSTRAINT fk_id_city FOREIGN KEY (city) REFERENCES cities (id),
    CONSTRAINT fk_id_province2 FOREIGN KEY (province) REFERENCES provinces (id),
    CONSTRAINT fk_user_type FOREIGN KEY (user_type) REFERENCES user_types (id)
);

CREATE TABLE line_types(
	id INT AUTO_INCREMENT, 
    type VARCHAR(30),
    CONSTRAINT pk_id_line_type PRIMARY KEY (id)
);

CREATE TABLE phone_lines(
	id INT AUTO_INCREMENT,
    number VARCHAR(15) UNIQUE,
    line_type INT,
    user_id INT,
    CONSTRAINT pk_id_phone_line PRIMARY KEY (id),
    CONSTRAINT fk_line_type FOREIGN KEY (line_type) REFERENCES line_types (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE bills(
	id INT AUTO_INCREMENT,
    quantity_of_calls INT,
    cost_price FLOAT,
    total_price FLOAT,
    date DATE,
    expiring_date DATE,
    client INT,
    phone_line INT,
    CONSTRAINT pk_id_bill PRIMARY KEY (id),
    CONSTRAINT fk_id_client FOREIGN KEY (client) REFERENCES users (id),
    CONSTRAINT fk_id_phone_line FOREIGN KEY (phone_line) REFERENCES phone_lines(id)
);

CREATE TABLE tariffs(
	origin_city INT,
    destiny_city INT,
    cost_price FLOAT,
    price_per_minute FLOAT,
    CONSTRAINT fk_origin_city FOREIGN KEY (origin_city) REFERENCES cities (id),
    CONSTRAINT fk_destiny_city FOREIGN KEY (destiny_city) REFERENCES cities (id)
);

CREATE TABLE calls(
	id INT AUTO_INCREMENT,
    price_per_minute FLOAT,
    duration TIME,
    date DATETIME,
    cost_price FLOAT,
    total_price FLOAT,
    origin_phone_line VARCHAR(9),
    destiny_phone_line VARCHAR(9),
    id_origin_line INT,
    id_destiny_line INT,
    origin_city INT,
    destiny_city INT,
    bill INT,
    CONSTRAINT pk_id_call PRIMARY KEY (id),
    CONSTRAINT fk_id_origin_line FOREIGN KEY (id_origin_line) REFERENCES phone_lines (id),
    CONSTRAINT fk_id_destiny_line FOREIGN KEY (id_destiny_line) REFERENCES phone_lines (id),
    CONSTRAINT fk_origin_city2 FOREIGN KEY (origin_city) REFERENCES cities (id),
    CONSTRAINT fk_destiny_city2 FOREIGN KEY (destiny_city) REFERENCES cities (id),
    CONSTRAINT fk_id_bill FOREIGN KEY (bill) REFERENCES bills (id)
);


/* Procedures */

DELIMITER $$
CREATE PROCEDURE add_city(IN province VARCHAR(30),IN city VARCHAR(30),IN prefix INT)
BEGIN
    DECLARE province_id INT;
    SELECT id INTO province_id FROM provinces AS p WHERE p.name = province;
    INSERT INTO cities(name,prefix,province) VALUES (city,prefix,province_id);
END; 
$$

/* Functions */

/*Debería ver que algunos prefijos tienen 4, no 3, por lo que debería hacerlo como Enzo)*/
DELIMITER $$
CREATE FUNCTION get_prefix(phone VARCHAR(9)) RETURNS VARCHAR(20)
BEGIN
	DECLARE prefix VARCHAR(9);
    DECLARE x INT default 0;
	SET x=5;
    WHILE x<=5 AND x>0 DO
			SET prefix= (SELECT SUBSTRING(phone, 1, x));
				IF EXISTS(SELECT prefix FROM cities AS c WHERE c.prefix=prefix) THEN
					RETURN prefix;
				END IF;
                SET x=x-1;
		END WHILE;
    RETURN prefix;
END    
$$

DROP FUNCTION get_prefix;

/* Procedures calls */

CALL add_city("Buenos Aires", "Mar del Plata", 223);

/* Default Inserts */

INSERT INTO provinces (name) VALUES ("Buenos Aires"), ("Catamarca"), ("Chaco"), ("Chubut"), ("Córdoba"), ("Corrientes"), ("Entre Ríos"), ("Formosa"), ("Jujuy"), ("La Pampa"), ("La Rioja"), ("Mendoza"), ("Misiones"), ("Neuquén"), ("Rio Negro"), ("Salta"), ("San Juan"), ("San Luis"), ("Santa Cruz"), ("Santa Fé"), ("Santiago del Estero"), ("Tierra del Fuego"), ("Tucumán");
INSERT INTO line_types (type) VALUES ("home"), ("mobile");
INSERT INTO user_types (type) VALUES ("client"), ("employee");

/* Testing */

DELIMITER $$
CREATE PROCEDURE testing()
BEGIN
	DECLARE prefix VARCHAR(9);
	SET prefix= get_prefix(223542694);
    SELECT prefix;
END;
$$

CALL testing;
DROP PROCEDURE testing;
