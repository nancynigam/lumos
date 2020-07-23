DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  age INT,
  country VARCHAR(100),
  PRIMARY KEY (id));

mysqld => start mysql server [always run on port:33060]
mysql -uroot => start mysql client