package fr.paris.lutece.plugins.extend.modules.actionbar.web;

import fr.paris.lutece.plugins.extend.modules.actionbar.business.ActionButton;
import fr.paris.lutece.plugins.extend.modules.actionbar.service.ActionbarResourceIdService;
import fr.paris.lutece.plugins.extend.modules.actionbar.service.ActionbarService;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.admin.AdminFeaturesPageJspBean;
import fr.paris.lutece.portal.web.pluginaction.DefaultPluginActionResult;
import fr.paris.lutece.portal.web.pluginaction.IPluginActionResult;
import fr.paris.lutece.util.datatable.DataTableManager;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.url.UrlItem;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;


/**
 * JspBean of ActionBar plugin.
 */
public class ActionbarJspBean extends AdminFeaturesPageJspBean
{
    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 3580512845617771375L;

    /**
     * Right to manage action buttons
     */
    public static final String MANAGE_ACTION_BUTTONS = "MANAGE_ACTION_BUTTONS";

    // PARAMETERS
    private static final String PARAMETER_ACTION_NAME = "name";
    private static final String PARAMETER_CANCEL = "cancel";
    private static final String PARAMETER_ID_ACTION_BUTTON = "id_action";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_HTML_CONTENT = "html_content";

    // PROPERTIES
    private static final String PROPERTY_MANAGE_ACTION_BUTTONS_PAGE_TITLE = "module.extend.actionbar.adminFeature.manage_action_buttons.pageTitle";
    private static final String PROPERTY_ADD_ACTION_BUTTON_PAGE_TITLE = "module.extend.actionbar.adminFeature.add_action_button.pageTitle";
    private static final String PROPERTY_MODIFY_ACTION_BUTTON_PAGE_TITLE = "module.extend.actionbar.adminFeature.modify_action_button.pageTitle";
    private static final String PROPERTY_DEFAULT_ITEMS_PER_PAGE = "module.extend.actionbar.manage_action_buttons.itemsPerPage";

    // MARKS
    private static final String MARK_DATA_TABLE_MANAGER = "dataTableManager";
    private static final String MARK_PERMISSIONS = "permissions";
    private static final String MARK_LOCALE = "locale";
    private static final String MARK_WEBAPP_URL = "webapp_url";
    private static final String MARK_ACTION_BUTTON = "action_button";

    // MESSAGES
    private static final String MESSAGE_LABEL_ACTION_NAME = "module.extend.actionbar.manage_action_buttons.actionbutton.name";
    private static final String MESSAGE_LABEL_ACTION = "module.extend.actionbar.manage_action_buttons.actionbutton.action";
    private static final String MESSAGE_UNAUTHORIZED_ACTION = "extend.message.unauthorizedAction";
    private static final String MESSAGE_CONFIRM_REMOVE_SOCIALHUB = "module.extend.actionbar.remove_action_button.confirmRemoveActionButton";
    private static final String MESSAGE_MANDATORY_FIELDS = "portal.util.message.mandatoryFields";
    private static final String MESSAGE_ACTION_BUTTON_NOT_FOUND = "module.extend.actionbar.manage_action_buttons.actionbutton.actionButtonNotFound";

    // TEMPLATES
    private static final String TEMPLATE_MANAGE_ACTION_BUTTONS = "admin/plugins/extend/modules/actionbar/manage_action_buttons.html";
    private static final String TEMPLATE_ADD_ACTION_BUTTON = "admin/plugins/extend/modules/actionbar/add_action_button.html";
    private static final String TEMPLATE_MODIFY_ACTION_BUTTON = "admin/plugins/extend/modules/actionbar/modify_action_button.html";

    // URLS
    private static final String JSP_URL_MANAGE_ACTION_BUTTONS = "jsp/admin/plugins/extend/modules/actionbar/GetManageActionButtons.jsp";
    private static final String JSP_URL_REMOVE_ACTION_BUTTON = "jsp/admin/plugins/extend/modules/actionbar/DoRemoveActionButton.jsp";

    // local variables
    @Inject
    private ActionbarService _actionbarService = SpringContextService.getBean( ActionbarService.BEAN_NAME );
    private DataTableManager<ActionButton> _dataTableManager = null;

