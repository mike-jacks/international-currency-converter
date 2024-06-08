-- Rename currencycode column to currency_code column name in product table
DROP TABLE currency;

CREATE TABLE currency (
    id UUID PRIMARY KEY,
    base_code VARCHAR(3) NOT NULL,
    target_code VARCHAR(3) NOT NULL,
    conversion_rate FLOAT NOT NULL
);

ALTER TABLE country DROP COLUMN IF EXISTS dutyrate;
ALTER TABLE country DROP COLUMN IF EXISTS taxrate;


