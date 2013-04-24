package fr.paris.lutece.plugins.extend.modules.actionbar.business.config;

import fr.paris.lutece.plugins.extend.business.extender.config.ExtenderConfig;

import java.util.ArrayList;
import java.util.List;


/**
 * Action bar extender configuration
 */
public class ActionbarExtenderConfig extends ExtenderConfig
{
    private List<Integer> _listActionButtonId = new ArrayList<Integer>( );
    private boolean _bAllButtons;

    /**
     * Get the list of id ActionButton associated to this configuration
     * @return A copy of the list of id ActionButton associated to this
     *         configuration
     */
    public List<Integer> getListActionButtonId( )
    {
        return new ArrayList<Integer>( _listActionButtonId );
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
     * Get the boolean that indicates if this action bar is associated with
     * every action buttons or not
     * @return True if this action bar is associated with every action buttons,
     *         false otherwise
     */
    public boolean getAllButtons( )
    {
        return _bAllButtons;
    }

    /**
     * Set the boolean that indicates if this action bar is associated with
     * every action buttons or not
     * @param bAllButtons True if this action bar is associated with every
     *            action buttons, false otherwise
     */
    public void setAllButtons( boolean bAllButtons )
    {
        this._bAllButtons = bAllButtons;
    }

}
