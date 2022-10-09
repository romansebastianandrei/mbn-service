CREATE TABLE IF NOT EXISTS "files"
(
    "file_id"         UUID PRIMARY KEY,
    "name"            varchar,
    "path"            varchar,
    "registration_id" INTEGER,
    "patient_id"      INTEGER
);