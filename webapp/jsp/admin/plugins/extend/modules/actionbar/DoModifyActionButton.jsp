<%@ page errorPage="../../../../ErrorPage.jsp" %>

<%@page import="fr.paris.lutece.plugins.extend.modules.actionbar.web.ActionbarJspBean"%>

${ actionbarJspBean.init( pageContext.request, ActionbarJspBean.MANAGE_ACTION_BUTTONS ) }
${ pageContext.response.sendRedirect( actionbarJspBean.doModifyActionButton( pageContext.request )) }