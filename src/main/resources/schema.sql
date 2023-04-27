/*Employee related*/
CREATE TABLE IF NOT EXISTS Employee
(
    user_name VARCHAR(30) PRIMARY KEY,
    full_name VARCHAR(100)          NOT NULL,
    password  VARCHAR(100)          NOT NULL,
    role      ENUM ('USER','ADMIN') NOT NULL,
    address   VARCHAR(100)          NOT NULL
);

CREATE TABLE IF NOT EXISTS Employee_contact
(
    user_name VARCHAR(30) NOT NULL,
    contact   VARCHAR(11) NOT NULL,
    CONSTRAINT pk_employee_contact PRIMARY KEY (user_name, contact),
    CONSTRAINT fk_username FOREIGN KEY (user_name) REFERENCES Employee (user_name)
);

/*Company related*/
CREATE TABLE IF NOT EXISTS company
(
    company_id   VARCHAR(15) NOT NULL,
    company_name VARCHAR(20) NOT NULL,
    email        VARCHAR(20),
    address      VARCHAR(100),
    CONSTRAINT company_pk PRIMARY KEY (company_id)
);

CREATE TABLE IF NOT EXISTS company_contact
(
    company_id VARCHAR(15) NOT NULL,
    contact    VARCHAR(11) NOT NULL,
    CONSTRAINT cmp_fk FOREIGN KEY (company_id) REFERENCES company (company_id),
    CONSTRAINT cmp_pk PRIMARY KEY (company_id)
);

/*Bill Related*/
CREATE TABLE IF NOT EXISTS company_order
(
    bill_id    VARCHAR(10) NOT NULL,
    date       DATETIME    NOT NULL,
    company_id VARCHAR(20) NOT NULL,
    username   VARCHAR(30) NOT NULL,
    CONSTRAINT cmp_order_pk PRIMARY KEY (bill_id),
    CONSTRAINT cmp_order_fk FOREIGN KEY (company_id) REFERENCES company (company_id),
    CONSTRAINT cmp_order_fk_un FOREIGN KEY (username) REFERENCES Employee (user_name)
);

CREATE TABLE IF NOT EXISTS item_details
(
    item_code  VARCHAR(15) NOT NULL,
    item_name  VARCHAR(50) NOT NULL,
    company_id VARCHAR(15) NOT NULL,
    CONSTRAINT pk_item_details PRIMARY KEY (item_code),
    CONSTRAINT fk_item_details FOREIGN KEY (company_id) REFERENCES company (company_id)
);

/*Item store Related*/
CREATE TABLE IF NOT EXISTS supplied_item
(
    bill_id    VARCHAR(10)   NOT NULL,
    item_code  VARCHAR(15)   NOT NULL,
    quantity   INT           NOT NULL,
    unit_price DECIMAL(8, 2) NOT NULL,
    CONSTRAINT pk_supplied_item PRIMARY KEY (bill_id),
    CONSTRAINT fk_bill_id FOREIGN KEY (bill_id) REFERENCES company_order (bill_id),
    CONSTRAINT fk_item_code FOREIGN KEY (item_code) REFERENCES item_details (item_code)
);

CREATE TABLE IF NOT EXISTS item_batch
(
    item_code  VARCHAR(15)   NOT NULL,
    batch_no   INT           NOT NULL,
    quantity   INT           NOT NULL,
    unit_price DECIMAL(8, 2) NOT NULL,
    CONSTRAINT pk_item_batch PRIMARY KEY (item_code, batch_no),
    CONSTRAINT fk_item_batch FOREIGN KEY (item_code) REFERENCES item_details (item_code)
);

/*Production item Related*/
CREATE TABLE IF NOT EXISTS production_item
(
    order_id  VARCHAR(15) NOT NULL,
    date      DATETIME    NOT NULL,
    user_name VARCHAR(30) NOT NULL,
    CONSTRAINT pk_order_id PRIMARY KEY (order_id),
    CONSTRAINT fk_usrname FOREIGN KEY (user_name) REFERENCES Employee (user_name)
);

CREATE TABLE IF NOT EXISTS production_item_details
(
    order_id  VARCHAR(15) NOT NULL,
    batch_no  INT         NOT NULL,
    item_code VARCHAR(15) NOT NULL,
    quantity  INT         NOT NULL,
    CONSTRAINT pk_item_details PRIMARY KEY (order_id, batch_no, item_code),
    CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES production_item (order_id),
    CONSTRAINT fk_batch_no FOREIGN KEY (item_code, batch_no) REFERENCES item_batch(item_code, batch_no)
);