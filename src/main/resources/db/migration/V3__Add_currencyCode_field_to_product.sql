-- Add new column to country without NOT NULL constraint
ALTER TABLE product ADD COLUMN currencycode VARCHAR(255);

-- Update the existing rows with default values for the new column
UPDATE product SET currencycode = 'USD' WHERE currencycode IS NULL;

-- Alter column to be set to NOT NULL constraint
ALTER TABLE product ALTER COLUMN currencycode SET NOT NULL;