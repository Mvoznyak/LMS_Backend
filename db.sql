--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

-- Started on 2024-01-12 21:25:25

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 203 (class 1259 OID 16507)
-- Name: academic_group; Type: TABLE; Schema: public; Owner: lms
--

CREATE TABLE public.academic_group (
    id uuid NOT NULL,
    name character varying(50) NOT NULL,
    faculty character varying(50) NOT NULL,
    study_year smallint NOT NULL
);


ALTER TABLE public.academic_group OWNER TO lms;

--
-- TOC entry 204 (class 1259 OID 16514)
-- Name: course; Type: TABLE; Schema: public; Owner: lms
--

CREATE TABLE public.course (
    id uuid NOT NULL,
    name character varying(50) NOT NULL,
    description text NOT NULL
);


ALTER TABLE public.course OWNER TO lms;

--
-- TOC entry 208 (class 1259 OID 16569)
-- Name: course_material; Type: TABLE; Schema: public; Owner: lms
--

CREATE TABLE public.course_material (
    id uuid NOT NULL,
    course_id uuid NOT NULL,
    name character varying(50) NOT NULL,
    material text NOT NULL,
    created_on timestamp without time zone NOT NULL
);


ALTER TABLE public.course_material OWNER TO lms;

--
-- TOC entry 209 (class 1259 OID 16598)
-- Name: course_moderator; Type: TABLE; Schema: public; Owner: lms
--

CREATE TABLE public.course_moderator (
    course_id uuid NOT NULL,
    moderator_id uuid NOT NULL
);


ALTER TABLE public.course_moderator OWNER TO lms;

--
-- TOC entry 207 (class 1259 OID 16552)
-- Name: course_teacher; Type: TABLE; Schema: public; Owner: lms
--

CREATE TABLE public.course_teacher (
    course_id uuid NOT NULL,
    teacher_id uuid NOT NULL
);


ALTER TABLE public.course_teacher OWNER TO lms;

--
-- TOC entry 202 (class 1259 OID 16497)
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: lms
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO lms;

--
-- TOC entry 205 (class 1259 OID 16524)
-- Name: group_course; Type: TABLE; Schema: public; Owner: lms
--

CREATE TABLE public.group_course (
    group_id uuid NOT NULL,
    course_id uuid NOT NULL
);


ALTER TABLE public.group_course OWNER TO lms;

--
-- TOC entry 210 (class 1259 OID 16614)
-- Name: homework; Type: TABLE; Schema: public; Owner: lms
--

CREATE TABLE public.homework (
    id uuid NOT NULL,
    course_id uuid,
    name character varying(50) NOT NULL,
    description text NOT NULL,
    start_time timestamp without time zone NOT NULL,
    end_time timestamp without time zone NOT NULL
);


ALTER TABLE public.homework OWNER TO lms;

--
-- TOC entry 212 (class 1259 OID 16667)
-- Name: homework_submission; Type: TABLE; Schema: public; Owner: lms
--

CREATE TABLE public.homework_submission (
    homework_id uuid NOT NULL,
    student_id uuid NOT NULL,
    send_time timestamp without time zone NOT NULL,
    submission text NOT NULL
);


ALTER TABLE public.homework_submission OWNER TO lms;

--
-- TOC entry 211 (class 1259 OID 16629)
-- Name: student; Type: TABLE; Schema: public; Owner: lms
--

CREATE TABLE public.student (
    id uuid NOT NULL,
    group_id uuid NOT NULL,
    admission_year smallint NOT NULL,
    degree character varying(50) NOT NULL,
    form character varying(50) NOT NULL,
    basis character varying(50) NOT NULL,
    CONSTRAINT chk_basis CHECK (((basis)::text = ANY ((ARRAY['контрактная'::character varying, 'бюджетная'::character varying])::text[]))),
    CONSTRAINT chk_degree CHECK (((degree)::text = ANY ((ARRAY['бакалавр'::character varying, 'магистр'::character varying, 'специалист'::character varying])::text[]))),
    CONSTRAINT chk_form CHECK (((form)::text = ANY ((ARRAY['очная'::character varying, 'заочная'::character varying, 'вечерняя'::character varying])::text[])))
);


