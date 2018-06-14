-- ================================================================================
-- PROCESS
-- ================================================================================

insert into process(code) values ('SECURITY');
insert into process(code, parent_process_id) values ('AUTHENTICATION', (select id from process where code = 'SECURITY'));
insert into process(code, parent_process_id) values ('LOCKOUT_POLICY', (select id from process where code = 'SECURITY'));

-- ================================================================================
-- PARAMETER
-- ================================================================================

insert into parameter (code, data_type_enum, value, value_source_type_enum, unit_of_measurement_enum, process_id)
values ('MINIMUM_PASSWORD_LENGTH', 'java.lang.Integer', '7', 'SIMPLE_VALUE', 'CHARACTER', (select id from process where code = 'AUTHENTICATION'));

insert into parameter_constraint (id, min_amount)
values ((select id from parameter where code = 'MINIMUM_PASSWORD_LENGTH'), 7);

insert into parameter (code, data_type_enum, value, value_source_type_enum, unit_of_measurement_enum, process_id)
values ('ENFORCE_PASSWORD_HISTORY', 'java.lang.Integer', '5', 'SIMPLE_VALUE', 'QUANTITY', (select id from process where code = 'AUTHENTICATION'));

insert into parameter_constraint (id, min_amount, max_amount)
values ((select id from parameter where code = 'ENFORCE_PASSWORD_HISTORY'), 1, 10);

insert into parameter (code, data_type_enum, value, value_source_type_enum, unit_of_measurement_enum, process_id)
values ('PASSWORD_EXPIRATION', 'java.lang.Integer', '6', 'SIMPLE_VALUE', 'MONTH', (select id from process where code = 'LOCKOUT_POLICY'));

insert into parameter_constraint (id, min_amount, max_amount)
values ((select id from parameter where code = 'PASSWORD_EXPIRATION'), 6, 12);

insert into parameter (code, data_type_enum, value, value_source_type_enum, unit_of_measurement_enum, process_id)
values ('MAXIMUM_DAYS_WITHOUT_USER_ACTIVITY', 'java.lang.Integer', '30', 'SIMPLE_VALUE', 'DAY', (select id from process where code = 'LOCKOUT_POLICY'));

insert into parameter_constraint (id, min_amount, max_amount)
values ((select id from parameter where code = 'MAXIMUM_DAYS_WITHOUT_USER_ACTIVITY'), 7, 60);

insert into parameter (code, data_type_enum, value, value_source_type_enum, unit_of_measurement_enum, process_id)
values ('MAXIMUM_DAYS_WITHOUT_RESETTING_PASWORD', 'java.lang.Integer', '7', 'SIMPLE_VALUE', 'DAY', (select id from process where code = 'LOCKOUT_POLICY'));

insert into parameter_constraint (id, min_amount, max_amount)
values ((select id from parameter where code = 'MAXIMUM_DAYS_WITHOUT_RESETTING_PASWORD'), 1, 30);

insert into parameter (code, data_type_enum, value, value_source_type_enum, unit_of_measurement_enum, process_id)
values ('MAXIMUM_FAILED_LOGIN_ATTEMPTS', 'java.lang.Integer', '5', 'SIMPLE_VALUE', 'N_TIMES', (select id from process where code = 'LOCKOUT_POLICY'));

insert into parameter_constraint (id, min_amount, max_amount)
values ((select id from parameter where code = 'MAXIMUM_FAILED_LOGIN_ATTEMPTS'), 3, 10);
