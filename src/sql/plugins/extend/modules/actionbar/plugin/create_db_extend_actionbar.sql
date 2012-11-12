DROP TABLE IF EXISTS extend_actionbar_action;
CREATE TABLE extend_actionbar_action (
	id_action INT NOT NULL,
	name VARCHAR(255) DEFAULT '' NOT NULL,
	html_content LONG VARCHAR NULL,
	PRIMARY KEY (id_action)
);

DROP TABLE IF EXISTS extend_actionbar_config;
CREATE TABLE extend_actionbar_config (
	id_extender INT NOT NULL,
	id_action INT NOT NULL,
	PRIMARY KEY (id_extender, id_action)
);