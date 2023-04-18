CREATE TABLE IF NOT EXISTS Employee (
     username VARCHAR(30) PRIMARY KEY ,
     password VARCHAR(30) NOT NULL ,
     role ENUM('USER','ADMIN') NOT NULL ,
    contact VARCHAR(11) NOT NULL
);

CREATE TABLE IF NOT EXISTS Company_Details(
    company_id VARCHAR(20) PRIMARY KEY ,
    companyname VARCHAR(100) NOT NULL ,
    refname VARCHAR(100) NOT NULL,
    itembrought DECIMAL(20) NOT NULL ,
    contactno VARCHAR(11) NOT NULL
);

CREATE TABLE IF NOT EXISTS Item(
    itemcode VARCHAR(20) PRIMARY KEY ,
    unitprice DECIMAL(20) NOT NULL ,
    quantity INT(20) NOT NULL ,
    itemName VARCHAR(100)NOT NULL ,
    date DATE NOT NULL ,
    company_id VARCHAR(20),
    FOREIGN KEY (company_id) REFERENCES Company_Details(company_id)
);


CREATE  TABLE  IF NOT EXISTS Production(
    itemcode VARCHAR(20) PRIMARY KEY ,
    company_id VARCHAR(20),
    item_out_count INT(10),
    out_date DATE NOT NULL,
    FOREIGN KEY (itemcode) REFERENCES Item(itemcode),
    FOREIGN KEY (company_id)REFERENCES Company_Details(company_id)
);

CREATE TABLE IF NOT EXISTS Item_Stork (
     itemcode VARCHAR(20) PRIMARY KEY ,
     company_id VARCHAR(20),
     FOREIGN KEY (itemcode) REFERENCES Item(itemcode),
     FOREIGN KEY (company_id)REFERENCES Company_Details(company_id)
);