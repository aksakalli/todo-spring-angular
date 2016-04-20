--
-- Application tables
--
--

CREATE TABLE app_user (
    id bigserial NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(60) NOT NULL,
    email character varying(100) NOT NULL,
    first_name character varying(50),
    last_name character varying(50),

    created_by character varying(50) NOT NULL,
    created_date timestamp NOT NULL,
    last_modified_by character varying(50),
    last_modified_date timestamp
);

CREATE TABLE thing (
    id bigserial NOT NULL,
    title character varying(255) NOT NULL,
    content text,

    created_by character varying(50) NOT NULL,
    created_date timestamp NOT NULL,
    last_modified_by character varying(50),
    last_modified_date timestamp
);


INSERT INTO app_user (id, username, password, email, first_name, last_name, created_by,created_date)
    VALUES (1, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'user@localhost', 'User', 'User', 'system', CURRENT_TIMESTAMP);

INSERT INTO thing (id, title, content, created_by,created_date)
    VALUES (1, 'Hello World', 'This your very first note.', 'system', CURRENT_TIMESTAMP);


--
-- Constrains
--

ALTER TABLE app_user
    ADD CONSTRAINT pk_app_user PRIMARY KEY (id);

ALTER TABLE app_user
    ADD CONSTRAINT app_user_email_key UNIQUE (email);

ALTER TABLE app_user
    ADD CONSTRAINT app_user_login_key UNIQUE (username);


ALTER TABLE thing
    ADD CONSTRAINT pk_thing PRIMARY KEY (id);
