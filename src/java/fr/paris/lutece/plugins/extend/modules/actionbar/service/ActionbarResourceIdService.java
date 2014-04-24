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
package fr.paris.lutece.plugins.extend.modules.actionbar.service;

import fr.paris.lutece.plugins.extend.modules.actionbar.business.ActionButton;
import fr.paris.lutece.portal.service.rbac.Permission;
import fr.paris.lutece.portal.service.rbac.ResourceIdService;
import fr.paris.lutece.portal.service.rbac.ResourceType;
import fr.paris.lutece.portal.service.rbac.ResourceTypeManager;
import fr.paris.lutece.util.ReferenceList;

import java.util.Locale;


/**
 * ActionbarResourceIdService
 */
public class ActionbarResourceIdService extends ResourceIdService
{
    /**
     * Permission key to create action buttons
     */
    public static final String PERMISSION_ADD_ACTION_BUTTON = "ADD_ACTION_BUTTON";
    /**
     * Permission key to modify action buttons
     */
    public static final String PERMISSION_MODIFY_ACTION_BUTTON = "MODIFY_ACTION_BUTTON";
    /**
     * Permission key to remove action buttons
     */
    public static final String PERMISSION_REMOVE_ACTION_BUTTON = "REMOVE_ACTION_BUTTON";

    private static final String PROPERTY_LABEL_RESOURCE_TYPE = "module.extend.actionbar.resourceType.label";
    private static final String PROPERTY_LABEL_ADD_ACTION_BUTTON = "module.extend.actionbar.permission.label.addActionButton";
    private static final String PROPERTY_LABEL_MODIFY_ACTION_BUTTON = "module.extend.actionbar.permission.label.modifyActionButton";
    private static final String PROPERTY_LABEL_REMOVE_ACTION_BUTTON = "module.extend.actionbar.permission.label.removeActionButton";

    /**
     * Instantiates a new resource type resource id service.
     */
    public ActionbarResourceIdService( )
    {
        setPluginName( ActionbarPlugin.PLUGIN_NAME );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void register( )
    {
        ResourceType rt = new ResourceType( );
        rt.setResourceIdServiceClass( ActionbarResourceIdService.class.getName( ) );
        rt.setPluginName( ActionbarPlugin.PLUGIN_NAME );
        rt.setResourceTypeKey( ActionButton.RESOURCE_TYPE );
        rt.setResourceTypeLabelKey( PROPERTY_LABEL_RESOURCE_TYPE );

        Permission p = new Permission( );
        p.setPermissionKey( PERMISSION_ADD_ACTION_BUTTON );
        p.setPermissionTitleKey( PROPERTY_LABEL_ADD_ACTION_BUTTON );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( PERMISSION_MODIFY_ACTION_BUTTON );
        p.setPermissionTitleKey( PROPERTY_LABEL_MODIFY_ACTION_BUTTON );
        rt.registerPermission( p );

        p = new Permission( );
        p.setPermissionKey( PERMISSION_REMOVE_ACTION_BUTTON );
        p.setPermissionTitleKey( PROPERTY_LABEL_REMOVE_ACTION_BUTTON );
        rt.registerPermission( p );

        ResourceTypeManager.registerResourceType( rt );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferenceList getResourceIdList( Locale locale )
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle( String strId, Locale locale )
    {
        return null;
    }
}
