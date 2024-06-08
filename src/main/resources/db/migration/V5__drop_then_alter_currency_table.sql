-- Rename currencycode column to currency_code column name in product table
DROP TABLE currency;
CREATE TABLE currency (
    id UUID PRIMARY KEY,
    baseCode VARCHAR(3) NOT NULL,
    targetCode VARCHAR(3) NOT NULL,
    conversionRate FLOAT NOT NULL
);

