/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.extend.modules.actionbar.web;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.api.user.User;
import fr.paris.lutece.plugins.extend.modules.actionbar.business.ActionButton;
import fr.paris.lutece.plugins.extend.modules.actionbar.service.ActionbarResourceIdService;
import fr.paris.lutece.plugins.extend.modules.actionbar.service.ActionbarService;
import fr.paris.lutece.plugins.extend.service.type.IExtendableResourceTypeService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.util.mvc.admin.MVCAdminJspBean;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.util.mvc.utils.MVCUtils;
import fr.paris.lutece.portal.web.cdi.mvc.Models;
import fr.paris.lutece.portal.web.util.IPager;
import fr.paris.lutece.portal.web.util.Pager;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.url.UrlItem;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Back office management of the action buttons.
 */
@RequestScoped
@Named( "actionbarJspBean" )
@Controller( controllerJsp = ActionbarJspBean.CONTROLLER_JSP, controllerPath = ActionbarJspBean.CONTROLLER_PATH, right = ActionbarJspBean.MANAGE_ACTION_BUTTONS, securityTokenEnabled = true )
public class ActionbarJspBean extends MVCAdminJspBean
{
    /**
     * Right to manage action buttons
     */
    public static final String MANAGE_ACTION_BUTTONS = "MANAGE_ACTION_BUTTONS";

    /** The controller JSP */
    public static final String CONTROLLER_JSP = "ManageActionButtons.jsp";

    /** The controller path */
    public static final String CONTROLLER_PATH = "jsp/admin/plugins/extend/modules/actionbar/";

    private static final long serialVersionUID = 3580512845617771375L;

    // VIEWS
    private static final String VIEW_MANAGE_ACTION_BUTTONS = "manageActionButtons";
    private static final String VIEW_CREATE_ACTION_BUTTON = "createActionButton";
    private static final String VIEW_MODIFY_ACTION_BUTTON = "modifyActionButton";
    private static final String VIEW_CONFIRM_REMOVE_ACTION_BUTTON = "confirmRemoveActionButton";

    // ACTIONS
    private static final String ACTION_CREATE_ACTION_BUTTON = VIEW_CREATE_ACTION_BUTTON;
    private static final String ACTION_MODIFY_ACTION_BUTTON = VIEW_MODIFY_ACTION_BUTTON;
    private static final String ACTION_REMOVE_ACTION_BUTTON = "removeActionButton";
    private static final String ACTION_MOVE_ACTION_BUTTON = "moveActionButton";

    // PARAMETERS
    private static final String PARAMETER_ID_ACTION_BUTTON = "id_action";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_HTML_CONTENT = "html_content";
    private static final String PARAM_RESOURCE_TYPE = "resourceType";
    private static final String PARAM_MOVE_UP = "moveUp";

    // PROPERTIES
    private static final String PROPERTY_MANAGE_ACTION_BUTTONS_PAGE_TITLE = "module.extend.actionbar.adminFeature.manage_action_buttons.pageTitle";
    private static final String PROPERTY_ADD_ACTION_BUTTON_PAGE_TITLE = "module.extend.actionbar.adminFeature.add_action_button.pageTitle";
    private static final String PROPERTY_MODIFY_ACTION_BUTTON_PAGE_TITLE = "module.extend.actionbar.adminFeature.modify_action_button.pageTitle";
    private static final String PROPERTY_DEFAULT_ITEMS_PER_PAGE = "module.extend.actionbar.manage_action_buttons.itemsPerPage";

    // MARKS
    private static final String MARK_ACTION_BUTTON_LIST = "action_button_list";
    private static final String MARK_PERMISSIONS = "permissions";
    private static final String MARK_ACTION_BUTTON = "action_button";
    private static final String MARK_RESOURCE_TYPES = "resourceTypes";
    private static final String MARK_LAST_ORDER = "last_order";

    // MESSAGES
    private static final String MESSAGE_UNAUTHORIZED_ACTION = "extend.message.unauthorizedAction";
    private static final String MESSAGE_CONFIRM_REMOVE_ACTION_BUTTON = "module.extend.actionbar.remove_action_button.confirmRemoveActionButton";
    private static final String MESSAGE_MANDATORY_FIELDS = "portal.util.message.mandatoryFields";
    private static final String MESSAGE_ACTION_BUTTON_NOT_FOUND = "module.extend.actionbar.manage_action_buttons.actionbutton.actionButtonNotFound";
    private static final String MESSAGE_LABEL_EVERY_RESOURCE_TYPE = "module.extend.actionbar.add_action_button.everyResourceType";