    /**
     * Get the manage action buttons page.
     * @param request The request
     * @return The HTML content to display
     */
    public String getManageActionButtons( HttpServletRequest request )
    {
        setPageTitleProperty( PROPERTY_MANAGE_ACTION_BUTTONS_PAGE_TITLE );

        if ( _dataTableManager == null )
        {
            _dataTableManager = new DataTableManager<ActionButton>( JSP_URL_MANAGE_ACTION_BUTTONS,
                    JSP_URL_MANAGE_ACTION_BUTTONS, AppPropertiesService.getPropertyInt(
                            PROPERTY_DEFAULT_ITEMS_PER_PAGE, 50 ), true );
            _dataTableManager.addColumn( MESSAGE_LABEL_ACTION_NAME, PARAMETER_ACTION_NAME, true );
            _dataTableManager.addActionColumn( MESSAGE_LABEL_ACTION );
        }
        _dataTableManager.filterSortAndPaginate( request, _actionbarService.findAllActionButtons( ) );

        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_DATA_TABLE_MANAGER, _dataTableManager );

        AdminUser user = AdminUserService.getAdminUser( request );

        Map<String, Boolean> mapPermissions = new HashMap<String, Boolean>( );
        mapPermissions.put( ActionbarResourceIdService.PERMISSION_MODIFY_ACTION_BUTTON, RBACService.isAuthorized(
                ActionButton.RESOURCE_TYPE, null, ActionbarResourceIdService.PERMISSION_MODIFY_ACTION_BUTTON, user ) );
        mapPermissions.put( ActionbarResourceIdService.PERMISSION_ADD_ACTION_BUTTON, RBACService.isAuthorized(
                ActionButton.RESOURCE_TYPE, null, ActionbarResourceIdService.PERMISSION_ADD_ACTION_BUTTON, user ) );
        mapPermissions.put( ActionbarResourceIdService.PERMISSION_REMOVE_ACTION_BUTTON, RBACService.isAuthorized(
                ActionButton.RESOURCE_TYPE, null, ActionbarResourceIdService.PERMISSION_REMOVE_ACTION_BUTTON, user ) );

