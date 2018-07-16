-- ================================================================================
-- CREATE DEFAULT SUPER USER
-- ================================================================================

insert into security.application_user (
    login
    , fullname
    , password
    , is_super_user
	, created_by)
values (
	'fresco'
	, 'Fresco'
	, 'Fresco2018!'
	, true
	, 1
	);


-- ================================================================================
-- PARAMETER
-- ================================================================================

insert into parameter (code, data_type_enum, value, value_source_type_enum, unit_of_measurement_enum, business_process_type_enum, created_by)
values ('MINIMUM_PASSWORD_LENGTH', 'java.lang.Integer', '7', 'SIMPLE_VALUE', 'CHARACTER', 'AUTHENTICATION', 1);

insert into parameter_constraint (id, min_amount)
values ((select id from parameter where code = 'MINIMUM_PASSWORD_LENGTH'), 7);

insert into parameter (code, data_type_enum, value, value_source_type_enum, unit_of_measurement_enum, business_process_type_enum, created_by)
values ('ENFORCE_PASSWORD_HISTORY', 'java.lang.Integer', '5', 'SIMPLE_VALUE', 'QUANTITY', 'AUTHENTICATION', 1);

insert into parameter_constraint (id, min_amount, max_amount)
values ((select id from parameter where code = 'ENFORCE_PASSWORD_HISTORY'), 1, 10);

insert into parameter (code, data_type_enum, value, value_source_type_enum, unit_of_measurement_enum, business_process_type_enum, created_by)
values ('PASSWORD_EXPIRATION', 'java.lang.Integer', '6', 'SIMPLE_VALUE', 'MONTH', 'LOCKOUT_POLICY', 1);

insert into parameter_constraint (id, min_amount, max_amount)
values ((select id from parameter where code = 'PASSWORD_EXPIRATION'), 6, 12);

insert into parameter (code, data_type_enum, value, value_source_type_enum, unit_of_measurement_enum, business_process_type_enum, created_by)
values ('MAXIMUM_DAYS_WITHOUT_USER_ACTIVITY', 'java.lang.Integer', '30', 'SIMPLE_VALUE', 'DAY', 'LOCKOUT_POLICY', 1);

insert into parameter_constraint (id, min_amount, max_amount)
values ((select id from parameter where code = 'MAXIMUM_DAYS_WITHOUT_USER_ACTIVITY'), 7, 60);

insert into parameter (code, data_type_enum, value, value_source_type_enum, unit_of_measurement_enum, business_process_type_enum, created_by)
values ('MAXIMUM_DAYS_WITHOUT_RESETTING_PASWORD', 'java.lang.Integer', '7', 'SIMPLE_VALUE', 'DAY', 'LOCKOUT_POLICY', 1);

insert into parameter_constraint (id, min_amount, max_amount)
values ((select id from parameter where code = 'MAXIMUM_DAYS_WITHOUT_RESETTING_PASWORD'), 1, 30);

insert into parameter (code, data_type_enum, value, value_source_type_enum, unit_of_measurement_enum, business_process_type_enum, created_by)
values ('MAXIMUM_FAILED_LOGIN_ATTEMPTS', 'java.lang.Integer', '5', 'SIMPLE_VALUE', 'N_TIMES', 'LOCKOUT_POLICY', 1);

insert into parameter_constraint (id, min_amount, max_amount)
values ((select id from parameter where code = 'MAXIMUM_FAILED_LOGIN_ATTEMPTS'), 3, 10);
