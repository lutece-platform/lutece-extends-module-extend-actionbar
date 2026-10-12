-- liquibase formatted sql
-- changeset extend-actionbar:create_db_extend_actionbar.sql
-- preconditions onFail:MARK_RAN onError:WARN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'extend_actionbar_action'
DROP TABLE IF EXISTS extend_actionbar_action;
CREATE TABLE extend_actionbar_action (
	id_action INT AUTO_INCREMENT,
	name VARCHAR(255) DEFAULT '' NOT NULL,
	html_content LONG VARCHAR NULL,
	resource_type VARCHAR(255) NOT NULL,
	btn_order INT NOT NULL,
	PRIMARY KEY (id_action)
);

DROP TABLE IF EXISTS extend_actionbar_config;
CREATE TABLE extend_actionbar_config (
	id_extender INT NOT NULL,
	id_action INT NOT NULL,
	PRIMARY KEY (id_extender, id_action)
);

-- changeset extend-actionbar:create_db_extend_actionbar-auto-increment.sql dbms:mariadb,mysql
-- preconditions onFail:MARK_RAN onError:WARN
-- precondition-sql-check expectedResult:1 SELECT COUNT(*) FROM information_schema.columns WHERE table_schema = DATABASE() AND table_name = 'extend_actionbar_action' AND column_name = 'id_action' AND extra NOT LIKE '%auto_increment%'
SET SESSION sql_mode='NO_AUTO_VALUE_ON_ZERO';
ALTER TABLE extend_actionbar_action MODIFY COLUMN id_action INT AUTO_INCREMENT;
