ALTER TABLE customer
    ADD COLUMN birth_date DATE NULL;

UPDATE customer
SET birth_date = '1990-01-01'
WHERE birth_date IS NULL;