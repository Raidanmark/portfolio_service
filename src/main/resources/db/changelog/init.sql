CREATE TABLE portfolio (
    id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE,
    name varchar(255) NOT NULL,
    description varchar(255)

)