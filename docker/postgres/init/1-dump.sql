--
-- PostgreSQL database cluster dump
--

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Drop databases (except postgres and template1)
--

DROP DATABASE IF EXISTS bookspot;


--
-- Roles
--

CREATE ROLE admin;
ALTER ROLE admin WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'md507029095ad3dae288fdb2112f39d189f';
CREATE ROLE dev;
ALTER ROLE dev WITH NOSUPERUSER INHERIT NOCREATEROLE CREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'md5be68960201fa79468470ddb9845ea2af';
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS PASSWORD 'md567d887f4b61d7c61fe46dbd6e09f5e84';






--
-- Databases
--

--
-- Database "template1" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 12.0
-- Dumped by pg_dump version 12.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

UPDATE pg_catalog.pg_database SET datistemplate = false WHERE datname = 'template1';
DROP DATABASE IF EXISTS template1;
--
-- Name: template1; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE template1 WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.utf8' LC_CTYPE = 'en_US.utf8';


ALTER DATABASE template1 OWNER TO postgres;

\connect template1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE template1 IS 'default template for new databases';


--
-- Name: template1; Type: DATABASE PROPERTIES; Schema: -; Owner: postgres
--

ALTER DATABASE template1 IS_TEMPLATE = true;


\connect template1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: DATABASE template1; Type: ACL; Schema: -; Owner: postgres
--

REVOKE CONNECT,TEMPORARY ON DATABASE template1 FROM PUBLIC;
GRANT CONNECT ON DATABASE template1 TO PUBLIC;


--
-- PostgreSQL database dump complete
--

--
-- Database "bookspot" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 12.0
-- Dumped by pg_dump version 12.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: bookspot; Type: DATABASE; Schema: -; Owner: admin
--

CREATE DATABASE bookspot WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.utf8' LC_CTYPE = 'en_US.utf8';


ALTER DATABASE bookspot OWNER TO admin;

\connect bookspot

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: catalog; Type: SCHEMA; Schema: -; Owner: admin
--

CREATE SCHEMA catalog;


ALTER SCHEMA catalog OWNER TO admin;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: authors; Type: TABLE; Schema: catalog; Owner: admin
--

CREATE TABLE catalog.authors (
    id bigint NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE catalog.authors OWNER TO admin;

--
-- Name: books; Type: TABLE; Schema: catalog; Owner: admin
--

CREATE TABLE catalog.books (
    id bigint NOT NULL,
    title character varying(255) NOT NULL,
    frontpage_url character varying(255),
    description character varying(255),
    rating numeric(6,3),
    isbn character varying(16),
    publication_date date,
    page_count smallint,
    language character(4),
    edition smallint,
    author_id bigint,
    category_id bigint,
    publisher_id bigint
);


ALTER TABLE catalog.books OWNER TO admin;

--
-- Name: categories; Type: TABLE; Schema: catalog; Owner: admin
--

CREATE TABLE catalog.categories (
    id bigint NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE catalog.categories OWNER TO admin;

--
-- Name: publishers; Type: TABLE; Schema: catalog; Owner: admin
--

CREATE TABLE catalog.publishers (
    id bigint NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE catalog.publishers OWNER TO admin;

--
-- Data for Name: authors; Type: TABLE DATA; Schema: catalog; Owner: admin
--

COPY catalog.authors (id, name) FROM stdin;
\.


--
-- Data for Name: books; Type: TABLE DATA; Schema: catalog; Owner: admin
--

COPY catalog.books (id, title, frontpage_url, description, rating, isbn, publication_date, page_count, language, edition, author_id, category_id, publisher_id) FROM stdin;
\.


--
-- Data for Name: categories; Type: TABLE DATA; Schema: catalog; Owner: admin
--

COPY catalog.categories (id, name) FROM stdin;
\.


--
-- Data for Name: publishers; Type: TABLE DATA; Schema: catalog; Owner: admin
--

COPY catalog.publishers (id, name) FROM stdin;
\.


--
-- Name: authors authors_pkey; Type: CONSTRAINT; Schema: catalog; Owner: admin
--

ALTER TABLE ONLY catalog.authors
    ADD CONSTRAINT authors_pkey PRIMARY KEY (id);


--
-- Name: books books_pkey; Type: CONSTRAINT; Schema: catalog; Owner: admin
--

ALTER TABLE ONLY catalog.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);


--
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: catalog; Owner: admin
--

ALTER TABLE ONLY catalog.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- Name: publishers publishers_pkey; Type: CONSTRAINT; Schema: catalog; Owner: admin
--

ALTER TABLE ONLY catalog.publishers
    ADD CONSTRAINT publishers_pkey PRIMARY KEY (id);


--
-- Name: books author_id; Type: FK CONSTRAINT; Schema: catalog; Owner: admin
--

ALTER TABLE ONLY catalog.books
    ADD CONSTRAINT author_id FOREIGN KEY (author_id) REFERENCES catalog.authors(id) NOT VALID;


--
-- Name: books category_id; Type: FK CONSTRAINT; Schema: catalog; Owner: admin
--

ALTER TABLE ONLY catalog.books
    ADD CONSTRAINT category_id FOREIGN KEY (category_id) REFERENCES catalog.categories(id) NOT VALID;


--
-- Name: books publisher_id; Type: FK CONSTRAINT; Schema: catalog; Owner: admin
--

ALTER TABLE ONLY catalog.books
    ADD CONSTRAINT publisher_id FOREIGN KEY (publisher_id) REFERENCES catalog.publishers(id) NOT VALID;


--
-- Name: SCHEMA catalog; Type: ACL; Schema: -; Owner: admin
--

GRANT ALL ON SCHEMA catalog TO dev;


--
-- Name: TABLE authors; Type: ACL; Schema: catalog; Owner: admin
--

GRANT ALL ON TABLE catalog.authors TO dev;


--
-- Name: TABLE books; Type: ACL; Schema: catalog; Owner: admin
--

GRANT ALL ON TABLE catalog.books TO dev;


--
-- Name: TABLE categories; Type: ACL; Schema: catalog; Owner: admin
--

GRANT ALL ON TABLE catalog.categories TO dev;


--
-- Name: TABLE publishers; Type: ACL; Schema: catalog; Owner: admin
--

GRANT ALL ON TABLE catalog.publishers TO dev;

--
-- PostgreSQL database cluster dump complete
--

