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
        insertActions( config.getIdExtender( ), config.getListActionButtonId( ), ActionbarPlugin.getPlugin( ) );
        insertActions( config.getIdExtender( ), config.getAndResetListAddedActionButtonId( ),
                ActionbarPlugin.getPlugin( ) );
        removeActions( config.getIdExtender( ), config.getAndResetListRemovedActionButtonId( ),
                ActionbarPlugin.getPlugin( ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store( ActionbarExtenderConfig config )
    {
        insertActions( config.getIdExtender( ), config.getAndResetListAddedActionButtonId( ),
                ActionbarPlugin.getPlugin( ) );
        removeActions( config.getIdExtender( ), config.getAndResetListRemovedActionButtonId( ),
                ActionbarPlugin.getPlugin( ) );
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
        while ( daoUtil.next( ) )
        {
            listActionsIds.add( daoUtil.getInt( 1 ) );
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
