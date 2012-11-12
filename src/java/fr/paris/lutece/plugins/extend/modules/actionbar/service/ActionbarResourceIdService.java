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
    public static final String PERMISSION_ADD_ACTION_BUTTON = "ADD_ACTION_BUTTON";
    public static final String PERMISSION_MODIFY_ACTION_BUTTON = "MODIFY_ACTION_BUTTON";
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