    // TEMPLATES
    private static final String TEMPLATE_MANAGE_ACTION_BUTTONS = "admin/plugins/extend/modules/actionbar/manage_action_buttons.html";
    private static final String TEMPLATE_ADD_ACTION_BUTTON = "admin/plugins/extend/modules/actionbar/add_action_button.html";
    private static final String TEMPLATE_MODIFY_ACTION_BUTTON = "admin/plugins/extend/modules/actionbar/modify_action_button.html";

    @Inject
    private ActionbarService _actionbarService;

    @Inject
    private IExtendableResourceTypeService _resourceTypeService;

    @Inject
    @Pager( listBookmark = MARK_ACTION_BUTTON_LIST, defaultItemsPerPage = PROPERTY_DEFAULT_ITEMS_PER_PAGE, baseUrl = CONTROLLER_PATH + CONTROLLER_JSP )
    private IPager<ActionButton, Void> _pager;

    /**
     * Get the manage action buttons page.
     * @param request The request
     * @param model The model
     * @return The HTML content to display
     */
    @View( value = VIEW_MANAGE_ACTION_BUTTONS, defaultView = true, securityTokenAction = ACTION_MOVE_ACTION_BUTTON )
    public String getManageActionButtons( HttpServletRequest request, Models model )
    {
        if ( request.getParameter( MVCUtils.PARAMETER_VIEW ) == null )
        {
            return redirectView( request, VIEW_MANAGE_ACTION_BUTTONS );
        }

        UrlItem urlPager = new UrlItem( CONTROLLER_PATH + CONTROLLER_JSP );
        urlPager.addParameter( MVCUtils.PARAMETER_VIEW, VIEW_MANAGE_ACTION_BUTTONS );
        _pager.withBaseUrl( urlPager.getUrl( ) ).withListItem( _actionbarService.findAllActionButtons( ) ).populateModels( request, model, getLocale( ) );

        Map<String, Boolean> mapPermissions = new HashMap<>( );
        mapPermissions.put( ActionbarResourceIdService.PERMISSION_MODIFY_ACTION_BUTTON, isAuthorized( ActionbarResourceIdService.PERMISSION_MODIFY_ACTION_BUTTON ) );
        mapPermissions.put( ActionbarResourceIdService.PERMISSION_ADD_ACTION_BUTTON, isAuthorized( ActionbarResourceIdService.PERMISSION_ADD_ACTION_BUTTON ) );
        mapPermissions.put( ActionbarResourceIdService.PERMISSION_REMOVE_ACTION_BUTTON, isAuthorized( ActionbarResourceIdService.PERMISSION_REMOVE_ACTION_BUTTON ) );

        model.put( MARK_PERMISSIONS, mapPermissions );
        model.put( MARK_LAST_ORDER, _actionbarService.getNewOrder( ) - 1 );

        return getPage( PROPERTY_MANAGE_ACTION_BUTTONS_PAGE_TITLE, TEMPLATE_MANAGE_ACTION_BUTTONS, model );
    }

    /**
     * Get the page to add an action button
     * @param request The request
     * @param model The model
     * @return The HTML content to display, or the redirection to an error message
     */
    @View( VIEW_CREATE_ACTION_BUTTON )
    public String getCreateActionButton( HttpServletRequest request, Models model )
    {
        if ( !isAuthorized( ActionbarResourceIdService.PERMISSION_ADD_ACTION_BUTTON ) )
        {
            return redirectUnauthorized( request );
        }

        model.put( MARK_RESOURCE_TYPES, getResourceTypes( getLocale( ) ) );

        return getPage( PROPERTY_ADD_ACTION_BUTTON_PAGE_TITLE, TEMPLATE_ADD_ACTION_BUTTON, model );
    }

