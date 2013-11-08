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
package fr.paris.lutece.plugins.extend.modules.actionbar.business.config;

import fr.paris.lutece.plugins.extend.business.extender.config.IExtenderConfigDAO;
import fr.paris.lutece.plugins.extend.modules.actionbar.service.ActionbarPlugin;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * DAO to manage ActionbarExtenderConfig objects.
 */
public class ActionbarExtenderConfigDAO implements IExtenderConfigDAO<ActionbarExtenderConfig>
{
    private static final String SQL_QUERY_SELECT = " SELECT id_action FROM extend_actionbar_config WHERE id_extender = ? ";
    private static final String SQL_INSERT_ACTION_BUTTON = " INSERT INTO extend_actionbar_config( id_extender, id_action) VALUES ";
    private static final String SQL_REMOVE_ACTION_BUTTON = " DELETE FROM extend_actionbar_config WHERE id_extender = ? AND id_action IN ";

    private static final String SQL_DELETE_CONFIG = " DELETE FROM extend_actionbar_config WHERE id_extender = ? ";

    private static final String SQL_VALUE_SOCIAL_HUB = " (?,?) ";

    private static final String CONSTANT_OPEN_PARENTHESIS = " ( ";
    private static final String CONSTANT_CLOSE_PARENTHESIS = " ) ";
    private static final String CONSTANT_COMMA = ",";
    private static final String CONSTANT_QUESTION_MARK = "?";

    /**
     * Associate a list of actions to an extender config
     * @param nIdExtender The id of the extender
     * @param listIdActions The list of action button ids
     * @param plugin The plugin
     */
    private void insertActions( int nIdExtender, List<Integer> listIdActions, Plugin plugin )
    {
        if ( listIdActions == null || listIdActions.size( ) <= 0 )
        {
            return;
        }
        StringBuilder sbSql = new StringBuilder( SQL_INSERT_ACTION_BUTTON );
        for ( int i = 0; i < listIdActions.size( ) - 1; i++ )
        {
            sbSql.append( SQL_VALUE_SOCIAL_HUB );
            sbSql.append( CONSTANT_COMMA );
        }
        sbSql.append( SQL_VALUE_SOCIAL_HUB );

        DAOUtil daoUtil = new DAOUtil( sbSql.toString( ), ActionbarPlugin.getPlugin( ) );
        int nIndex = 0;
        for ( Integer nId : listIdActions )
        {
            daoUtil.setInt( ++nIndex, nIdExtender );
            daoUtil.setInt( ++nIndex, nId );
        }
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * Remove associations between a list of action buttons and an extender
     * config
     * @param nIdExtender The id of the extender
     * @param listIdActions The list of action button ids
     * @param plugin The plugin
     */
    private void removeActions( int nIdExtender, List<Integer> listIdActions, Plugin plugin )
    {
        if ( listIdActions == null || listIdActions.size( ) <= 0 )
        {
            return;
        }
        StringBuilder sbSql = new StringBuilder( SQL_REMOVE_ACTION_BUTTON );
        sbSql.append( CONSTANT_OPEN_PARENTHESIS );
        for ( int i = 0; i < listIdActions.size( ) - 1; i++ )
        {
            sbSql.append( CONSTANT_QUESTION_MARK );
            sbSql.append( CONSTANT_COMMA );
        }
        sbSql.append( CONSTANT_QUESTION_MARK );
        sbSql.append( CONSTANT_CLOSE_PARENTHESIS );

        DAOUtil daoUtil = new DAOUtil( sbSql.toString( ), ActionbarPlugin.getPlugin( ) );
        int nIndex = 0;
        daoUtil.setInt( ++nIndex, nIdExtender );

        for ( Integer nId : listIdActions )
        {
            daoUtil.setInt( ++nIndex, nId );
        }
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert( ActionbarExtenderConfig config )
    {
        List<Integer> listActionsId = config.getListActionButtonId( );
        if ( config.getAllButtons( ) )
        {
            listActionsId.add( -1 );
        }
        insertActions( config.getIdExtender( ), listActionsId, ActionbarPlugin.getPlugin( ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store( ActionbarExtenderConfig config )
    {
        ActionbarExtenderConfig oldConfig = load( config.getIdExtender( ) );
        List<Integer> listNewButtons = config.getListActionButtonId( );
        if ( oldConfig != null )
        {
            // We remove from the list of new buttons the buttons that has already been created
            listNewButtons.removeAll( oldConfig.getListActionButtonId( ) );
            // We generate the list of buttons to remove
            List<Integer> listOldButtons = oldConfig.getListActionButtonId( );
            listOldButtons.removeAll( config.getListActionButtonId( ) );

            // If we just removed every buttons, we add it the list of buttons to remove
            if ( oldConfig.getAllButtons( ) && !config.getAllButtons( ) )
            {
                listOldButtons.add( -1 );
            }
            // We remove old buttons
            removeActions( config.getIdExtender( ), listOldButtons, ActionbarPlugin.getPlugin( ) );

            // If we just added every buttons, we add it the list of buttons to save
            if ( config.getAllButtons( ) && !oldConfig.getAllButtons( ) )
            {
                listNewButtons.add( -1 );
            }
        }
        // We create new buttons
        insertActions( config.getIdExtender( ), listNewButtons, ActionbarPlugin.getPlugin( ) );

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionbarExtenderConfig load( int nIdExtender )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, ActionbarPlugin.getPlugin( ) );
        daoUtil.setInt( 1, nIdExtender );
        daoUtil.executeQuery( );

        ActionbarExtenderConfig actionbarExtenderConfig = new ActionbarExtenderConfig( );
        actionbarExtenderConfig.setIdExtender( nIdExtender );

        List<Integer> listActionsIds = new ArrayList<Integer>( );
        int nId = 1;
        // We break if we found -1 (which means every button)
        while ( nId > 0 && daoUtil.next( ) )
        {
            nId = daoUtil.getInt( 1 );
            if ( nId > 0 )
            {
                listActionsIds.add( nId );
            }
            else
            {
                actionbarExtenderConfig.setAllButtons( true );
                actionbarExtenderConfig.setListActionButtonId( new ArrayList<Integer>( ) );
            }
        }
        actionbarExtenderConfig.setListActionButtonId( listActionsIds );
        daoUtil.free( );
        return actionbarExtenderConfig;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdExtender )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_DELETE_CONFIG, ActionbarPlugin.getPlugin( ) );
        daoUtil.setInt( 1, nIdExtender );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }
}
