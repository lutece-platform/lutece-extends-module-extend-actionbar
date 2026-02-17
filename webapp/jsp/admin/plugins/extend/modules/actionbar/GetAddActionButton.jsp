<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp" />

<%@page import="fr.paris.lutece.plugins.extend.modules.actionbar.web.ActionbarJspBean"%>

${ actionbarJspBean.init( pageContext.request, ActionbarJspBean.MANAGE_ACTION_BUTTONS ) }

${ pageContext.setAttribute( 'pluginActionResult', actionbarJspBean.getAddActionButton( pageContext.request ) ) }
${ not empty pageContext.getAttribute( 'pluginActionResult' ).redirect ? pageContext.response.sendRedirect( pageContext.getAttribute( 'pluginActionResult' ).redirect ) : '' }

${ not empty pageContext.getAttribute( 'pluginActionResult' ).htmlContent ? pageContext.getAttribute( 'pluginActionResult' ).htmlContent : '' }

<%@ include file="../../../../AdminFooter.jsp" %>