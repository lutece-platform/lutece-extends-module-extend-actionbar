<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp"  flush="true" />

<jsp:useBean id="actionbarJspBean" scope="session" class="fr.paris.lutece.plugins.extend.modules.actionbar.web.ActionbarJspBean" />

<% actionbarJspBean.init( request, fr.paris.lutece.plugins.extend.modules.actionbar.web.ActionbarJspBean.MANAGE_ACTION_BUTTONS ) ; %>
<%= actionbarJspBean.getManageActionButtons( request ) %>

<%@ include file="../../../../AdminFooter.jsp" %>