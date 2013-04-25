package fr.paris.lutece.plugins.extend.modules.actionbar.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.List;


/**
 * Home for ActionButton management
 */
public final class ActionButtonHome
{
    private static IActionButtonDAO _dao = SpringContextService.getBean( "extend-actionbar.actionButtonDAO" );

    /**
     * Private constructor
     */
    private ActionButtonHome( )
    {

    }

    /**
     * Create an action button
     * @param actionButton Action button to save
     * @param plugin The plugin
     */
    public static synchronized void create( ActionButton actionButton, Plugin plugin )
    {
        _dao.create( actionButton, plugin );
    }

    /**
     * Get an action button from its id
     * @param nIdActionButton The id of the action button
     * @param plugin The plugin
     * @return The action button, or null if no action button has the given id
     */
    public static ActionButton findById( int nIdActionButton, Plugin plugin )
    {
        return _dao.findById( nIdActionButton, plugin );
    }

    /**
     * Update an action button
     * @param actionButton The action button to update
     * @param plugin The plugin
     */
    public static void update( ActionButton actionButton, Plugin plugin )
    {
        _dao.update( actionButton, plugin );
    }

    /**
     * Delete an action button
     * @param actionButton The action button to delete
     * @param plugin The plugin
     */
    public static synchronized void delete( ActionButton actionButton, Plugin plugin )
    {
        _dao.delete( actionButton.getIdAction( ), plugin );
        _dao.fillBlankInOrder( actionButton.getOrder( ), plugin );
    }

    /**
     * Find every action button
     * @param plugin The plugin
     * @return The list of action buttons
     */
    public static List<ActionButton> findAll( Plugin plugin )
    {
        return _dao.findAll( plugin );
    }

    /**
     * Get the list of action buttons associated with a resource type. Action
     * buttons associated with every resource types are also returned.
     * @param strResourceType The resource type of buttons
     * @param plugin The plugin
     * @return The list of action buttons
     */
    public static List<ActionButton> findAllByResourceType( String strResourceType, Plugin plugin )
    {
        return _dao.findAllByResourceType( strResourceType, plugin );
    }

    /**
     * Find an action button from its name
     * @param strName The name of the action button
     * @param plugin The plugin
     * @return The action button, or null if no action button is associated with
     *         the given name
     */
    public static ActionButton findByName( String strName, Plugin plugin )
    {
        return _dao.findByName( strName, plugin );
    }

    /**
     * Get a list of actions buttons from a list of ids
     * @param listIdActions The list of ids of action buttons to get
     * @param plugin The plugin
     * @return The list of action button, or an empty list if the specified list
     *         is null or empty, or if no action buttons was found
     */
    public static List<ActionButton> findActionButtons( List<Integer> listIdActions, Plugin plugin )
    {
        return _dao.findActionButtons( listIdActions, plugin );
    }

    /**
     * Update the order of an action button. The order of the given action
     * button is set to the new value, and the action button that had this order
     * gets the old order of the updated one.
     * @param action The action button to move. The order attribute of the
     *            action button <b>MUST</b> be its old order.
     * @param nNewOrder The new order of the topic
     * @param plugin The plugin
     */
    public static synchronized void updateActionButtonOrder( ActionButton action, int nNewOrder, Plugin plugin )
    {
        List<ActionButton> listActionButtons = _dao.findByOrder( nNewOrder, plugin );
        if ( listActionButtons != null && listActionButtons.size( ) > 0 )
        {
            _dao.updateActionButtonOrder( listActionButtons.get( 0 ).getIdAction( ), action.getOrder( ), plugin );
            // If there is more than one button that had the new id of the current button (which should not happen), we put them at the end of the list
            if ( listActionButtons.size( ) > 1 )
            {
                listActionButtons.remove( 0 );
                int nNextOrder = _dao.getNewOrder( plugin );
                for ( ActionButton actionButton : listActionButtons )
                {
                    _dao.updateActionButtonOrder( actionButton.getIdAction( ), nNextOrder++, plugin );
                }
            }
        }
        _dao.updateActionButtonOrder( action.getIdAction( ), nNewOrder, plugin );
    }

    /**
     * Get the next available order
     * @param plugin The plugin
     * @return The next available order
     */
    public static int getNewOrder( Plugin plugin )
    {
        return _dao.getNewOrder( plugin );
    }
}
