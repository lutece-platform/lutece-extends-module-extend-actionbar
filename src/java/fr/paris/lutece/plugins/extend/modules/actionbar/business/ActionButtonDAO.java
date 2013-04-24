package fr.paris.lutece.plugins.extend.modules.actionbar.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * DAO to manage ActionButton
 */
public class ActionButtonDAO implements IActionButtonDAO
{
    private static final String SQL_QUERY_SELECT = " SELECT id_action, name, html_content FROM extend_actionbar_action WHERE id_action = ? ";
    private static final String SQL_QUERY_SELECT_BY_NAME = " SELECT id_action, name, html_content FROM extend_actionbar_action WHERE name = ? ";
    private static final String SQL_QUERY_SELECT_ALL = " SELECT id_action, name, html_content FROM extend_actionbar_action ";
    private static final String SQL_INSERT = " INSERT INTO extend_actionbar_action( id_action, name, html_content) VALUES (?,?,?) ";
    private static final String SQL_DELETE = " DELETE FROM extend_actionbar_action WHERE id_action = ? ";
    private static final String SQL_UPDATE = " UPDATE extend_actionbar_action SET name = ?, html_content = ? WHERE id_action = ? ";
    private static final String SQL_QUERY_SELECT_FROM_LIST_ID = " SELECT id_action, name, html_content FROM extend_actionbar_action WHERE id_action IN ( ";

    private static final String SQL_QUERY_GET_NEW_PRIMARY_KEY = " SELECT MAX(id_action) FROM extend_actionbar_action ";

    private static final String CONSTANT_CLOSE_PARENTHESIS = ")";
    private static final String CONSTANT_COMMA = ",";
    private static final String CONSTANT_QUESTION_MARK = "?";

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
        daoUtil.setInt( 3, actionButton.getIdAction( ) );
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
                listActions.add( action );
            }

            daoUtil.free( );
        }
        return listActions;
    }

}
