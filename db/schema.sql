
CREATE TABLE certificates
(
    id               serial,
    name             character varying(50) NOT NULL,
    description      text                  NOT NULL,
    price            numeric(6, 2)         NOT NULL,
    duration         integer               NOT NULL,
    create_date      timestamp             NOT NULL,
    last_update_date timestamp             NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE tags
(
    id   serial,
    name character varying(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

CREATE TABLE certificates_tags
(
    certificate_id      integer NOT NULL,
    tag_id              integer NOT NULL,
    PRIMARY KEY (certificate_id, tag_id),
    FOREIGN KEY (certificate_id) REFERENCES certificates (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags (id) ON DELETE CASCADE
);

CREATE TABLE users
(
    id       serial,
    username character varying(50) NOT NULL,
    password character varying(100) NOT NULL,
    role character varying(50),
    PRIMARY KEY (id),
    UNIQUE (username)
);

CREATE TABLE orders
(
    id                  serial,
    cost                numeric(6, 2) NOT NULL,
    order_date          timestamp     NOT NULL,
    certificate_id      integer       NOT NULL,
    user_id             integer       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (certificate_id) REFERENCES certificates (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    UNIQUE (certificate_id)
);