ALTER TABLE public.student OWNER TO lms;

--
-- TOC entry 206 (class 1259 OID 16540)
-- Name: user_account; Type: TABLE; Schema: public; Owner: lms
--

CREATE TABLE public.user_account (
    id uuid NOT NULL,
    verification_code character varying(50) DEFAULT md5((random())::text) NOT NULL,
    email character varying(350),
    password_hash character varying(350),
    first_name character varying(50),
    middle_name character varying(50),
    last_name character varying(50),
    phone_number character varying(50),
    city character varying(50),
    info text,
    vk_link character varying(350),
    facebook_link character varying(350),
    linkedin_link character varying(350),
    instagram_link character varying(350)
);


ALTER TABLE public.user_account OWNER TO lms;

--
-- TOC entry 2914 (class 0 OID 16507)
-- Dependencies: 203
-- Data for Name: academic_group; Type: TABLE DATA; Schema: public; Owner: lms
--

COPY public.academic_group (id, name, faculty, study_year) FROM stdin;
c2c03e3f-eaa2-492f-9544-9837f5425cba	М05-916	ФПМИ	1
5a7646af-8e3c-4e54-9a0b-d10dc032b62b	М05-911	ФПМИ	1
\.


--
-- TOC entry 2915 (class 0 OID 16514)
-- Dependencies: 204
-- Data for Name: course; Type: TABLE DATA; Schema: public; Owner: lms
--

COPY public.course (id, name, description) FROM stdin;
42b39fbf-5dd4-4871-99ef-b6b452db4893	Архитектура ПО	Архитектура, дизайн и процесс разработки ПО
1a423fb0-72bf-4e99-96b7-a362f1e58074	Алгебра и алгоритмы	Алгоритмы и алгебра
0af8bfba-9c09-4b17-91ee-fe8c9aa316d2	Машинное обучение	Машинка
\.


--
-- TOC entry 2919 (class 0 OID 16569)
-- Dependencies: 208
-- Data for Name: course_material; Type: TABLE DATA; Schema: public; Owner: lms
--

COPY public.course_material (id, course_id, name, material, created_on) FROM stdin;
\.


--
-- TOC entry 2920 (class 0 OID 16598)
-- Dependencies: 209
-- Data for Name: course_moderator; Type: TABLE DATA; Schema: public; Owner: lms
--

COPY public.course_moderator (course_id, moderator_id) FROM stdin;
\.


--
-- TOC entry 2918 (class 0 OID 16552)
-- Dependencies: 207
-- Data for Name: course_teacher; Type: TABLE DATA; Schema: public; Owner: lms
--

COPY public.course_teacher (course_id, teacher_id) FROM stdin;
1a423fb0-72bf-4e99-96b7-a362f1e58074	02ef7688-6e87-40f6-9f6b-d01d304a6e37
\.


--
-- TOC entry 2913 (class 0 OID 16497)
-- Dependencies: 202
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: lms
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	0.0.1.01	Add academic group table	SQL	V0.0.1.01__Add_academic_group_table.sql	-352979991	lms	2024-01-09 17:12:24.717984	95	t
2	0.0.1.02	Add course table	SQL	V0.0.1.02__Add_course_table.sql	-1041534060	lms	2024-01-09 17:12:24.823687	107	t
3	0.0.1.03	Add group course table	SQL	V0.0.1.03__Add_group_course_table.sql	1917274805	lms	2024-01-09 17:12:24.939232	103	t
4	0.0.1.04	Add user account table	SQL	V0.0.1.04__Add_user_account_table.sql	-2090148068	lms	2024-01-09 17:12:25.050496	170	t
5	0.0.1.05	Add course teacher table	SQL	V0.0.1.05__Add_course_teacher_table.sql	1086501152	lms	2024-01-09 17:12:25.228973	108	t
6	0.0.1.06	Add course material table	SQL	V0.0.1.06__Add_course_material_table.sql	1870992625	lms	2024-01-09 17:12:25.344065	462	t
7	0.0.1.07	Add course moderator table	SQL	V0.0.1.07__Add_course_moderator_table.sql	1955910932	lms	2024-01-09 17:13:01.176511	95	t
8	0.0.1.08	Add homework table	SQL	V0.0.1.08__Add_homework_table.sql	1670521758	lms	2024-01-09 17:13:01.280746	184	t
9	0.0.1.09	Add student table	SQL	V0.0.1.09__Add_student_table.sql	1613204183	lms	2024-01-09 17:13:01.472604	54	t
10	0.0.1.10	Add homework submission table	SQL	V0.0.1.10__Add_homework_submission_table.sql	1472665127	lms	2024-01-12 14:16:53.665381	147	t
11	0.0.1.11	Add student constraint	SQL	V0.0.1.11__Add_student_constraint.sql	508329991	lms	2024-01-12 14:17:44.548303	37	t
\.