    /**
     * Do create an action button
     * @param request The request
     * @return The next URL to redirect to
     */
    @Action( ACTION_CREATE_ACTION_BUTTON )
    public String doCreateActionButton( HttpServletRequest request )
    {
        if ( !isAuthorized( ActionbarResourceIdService.PERMISSION_ADD_ACTION_BUTTON ) )
        {
            return redirectUnauthorized( request );
        }

        ActionButton actionButton = getActionButtonFromRequest( request );
        if ( actionButton == null )
        {
            addError( MESSAGE_MANDATORY_FIELDS, getLocale( ) );
            return redirectView( request, VIEW_CREATE_ACTION_BUTTON );
        }

        _actionbarService.createActionButton( actionButton );

        return redirectView( request, VIEW_MANAGE_ACTION_BUTTONS );
    }

    /**
     * Get the page to modify an action button
     * @param request The request
     * @param model The model
     * @return The HTML content to display, or the redirection to an error message
     */
    @View( VIEW_MODIFY_ACTION_BUTTON )
    public String getModifyActionButton( HttpServletRequest request, Models model )
    {
        if ( !isAuthorized( ActionbarResourceIdService.PERMISSION_MODIFY_ACTION_BUTTON ) )
        {
            return redirectUnauthorized( request );
        }

        ActionButton actionButton = findActionButton( request );
        if ( actionButton == null )
        {
            return redirect( request, AdminMessageService.getMessageUrl( request, MESSAGE_ACTION_BUTTON_NOT_FOUND, AdminMessage.TYPE_STOP ) );
        }

        model.put( MARK_ACTION_BUTTON, actionButton );
        model.put( MARK_RESOURCE_TYPES, getResourceTypes( getLocale( ) ) );

        return getPage( PROPERTY_MODIFY_ACTION_BUTTON_PAGE_TITLE, TEMPLATE_MODIFY_ACTION_BUTTON, model );
    }

    /**
     * Do modify an action button
     * @param request The request
     * @return The next URL to redirect to
     */
    @Action( ACTION_MODIFY_ACTION_BUTTON )
    public String doModifyActionButton( HttpServletRequest request )
    {
        if ( !isAuthorized( ActionbarResourceIdService.PERMISSION_MODIFY_ACTION_BUTTON ) )
        {
            return redirectUnauthorized( request );
        }

        ActionButton existing = findActionButton( request );
        if ( existing == null )
        {
            return redirect( request, AdminMessageService.getMessageUrl( request, MESSAGE_ACTION_BUTTON_NOT_FOUND, AdminMessage.TYPE_STOP ) );
        }

        ActionButton actionButton = getActionButtonFromRequest( request );
        if ( actionButton == null )
        {
            addError( MESSAGE_MANDATORY_FIELDS, getLocale( ) );
            return redirect( request, VIEW_MODIFY_ACTION_BUTTON, PARAMETER_ID_ACTION_BUTTON, existing.getIdAction( ) );
        }

        actionButton.setIdAction( existing.getIdAction( ) );
        _actionbarService.updateActionButton( actionButton );

        return redirectView( request, VIEW_MANAGE_ACTION_BUTTONS );
    }

