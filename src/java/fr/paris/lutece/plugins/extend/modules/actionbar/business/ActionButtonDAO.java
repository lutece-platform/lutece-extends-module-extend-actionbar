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

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * DAO to manage ActionButton
 */
@ApplicationScoped
public class ActionButtonDAO implements IActionButtonDAO
{
    private static final String SQL_QUERY_SELECT_COLUMNS = " SELECT id_action, name, html_content, resource_type, btn_order FROM extend_actionbar_action ";
    private static final String SQL_QUERY_SELECT = SQL_QUERY_SELECT_COLUMNS + " WHERE id_action = ? ";
    private static final String SQL_QUERY_SELECT_BY_NAME = SQL_QUERY_SELECT_COLUMNS + " WHERE name = ? ";
    private static final String SQL_QUERY_SELECT_BY_ORDER = SQL_QUERY_SELECT_COLUMNS + " WHERE btn_order = ? ";
    private static final String SQL_QUERY_SELECTALL = SQL_QUERY_SELECT_COLUMNS + " ORDER BY btn_order ASC ";
    private static final String SQL_QUERY_SELECT_ALL_BY_RESOURCE_TYPE = SQL_QUERY_SELECT_COLUMNS + " WHERE resource_type = '"
            + ActionButton.EVERY_RESOURCE_TYPE + "' OR resource_type = ? ORDER BY btn_order ASC ";
    private static final String SQL_QUERY_GET_NEW_ORDER = " SELECT MAX(btn_order) + 1 FROM extend_actionbar_action ";
    private static final String SQL_QUERY_INSERT = " INSERT INTO extend_actionbar_action( name, html_content, resource_type, btn_order ) VALUES (?,?,?,?) ";
    private static final String SQL_QUERY_DELETE = " DELETE FROM extend_actionbar_action WHERE id_action = ? ";
    private static final String SQL_QUERY_UPDATE = " UPDATE extend_actionbar_action SET name = ?, html_content = ?, resource_type = ? WHERE id_action = ? ";
    private static final String SQL_QUERY_SELECT_FROM_LIST_ID = SQL_QUERY_SELECT_COLUMNS + " WHERE id_action IN ( ";
    private static final String SQL_ORDER_BY_BTN_ORDER = " ORDER BY btn_order ASC ";
    private static final String SQL_QUERY_UPDATE_BTN_ORDER = " UPDATE extend_actionbar_action SET btn_order = ? WHERE id_action = ? ";
    private static final String SQL_QUERY_UPDATE_FILL_BLANK_IN_ORDER = " UPDATE extend_actionbar_action SET btn_order = btn_order - 1 WHERE btn_order > ? ";

    private static final String CONSTANT_CLOSE_PARENTHESIS = ")";
    private static final String CONSTANT_COMMA = ",";
    private static final String CONSTANT_QUESTION_MARK = "?";

    /**
     * {@inheritDoc}
     */
    @Override
    public void create( ActionButton actionButton, Plugin plugin )
    {
        int nOrder = getNewOrder( plugin );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++, actionButton.getName( ) );
            daoUtil.setString( nIndex++, actionButton.getHtmlContent( ) );
            daoUtil.setString( nIndex++, actionButton.getResourceType( ) );
            daoUtil.setInt( nIndex, nOrder );
            daoUtil.executeUpdate( );
            if ( daoUtil.nextGeneratedKey( ) )
            {
                actionButton.setIdAction( daoUtil.getGeneratedKeyInt( 1 ) );
            }
            actionButton.setOrder( nOrder );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionButton findById( int nIdActionButton, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
            daoUtil.setInt( 1, nIdActionButton );
            daoUtil.executeQuery( );
            ActionButton action = null;
            if ( daoUtil.next( ) )
            {
                action = load( daoUtil );
            }
            return action;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update( ActionButton actionButton, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++, actionButton.getName( ) );
            daoUtil.setString( nIndex++, actionButton.getHtmlContent( ) );
            daoUtil.setString( nIndex++, actionButton.getResourceType( ) );
            daoUtil.setInt( nIndex, actionButton.getIdAction( ) );
            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdActionButton, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin ) )
        {
            daoUtil.setInt( 1, nIdActionButton );
            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionButton> findAll( Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery( );
            return loadList( daoUtil );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionButton> findAllByResourceType( String strResourceType, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL_BY_RESOURCE_TYPE, plugin ) )
        {
            daoUtil.setString( 1, strResourceType );
            daoUtil.executeQuery( );
            return loadList( daoUtil );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionButton findByName( String strName, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_NAME, plugin ) )
        {
            daoUtil.setString( 1, strName );
            daoUtil.executeQuery( );
            ActionButton action = null;
            if ( daoUtil.next( ) )
            {
                action = load( daoUtil );
            }
            return action;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionButton> findActionButtons( List<Integer> listIdActions, Plugin plugin )
    {
        if ( listIdActions == null || listIdActions.isEmpty( ) )
        {
            return new ArrayList<>( );
        }
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
        try ( DAOUtil daoUtil = new DAOUtil( sbSql.toString( ), plugin ) )
        {
            int nIndex = 1;
            for ( Integer nIdAction : listIdActions )
            {
                daoUtil.setInt( nIndex++, nIdAction );
            }
            daoUtil.executeQuery( );
            return loadList( daoUtil );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNewOrder( Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_GET_NEW_ORDER, plugin ) )
        {
            daoUtil.executeQuery( );
            int nResult = 1;
            if ( daoUtil.next( ) )
            {
                nResult = Math.max( daoUtil.getInt( 1 ), 1 );
            }
            return nResult;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateActionButtonOrder( int nIdAction, int nNewOrder, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE_BTN_ORDER, plugin ) )
        {
            daoUtil.setInt( 1, nNewOrder );
            daoUtil.setInt( 2, nIdAction );
            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionButton> findByOrder( int nNewOrder, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ORDER, plugin ) )
        {
            daoUtil.setInt( 1, nNewOrder );
            daoUtil.executeQuery( );
            return loadList( daoUtil );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void fillBlankInOrder( int nOrder, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE_FILL_BLANK_IN_ORDER, plugin ) )
        {
            daoUtil.setInt( 1, nOrder );
            daoUtil.executeUpdate( );
        }
    }

    /**
     * Build the action buttons of every remaining row of a query
     * @param daoUtil The executed query
     * @return The list of action buttons
     */
    private static List<ActionButton> loadList( DAOUtil daoUtil )
    {
        List<ActionButton> listActions = new ArrayList<>( );
        while ( daoUtil.next( ) )
        {
            listActions.add( load( daoUtil ) );
        }
        return listActions;
    }

    /**
     * Build an action button from the current row of a query
     * @param daoUtil The executed query, positioned on a row
     * @return The action button
     */
    private static ActionButton load( DAOUtil daoUtil )
    {
        ActionButton action = new ActionButton( );
        int nIndex = 1;
        action.setIdAction( daoUtil.getInt( nIndex++ ) );
        action.setName( daoUtil.getString( nIndex++ ) );
        action.setHtmlContent( daoUtil.getString( nIndex++ ) );
        action.setResourceType( daoUtil.getString( nIndex++ ) );
        action.setOrder( daoUtil.getInt( nIndex ) );
        return action;
    }
}
