<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp" />

<%@page import="fr.paris.lutece.plugins.extend.modules.actionbar.web.ActionbarJspBean"%>

${ actionbarJspBean.init( pageContext.request, ActionbarJspBean.MANAGE_ACTION_BUTTONS ) }
${ actionbarJspBean.doMoveActionButton( pageContext.request ) }

<%@ include file="../../../../AdminFooter.jsp" %>