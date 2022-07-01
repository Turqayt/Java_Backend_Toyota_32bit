-- mobile."role" definition

-- Drop table

-- DROP TABLE mobile."role";

CREATE TABLE mobile."role" (
	id serial4 NOT NULL,
	role_name varchar(255) NULL,
	short_name varchar(255) NULL,
	CONSTRAINT role_pkey PRIMARY KEY (id)
);


-- mobile."user" definition

-- Drop table

-- DROP TABLE mobile."user";

CREATE TABLE mobile."user" (
	id serial4 NOT NULL,
	address varchar(255) NULL,
	company_name varchar(255) NULL,
	created_at timestamp NULL,
	created_by varchar(255) NULL,
	email varchar(255) NULL,
	"name" varchar(255) NULL,
	occupation varchar(255) NULL,
	"password" varchar(255) NULL,
	phone_number varchar(255) NULL,
	status int2 NULL,
	surname varchar(255) NULL,
	tc_number varchar(255) NULL,
	updated_at timestamp NULL,
	updated_by varchar(255) NULL,
	user_name varchar(255) NULL,
	reset_key varchar(255) NULL,
	is_admin int2 NULL,
	CONSTRAINT uklqjrcobrh9jc8wpcar64q1bfh UNIQUE (user_name),
	CONSTRAINT ukob8kqyqqgmefl0aco34akdtpe UNIQUE (email),
	CONSTRAINT user_pkey PRIMARY KEY (id)
);
CREATE INDEX user_lower_idx ON mobile."user" USING btree (lower((name)::text));
CREATE INDEX user_lower_idx1 ON mobile."user" USING btree (lower((address)::text));
CREATE INDEX user_lower_idx2 ON mobile."user" USING btree (lower((company_name)::text));
CREATE INDEX user_lower_idx3 ON mobile."user" USING btree (lower((occupation)::text));
CREATE INDEX user_lower_idx4 ON mobile."user" USING btree (lower((email)::text));
CREATE INDEX user_lower_idx5 ON mobile."user" USING btree (lower((surname)::text));
CREATE INDEX user_lower_idx6 ON mobile."user" USING btree (lower((tc_number)::text));
CREATE INDEX user_lower_idx7 ON mobile."user" USING btree (lower((user_name)::text));


-- mobile.user_role definition

-- Drop table

-- DROP TABLE mobile.user_role;

CREATE TABLE mobile.userrole (
    id serial4 NOT NULL,
	role_id int4 NOT NULL,
	user_id int4 NOT NULL,
	CONSTRAINT userrole_pkey PRIMARY KEY (id),
	CONSTRAINT fk859n2jvi8ivhui0rl0esws6o FOREIGN KEY (user_id) REFERENCES mobile."user"(id),
	CONSTRAINT fka68196081fvovjhkek5m97n3y FOREIGN KEY (role_id) REFERENCES mobile."role"(id)
);

CREATE TABLE mobile."homepage" (
                               id serial4 NOT NULL,
                               number_of_forms int2 NULL,
                               number_of_users int2 NULL,
                               number_of_completed_forms int2 NULL,
                               CONSTRAINT homepage_pkey PRIMARY KEY (id)
);

CREATE TABLE mobile."application" (
                                   id serial4 NOT NULL,
                                   created_at timestamp NULL,
                                   description varchar(255) NULL,
                                   name varchar(255) NULL,
                                   short_name varchar(255) NULL,
                                   updated_at timestamp NULL,
                                   updated_by varchar(255) NULL,
                                   version_number varchar(255) NULL,
                                   logo varchar(255) NULL,
                                   status int2 NULL,
                                   form_type int2 NULL,
                                   CONSTRAINT application_pkey PRIMARY KEY (id)

);
CREATE TABLE mobile."user_application" (
                                      id serial4 NOT NULL,
                                      application_id int4 NOT NULL ,
                                      user_id int4 NOT NULL ,
                                      created_at timestamp NULL,
                                      created_by varchar(255) NULL,
                                      status int2 NULL,
                                      updated_at timestamp NULL,
                                      updated_by varchar(255) NULL,
                                      CONSTRAINT user_application_pkey PRIMARY KEY (id),
                                      CONSTRAINT fk859n2jvi8ivhui0rl0esws6o FOREIGN KEY (user_id) REFERENCES mobile."user"(id),
                                      CONSTRAINT fka68196081fvovjhkek5m97n3a FOREIGN KEY (application_id) REFERENCES mobile."application"(id)

);

CREATE TABLE mobile."control_metadata" (
                                           id serial4 NOT NULL,
                                           barcode varchar(255) NULL,
                                           control_date timestamp NULL,
                                           coordinate_x int8 NULL,
                                           coordinate_y int8 NULL,
                                           state_code varchar(255) NULL,
                                           application_id int4 NOT NULL,
                                           assigment_id int2 NULL,
                                           device_id varchar(255) NULL,
                                           user_id int4 NOT NULL ,
                                           is_report_result_ready int2 NULL,
                                           try_count int2 NULL,


                                           CONSTRAINT control_metadata_pkey PRIMARY KEY (id),
                                           CONSTRAINT fk859n2jvi8ivhui0rl0esws6o FOREIGN KEY (user_id) REFERENCES mobile."user"(id),
                                           CONSTRAINT fka68196081fvovjhkek5m97n3a FOREIGN KEY (application_id) REFERENCES mobile."application"(id)

);

