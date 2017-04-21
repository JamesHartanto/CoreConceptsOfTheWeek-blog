--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.2
-- Dumped by pg_dump version 9.6.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: blogs; Type: TABLE; Schema: public; Owner: JamesHartanto
--

CREATE TABLE blogs (
    id integer NOT NULL,
    person_id integer NOT NULL,
    blog character varying(500) NOT NULL
);


ALTER TABLE blogs OWNER TO "JamesHartanto";

--
-- Name: blogs_id_seq; Type: SEQUENCE; Schema: public; Owner: JamesHartanto
--

CREATE SEQUENCE blogs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE blogs_id_seq OWNER TO "JamesHartanto";

--
-- Name: blogs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: JamesHartanto
--

ALTER SEQUENCE blogs_id_seq OWNED BY blogs.id;


--
-- Name: person; Type: TABLE; Schema: public; Owner: JamesHartanto
--

CREATE TABLE person (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(50) NOT NULL
);


ALTER TABLE person OWNER TO "JamesHartanto";

--
-- Name: person_id_seq; Type: SEQUENCE; Schema: public; Owner: JamesHartanto
--

CREATE SEQUENCE person_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE person_id_seq OWNER TO "JamesHartanto";

--
-- Name: person_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: JamesHartanto
--

ALTER SEQUENCE person_id_seq OWNED BY person.id;


--
-- Name: blogs id; Type: DEFAULT; Schema: public; Owner: JamesHartanto
--

ALTER TABLE ONLY blogs ALTER COLUMN id SET DEFAULT nextval('blogs_id_seq'::regclass);


--
-- Name: person id; Type: DEFAULT; Schema: public; Owner: JamesHartanto
--

ALTER TABLE ONLY person ALTER COLUMN id SET DEFAULT nextval('person_id_seq'::regclass);


--
-- Data for Name: blogs; Type: TABLE DATA; Schema: public; Owner: JamesHartanto
--

COPY blogs (id, person_id, blog) FROM stdin;
\.


--
-- Name: blogs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: JamesHartanto
--

SELECT pg_catalog.setval('blogs_id_seq', 1, false);


--
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: JamesHartanto
--

COPY person (id, username, password) FROM stdin;
1	james	james
2	bob	what
13	what	hello
15	huh	what
17	help	why
12	stop 	hello
21	Rocket	Raccoon
22	toilet	seat
23	Hawaii	Moana
24	KimJongUn	NKorea
26	Rashford	manu
27	blind	holland
28	De Gea	spain
\.


--
-- Name: person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: JamesHartanto
--

SELECT pg_catalog.setval('person_id_seq', 28, true);


--
-- Name: blogs blogs_pkey; Type: CONSTRAINT; Schema: public; Owner: JamesHartanto
--

ALTER TABLE ONLY blogs
    ADD CONSTRAINT blogs_pkey PRIMARY KEY (id);


--
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: JamesHartanto
--

ALTER TABLE ONLY person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- Name: blogs_id_uindex; Type: INDEX; Schema: public; Owner: JamesHartanto
--

CREATE UNIQUE INDEX blogs_id_uindex ON blogs USING btree (id);


--
-- Name: person_id_uindex; Type: INDEX; Schema: public; Owner: JamesHartanto
--

CREATE UNIQUE INDEX person_id_uindex ON person USING btree (id);


--
-- Name: person_username_uindex; Type: INDEX; Schema: public; Owner: JamesHartanto
--

CREATE UNIQUE INDEX person_username_uindex ON person USING btree (username);


--
-- Name: blogs blogs_person_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: JamesHartanto
--

ALTER TABLE ONLY blogs
    ADD CONSTRAINT blogs_person_id_fk FOREIGN KEY (person_id) REFERENCES person(id);


--
-- PostgreSQL database dump complete
--

