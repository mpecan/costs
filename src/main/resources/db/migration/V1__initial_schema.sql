CREATE TABLE medical_provider
(
    id                          BIGINT PRIMARY KEY,
    name                        VARCHAR(512),
    street_address              VARCHAR(512),
    city                        VARCHAR(128),
    state                       VARCHAR(2),
    zip_code                    BIGINT,
    referral_region_description VARCHAR(256)
);

CREATE TABLE cost_data
(
    id                        BIGINT PRIMARY KEY,
    drg_definition            VARCHAR(512),
    provider_id               BIGINT REFERENCES medical_provider(id),
    total_discharges          BIGINT,
    average_covered_charges   NUMERIC,
    average_total_payments    NUMERIC,
    average_medicare_payments NUMERIC
);


