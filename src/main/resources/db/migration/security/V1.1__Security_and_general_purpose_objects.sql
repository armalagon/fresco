-- *******************************************************************************
-- PUBLIC
-- *******************************************************************************
drop table if exists parameter cascade;
drop table if exists parameter_constraint cascade;
drop table if exists parameter_source cascade;


create table parameter (
    id serial not null
    , code character varying(85) not null
    , data_type_enum character varying(100) not null -- java data types
    , value character varying(400)
    , value_source_type_enum character varying(15) not null -- simple value, model (table or enum)
	, unit_of_measurement_enum character varying(15) not null
    , business_process_type_enum character varying(15) not null
    , constraint parameter_pk primary key (id)
    , constraint parameter_code_uq unique (code)
);

create table parameter_constraint (
    id integer not null
    , min_amount bigint
    , max_amount bigint
    , min_date date
    , max_date date
    , min_total numeric(18, 2)
    , max_total numeric(18, 2)
    , constraint parameter_constraint_pk primary key (id)
    , constraint parameter_constraint_parameter_fk foreign key (id) references parameter (id)
);

create table parameter_source (
    id serial not null
	, code character varying(85) not null -- source value identification. When a query is being used as a source: This allows to identify the query by name
    , parameter_id integer not null
    , fully_qualified_classname character varying(500) -- options: enum, domain class
    , query character varying(800) -- must return always two columns: id, description. Consider parameter sustitution
	, sequence_number smallint not null
    , constraint parameter_source_pk primary key (id)
	, constraint parameter_source_uq unique (parameter_id, sequence_number)
	, constraint parameter_source_code_uq unique (code)
    , constraint parameter_source_parameter_fk foreign key (parameter_id) references parameter (id)
    , constraint parameter_source_sequence_number_ck check (sequence_number > 0)
    , constraint parameter_source_source_ck check ((fully_qualified_classname is null and query is not null) or
		(fully_qualified_classname is not null and query is null))
);

-- *******************************************************************************
-- SECURITY
-- *******************************************************************************
drop schema if exists security cascade;
create schema security;

set search_path to security;

create table application_user (
    id serial not null
    , login character varying(15) not null
    , fullname character varying(40) not null
    , email character varying(15)
    , password character varying(200) not null
    , secret_question_enum character varying(50) -- for recovery purposes
    , secret_answer character varying(200)
    , failed_login_attempts smallint not null default 0 -- reset this value every 15 minutes
	, first_failed_login_attempt_date timestamp(3) without time zone
    , is_active boolean not null default true
    , is_locked boolean not null default false
    , is_super_user boolean not null default false
    , last_login_date timestamp(3) without time zone
	, credential_expire boolean not null default false
	, credential_expiration_date timestamp(3) without time zone
	, created_on timestamp(3) without time zone not null default now()
	, created_by integer not null
	, updated_on timestamp(3) without time zone
	, updated_by integer
    , constraint application_user_pk primary key (id)
    , constraint application_user_login_uq unique (login)
    , constraint application_user_failed_login_attempts_ck check (failed_login_attempts >= 0)
    , constraint application_user_secret_question_ck check ((secret_question_enum is null and secret_answer is null) or (secret_question_enum is not null and secret_answer is not null))
);

create table locked_user (
	id bigserial not null
	, locked_type_enum character varying(50) not null
	, locked_user_id integer not null
    , start_date timestamp(3) without time zone
    , end_date timestamp(3) without time zone
    , is_active boolean not null default true
	, created_on timestamp(3) without time zone not null default now()
	, created_by integer not null
	, updated_on timestamp(3) without time zone
	, updated_by integer
	, constraint lock_user_pk primary key (id)
    , constraint locked_user_fk foreign key (locked_user_id) references application_user (id)
	, constraint locked_user_created_by_fk foreign key (created_by) references application_user (id)
	, constraint locked_user_valid_date_ck check ((start_date is null and end_date is null) or (start_date <= end_date))
);

create table password_history (
	id bigserial not null
    , user_id integer not null
    , old_password character varying(200) not null
	, created_on timestamp(3) without time zone not null default now()
	, created_by integer not null
    , constraint password_history_pk primary key (id)
	, constraint password_history_user_fk foreign key (user_id) references application_user (id)
);

create table user_activity (
	id bigserial not null
	, user_id integer not null
	, session_id character varying(300) not null
	, logged_in timestamp(3) without time zone not null
	, logged_out timestamp(3) without time zone
	, is_active boolean not null default true
	, created_on timestamp(3) without time zone not null default now()
	, created_by integer not null
	, constraint user_activity_pk primary key (id)
	, constraint user_activity_user_fk foreign key (user_id) references application_user (id)
);

create table user_activity_detail (
	id bigserial not null
	, user_activity_id bigint not null
	, url character varying(400)
	, action_executed character varying(200)
	, parent_user_activity_detail_id bigint
	, created_on timestamp(3) without time zone not null default now()
	, created_by integer not null
	, constraint user_activity_detail_pk primary key (id)
	, constraint user_activity_fk foreign key (user_activity_id) references user_activity (id)
	, constraint user_activity_detail_parent_fk foreign key (parent_user_activity_detail_id) references user_activity_detail (id)
);