    /**
     * Get the confirmation page before removing an action button
     * @param request The request
     * @return The next URL to redirect to
     */
    @View( value = VIEW_CONFIRM_REMOVE_ACTION_BUTTON, securityTokenAction = ACTION_REMOVE_ACTION_BUTTON )
    public String getConfirmRemoveActionButton( HttpServletRequest request )
    {
        if ( !isAuthorized( ActionbarResourceIdService.PERMISSION_REMOVE_ACTION_BUTTON ) )
        {
            return redirectUnauthorized( request );
        }

        ActionButton actionButton = findActionButton( request );
        if ( actionButton == null )
        {
            return redirect( request, AdminMessageService.getMessageUrl( request, MESSAGE_ACTION_BUTTON_NOT_FOUND, AdminMessage.TYPE_STOP ) );
        }

        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_ACTION_BUTTON ) );
        url.addParameter( PARAMETER_ID_ACTION_BUTTON, actionButton.getIdAction( ) );

        return redirect( request, AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_ACTION_BUTTON, url.getUrl( ),
                AdminMessage.TYPE_CONFIRMATION ) );
    }

    /**
     * Do remove an action button
     * @param request The request
     * @return The next URL to redirect to
     */
    @Action( ACTION_REMOVE_ACTION_BUTTON )
    public String doRemoveActionButton( HttpServletRequest request )
    {
        if ( !isAuthorized( ActionbarResourceIdService.PERMISSION_REMOVE_ACTION_BUTTON ) )
        {
            return redirectUnauthorized( request );
        }

        ActionButton actionButton = findActionButton( request );
        if ( actionButton != null )
        {
            _actionbarService.removeActionButton( actionButton.getIdAction( ) );
        }

        return redirectView( request, VIEW_MANAGE_ACTION_BUTTONS );
    }

    /**
     * Move an action button up or down
     * @param request The request
     * @return The next URL to redirect to
     */
    @Action( ACTION_MOVE_ACTION_BUTTON )
    public String doMoveActionButton( HttpServletRequest request )
    {
        if ( !isAuthorized( ActionbarResourceIdService.PERMISSION_MODIFY_ACTION_BUTTON ) )
        {
            return redirectUnauthorized( request );
        }

        ActionButton actionButton = findActionButton( request );
        if ( actionButton != null )
        {
            int nOrder = actionButton.getOrder( );
            int nNewOrder = Boolean.parseBoolean( request.getParameter( PARAM_MOVE_UP ) ) ? nOrder - 1 : nOrder + 1;
            if ( nNewOrder > 0 && nNewOrder < _actionbarService.getNewOrder( ) )
            {
                _actionbarService.updateActionButtonOrder( actionButton, nNewOrder );
            }
        }

        return redirectView( request, VIEW_MANAGE_ACTION_BUTTONS );
    }

    /**
     * Tell whether the current user holds a permission on the action buttons
     * @param strPermission The permission
     * @return true if the user holds the permission
     */
    private boolean isAuthorized( String strPermission )
    {
        return RBACService.isAuthorized( ActionButton.RESOURCE_TYPE, null, strPermission, (User) getUser( ) );
    }

    /**
     * Redirect to the unauthorized action message
     * @param request The request
     * @return The redirection
     */
    private String redirectUnauthorized( HttpServletRequest request )
    {
        return redirect( request, AdminMessageService.getMessageUrl( request, MESSAGE_UNAUTHORIZED_ACTION, AdminMessage.TYPE_STOP ) );
    }

    /**
     * Load the action button named by the request
     * @param request The request
     * @return The action button, or null if the id is missing, not numeric or unknown
     */
    private ActionButton findActionButton( HttpServletRequest request )
    {
        String strId = request.getParameter( PARAMETER_ID_ACTION_BUTTON );
        if ( !StringUtils.isNumeric( strId ) )
        {
            return null;
        }
        return _actionbarService.findActionButton( Integer.parseInt( strId ) );
    }

    /**
     * Build an action button from the posted fields
     * @param request The request
     * @return The action button, or null if a mandatory field is blank
     */
    private ActionButton getActionButtonFromRequest( HttpServletRequest request )
    {
        String strName = request.getParameter( PARAM_NAME );
        String strHtmlContent = request.getParameter( PARAM_HTML_CONTENT );
        if ( StringUtils.isBlank( strName ) || StringUtils.isBlank( strHtmlContent ) )
        {
            return null;
        }

        ActionButton actionButton = new ActionButton( );
        actionButton.setName( strName );
        actionButton.setHtmlContent( strHtmlContent );
        actionButton.setResourceType( StringUtils.defaultIfBlank( request.getParameter( PARAM_RESOURCE_TYPE ), ActionButton.EVERY_RESOURCE_TYPE ) );
        return actionButton;
    }

    /**
     * Get the resource types an action button can target, every resource type first
     * @param locale The locale
     * @return The resource types
     */
    private ReferenceList getResourceTypes( Locale locale )
    {
        ReferenceList refListResourceTypes = _resourceTypeService.findAllAsRef( locale );
        ReferenceItem refItem = new ReferenceItem( );
        refItem.setCode( ActionButton.EVERY_RESOURCE_TYPE );
        refItem.setName( I18nService.getLocalizedString( MESSAGE_LABEL_EVERY_RESOURCE_TYPE, locale ) );
        refListResourceTypes.add( 0, refItem );
        return refListResourceTypes;
    }
}