CREATE TABLE mobile."version" (
                                           id serial4 NOT NULL,
                                           version varchar(255) NULL,
                                           application_id int4 NOT NULL ,



                                           CONSTRAINT version_pkey PRIMARY KEY (id),
                                           CONSTRAINT fka68196081fvovjhkek5m97n3a FOREIGN KEY (application_id) REFERENCES mobile."application"(id)

);
CREATE TABLE mobile."application_page" (
                                  id serial4 NOT NULL,
                                  created_at timestamp NULL,
                                  created_by varchar(255) NULL,
                                  is_home_page boolean,
                                  short_name varchar(255) NULL ,
                                  updated_at timestamp NULL,
                                  update_by varchar(255) NULL,
                                  version varchar(255) NULL,
                                  application_id int4 NOT NULL ,
                                  page_id int4 NOT NULL ,
                                  version_id int4 NOT NULL ,
                                  template_url varchar(255) NULL ,


                                  CONSTRAINT application_page_pkey PRIMARY KEY (id),
                                  CONSTRAINT fka68196081fversionk5m97n3a FOREIGN KEY (version_id) REFERENCES mobile."version"(id),
                                  CONSTRAINT fka68196081fvovjhkek5m97n3a FOREIGN KEY (application_id) REFERENCES mobile."application"(id),
                                  CONSTRAINT fka68196081page_idek5m97n3a FOREIGN KEY (page_id) REFERENCES mobile."pageV"(id)

);
CREATE TABLE mobile."navigation" (
                                           id serial4 NOT NULL,
                                           created_at timestamp NULL,
                                           created_by varchar(255) NULL,
                                           name varchar (255) NULL ,
                                           order_number int4 NULL ,
                                           updated_at timestamp NULL,
                                           update_by varchar(255) NULL,
                                           version varchar(255) NULL,
                                           application_page_id int4 NOT NULL ,
                                           function_id int4 NOT NULL ,
                                           status int2 NULL ,


                                           CONSTRAINT navigation_pkey PRIMARY KEY (id),
                                           CONSTRAINT fka6819application_pagen3a FOREIGN KEY (application_page_id) REFERENCES mobile."application_page"(id)
);

CREATE TABLE mobile."pageV" (
                                     id serial4 NOT NULL,
                                     created_at timestamp NULL,
                                     created_by varchar(255) NULL,
                                     title varchar (255) NULL ,
                                     updated_at timestamp NULL,
                                     update_by varchar(255) NULL,
                                     is_page_name_hide boolean NULL ,
                                     page_number int4 NULL ,


                                     CONSTRAINT page_pkey PRIMARY KEY (id)

);
CREATE TABLE mobile."page_form" (
                               id serial4 NOT NULL,
                               created_at timestamp NULL,
                               created_by varchar(255) NULL,
                               order_no varchar (255) NULL ,
                               short_name varchar(255) NULL,
                               updated_at timestamp NULL,
                               updated_by varchar(255) NULL,
                               version varchar(255) NULL,
                               form_id int4  NOT NULL,
                               page_id int4  NOT NULL,
                               version_id int4  NOT NULL,


                               CONSTRAINT page_form_key PRIMARY KEY (id),
                               CONSTRAINT fka68196081fversionk5m97n3a FOREIGN KEY (version_id) REFERENCES mobile."version"(id),
                               CONSTRAINT fka68196081formjhkek5m97n3a FOREIGN KEY (form_id) REFERENCES mobile."form"(id),
                               CONSTRAINT fka68196081page_idek5m97n3a FOREIGN KEY (page_id) REFERENCES mobile."pageV"(id)


);
CREATE TABLE mobile."form" (
                               id serial4 NOT NULL,
                               created_at timestamp NULL,
                               created_by varchar(255) NULL,
                               title varchar (255) NULL ,
                               updated_at timestamp NULL,
                               update_by varchar(255) NULL,



                               CONSTRAINT form_pkey PRIMARY KEY (id)

);
CREATE TABLE mobile."component" (
                               id serial4 NOT NULL,
                               created_at timestamp NULL,
                               created_by varchar(255) NULL,
                               type varchar (255) NULL ,
                               updated_at timestamp NULL,
                               update_by varchar(255) NULL,



                               CONSTRAINT component_pkey PRIMARY KEY (id)

);
CREATE TABLE mobile."form_component" (
                                    id serial4 NOT NULL,
                                    created_at timestamp NULL,
                                    created_by varchar(255) NULL,
                                    short_name varchar(255) NULL,
                                    updated_at timestamp NULL,
                                    updated_by varchar(255) NULL,
                                    version varchar(255) NULL,
                                    component_id int4  NOT NULL,
                                    form_id int4  NOT NULL,
                                    version_id int4  NOT NULL,
                                    col_size varchar(255) NULL ,
                                    col_number int4 NULL ,
                                    row_number int4 NULL ,
                                    description varchar(255) NULL ,
                                    col_align varchar(255) NULL ,
                                    datasource_type boolean NULL ,


                                    CONSTRAINT form_component_key PRIMARY KEY (id),

                                    CONSTRAINT fka68196081component5m97n3a FOREIGN KEY (component_id) REFERENCES mobile."component"(id),
                                    CONSTRAINT fka68196081fversionk5m97n3a FOREIGN KEY (version_id) REFERENCES mobile."version"(id),
                                    CONSTRAINT fka68196081formjhkek5m97n3a FOREIGN KEY (form_id) REFERENCES mobile."form"(id)

);
CREATE TABLE mobile."component_validation" (
                                    id serial4 NOT NULL,
                                    error_message varchar(255) NULL,
                                    value varchar(255) NULL,
                                    form_component_id int4 NOT NULL ,
                                    type int4  NULL ,



                                    CONSTRAINT component_validation_pkey PRIMARY KEY (id),
                                    CONSTRAINT fka6819formcomponent5m97n3a FOREIGN KEY (form_component_id) REFERENCES mobile."form_component"(id)
);
select * from mobile.page where id=34
