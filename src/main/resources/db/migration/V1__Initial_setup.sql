CREATE TABLE currency (
    id UUID PRIMARY KEY,
    baseCode VARCHAR(3) NOT NULL,
    targetCode VARCHAR(3) NOT NULL,
    conversionRate FLOAT NOT NULL
);

CREATE TABLE country (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    dutRate FLOAT,
    taxRate FLOAT
);

CREATE TABLE product (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price FLOAT NOT NULL
);

