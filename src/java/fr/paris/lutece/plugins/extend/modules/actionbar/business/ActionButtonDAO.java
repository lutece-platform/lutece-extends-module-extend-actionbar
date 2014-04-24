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
package fr.paris.lutece.plugins.extend.modules.actionbar.business;

import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;


/**
 * DAO to manage ActionButton
 */
public class ActionButtonDAO implements IActionButtonDAO
{
    private static final String SQL_QUERY_SELECT = " SELECT id_action, name, html_content, resource_type, btn_order FROM extend_actionbar_action WHERE id_action = ? ";
    private static final String SQL_QUERY_SELECT_BY_NAME = " SELECT id_action, name, html_content, resource_type, btn_order FROM extend_actionbar_action WHERE name = ? ";
    private static final String SQL_QUERY_SELECT_BY_ORDER = " SELECT id_action, name, html_content, resource_type, btn_order FROM extend_actionbar_action WHERE btn_order = ? ";
    private static final String SQL_QUERY_SELECT_ALL = " SELECT id_action, name, html_content, resource_type, btn_order FROM extend_actionbar_action ORDER BY btn_order ASC ";
    private static final String SQL_QUERY_SELECT_ALL_BY_RESOURCE_TYPE = " SELECT id_action, name, html_content, resource_type, btn_order FROM extend_actionbar_action WHERE resource_type = '"
            + ActionButton.EVERY_RESOURCE_TYPE + "' OR resource_type = ? ORDER BY btn_order ASC ";
    private static final String SQL_QUERY_GET_NEW_ORDER = " SELECT MAX(btn_order) + 1 FROM extend_actionbar_action ";
    private static final String SQL_INSERT = " INSERT INTO extend_actionbar_action( id_action, name, html_content, resource_type, btn_order ) VALUES (?,?,?,?,?) ";
    private static final String SQL_DELETE = " DELETE FROM extend_actionbar_action WHERE id_action = ? ";
    private static final String SQL_UPDATE = " UPDATE extend_actionbar_action SET name = ?, html_content = ?, resource_type = ? WHERE id_action = ? ";
    private static final String SQL_QUERY_SELECT_FROM_LIST_ID = " SELECT id_action, name, html_content, resource_type, btn_order FROM extend_actionbar_action WHERE id_action IN ( ";
    private static final String SQL_ORDER_BY_BTN_ORDER = " ORDER BY btn_order ASC ";
    private static final String SQL_UPDATE_BTN_ORDER = " UPDATE extend_actionbar_action SET btn_order = ? WHERE id_action = ? ";

    private static final String SQL_UPDATE_FILL_BLANK_IN_ORDER = " UPDATE extend_actionbar_action SET btn_order = btn_order - 1 WHERE btn_order > ? ";
    private static final String SQL_QUERY_GET_NEW_PRIMARY_KEY = " SELECT MAX(id_action) FROM extend_actionbar_action ";

    private static final String CONSTANT_CLOSE_PARENTHESIS = ")";
    private static final String CONSTANT_COMMA = ",";
    private static final String CONSTANT_QUESTION_MARK = "?";

