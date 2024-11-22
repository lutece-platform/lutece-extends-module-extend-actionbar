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
package fr.paris.lutece.plugins.extend.modules.actionbar.web.component;

import fr.paris.lutece.plugins.extend.business.extender.ResourceExtenderDTO;
import fr.paris.lutece.plugins.extend.business.extender.config.IExtenderConfig;
import fr.paris.lutece.plugins.extend.modules.actionbar.business.ActionButton;
import fr.paris.lutece.plugins.extend.modules.actionbar.business.config.ActionbarExtenderConfig;
import fr.paris.lutece.plugins.extend.modules.actionbar.service.ActionbarService;
import fr.paris.lutece.plugins.extend.modules.actionbar.service.extender.ActionbarResourceExtender;
import fr.paris.lutece.plugins.extend.service.extender.config.IResourceExtenderConfigService;
import fr.paris.lutece.plugins.extend.util.ExtendErrorException;
import fr.paris.lutece.plugins.extend.web.component.AbstractResourceExtenderComponent;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;


/**
 * 
 * ActionbarResourceExtenderComponent
 * 
 */
public class ActionbarResourceExtenderComponent extends AbstractResourceExtenderComponent
{
    // TEMPLATES
    private static final String TEMPLATE_MODIFY_ACTIONBAR_CONFIG = "admin/plugins/extend/modules/actionbar/modify_action_bar_config.html";
    private static final String TEMPLATE_ACTION_BAR = "skin/plugins/extend/modules/actionbar/action_bar.html";

    private static final String PARAMETER_ALL_ACTIONS = "all_buttons";
    private static final String PARAMETER_ACTION_BUTTON = "action_button";

    private static final String MARK_ACTION_BUTTONS = "action_buttons";
    private static final String MARK_RESOURCE_EXTENDER_CONFIG = "config";
    private static final String PARAMETER_RESOURCE_ID = "@id_resource";
    private static final String PARAMETER_RESOURCE_TYPE = "@type_resource";
    private static final String MARK_ACTIONS = "actions";

    // SERVICES
    @Inject
    @Named( "extend-actionbar.actionbarExtenderConfigService" )
    private IResourceExtenderConfigService _configService;
    @Inject
    private ActionbarService _actionbarService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void buildXmlAddOn( String strIdExtendableResource, String strExtendableResourceType, String strParameters,
            StringBuffer strXml )
    {
        // Nothing yet
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPageAddOn( String strIdExtendableResource, String strExtendableResourceType, String strParameters,
            HttpServletRequest request )
    {
        // Method to get the html code of the extension in front office
        ActionbarExtenderConfig config = _configService.find( ActionbarResourceExtender.EXTENDER_TYPE,
                strIdExtendableResource, strExtendableResourceType );
        List<ActionButton> listActionsButtons;
        if ( config.getAllButtons( ) )
        {
            listActionsButtons = _actionbarService.findActionButtonsByResourceType( strExtendableResourceType );
        }
        else
        {
            listActionsButtons = _actionbarService.findActionButtons( config.getListActionButtonId( ) );
        }
        for ( ActionButton action : listActionsButtons )
        {
            String strHtmlContent = action.getHtmlContent( );
            strHtmlContent = strHtmlContent.replaceAll( PARAMETER_RESOURCE_ID, strIdExtendableResource );
            strHtmlContent = strHtmlContent.replaceAll( PARAMETER_RESOURCE_TYPE, strExtendableResourceType );
            action.setHtmlContent( strHtmlContent );
        }

        Map<String, Object> model = new HashMap<String, Object>( );
        model.put( MARK_ACTIONS, listActionsButtons );
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_ACTION_BAR, request.getLocale( ), model );
        return template.getHtml( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getConfigHtml( ResourceExtenderDTO resourceExtender, Locale locale, HttpServletRequest request )
    {
        // Method to get the html of the config modification screen
        Map<String, Object> model = new HashMap<String, Object>( );
        List<ActionButton> listActionButton = _actionbarService.findActionButtonsByResourceType( resourceExtender
                .getExtendableResourceType( ) );
        model.put( MARK_ACTION_BUTTONS, listActionButton );
        model.put( MARK_RESOURCE_EXTENDER_CONFIG, getConfig( resourceExtender.getIdExtender( ) ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MODIFY_ACTIONBAR_CONFIG, locale, model );
        return template.getHtml( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IExtenderConfig getConfig( int nIdExtender )
    {
        return _configService.find( nIdExtender );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSaveConfig( HttpServletRequest request, IExtenderConfig config ) throws ExtendErrorException
    {
        String strAllActions = request.getParameter( PARAMETER_ALL_ACTIONS );
        ActionbarExtenderConfig actionbarExtenderConfig = (ActionbarExtenderConfig) config;
        if ( Boolean.parseBoolean( strAllActions ) )
        {
            actionbarExtenderConfig.setAllButtons( true );
            actionbarExtenderConfig.setListActionButtonId( new ArrayList<Integer>( ) );
        }
        else
        {
            actionbarExtenderConfig.setAllButtons( false );
            String[] strButtons = request.getParameterValues( PARAMETER_ACTION_BUTTON );
            if ( strButtons != null && strButtons.length > 0 )
            {
                List<Integer> listActionsButtonId = new ArrayList<Integer>( );
                for ( String strActionId : strButtons )
                {
                    listActionsButtonId.add( Integer.parseInt( strActionId ) );
                }
                actionbarExtenderConfig.setListActionButtonId( listActionsButtonId );
            }
            else
            {
                actionbarExtenderConfig.setListActionButtonId( new ArrayList<Integer>( ) );
            }
        }
        _configService.update( actionbarExtenderConfig );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInfoHtml( ResourceExtenderDTO resourceExtender, Locale locale, HttpServletRequest request )
    {
        return StringUtils.EMPTY;
    }
}
