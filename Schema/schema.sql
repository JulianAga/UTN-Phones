CREATE DATABASE utn_phones;
drop database utn_phones;
USE utn_phones;

select * from USERS;
INSERT INTO users (dni, username, password, name, surname, city, user_type) VALUES ("40225328", "SasukeExco", "heno", "Sasuke", "Excoffon", 1, 1);
SELECT * FROM PROVINCES;
SELECT * FROM CITIES;
INSERT INTO cities (prefix, name, province) VALUES (3541, "Carlos Paz", 5);
select * from user_types;
select * from line_types;
select * from bills;
select * from phone_lines;
select * from tariffs;

CREATE TABLE line_types(
	id INT AUTO_INCREMENT,
    type VARCHAR(30),
    CONSTRAINT pk_id_line_type PRIMARY KEY (id)
);

CREATE TABLE phone_lines(
	id INT AUTO_INCREMENT,
    number VARCHAR(9) UNIQUE,
    line_type INT,
    CONSTRAINT pk_id_phone_line PRIMARY KEY (id),
    CONSTRAINT fk_line_type FOREIGN KEY (line_type) REFERENCES line_types (id)
);

ALTER TABLE phone_lines ADD COLUMN user_id INT;
ALTER TABLE phone_lines ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id);

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
    user_type INT,
    CONSTRAINT pk_id_user PRIMARY KEY (id),
    CONSTRAINT fk_id_city FOREIGN KEY (city) REFERENCES cities (id),
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
    suspended BOOLEAN DEFAULT FALSE,
    CONSTRAINT pk_id_phone_line PRIMARY KEY (id),
    CONSTRAINT fk_line_type FOREIGN KEY (line_type) REFERENCES line_types (id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id)
);

INSERT INTO phone_lines (number, line_type, user_id) VALUES ("354158763",2, 2);

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

INSERT INTO bills(quantity_of_calls, cost_price, total_price, date, expiring_date, client, phone_line) VALUES (2, 1, 5, "1999-06-25", "1999-06-30", 1, 1);

CREATE TABLE tariffs(
    id INT AUTO_INCREMENT,
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
    bill INT DEFAULT NULL,
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
END
$$

/*
DELIMITER $$
CREATE PROCEDURE add_call(IN origin_number VARCHAR(15), IN destiny_number VARCHAR(15), IN duration TIME, IN date DATETIME)
BEGIN
	DECLARE origin_city INT;
    DECLARE destiny_city INT;
    SET origin_city= get_city_from_number(origin_number);
    SET destiny_city= get_city_from_number(destiny_number);
    
END
$$
*/
/* Functions */

DELIMITER %%
CREATE FUNCTION get_prefix(phone VARCHAR(20)) RETURNS VARCHAR(9)
BEGIN
	DECLARE prefix INT;
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
%%

DROP FUNCTION get_prefix;

DELIMITER &&
CREATE FUNCTION get_city_from_prefix(prefix INT) RETURNS INT
BEGIN
	DECLARE city_id INT;
    SET city_id= (SELECT c.id FROM cities AS c WHERE c.prefix=prefix);
    RETURN city_id;
END
&&

DELIMITER //
CREATE FUNCTION get_city_from_number(number VARCHAR(15)) RETURNS INT
BEGIN
	DECLARE prefix INT;
    DECLARE city_id INT;
    SET prefix= get_prefix(number);
    SET city_id= get_city_from_prefix(prefix);
    RETURN city_id;
END
//

DELIMITER !!
CREATE FUNCTION get_id_phone_by_number(number VARCHAR(15)) RETURNS INT
BEGIN
	DECLARE phone_id INT;
    SET phone_id= (SELECT pl.id FROM phone_lines AS pl WHERE pl.number=number);
    RETURN phone_id;
END
!!

DELIMITER $$
CREATE FUNCTION get_user_from_number(number VARCHAR(15)) RETURNS INT
BEGIN
	DECLARE user_id INT;
    DECLARE phone_id INT;
    SET phone_id= get_id_phone_by_number(number);
    SET user_id= (SELECT u.id FROM users AS u INNER JOIN phone_lines AS pl WHERE pl.id=phone_id AND u.id=pl.user_id);
	RETURN user_id;
END
$$

DELIMITER //
CREATE FUNCTION get_price_per_minute (origin_city INT, destiny_city INT) RETURNS FLOAT
BEGIN
	DECLARE price_per_minute FLOAT;
    SET price_per_minute= (SELECT t.price_per_minute FROM tariffs AS t WHERE t.origin_city=origin_city AND t.destiny_city=destiny_city);
    RETURN price_per_minute;
END
//

/*calcular tambien los segundos!*/
DELIMITER $$
CREATE FUNCTION get_total_price (price_per_minute FLOAT, duration TIME) RETURNS FLOAT
BEGIN
	DECLARE total_price FLOAT;
    DECLARE minutes INT;
    SET minutes= (SELECT EXTRACT(MINUTE FROM duration));
    SET total_price= price_per_minute * minutes;
    RETURN total_price;
END
$$
DROP FUNCTION get_total_price;

/* Default Inserts */

INSERT INTO provinces (name) VALUES ("Buenos Aires"), ("Catamarca"), ("Chaco"), ("Chubut"), ("Córdoba"), ("Corrientes"), ("Entre Ríos"), ("Formosa"), ("Jujuy"), ("La Pampa"), ("La Rioja"), ("Mendoza"), ("Misiones"), ("Neuquén"), ("Rio Negro"), ("Salta"), ("San Juan"), ("San Luis"), ("Santa Cruz"), ("Santa Fé"), ("Santiago del Estero"), ("Tierra del Fuego"), ("Tucumán");
INSERT INTO line_types (type) VALUES ("home"), ("mobile");
INSERT INTO user_types (type) VALUES ("client"), ("employee");

/* Procedures calls */

CALL add_city("Buenos Aires", "Mar del Plata", 223);
CALL add_city("Buenos Aires", "Buenos Aires", 11);

/* Testing */

INSERT INTO users (dni, username, password, name, surname, city, user_type) VALUES ("41715326", "florchiexco", "123", "Florencia", "Excoffon", 1, 1);
INSERT INTO phone_lines (number, line_type, user_id) VALUES ("113542694", 1, 1);
INSERT INTO tariffs (origin_city, destiny_city, price_per_minute, cost_price) VALUES (1, 2, 1.5, 1);

DELIMITER $$
CREATE PROCEDURE testing()
BEGIN
	DECLARE city_id INT;
    DECLARE phone_id INT;
    DECLARE user INT;
    DECLARE price_per_minute FLOAT;
    DECLARE total_price FLOAT;

    
    SET price_per_minute= get_price_per_minute(1, 2);
    SET city_id= get_city_from_number(113542694);
    SET phone_id= get_id_phone_by_number(113542694);
    SET user= get_user_from_number(113542694);
    SET total_price= get_total_price(price_per_minute, "00:01:30");

    SELECT price_per_minute, user, city_id, phone_id, total_price;
END;
$$

CALL testing;
DROP PROCEDURE testing;
