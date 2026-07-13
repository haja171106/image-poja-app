CREATE TABLE images (
                        id BIGSERIAL PRIMARY KEY,
                        filename VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT now()
);