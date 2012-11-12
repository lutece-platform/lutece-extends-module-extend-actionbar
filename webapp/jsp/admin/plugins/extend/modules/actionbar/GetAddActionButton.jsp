<%@page import="fr.paris.lutece.portal.web.pluginaction.IPluginActionResult"%>

<jsp:useBean id="actionbarJspBean" scope="session" class="fr.paris.lutece.plugins.extend.modules.actionbar.web.ActionbarJspBean" />

<% 
	actionbarJspBean.init( request, actionbarJspBean.MANAGE_ACTION_BUTTONS );
	IPluginActionResult result = actionbarJspBean.getAddActionButton( request );
	if ( result.getRedirect(  ) != null )
	{
		response.sendRedirect( result.getRedirect(  ) );
	}
	else if ( result.getHtmlContent(  ) != null )
	{
%>
		<%@ page errorPage="../../../../ErrorPage.jsp" %>
		<jsp:include page="../../../../AdminHeader.jsp" />

		<%= result.getHtmlContent(  ) %>

		<%@ include file="../../../../AdminFooter.jsp" %>
<%
	}
%>
