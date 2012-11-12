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
    public static final String BEAN_NAME = "extend-actionbar.actionbarService";

    private Plugin _plugin;

    /**
     * Get the list of action buttons
     * @return The list of action buttons
     */
    public List<ActionButton> findAll( )
    {
        return ActionButtonHome.findAll( getPlugin( ) );
    }

    public void createActionButton( ActionButton actionButton )
    {
        ActionButtonHome.create( actionButton, getPlugin( ) );
    }

    public ActionButton findActionButton( int nIdAction )
    {
        return ActionButtonHome.findById( nIdAction, getPlugin( ) );
    }

    public void updateActionButton( ActionButton actionButton )
    {
        ActionButtonHome.update( actionButton, getPlugin( ) );
    }

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
