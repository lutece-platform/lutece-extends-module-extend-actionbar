--
-- Init  table core_admin_right
--
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url, documentation_url) 
VALUES ('MANAGE_ACTION_BUTTONS', 'module.extend.actionbar.adminFeature.manage_action_buttons.name', '2', 'jsp/admin/plugins/extend/modules/actionbar/GetManageActionButtons.jsp'
, 'module.extend.actionbar.adminFeature.manage_action_buttons.description', '0', 'extend-actionbar', 'CONTENT', 'images/admin/skin/plugins/extend/modules/actionbar/extend-actionbar.png', 'jsp/admin/documentation/AdminDocumentation.jsp?doc=admin-extend-actionbar');

--
-- Init  table core_user_right
--
INSERT INTO core_user_right (id_right,id_user) VALUES ('MANAGE_ACTION_BUTTONS',1);
INSERT INTO core_user_right (id_right,id_user) VALUES ('MANAGE_ACTION_BUTTONS',2);

--
-- Init  table core_admin_role
--
INSERT INTO core_admin_role (role_key,role_description) VALUES ('extend_action_button_manager','Gestion des boutons d''actions');

--
-- Init  table core_admin_role_resource
--
INSERT INTO core_admin_role_resource (rbac_id,role_key,resource_type,resource_id,permission) VALUES 
 (567,'extend_action_button_manager','ACTION_BUTTON','*','*');
 
--
-- Init  table core_user_role
--
INSERT INTO core_user_role (role_key,id_user) VALUES ('extend_action_button_manager',1);
INSERT INTO core_user_role (role_key,id_user) VALUES ('extend_action_button_manager',2);
