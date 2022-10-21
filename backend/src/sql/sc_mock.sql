DROP TABLE "SCOTT"."ACCOUNT";

DROP TABLE "SCOTT"."PREFERENCES";

DROP TABLE "SCOTT"."TRADE_HISTORY";

DROP TABLE "SCOTT"."STOCKS";

DROP TABLE "SCOTT"."MUTUALFUNDS";

DROP TABLE "SCOTT"."BONDS";

DROP TABLE "SCOTT"."USER";

DROP SEQUENCE seq_trade_history;

CREATE SEQUENCE seq_trade_history MINVALUE 1 MAXVALUE 999999999999999999999999999 START WITH 1 INCREMENT BY 1 CACHE 20;

CREATE TABLE "SCOTT"."USER"(
  user_id VARCHAR2(10) PRIMARY KEY,
  name VARCHAR2(50) NOT NULL,
  acct_no NUMBER UNIQUE NOT NULL,
  email VARCHAR2(50) UNIQUE NOT NULL,
  password_hash VARCHAR2(64) NOT NULL
);

CREATE TABLE "SCOTT"."ACCOUNT" (
  user_id VARCHAR2(10) UNIQUE NOT NULL,
  acct_no int UNIQUE NOT NULL,
  bank_name VARCHAR2(50),
  acct_type VARCHAR2(20),
  acct_balance FLOAT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES "SCOTT"."USER"(user_id)
);

CREATE TABLE "SCOTT"."PREFERENCES" (
  user_id VARCHAR2(10) UNIQUE NOT NULL,
  investment_purpose VARCHAR2(100),
  risk_tolerance INTEGER CHECK(
    risk_tolerance >= 1
    AND risk_tolerance <= 5
  ),
  income_category VARCHAR2(10),
  investment_length VARCHAR2(50),
  FOREIGN KEY (user_id) REFERENCES "SCOTT"."USER"(user_id)
);

CREATE TABLE "SCOTT"."TRADE_HISTORY" (
  trade_id NUMBER DEFAULT seq_trade_history.NEXTVAL,
  user_id VARCHAR2(10) NOT NULL,
  asset_code VARCHAR2(10) NOT NULL,
  trade_price FLOAT,
  trade_type VARCHAR2(10),
  trade_quantity INTEGER,
  trade_date TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES "SCOTT"."USER"(user_id)
);

CREATE TABLE "SCOTT"."STOCKS" (
  code VARCHAR2(10),
  name VARCHAR2(50),
  quantity INTEGER,
  current_price FLOAT,
  stock_type VARCHAR2(50),
  exchange VARCHAR2(50)
);

CREATE TABLE "SCOTT"."MUTUALFUNDS" (
  code VARCHAR2(10),
  name VARCHAR2(50),
  quantity INTEGER,
  current_price FLOAT,
  mf_type VARCHAR2(50),
  interest_rate FLOAT
);

CREATE TABLE "SCOTT"."BONDS" (
  code VARCHAR2(10),
  name VARCHAR2(50),
  quantity INTEGER,
  current_price FLOAT,
  bond_type VARCHAR2(50),
  interest_rate FLOAT,
  duration_months INTEGER,
  exchange VARCHAR2(50)
);

/*mock entries for stock*/
INSERT INTO
  STOCKS(
    code,
    name,
    quantity,
    current_price,
    stock_type,
    exchange
  )
VALUES
(
    'AAPL',
    'Apple Inc',
    100,
    155.34,
    'main_index_stocks',
    'NYSE'
  );

INSERT INTO
  STOCKS(
    code,
    name,
    quantity,
    current_price,
    stock_type,
    exchange
  )
VALUES
(
    'TSLA',
    'Tesla Inc',
    100,
    657.65,
    'main_index_stocks',
    'NASDAQ'
  );

INSERT INTO
  STOCKS(
    code,
    name,
    quantity,
    current_price,
    stock_type,
    exchange
  )
VALUES
(
    'AMZN',
    'Amazon.com Inc',
    100,
    2360.00,
    'main_index_stocks',
    'NASDAQ'
  );

/*mock entries for mfs*/
INSERT INTO
  MUTUALFUNDS(
    code,
    name,
    quantity,
    current_price,
    mf_type,
    interest_rate
  )
VALUES
(
    'VFIAX',
    'Vanguard 500',
    100,
    400.34,
    'debt fund',
    1.19
  );

INSERT INTO
  MUTUALFUNDS(
    code,
    name,
    quantity,
    current_price,
    mf_type,
    interest_rate
  )
