-- Add "open" flag to restaurant table for open/close status
ALTER TABLE restaurant ADD COLUMN open BOOLEAN DEFAULT TRUE NOT NULL;
