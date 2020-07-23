mysqld => start mysql server [always run on port:33060]
mysql -uroot => start mysql client
1st start the SQL server.
show databases;
use <db name>;
show tables;
select * from <table_name>;

API end points :
http://localhost:8080/users/
404 means that nothing exists at that URI

Exception handling :
DAO layer will throw exception