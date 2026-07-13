CREATE TABLE images (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        filename VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT now()
);