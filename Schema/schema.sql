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
CREATE PROCEDURE throw_signal(IN id_origin_phone INT, IN id_destiny_phone INT, IN tariff_id INT)
BEGIN
	IF(id_origin_phone = 0)THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid origin number', MYSQL_ERRNO = 0001;
	END IF;
	IF(id_destiny_phone = 0)THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid destiny number', MYSQL_ERRNO = 0002;
	END IF;
	IF(tariff_id = 0)THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'There is no tariff for those cities', MYSQL_ERRNO = 0003;
	END IF;
END $$

DELIMITER $$
CREATE TRIGGER add_call BEFORE INSERT ON calls FOR EACH ROW
BEGIN
    DECLARE tariff_id INT;
	SET new.id_origin_line= get_id_phone_by_number(new.origin_phone_line);
    SET new.id_destiny_line= get_id_phone_by_number(new.destiny_phone_line);
    SET new.origin_city=get_city_from_number(new.origin_phone_line);
    SET new.destiny_city=get_city_from_number(new.destiny_phone_line);
    SET new.price_per_minute= get_price_per_minute(new.origin_city, new.destiny_city);
    SET new.cost_price= get_total_cost_price(get_cost_price(new.origin_city, new.destiny_city), new.duration);
	SET new.total_price= get_total_price(new.price_per_minute, new.duration);
    

    SET tariff_id= get_id_tariff(new.origin_phone_line, new.destiny_phone_line);
    CALL throw_signal(new.id_origin_line, new.id_destiny_line, tariff_id);
END
$$

DELIMITER //
CREATE PROCEDURE get_calls_total_cost_and_total_price(IN id_phone_line INT, OUT quantity_of_calls INT, OUT cost_price FLOAT, OUT total_price FLOAT)
BEGIN
	SET cost_price = IFNULL((SELECT SUM(c.cost_price) FROM calls AS c WHERE id_origin_line = id_phone_line AND ISNULL(bill)), 0);
	SET quantity_of_calls = IFNULL((SELECT COUNT(c.id) FROM calls AS c WHERE id_origin_line = id_phone_line  AND ISNULL(bill)), 0);
	SET total_price = IFNULL((SELECT SUM(c.total_price) FROM calls AS c WHERE id_origin_line = id_phone_line  AND ISNULL(bill)), 0);
END
//


DELIMITER $$
CREATE PROCEDURE set_bill(IN bill_id int,IN id_phone_line INT)
BEGIN
	UPDATE calls SET bill = bill_id WHERE id_origin_line = id_phone_line;
END
$$


DELIMITER $$
SET GLOBAL event_scheduler = ON;
CREATE DEFINER = 'root'@'localhost' EVENT IF NOT EXISTS generate_bill ON SCHEDULE
EVERY 30 DAY
STARTS '2020-06-01 01:00:00'
ENABLE
DO
	BEGIN
		DECLARE id_phone_line int;
		DECLARE done INT DEFAULT 0;
        DECLARE client INT;
		DECLARE cur_phonelines CURSOR FOR SELECT id FROM utn_phones.phone_lines;
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
		OPEN cur_phonelines;
			loop_phonelines:LOOP
				FETCH cur_phonelines INTO id_phone_line;
				IF done = 1 THEN
					LEAVE loop_phonelines;
				END IF;
                SET client = get_user_from_id_phone(id_phone_line);
				CALL get_calls_total_cost_and_total_price(id_phone_line, @quantity_of_calls, @cost_price, @total_price);
				INSERT INTO utn_phones.bills(phone_line, quantity_of_calls , cost_price , total_price, expiring_date, date, client ) VALUES (id_phone_line, @quantity_of_calls, @cost_price, @total_price, NOW() + INTERVAL 15 day, CURDATE(), client);
				CALL set_bill(last_insert_id(), id_phone_line);
			END LOOP;
		CLOSE cur_phonelines;
   	END
    $$
    

/* Default Inserts */

INSERT INTO provinces (name) VALUES ("Buenos Aires"), ("Catamarca"), ("Chaco"), ("Chubut"), ("Córdoba"), ("Corrientes"), ("Entre Ríos"), ("Formosa"), ("Jujuy"), ("La Pampa"), ("La Rioja"), ("Mendoza"), ("Misiones"), ("Neuquén"), ("Rio Negro"), ("Salta"), ("San Juan"), ("San Luis"), ("Santa Cruz"), ("Santa Fé"), ("Santiago del Estero"), ("Tierra del Fuego"), ("Tucumán");
INSERT INTO line_types (type) VALUES ("home"), ("mobile");
INSERT INTO user_types (type) VALUES ("client"), ("employee");