--
-- TOC entry 2916 (class 0 OID 16524)
-- Dependencies: 205
-- Data for Name: group_course; Type: TABLE DATA; Schema: public; Owner: lms
--

COPY public.group_course (group_id, course_id) FROM stdin;
c2c03e3f-eaa2-492f-9544-9837f5425cba	42b39fbf-5dd4-4871-99ef-b6b452db4893
c2c03e3f-eaa2-492f-9544-9837f5425cba	1a423fb0-72bf-4e99-96b7-a362f1e58074
5a7646af-8e3c-4e54-9a0b-d10dc032b62b	0af8bfba-9c09-4b17-91ee-fe8c9aa316d2
\.


--
-- TOC entry 2921 (class 0 OID 16614)
-- Dependencies: 210
-- Data for Name: homework; Type: TABLE DATA; Schema: public; Owner: lms
--

COPY public.homework (id, course_id, name, description, start_time, end_time) FROM stdin;
\.


--
-- TOC entry 2923 (class 0 OID 16667)
-- Dependencies: 212
-- Data for Name: homework_submission; Type: TABLE DATA; Schema: public; Owner: lms
--

COPY public.homework_submission (homework_id, student_id, send_time, submission) FROM stdin;
\.


--
-- TOC entry 2922 (class 0 OID 16629)
-- Dependencies: 211
-- Data for Name: student; Type: TABLE DATA; Schema: public; Owner: lms
--

COPY public.student (id, group_id, admission_year, degree, form, basis) FROM stdin;
48b4c5c7-b60a-4bad-b403-ca31395b11e9	c2c03e3f-eaa2-492f-9544-9837f5425cba	2019	магистр	очная	бюджетная
0d36407b-4dc6-4430-8184-37a81ab45416	c2c03e3f-eaa2-492f-9544-9837f5425cba	2019	магистр	очная	бюджетная
dad784c9-5ffc-4f15-93ad-8d2264ab73dc	5a7646af-8e3c-4e54-9a0b-d10dc032b62b	2019	магистр	очная	бюджетная
\.


--
-- TOC entry 2917 (class 0 OID 16540)
-- Dependencies: 206
-- Data for Name: user_account; Type: TABLE DATA; Schema: public; Owner: lms
--

COPY public.user_account (id, verification_code, email, password_hash, first_name, middle_name, last_name, phone_number, city, info, vk_link, facebook_link, linkedin_link, instagram_link) FROM stdin;
cad4af03-183e-426f-9bf5-80189da3e89c	1312c60a34d2f1963c3d9db3b9bb52b0	\N	\N	Артур	\N	Хашаев	\N	\N	\N	\N	\N	\N	\N
0d36407b-4dc6-4430-8184-37a81ab45416	889f73d21e012174907f379dc4b043f7	\N	\N	Александр	\N	Александров	\N	\N	\N	\N	\N	\N	\N
dad784c9-5ffc-4f15-93ad-8d2264ab73dc	821165671d1a0c88095bc773a2603adc	\N	\N	Сергей	\N	Сергеев	\N	\N	\N	\N	\N	\N	\N
48b4c5c7-b60a-4bad-b403-ca31395b11e9	1106a21944db66aacf42b006086aa4e3	test@test.com	ef990b85e026d67e39e25c5b287f696	Иван	Иванович	Иванов	\N	\N	\N	\N	\N	\N	\N
02ef7688-6e87-40f6-9f6b-d01d304a6e37	753f4990f48a8ad3b9cb00a5e55f7a66	teacher@test.com	ef990b85e026d67e39e25c5b287f696	Учитель	\N	Тестовый	\N	\N	\N	\N	\N	\N	\N
\.


