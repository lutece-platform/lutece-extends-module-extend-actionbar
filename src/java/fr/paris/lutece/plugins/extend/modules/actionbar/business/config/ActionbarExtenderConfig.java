package fr.paris.lutece.plugins.extend.modules.actionbar.business.config;

import fr.paris.lutece.plugins.extend.business.extender.config.ExtenderConfig;

import java.util.ArrayList;
import java.util.List;


/**
 * Action bar extender configuration
 */
public class ActionbarExtenderConfig extends ExtenderConfig
{
    List<Integer> _listActionButtonId = new ArrayList<Integer>( );
    List<Integer> _listAddedActionButtonId = new ArrayList<Integer>( );
    List<Integer> _listRemovedActionButtonId = new ArrayList<Integer>( );

    /**
     * Get the list of id ActionButton associated to this configuration
     * @return The list of id ActionButton associated to this
     *         configuration
     */
    public List<Integer> getListActionButtonId( )
    {
        return new ArrayList<Integer>( _listActionButtonId );
    }

    /**
     * Add an ActionButton id to the list of ActionButton id
     * associated to this config
     * @param nActionButtonId The id to add
     */
    public void addActionButtonId( Integer nActionButtonId )
    {
        _listAddedActionButtonId.add( nActionButtonId );
        _listActionButtonId.add( nActionButtonId );
    }

    /**
     * Remove an ActionButton id from the list of ActionButton id
     * associated to this config
     * @param nActionButtonId The id to remove
     */
    public void removeActionButtonId( Integer nActionButtonId )
    {
        _listRemovedActionButtonId.add( nActionButtonId );
        _listActionButtonId.remove( nActionButtonId );
    }

    /**
     * Get the list of id ActionButton associated to this configuration
     * @param listActionButtonId The list of id ActionButton
     *            associated to this configuration
     */
    public void setListActionButtonId( List<Integer> listActionButtonId )
    {
        _listActionButtonId = listActionButtonId;
    }

    /**
     * Get the list of added ActionButton ids, and reset it.
     * @return The list of added ActionButton ids.
     */
    public List<Integer> getAndResetListAddedActionButtonId( )
    {
        List<Integer> listAdded = _listAddedActionButtonId;
        _listAddedActionButtonId = new ArrayList<Integer>( );
        return listAdded;
    }

    /**
     * Get the list of removed ActionButton ids, and reset it.
     * @return The list of removed ActionButton ids.
     */
    public List<Integer> getAndResetListRemovedActionButtonId( )
    {
        List<Integer> listRemoved = _listRemovedActionButtonId;
        _listRemovedActionButtonId = new ArrayList<Integer>( );
        return listRemoved;
    }

}