VALUES
(
    'AGTHX',
    'American Funds gro',
    34,
    84.67,
    'quant fund',
    2.19
  );

INSERT INTO
  MUTUALFUNDS(
    code,
    name,
    quantity,
    current_price,
    mf_type,
    interest_rate
  )
VALUES
(
    'FDRXX',
    'Finance Govt Cash Rsrvs',
    550,
    120.54,
    'small cap',
    0.98
  );

/*mock entries for bond*/
INSERT INTO
  BONDS (
    code,
    name,
    quantity,
    current_price,
    bond_type,
    interest_rate,
    duration_months,
    exchange
  )
VALUES
  (
    'BLONDE',
    'James Bond 007',
    100,
    188.34,
    'government_bond',
    3.0,
    24,
    'NASDAQ'
  );

INSERT INTO
  "SCOTT"."BONDS" (
    code,
    name,
    quantity,
    current_price,
    bond_type,
    interest_rate,
    duration_months,
    exchange
  )
VALUES
  (
    'POND',
    'Lily pond 30',
    89,
    668.34,
    'corporate_bond',
    7.0,
    36,
    'NYSE'
  );

INSERT INTO
  "SCOTT"."BONDS" (
    code,
    name,
    quantity,
    current_price,
    bond_type,
    interest_rate,
    duration_months,
    exchange
  )
VALUES
  (
    'WAND',
    'MAGIC WAND 1200',
    1300,
    665.4,
    'high_yield_bond',
    2.5,
    12,
    'NASDAQ'
  );

/*mock user entries*/
INSERT INTO
  "SCOTT"."USER" (user_id, name, acct_no, email, password_hash)
VALUES
  (
    1001,
    'preethi',
    234256786534,
    'preethi@fmr.com',
    '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918'
  );

INSERT INTO
  "SCOTT"."USER" (user_id, name, acct_no, email, password_hash)
VALUES
  (
    1002,
    'shaan',
    982167897543,
    'shaan01@fmr.com',
    '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918'
  );

/* mock entries for Account */
INSERT INTO
  ACCOUNT(user_id, acct_no, bank_name, acct_type, acct_balance)
VALUES
(1001, 234256786534, 'citi bank', 'savings', 12000.56);

INSERT INTO
  ACCOUNT(user_id, acct_no, bank_name, acct_type, acct_balance)
VALUES
(1002, 982167897543, 'US bank', 'salary', 156000.76);

/*mock trade_history entries*/
INSERT INTO
  TRADE_HISTORY (
    user_id,
    asset_code,
    trade_price,
    trade_type,
    trade_quantity,
    trade_date
  )
VALUES
  (
    1001,
    'AAPL',
    155.34,
    'buy',
    100,
    TO_TIMESTAMP (
      '2021-09-02 14:10:10.123',
      'YYYY-MM-DD HH24:MI:SS.FF'
    )
  );

INSERT INTO
  TRADE_HISTORY (
    user_id,
    asset_code,
    trade_price,
    trade_type,
    trade_quantity,
    trade_date
  )
VALUES
  (
    1001,
    'TSLA',
    657.65,
    'buy',
    22,
    TO_TIMESTAMP(
      '2021-03-23 11:40:00',
      'YYYY-MM-DD HH24:MI:SS.FF'
    )
  );

INSERT INTO
  TRADE_HISTORY (
    user_id,
    asset_code,
    trade_price,
    trade_type,
    trade_quantity,
    trade_date
  )
VALUES
  (
    1002,
    'AMZN',
    2354.34,
    'buy',
    100,
    TO_TIMESTAMP(
      '2021-04-23 14:00:34',
      'YYYY-MM-DD HH24:MI:SS.FF'
    )
  );

INSERT INTO
  TRADE_HISTORY (
    user_id,
    asset_code,
    trade_price,
    trade_type,
    trade_quantity,
    trade_date
  )
VALUES
  (
    1001,
    'TSLA',
    657.65,
    'sell',
    20,
    TO_TIMESTAMP(
      '2021-08-03 14:21:23',
      'YYYY-MM-DD HH24:MI:SS.FF'
    )
  );

INSERT INTO
  TRADE_HISTORY (
    user_id,
    asset_code,
    trade_price,
    trade_type,
    trade_quantity,
    trade_date
  )
VALUES
  (
    1002,
    'TSLA',
    657.65,
    'sell',
    20,
    TO_TIMESTAMP(
      '2021-08-03 14:21:23',
      'YYYY-MM-DD HH24:MI:SS.FF'
    )
  );