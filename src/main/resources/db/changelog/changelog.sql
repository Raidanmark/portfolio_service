DROP TABLE IF EXISTS portfolios;

CREATE TABLE portfolios (
                            id UUID PRIMARY KEY,
                            name VARCHAR(100) NOT NULL CHECK (length(trim(name)) > 0),
                            base_currency VARCHAR(3) CHECK (base_currency IS NULL OR length(base_currency) = 3),
                            created_at TIMESTAMPTZ NOT NULL
);