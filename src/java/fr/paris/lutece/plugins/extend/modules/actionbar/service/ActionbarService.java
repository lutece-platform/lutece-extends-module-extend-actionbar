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
        ActionButtonHome.delete( nIdAction, getPlugin( ) );
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
