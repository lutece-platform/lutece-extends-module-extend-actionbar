INSERT INTO extend_actionbar_action VALUES (1,'Imprimer','<a class=\"btn btn-small\" target=\"_print\" title=\"imprimer\" href=\"javascript:window.print();\"><i class=\"icon-print\"> </i> imprimer</a>','PAGE');
INSERT INTO extend_actionbar_action VALUES (2,'Retour','<a class=\"btn btn-small\" title=\"Retour\" href=\"javascript:history.go(-1);\"><i class=\"icon-remove-circle\"></i> Retour</a>','*');
INSERT INTO extend_actionbar_action VALUES (3,'Imprimer document','<a class=\"btn btn-small\" target=\"_print\" title=\"imprimer\" href=\"jsp/site/plugins/document/PrintDocument.jsp?document_id=@id_resource\"><i class=\"icon-print\"></i> imprimer</a>','document');
INSERT INTO extend_actionbar_action VALUES (4,'Envoyer document','<a class=\"btn btn-small\" href=\"jsp/site/plugins/document/SendDocument.jsp?document_id=@id_resource\" target=\"_send\"><i class=\"icon-envelope\"></i> Envoyer</a>','document');

INSERT INTO extend_actionbar_config VALUES (-1,-1);