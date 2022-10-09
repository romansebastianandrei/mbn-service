CREATE TABLE IF NOT EXISTS registrations
(
    registration_id      SERIAL NOT NULL PRIMARY KEY,
    recommended_doctor   TEXT,
    consulted_doctor     TEXT,
    date_of_consultation DATE,
    age_at_consultation  SERIAL,
    diagnostic           TEXT,
    investigation        TEXT,
    treatment            TEXT,
    recommendation       TEXT,
    cod_patient          SERIAL,
    CONSTRAINT fk_client FOREIGN KEY (cod_patient) REFERENCES clients (cod_patient)
);