--
-- TOC entry 2740 (class 2606 OID 16513)
-- Name: academic_group academic_group_name_key; Type: CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.academic_group
    ADD CONSTRAINT academic_group_name_key UNIQUE (name);


--
-- TOC entry 2742 (class 2606 OID 16511)
-- Name: academic_group academic_group_pkey; Type: CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.academic_group
    ADD CONSTRAINT academic_group_pkey PRIMARY KEY (id);


--
-- TOC entry 2760 (class 2606 OID 16576)
-- Name: course_material course_material_pkey; Type: CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.course_material
    ADD CONSTRAINT course_material_pkey PRIMARY KEY (id);


--
-- TOC entry 2763 (class 2606 OID 16602)
-- Name: course_moderator course_moderator_pkey; Type: CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.course_moderator
    ADD CONSTRAINT course_moderator_pkey PRIMARY KEY (course_id, moderator_id);


--
-- TOC entry 2744 (class 2606 OID 16523)
-- Name: course course_name_key; Type: CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.course
    ADD CONSTRAINT course_name_key UNIQUE (name);


--
-- TOC entry 2746 (class 2606 OID 16521)
-- Name: course course_pkey; Type: CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.course
    ADD CONSTRAINT course_pkey PRIMARY KEY (id);


--
-- TOC entry 2757 (class 2606 OID 16556)
-- Name: course_teacher course_teacher_pkey; Type: CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.course_teacher
    ADD CONSTRAINT course_teacher_pkey PRIMARY KEY (course_id, teacher_id);


--
-- TOC entry 2737 (class 2606 OID 16505)
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- TOC entry 2748 (class 2606 OID 16528)
-- Name: group_course group_course_pkey; Type: CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.group_course
    ADD CONSTRAINT group_course_pkey PRIMARY KEY (group_id, course_id);


--
-- TOC entry 2767 (class 2606 OID 16621)
-- Name: homework homework_pkey; Type: CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.homework
    ADD CONSTRAINT homework_pkey PRIMARY KEY (id);


--
-- TOC entry 2773 (class 2606 OID 16674)
-- Name: homework_submission homework_submission_pkey; Type: CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.homework_submission
    ADD CONSTRAINT homework_submission_pkey PRIMARY KEY (homework_id, student_id);


--
-- TOC entry 2770 (class 2606 OID 16633)
-- Name: student student_pkey; Type: CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_pkey PRIMARY KEY (id);


--
-- TOC entry 2752 (class 2606 OID 16550)
-- Name: user_account user_account_email_key; Type: CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.user_account
    ADD CONSTRAINT user_account_email_key UNIQUE (email);


--
-- TOC entry 2754 (class 2606 OID 16548)
-- Name: user_account user_account_pkey; Type: CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.user_account
    ADD CONSTRAINT user_account_pkey PRIMARY KEY (id);


--
-- TOC entry 2755 (class 1259 OID 16567)
-- Name: course_id_idx; Type: INDEX; Schema: public; Owner: lms
--

CREATE INDEX course_id_idx ON public.course_teacher USING btree (course_id);


--
-- TOC entry 2761 (class 1259 OID 16582)
-- Name: created_on_idx; Type: INDEX; Schema: public; Owner: lms
--

CREATE INDEX created_on_idx ON public.course_material USING btree (created_on);


--
-- TOC entry 2750 (class 1259 OID 16551)
-- Name: email_idx; Type: INDEX; Schema: public; Owner: lms
--

CREATE INDEX email_idx ON public.user_account USING btree (email);


--
-- TOC entry 2765 (class 1259 OID 16628)
-- Name: end_time_idx; Type: INDEX; Schema: public; Owner: lms
--

CREATE INDEX end_time_idx ON public.homework USING btree (end_time);


--
-- TOC entry 2738 (class 1259 OID 16506)
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: lms
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- TOC entry 2749 (class 1259 OID 16539)
-- Name: group_id_idx; Type: INDEX; Schema: public; Owner: lms
--

CREATE INDEX group_id_idx ON public.group_course USING btree (group_id);


