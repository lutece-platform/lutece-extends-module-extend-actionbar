<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:useBean id="actionbarJspBean" scope="session" class="fr.paris.lutece.plugins.extend.modules.actionbar.web.ActionbarJspBean" />
<% 
	actionbarJspBean.init( request, actionbarJspBean.MANAGE_ACTION_BUTTONS );
	response.sendRedirect( actionbarJspBean.confirmRemoveActionButton( request ) );
%>