    /**
     * Get a new primary key for action buttons
     * @param plugin The plugin
     * @return The new primary key
     */
    private int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_GET_NEW_PRIMARY_KEY, plugin );
        daoUtil.executeQuery( );
        int res = 1;
        if ( daoUtil.next( ) )
        {
            res = daoUtil.getInt( 1 ) + 1;
        }

        daoUtil.free( );

        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create( ActionButton actionButton, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_INSERT, plugin );
        actionButton.setIdAction( newPrimaryKey( plugin ) );
        daoUtil.setInt( 1, actionButton.getIdAction( ) );
        daoUtil.setString( 2, actionButton.getName( ) );
        daoUtil.setString( 3, actionButton.getHtmlContent( ) );
        daoUtil.setString( 4, actionButton.getResourceType( ) );
        daoUtil.setInt( 5, getNewOrder( plugin ) );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionButton findById( int nIdActionButton, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nIdActionButton );
        daoUtil.executeQuery( );
        ActionButton action = null;
        if ( daoUtil.next( ) )
        {
            action = new ActionButton( );
            action.setIdAction( daoUtil.getInt( 1 ) );
            action.setName( daoUtil.getString( 2 ) );
            action.setHtmlContent( daoUtil.getString( 3 ) );
            action.setResourceType( daoUtil.getString( 4 ) );
            action.setOrder( daoUtil.getInt( 5 ) );
        }

        daoUtil.free( );

        return action;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update( ActionButton actionButton, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_UPDATE, plugin );
        daoUtil.setString( 1, actionButton.getName( ) );
        daoUtil.setString( 2, actionButton.getHtmlContent( ) );
        daoUtil.setString( 3, actionButton.getResourceType( ) );
        daoUtil.setInt( 4, actionButton.getIdAction( ) );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdActionButton, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_DELETE, plugin );
        daoUtil.setInt( 1, nIdActionButton );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionButton> findAll( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL, plugin );
        daoUtil.executeQuery( );
        List<ActionButton> listActions = new ArrayList<ActionButton>( );
        while ( daoUtil.next( ) )
        {
            ActionButton action = new ActionButton( );
            action.setIdAction( daoUtil.getInt( 1 ) );
            action.setName( daoUtil.getString( 2 ) );
            action.setHtmlContent( daoUtil.getString( 3 ) );
            action.setResourceType( daoUtil.getString( 4 ) );
            action.setOrder( daoUtil.getInt( 5 ) );
            listActions.add( action );
        }

        daoUtil.free( );

        return listActions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionButton> findAllByResourceType( String strResourceType, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL_BY_RESOURCE_TYPE, plugin );
        daoUtil.setString( 1, strResourceType );
        daoUtil.executeQuery( );
        List<ActionButton> listActions = new ArrayList<ActionButton>( );
        while ( daoUtil.next( ) )
        {
            ActionButton action = new ActionButton( );
            action.setIdAction( daoUtil.getInt( 1 ) );
            action.setName( daoUtil.getString( 2 ) );
            action.setHtmlContent( daoUtil.getString( 3 ) );
            action.setResourceType( daoUtil.getString( 4 ) );
            action.setOrder( daoUtil.getInt( 5 ) );
            listActions.add( action );
        }

        daoUtil.free( );

        return listActions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionButton findByName( String strName, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_NAME, plugin );
        daoUtil.setString( 1, strName );
        daoUtil.executeQuery( );
        ActionButton action = null;
        if ( daoUtil.next( ) )
        {
            action = new ActionButton( );
            action.setIdAction( daoUtil.getInt( 1 ) );
            action.setName( daoUtil.getString( 2 ) );
            action.setHtmlContent( daoUtil.getString( 3 ) );
            action.setResourceType( daoUtil.getString( 4 ) );
            action.setOrder( daoUtil.getInt( 5 ) );
        }

        daoUtil.free( );

        return action;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionButton> findActionButtons( List<Integer> listIdActions, Plugin plugin )
    {
        List<ActionButton> listActions = new ArrayList<ActionButton>( );
        if ( listIdActions != null && listIdActions.size( ) > 0 )
        {
            StringBuilder sbSql = new StringBuilder( SQL_QUERY_SELECT_FROM_LIST_ID );
            for ( int i = 0; i < listIdActions.size( ); i++ )
            {
                sbSql.append( CONSTANT_QUESTION_MARK );
                if ( i + 1 < listIdActions.size( ) )
                {
                    sbSql.append( CONSTANT_COMMA );
                }
            }
            sbSql.append( CONSTANT_CLOSE_PARENTHESIS );
            sbSql.append( SQL_ORDER_BY_BTN_ORDER );
            DAOUtil daoUtil = new DAOUtil( sbSql.toString( ), plugin );
            int nIndex = 1;
            for ( Integer nIdAction : listIdActions )
            {
                daoUtil.setInt( nIndex++, nIdAction );
            }
            daoUtil.executeQuery( );
            while ( daoUtil.next( ) )
            {
                ActionButton action = new ActionButton( );
                action.setIdAction( daoUtil.getInt( 1 ) );
                action.setName( daoUtil.getString( 2 ) );
                action.setHtmlContent( daoUtil.getString( 3 ) );
                action.setResourceType( daoUtil.getString( 4 ) );
                action.setOrder( daoUtil.getInt( 5 ) );
                listActions.add( action );
            }

            daoUtil.free( );
        }
        return listActions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNewOrder( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_GET_NEW_ORDER, plugin );
        daoUtil.executeQuery( );
        int nResult = 1;
        if ( daoUtil.next( ) )
        {
            nResult = daoUtil.getInt( 1 );
            if ( nResult == 0 )
            {
                nResult++;
            }
        }
        daoUtil.free( );
        return nResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateActionButtonOrder( int nIdAction, int nNewOrder, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_UPDATE_BTN_ORDER, plugin );
        daoUtil.setInt( 1, nNewOrder );
        daoUtil.setInt( 2, nIdAction );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionButton> findByOrder( int nNewOrder, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ORDER, plugin );
        daoUtil.setInt( 1, nNewOrder );
        daoUtil.executeQuery( );
        List<ActionButton> listAction = new ArrayList<ActionButton>( );
        if ( daoUtil.next( ) )
        {
            ActionButton action = new ActionButton( );
            action.setIdAction( daoUtil.getInt( 1 ) );
            action.setName( daoUtil.getString( 2 ) );
            action.setHtmlContent( daoUtil.getString( 3 ) );
            action.setResourceType( daoUtil.getString( 4 ) );
            action.setOrder( daoUtil.getInt( 5 ) );
            listAction.add( action );
        }

        daoUtil.free( );

        return listAction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fillBlankInOrder( int nOrder, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_UPDATE_FILL_BLANK_IN_ORDER, plugin );
        daoUtil.setInt( 1, nOrder );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }
}
