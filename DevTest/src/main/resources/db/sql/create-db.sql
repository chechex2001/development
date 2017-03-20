--DROP TABLE users IF EXISTS;


CREATE TABLE FLIGHT (
  id            INTEGER PRIMARY KEY  AUTO_INCREMENT,
  origin 		VARCHAR(3),
  destination   VARCHAR(3),
  airline       VARCHAR(6),
  price 		FLOAT
);
CREATE TABLE AIRLINES_INFANT_PRICES (
  id            INTEGER PRIMARY KEY  AUTO_INCREMENT,
  iataCode 		VARCHAR(3),  
  price 		FLOAT
);
