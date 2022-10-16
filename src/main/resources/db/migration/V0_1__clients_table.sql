CREATE TABLE IF NOT EXISTS clients (
    cod_patient SERIAL NOT NULL PRIMARY KEY,
    first_name TEXT,
    last_name TEXT,
    cnp TEXT,
    date_of_birth TIMESTAMP,
    address TEXT,
    phone TEXT,
    gdpr_completed BOOLEAN
);