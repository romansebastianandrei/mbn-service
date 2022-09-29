CREATE TABLE IF NOT EXISTS registrations (
    id_registration SERIAL NOT NULL PRIMARY KEY,
    id_recommended_doctor SERIAL,
    id_consulted_doctor SERIAL,
    date_of_consultation TIMESTAMP,
    diagnostic TEXT,
    investigation TEXT,
    treatment TEXT,
    recommendation TEXT,
    cod_patient SERIAL,
    CONSTRAINT fk_client FOREIGN KEY(cod_patient) REFERENCES clients(cod_patient)
);