        model.put( MARK_PERMISSIONS, mapPermissions );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_ACTION_BUTTONS,
                AdminUserService.getLocale( request ), model );

        String strContent = getAdminPage( template.getHtml( ) );
        _dataTableManager.clearItems( );

        return strContent;
    }

    /**
     * Get the page to add an action button
     * @param request The request
     * @return A IPluginActionResult containing the URL to redirect to or the
     *         HTML content to display
     */
    public IPluginActionResult getAddActionButton( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( ActionButton.RESOURCE_TYPE, null,
                ActionbarResourceIdService.PERMISSION_ADD_ACTION_BUTTON, AdminUserService.getAdminUser( request ) ) )
        {
            IPluginActionResult result = new DefaultPluginActionResult( );
            result.setRedirect( AdminMessageService.getMessageUrl( request, MESSAGE_UNAUTHORIZED_ACTION,
                    AdminMessage.TYPE_STOP ) );

            return result;
        }

        setPageTitleProperty( PROPERTY_ADD_ACTION_BUTTON_PAGE_TITLE );

        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_LOCALE, AdminUserService.getLocale( request ) );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_ADD_ACTION_BUTTON,
                AdminUserService.getLocale( request ), model );

        IPluginActionResult result = new DefaultPluginActionResult( );
        result.setHtmlContent( getAdminPage( template.getHtml( ) ) );
        return result;
    }

    /**
     * Do create an action button
     * @param request The request
     * @return The next URL to redirect to
     */
    public String doAddActionButton( HttpServletRequest request )
    {
        if ( request.getParameter( PARAMETER_CANCEL ) != null )
        {
            return AppPathService.getBaseUrl( request ) + JSP_URL_MANAGE_ACTION_BUTTONS;
        }

        if ( !RBACService.isAuthorized( ActionButton.RESOURCE_TYPE, null,
                ActionbarResourceIdService.PERMISSION_ADD_ACTION_BUTTON, AdminUserService.getAdminUser( request ) ) )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_UNAUTHORIZED_ACTION, AdminMessage.TYPE_STOP );
        }

        String strName = request.getParameter( PARAM_NAME );
        if ( StringUtils.isBlank( strName ) )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        String strHtmlContent = request.getParameter( PARAM_HTML_CONTENT );
        if ( StringUtils.isBlank( strHtmlContent ) )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        ActionButton actionButton = new ActionButton( );
        actionButton.setName( strName );
        actionButton.setHtmlContent( strHtmlContent );

        _actionbarService.createActionButton( actionButton );

        return AppPathService.getBaseUrl( request ) + JSP_URL_MANAGE_ACTION_BUTTONS;
    }

    /**
     * Get the page to modify an action button
     * @param request The request
     * @return A IPluginActionResult containing the URL to redirect to or the
     *         HTML content to display
     */
    public IPluginActionResult getModifyActionButton( HttpServletRequest request )
    {
        if ( !RBACService.isAuthorized( ActionButton.RESOURCE_TYPE, null,
                ActionbarResourceIdService.PERMISSION_MODIFY_ACTION_BUTTON, AdminUserService.getAdminUser( request ) ) )
        {
            IPluginActionResult result = new DefaultPluginActionResult( );
            result.setRedirect( AdminMessageService.getMessageUrl( request, MESSAGE_UNAUTHORIZED_ACTION,
                    AdminMessage.TYPE_STOP ) );

            return result;
        }

        setPageTitleProperty( PROPERTY_MODIFY_ACTION_BUTTON_PAGE_TITLE );

        int nActionButtonId = 0;
        try
        {
            nActionButtonId = Integer.parseInt( request.getParameter( PARAMETER_ID_ACTION_BUTTON ) );
        }
        catch ( NumberFormatException e )
        {
            IPluginActionResult result = new DefaultPluginActionResult( );
            result.setRedirect( AdminMessageService.getMessageUrl( request, MESSAGE_ACTION_BUTTON_NOT_FOUND,
                    AdminMessage.TYPE_STOP ) );
            return result;
        }

        ActionButton actionButton = _actionbarService.findActionButton( nActionButtonId );
        if ( actionButton == null )
        {
            IPluginActionResult result = new DefaultPluginActionResult( );
            result.setRedirect( AdminMessageService.getMessageUrl( request, MESSAGE_ACTION_BUTTON_NOT_FOUND,
                    AdminMessage.TYPE_STOP ) );
            return result;
        }

        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_LOCALE, AdminUserService.getLocale( request ) );
        model.put( MARK_WEBAPP_URL, AppPathService.getBaseUrl( request ) );
        model.put( MARK_ACTION_BUTTON, actionButton );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_ACTION_BUTTON,
                AdminUserService.getLocale( request ), model );

        IPluginActionResult result = new DefaultPluginActionResult( );
        result.setHtmlContent( getAdminPage( template.getHtml( ) ) );
        return result;
    }

    /**
     * Do modify an action button
     * @param request The request
     * @return The next URL to redirect to
     */
    public String doModifyActionButton( HttpServletRequest request )
    {
        if ( request.getParameter( PARAMETER_CANCEL ) != null )
        {
            return AppPathService.getBaseUrl( request ) + JSP_URL_MANAGE_ACTION_BUTTONS;
        }

        if ( !RBACService.isAuthorized( ActionButton.RESOURCE_TYPE, null,
                ActionbarResourceIdService.PERMISSION_MODIFY_ACTION_BUTTON, AdminUserService.getAdminUser( request ) ) )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_UNAUTHORIZED_ACTION, AdminMessage.TYPE_STOP );
        }

        String strName = request.getParameter( PARAM_NAME );
        if ( StringUtils.isBlank( strName ) )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        String strHtmlContent = request.getParameter( PARAM_HTML_CONTENT );
        if ( StringUtils.isBlank( strHtmlContent ) )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nIdActionButton = 0;
        try
        {
            nIdActionButton = Integer.parseInt( request.getParameter( PARAMETER_ID_ACTION_BUTTON ) );
        }
        catch ( NumberFormatException e )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        ActionButton actionButton = new ActionButton( );
        actionButton.setName( strName );
        actionButton.setHtmlContent( strHtmlContent );
        actionButton.setIdAction( nIdActionButton );

        _actionbarService.updateActionButton( actionButton );

        return AppPathService.getBaseUrl( request ) + JSP_URL_MANAGE_ACTION_BUTTONS;
    }

    /**
     * Get the confirmation page before removing an action button
     * @param request The request
     * @return The next URL to redirect to
     */
    public String confirmRemoveActionButton( HttpServletRequest request )
    {
        String strId = request.getParameter( PARAMETER_ID_ACTION_BUTTON );
        if ( StringUtils.isBlank( strId ) )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        UrlItem url = new UrlItem( JSP_URL_REMOVE_ACTION_BUTTON );
        url.addParameter( PARAMETER_ID_ACTION_BUTTON, strId );

        return AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_SOCIALHUB, url.getUrl( ),
                AdminMessage.TYPE_CONFIRMATION );
    }

    /**
     * Do remove an action button
     * @param request The request
     * @return The next URL to redirect to
     */
    public String doRemoveActionButton( HttpServletRequest request )
    {
        String strId = request.getParameter( PARAMETER_ID_ACTION_BUTTON );
        if ( StringUtils.isBlank( strId ) )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }
        int nId = 0;
        try
        {
            nId = Integer.parseInt( strId );
        }
        catch ( NumberFormatException e )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        _actionbarService.removeActionButton( nId );
        return AppPathService.getBaseUrl( request ) + JSP_URL_MANAGE_ACTION_BUTTONS;
    }
}
