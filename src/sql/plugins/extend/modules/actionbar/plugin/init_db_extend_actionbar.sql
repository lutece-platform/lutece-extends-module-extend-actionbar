-- liquibase formatted sql
-- changeset extend-actionbar:init_db_extend_actionbar.sql
-- preconditions onFail:MARK_RAN onError:WARN
-- precondition-sql-check expectedResult:0 SELECT ( SELECT COUNT(*) FROM extend_actionbar_config WHERE id_extender = -1 ) + ( SELECT COUNT(*) FROM extend_actionbar_action )
INSERT INTO extend_actionbar_action VALUES (1,'Imprimer','<a class=\"btn btn-sm\" title=\"imprimer\" href=\"javascript:window.print();\"><i class=\"ti ti-printer\"></i> Imprimer</a>','PAGE',1);
INSERT INTO extend_actionbar_action VALUES (2,'Envoyer','<a class=\"btn btn-sm\" href=\"jsp/site/SendResource.jsp?idExtendableResource=@id_resource&extendableResourceType=@type_resource\" target=\"_send\"><i class=\"ti ti-mail\"></i> Envoyer</a>','*',2);
INSERT INTO extend_actionbar_action VALUES (3,'Imprimer document','<a class=\"btn btn-sm\" target=\"_print\" title=\"imprimer\" href=\"jsp/site/plugins/document/PrintDocument.jsp?document_id=@id_resource\"><i class=\"ti ti-printer\"></i> Imprimer</a>','document',3);
INSERT INTO extend_actionbar_action VALUES (4,'Retour','<a class=\"btn btn-sm\" title=\"Retour\" href=\"javascript:history.go(-1);\"><i class=\"ti ti-arrow-back-up\"></i> Retour</a>','*',4);

INSERT INTO extend_actionbar_config VALUES (-1,-1);