--
-- TOC entry 2771 (class 1259 OID 16685)
-- Name: homework_id_idx; Type: INDEX; Schema: public; Owner: lms
--

CREATE INDEX homework_id_idx ON public.homework_submission USING btree (homework_id);


--
-- TOC entry 2764 (class 1259 OID 16613)
-- Name: moderator_id_idx; Type: INDEX; Schema: public; Owner: lms
--

CREATE INDEX moderator_id_idx ON public.course_moderator USING btree (moderator_id);


--
-- TOC entry 2768 (class 1259 OID 16627)
-- Name: start_time_idx; Type: INDEX; Schema: public; Owner: lms
--

CREATE INDEX start_time_idx ON public.homework USING btree (start_time);


--
-- TOC entry 2774 (class 1259 OID 16686)
-- Name: student_id_idx; Type: INDEX; Schema: public; Owner: lms
--

CREATE INDEX student_id_idx ON public.homework_submission USING btree (student_id);


--
-- TOC entry 2758 (class 1259 OID 16568)
-- Name: teacher_id_idx; Type: INDEX; Schema: public; Owner: lms
--

CREATE INDEX teacher_id_idx ON public.course_teacher USING btree (teacher_id);


--
-- TOC entry 2779 (class 2606 OID 16577)
-- Name: course_material course_material_course_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.course_material
    ADD CONSTRAINT course_material_course_id_fkey FOREIGN KEY (course_id) REFERENCES public.course(id);


--
-- TOC entry 2780 (class 2606 OID 16603)
-- Name: course_moderator course_moderator_course_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.course_moderator
    ADD CONSTRAINT course_moderator_course_id_fkey FOREIGN KEY (course_id) REFERENCES public.course(id);


--
-- TOC entry 2781 (class 2606 OID 16608)
-- Name: course_moderator course_moderator_moderator_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.course_moderator
    ADD CONSTRAINT course_moderator_moderator_id_fkey FOREIGN KEY (moderator_id) REFERENCES public.user_account(id);


--
-- TOC entry 2777 (class 2606 OID 16557)
-- Name: course_teacher course_teacher_course_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.course_teacher
    ADD CONSTRAINT course_teacher_course_id_fkey FOREIGN KEY (course_id) REFERENCES public.course(id);


--
-- TOC entry 2778 (class 2606 OID 16562)
-- Name: course_teacher course_teacher_teacher_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.course_teacher
    ADD CONSTRAINT course_teacher_teacher_id_fkey FOREIGN KEY (teacher_id) REFERENCES public.user_account(id);


--
-- TOC entry 2776 (class 2606 OID 16534)
-- Name: group_course group_course_course_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.group_course
    ADD CONSTRAINT group_course_course_id_fkey FOREIGN KEY (course_id) REFERENCES public.course(id);


--
-- TOC entry 2775 (class 2606 OID 16529)
-- Name: group_course group_course_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.group_course
    ADD CONSTRAINT group_course_group_id_fkey FOREIGN KEY (group_id) REFERENCES public.academic_group(id);


--
-- TOC entry 2782 (class 2606 OID 16622)
-- Name: homework homework_course_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.homework
    ADD CONSTRAINT homework_course_id_fkey FOREIGN KEY (course_id) REFERENCES public.course(id);


--
-- TOC entry 2785 (class 2606 OID 16675)
-- Name: homework_submission homework_submission_homework_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.homework_submission
    ADD CONSTRAINT homework_submission_homework_id_fkey FOREIGN KEY (homework_id) REFERENCES public.homework(id);


--
-- TOC entry 2786 (class 2606 OID 16680)
-- Name: homework_submission homework_submission_student_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.homework_submission
    ADD CONSTRAINT homework_submission_student_id_fkey FOREIGN KEY (student_id) REFERENCES public.user_account(id);


--
-- TOC entry 2784 (class 2606 OID 16639)
-- Name: student student_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_group_id_fkey FOREIGN KEY (group_id) REFERENCES public.academic_group(id);


--
-- TOC entry 2783 (class 2606 OID 16634)
-- Name: student student_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lms
--

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_id_fkey FOREIGN KEY (id) REFERENCES public.user_account(id);


-- Completed on 2024-01-12 21:25:26

--
-- PostgreSQL database dump complete
--

