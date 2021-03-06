DROP DATABASE utn_phones;
CREATE DATABASE utn_phones;
USE utn_phones;

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
    password VARCHAR(80),
    name VARCHAR(30),
    surname VARCHAR(30),
    city INT,
    user_type INT,
    active BOOLEAN DEFAULT TRUE,
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
    active BOOLEAN DEFAULT TRUE,
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
	id INT AUTO_INCREMENT,
	origin_city INT,
    destiny_city INT,
    cost_price FLOAT,
    price_per_minute FLOAT,
    CONSTRAINT pk_id_tariff PRIMARY KEY (id),
    CONSTRAINT fk_origin_city FOREIGN KEY (origin_city) REFERENCES cities (id),
    CONSTRAINT fk_destiny_city FOREIGN KEY (destiny_city) REFERENCES cities (id)
);

CREATE TABLE calls(
	id INT AUTO_INCREMENT,
    price_per_minute FLOAT,
    duration INT,
    date DATETIME,
    cost_price FLOAT,
    total_price FLOAT,
    origin_phone_line VARCHAR(15),
    destiny_phone_line VARCHAR(15),
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
    SET phone_id=  (IFNULL((SELECT id FROM phone_lines AS pl WHERE pl.number = number), 0));
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

DELIMITER $$
CREATE FUNCTION get_user_from_id_phone(id_phone INT) RETURNS INT
BEGIN
	DECLARE user_id INT;
    SET user_id= (SELECT u.id FROM users AS u INNER JOIN phone_lines AS pl WHERE pl.id=id_phone AND u.id=pl.user_id);
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

DELIMITER //
CREATE FUNCTION get_cost_price (origin_city INT, destiny_city INT) RETURNS FLOAT
BEGIN
	DECLARE cost_price FLOAT;
    SET cost_price= (SELECT t.cost_price FROM tariffs AS t WHERE t.origin_city=origin_city AND t.destiny_city=destiny_city);
    RETURN cost_price;
END
//


DELIMITER $$
CREATE FUNCTION get_total_price (price_per_minute FLOAT, duration INT) RETURNS FLOAT
BEGIN
	DECLARE total_price FLOAT;
    SET total_price= (duration/60) * price_per_minute;
    RETURN ROUND(total_price, 2);
END
$$

DELIMITER $$
CREATE FUNCTION get_total_cost_price (cost_price FLOAT, duration INT) RETURNS FLOAT
BEGIN
	DECLARE total_cost_price FLOAT;
    SET total_cost_price= (duration/60) * cost_price;
    RETURN ROUND(total_cost_price, 2);
END
$$

DELIMITER //
CREATE FUNCTION get_id_tariff(origin_number VARCHAR(15), destiny_number VARCHAR(15))
RETURNS INT
BEGIN
	DECLARE tariff_id int;
	SET tariff_id =IFNULL((SELECT t.id FROM tariffs AS t WHERE t.origin_city = get_city_from_number(origin_number) AND t.destiny_city = get_city_from_number(destiny_number)), 0);
	RETURN tariff_id;
END
//

/* Procedures, triggers, events */

DELIMITER $$
CREATE PROCEDURE throw_signal(IN id_origin_phone INT, IN id_destiny_phone INT)
BEGIN
	IF(id_origin_phone = 0)THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid origin number', MYSQL_ERRNO = 0001;
	END IF;
	IF(id_destiny_phone = 0)THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid destiny number', MYSQL_ERRNO = 0002;
	END IF;
END $$

DELIMITER //
CREATE PROCEDURE add_new_tariff (IN origin_city INT, IN destiny_city INT)
BEGIN
	DECLARE price_per_minute FLOAT;
    DECLARE cost_price FLOAT;
    SET price_per_minute= (RAND() * (10 - 1)) + 1;
    SET cost_price= (RAND() * (10 - 1)) + 1;
	INSERT INTO tariffs (origin_city, destiny_city, price_per_minute, cost_price) VALUES (origin_city, destiny_city, price_per_minute, cost_price);
    INSERT INTO tariffs (origin_city, destiny_city, price_per_minute, cost_price) VALUES (destiny_city, origin_city, price_per_minute, cost_price);
END
//

DELIMITER $$
CREATE TRIGGER add_call BEFORE INSERT ON calls FOR EACH ROW
BEGIN
    DECLARE tariff_id_exists INT;
	SET new.id_origin_line= get_id_phone_by_number(new.origin_phone_line);
    SET new.id_destiny_line= get_id_phone_by_number(new.destiny_phone_line);
    SET new.origin_city=get_city_from_number(new.origin_phone_line);
    SET new.destiny_city=get_city_from_number(new.destiny_phone_line);
    
	SET tariff_id_exists= get_id_tariff(new.origin_phone_line, new.destiny_phone_line);
    IF (tariff_id_exists=0) THEN
		CALL add_new_tariff(new.origin_city, new.destiny_city);
    END IF;
    
    SET new.price_per_minute= get_price_per_minute(new.origin_city, new.destiny_city);
    SET new.cost_price= get_total_cost_price(get_cost_price(new.origin_city, new.destiny_city), new.duration);
	SET new.total_price= get_total_price(new.price_per_minute, new.duration);
    
    CALL throw_signal(new.id_origin_line, new.id_destiny_line);
END
$$

DELIMITER //
CREATE PROCEDURE get_calls_total_cost_and_total_price(IN id_phone_line INT, OUT quantity_of_calls INT, OUT cost_price FLOAT, OUT total_price FLOAT)
BEGIN
	SET cost_price = IFNULL((SELECT SUM(c.cost_price) FROM calls AS c WHERE id_origin_line = id_phone_line AND ISNULL(bill)), 0);
    SET total_price = IFNULL((SELECT SUM(c.total_price) FROM calls AS c WHERE id_origin_line = id_phone_line  AND ISNULL(bill)), 0);
	SET quantity_of_calls = IFNULL((SELECT COUNT(c.id) FROM calls AS c WHERE id_origin_line = id_phone_line  AND ISNULL(bill)), 0);
END
//


DELIMITER $$
CREATE PROCEDURE set_bill(IN bill_id int,IN id_phone_line INT)
BEGIN
	UPDATE calls SET bill = bill_id WHERE id_origin_line = id_phone_line;
END
$$


DELIMITER $$
CREATE PROCEDURE generate_bill()
BEGIN
		DECLARE id_phone_line int;
		DECLARE end INT DEFAULT 0;
        DECLARE client INT;
		DECLARE cursor_phone_lines CURSOR FOR SELECT id FROM utn_phones.phone_lines;
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET end = TRUE;
	START TRANSACTION;
		OPEN cursor_phone_lines;
			loop_phone_lines:LOOP
				FETCH cursor_phone_lines INTO id_phone_line;
				IF end = 1 THEN
					LEAVE loop_phone_lines;
				END IF;
                SET client = get_user_from_id_phone(id_phone_line);
				CALL get_calls_total_cost_and_total_price(id_phone_line, @quantity_of_calls, @cost_price, @total_price);
				INSERT INTO utn_phones.bills(phone_line, quantity_of_calls , cost_price , total_price, expiring_date, date, client ) VALUES (id_phone_line, @quantity_of_calls, @cost_price, @total_price, NOW() + INTERVAL 15 day, CURDATE(), client);
				CALL set_bill(last_insert_id(), id_phone_line);
			END LOOP;
		CLOSE cursor_phone_lines;
	COMMIT;
END
$$

DROP EVENT event_generate_bill;
DELIMITER $$
SET GLOBAL event_scheduler = ON;
CREATE EVENT IF NOT EXISTS event_generate_bill ON SCHEDULE
EVERY 1 MONTH
STARTS '2020-06-25 21:48:00'
ENABLE
DO
BEGIN
	CALL generate_bill();
END
$$

DELIMITER //
CREATE INDEX index_calls ON calls (origin_phone_line, date);
//

DELIMITER $$
CREATE VIEW calls_consult 
AS
SELECT c.origin_phone_line, c.origin_city AS origin_city, c.destiny_phone_line, c.destiny_city, c.total_price, c.duration, c.date, pl.user_id FROM calls AS c INNER JOIN phone_lines AS pl ON pl.id=c.id_origin_line;
$$


DELIMITER //
CREATE PROCEDURE calls_by_user (IN client INT)
BEGIN
	SELECT * FROM calls_consult WHERE user_id=client;
END
//

DELIMITER $$
CREATE PROCEDURE calls_by_date (IN from_date DATE, IN to_date DATE)
BEGIN
	SELECT * FROM calls_consult WHERE date BETWEEN from_date AND to_date;
END
$$

DELIMITER $$
CREATE PROCEDURE calls_by_date_and_user (IN user INT,IN from_date DATE, IN to_date DATE)
BEGIN
	 SELECT * FROM calls_consult WHERE user_id=user AND date BETWEEN from_date AND to_date;
END
$$


/* Default Inserts */

INSERT INTO provinces (name) VALUES ("Buenos Aires"), ("Catamarca"), ("Chaco"), ("Chubut"), ("Córdoba"), ("Corrientes"), ("Entre Ríos"), ("Formosa"), ("Jujuy"), ("La Pampa"), ("La Rioja"), ("Mendoza"), ("Misiones"), ("Neuquén"), ("Rio Negro"), ("Salta"), ("San Juan"), ("San Luis"), ("Santa Cruz"), ("Santa Fé"), ("Santiago del Estero"), ("Tierra del Fuego"), ("Tucumán");
INSERT INTO line_types (type) VALUES ("home"), ("mobile");
INSERT INTO user_types (type) VALUES ("client"), ("employee"), ("antenna");
INSERT INTO cities (prefix, name, province) VALUES (11, "Buenos Aires", 1), (351, "Córdoba", 5), (379, "Corrientes", 6), (370, "Formosa", 8),
(221, "La Plata", 1), (380, "La Rioja", 11), (261, "Mendoza", 12), (299, "Neuquén", 14), (343, "Paraná", 7), (376, "Posadas", 13), (280, "Rawson", 4),
(362, "Resistencia", 3), (2966, "Río Gallegos", 19), (387, "Salta", 16), (383, "Catamarca", 2), (264, "San Juan", 17), (266, "San Luis", 18),
(381, "San Miguel de Tucumán", 23), (388, "San Salvador de Jujuy", 9), (342, "Santa Fé", 20), (2954, "Santa Rosa", 10), (385, "Santiago del Estero", 21),
(2920, "Viedma", 15), (2901, "Ushuaia", 22), (223, "Mar del Plata", 1);
/*INSERT INTO users (dni, username, password, name, surname, city, user_type) VALUES ("41715326", "florchiexco", "123", "Florencia", "Excoffon", "25", 1),
("41123456", "jaga", "123", "Julian", "Aga", 16, 1), ("45678932", "anita_e", "123", "Anita", "Excoffon", 16, 3), ("12458792", "admin", "202CB962AC59075B964B07152D234B70", "admin", "admin", 16, 2), ("123456789", "antenna", "202CB962AC59075B964B07152D234B70", "antenna", "antenna", 16, 2);
*/
INSERT INTO users (dni, username, password, name, surname, city, user_type) VALUES ("12458792", "admin", "202CB962AC59075B964B07152D234B70", "admin", "admin", 16, 2), ("123456789", "antenna", "202CB962AC59075B964B07152D234B70", "antenna", "antenna", 16, 3);
/*INSERT INTO phone_lines (number, line_type , user_id ) VALUES ("2235426942", 1, 1), ("264789251", 1, 2), ("266878941", 1, 1);
INSERT INTO calls (duration, date, origin_phone_line , destiny_phone_line ) VALUES (50, "2020-06-11", "2235426942", "266878941");
*/
/* Users */

CREATE USER 'spring_admin'@'localhost' IDENTIFIED BY '123';
CREATE USER 'backoffice'@'localhost' IDENTIFIED BY '123';
CREATE USER 'clients'@'localhost' IDENTIFIED BY '123';
CREATE USER 'infrastructure'@'localhost' IDENTIFIED BY '123';
CREATE USER 'billing'@'localhost' IDENTIFIED BY '123';

GRANT ALL ON utn_phones.* TO 'spring_admin'@'localhost';

GRANT ALL ON utn_phones.phone_lines TO 'backoffice'@'localhost';
GRANT ALL ON utn_phones.users TO 'backoffice'@'localhost';
GRANT ALL ON utn_phones.tariffs TO 'backoffice'@'localhost';

GRANT SELECT ON utn_phones.calls TO 'clients'@'localhost';
GRANT SELECT ON utn_phones.bills TO 'clients'@'localhost';

GRANT INSERT ON utn_phones.calls TO 'infrastructure'@'localhost';
GRANT TRIGGER ON utn_phones.* TO 'infrastructure'@'localhost';

GRANT EVENT ON utn_phones.* TO 'billing'@'localhost';
GRANT EXECUTE ON PROCEDURE utn_phones.generate_bill TO 'billing'@'localhost';
GRANT EXECUTE ON PROCEDURE get_calls_total_cost_and_total_price TO 'billing'@'localhost';
GRANT EXECUTE ON PROCEDURE set_bill TO 'billing'@'localhost';

select * from users;
select * from phone_lines;
select * from tariffs;
select * from cities;
select * from bills;
select * from calls;