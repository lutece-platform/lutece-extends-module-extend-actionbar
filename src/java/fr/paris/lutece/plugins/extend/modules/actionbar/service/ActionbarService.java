/*
 * Copyright (c) 2002-2013, Mairie de Paris
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
import fr.paris.lutece.plugins.extend.modules.actionbar.business.ActionButtonHome;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;

import java.util.List;


/**
 * ActionbarService
 */
public class ActionbarService
{
    /**
     * Bean name
     */
    public static final String BEAN_NAME = "extend-actionbar.actionbarService";

    private Plugin _plugin;

    /**
     * Get the list of action buttons associated with a resource type. Action
     * buttons associated with every resource types are also returned.
     * @param strResourceType The resource type of buttons
     * @return The list of action buttons
     */
    public List<ActionButton> findActionButtonsByResourceType( String strResourceType )
    {
        return ActionButtonHome.findAllByResourceType( strResourceType, getPlugin( ) );
    }

    /**
     * Get the list of action buttons
     * @return The list of action buttons
     */
    public List<ActionButton> findAllActionButtons( )
    {
        return ActionButtonHome.findAll( getPlugin( ) );
    }

    /**
     * Get a list of actions buttons from a list of ids
     * @param listIdActions The list of ids of action buttons to get
     * @return The list of action button, or an empty list if the specified list
     *         is null or empty, or if no action buttons was found
     */
    public List<ActionButton> findActionButtons( List<Integer> listIdActions )
    {
        return ActionButtonHome.findActionButtons( listIdActions, getPlugin( ) );
    }

    /**
     * Creates an action button
     * @param actionButton The action button to create
     */
    public void createActionButton( ActionButton actionButton )
    {
        ActionButtonHome.create( actionButton, getPlugin( ) );
    }

    /**
     * find an action button
     * @param nIdAction The id of the action button
     * @return The action button, or null if no action button has this id
     */
    public ActionButton findActionButton( int nIdAction )
    {
        return ActionButtonHome.findById( nIdAction, getPlugin( ) );
    }

    /**
     * Update an action button
     * @param actionButton The action button to update
     */
    public void updateActionButton( ActionButton actionButton )
    {
        ActionButtonHome.update( actionButton, getPlugin( ) );
    }

    /**
     * Remove an action button from its id
     * @param nIdAction The id of the action button to remove
     */
    public void removeActionButton( int nIdAction )
    {
        Plugin plugin = getPlugin( );
        ActionButtonHome.delete( ActionButtonHome.findById( nIdAction, plugin ), plugin );
    }

    /**
     * Update the order of an action button. The order of the given action
     * button is set to the new value, and the action button that had this order
     * gets the old order of the updated one.
     * @param action The action button to move. The order attribute of the
     *            action button <b>MUST</b> be its old order.
     * @param nNewOrder The new order of the topic
     */
    public void updateActionButtonOrder( ActionButton action, int nNewOrder )
    {
        ActionButtonHome.updateActionButtonOrder( action, nNewOrder, getPlugin( ) );
    }

    /**
     * Get the next available order
     * @return The next available order
     */
    public int getNewOrder( )
    {
        return ActionButtonHome.getNewOrder( getPlugin( ) );
    }

    /**
     * Get the plugin associated to this service
     * @return The plugin associated to this service
     */
    private Plugin getPlugin( )
    {
        if ( _plugin == null )
        {
            _plugin = PluginService.getPlugin( ActionbarPlugin.PLUGIN_NAME );
        }
        return _plugin;
    }
}
