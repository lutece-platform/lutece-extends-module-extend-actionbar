-- liquibase formatted sql
-- changeset extend-actionbar:update_db_extend_actionbar-1.1.3-2.0.0.sql
-- preconditions onFail:MARK_RAN onError:WARN
-- precondition-sql-check expectedResult:1 SELECT COUNT(*) FROM core_admin_right WHERE id_right = 'MANAGE_ACTION_BUTTONS' AND icon_url = 'images/admin/skin/plugins/extend/modules/actionbar/extend-actionbar.png'
UPDATE core_admin_right SET icon_url = 'ti ti-click' WHERE id_right = 'MANAGE_ACTION_BUTTONS' AND icon_url = 'images/admin/skin/plugins/extend/modules/actionbar/extend-actionbar.png';
-- changeset extend-actionbar:update_db_extend_actionbar-1.1.3-2.0.0-admin-url.sql
-- preconditions onFail:MARK_RAN onError:WARN
UPDATE core_admin_right SET admin_url = 'jsp/admin/plugins/extend/modules/actionbar/ManageActionButtons.jsp' WHERE id_right = 'MANAGE_ACTION_BUTTONS';
-- changeset extend-actionbar:update_db_extend_actionbar-1.1.3-2.0.0-mariaDB-mySQL.sql dbms:mariadb,mysql
-- preconditions onFail:MARK_RAN onError:WARN
SET SESSION sql_mode='NO_AUTO_VALUE_ON_ZERO';
ALTER TABLE extend_actionbar_action MODIFY COLUMN id_action INT AUTO_INCREMENT;
-- changeset extend-actionbar:update_db_extend_actionbar-1.1.3-2.0.0-icon-print.sql
-- preconditions onFail:MARK_RAN onError:WARN
-- precondition-sql-check expectedResult:1 SELECT COUNT(*) FROM extend_actionbar_action WHERE id_action = 1 AND html_content LIKE '%class="fa fa-print"%'
UPDATE extend_actionbar_action SET html_content = REPLACE(html_content, 'class="fa fa-print"', 'class="ti ti-printer"') WHERE id_action = 1 AND html_content LIKE '%class="fa fa-print"%';
-- changeset extend-actionbar:update_db_extend_actionbar-1.1.3-2.0.0-icon-send.sql
-- preconditions onFail:MARK_RAN onError:WARN
-- precondition-sql-check expectedResult:1 SELECT COUNT(*) FROM extend_actionbar_action WHERE id_action = 2 AND html_content LIKE '%class="fa fa-envelope-o"%'
UPDATE extend_actionbar_action SET html_content = REPLACE(html_content, 'class="fa fa-envelope-o"', 'class="ti ti-mail"') WHERE id_action = 2 AND html_content LIKE '%class="fa fa-envelope-o"%';
-- changeset extend-actionbar:update_db_extend_actionbar-1.1.3-2.0.0-icon-print-document.sql
-- preconditions onFail:MARK_RAN onError:WARN
-- precondition-sql-check expectedResult:1 SELECT COUNT(*) FROM extend_actionbar_action WHERE id_action = 3 AND html_content LIKE '%class="fa fa-print"%'
UPDATE extend_actionbar_action SET html_content = REPLACE(html_content, 'class="fa fa-print"', 'class="ti ti-printer"') WHERE id_action = 3 AND html_content LIKE '%class="fa fa-print"%';
-- changeset extend-actionbar:update_db_extend_actionbar-1.1.3-2.0.0-icon-back.sql
-- preconditions onFail:MARK_RAN onError:WARN
-- precondition-sql-check expectedResult:1 SELECT COUNT(*) FROM extend_actionbar_action WHERE id_action = 4 AND html_content LIKE '%class="fa fa-times-circle"%'
UPDATE extend_actionbar_action SET html_content = REPLACE(html_content, 'class="fa fa-times-circle"', 'class="ti ti-arrow-back-up"') WHERE id_action = 4 AND html_content LIKE '%class="fa fa-times-circle"%';
