-- Add new column to country without NOT NULL constraint
ALTER TABLE country ADD COLUMN code VARCHAR(255);

-- Update the existing rows with default values for the new column
UPDATE country SET code = 'BAD' WHERE code IS NULL;

-- Alter column to be set to NOT NULL constraint
ALTER TABLE country ALTER COLUMN code SET NOT NULL;