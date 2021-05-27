
CREATE TABLE task (
	id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	name varchar(65) NOT NULL,
	description varchar(125),
	task_date timestamp,
	is_done int default 0,
    PRIMARY KEY (id)
);


