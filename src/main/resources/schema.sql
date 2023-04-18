CREATE TABLE IF NOT EXISTS Employee (
     user_name VARCHAR(30) PRIMARY KEY ,
     password VARCHAR(30) NOT NULL ,
     role ENUM('USER','ADMIN') NOT NULL ,
     contact VARCHAR(11) NOT NULL
);


CREATE TABLE IF NOT EXISTS Company_Details(
     company_id VARCHAR(10) PRIMARY KEY ,
     company_name VARCHAR(100) NOT NULL ,
     company_contact VARCHAR(11) NOT NULL ,
     company_address VARCHAR(300) NOT NULL ,
     rep_name VARCHAR(100) NOT NULL,
     rep_contact   VARCHAR(11) NOT NULL
);

CREATE TABLE IF NOT EXISTS Item(
     item_code VARCHAR(20) PRIMARY KEY ,
     item_name VARCHAR(100)NOT NULL ,
     unit_price DECIMAL(20) NOT NULL ,
     company_id VARCHAR(20),
     FOREIGN KEY (company_id) REFERENCES Company_Details(company_id)
);


CREATE  TABLE  IF NOT EXISTS Stock_Management(
     reference_number INT AUTO_INCREMENT PRIMARY KEY,
     item_code VARCHAR(20) NOT NULL ,
     item_quantity INT(10) NOT NULL,
     in_out ENUM('IN','OUT'),
     date DATETIME NOT NULL,
     user VARCHAR(30) NOT NULL,
     unit_price DECIMAL(20) NOT NULL,
     FOREIGN KEY (item_code) REFERENCES Item(item_code),
     FOREIGN KEY (user) REFERENCES Employee(user_name),
     FOREIGN KEY (unit_price) REFERENCES Item(unit_price)
);
