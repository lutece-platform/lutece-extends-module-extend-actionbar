package fr.paris.lutece.plugins.extend.modules.actionbar.business;

/**
 * Action button
 */
public class ActionButton
{
    /**
     * ActionButton resource type
     */
    public static final String RESOURCE_TYPE = "ACTION_BUTTON";

    private int _nIdAction;
    private String _strName;
    private String _strHtmlContent;

    /**
     * Get the id of the action button
     * @return The id of the action button
     */
    public int getIdAction( )
    {
        return _nIdAction;
    }

    /**
     * Set the id of the action button
     * @param nIdAction The id of the action button
     */
    public void setIdAction( int nIdAction )
    {
        this._nIdAction = nIdAction;
    }

    /**
     * Get the name of the action button
     * @return The name of the action button
     */
    public String getName( )
    {
        return _strName;
    }

    /**
     * Set the name of the action button
     * @param strName The name of the action button
     */
    public void setName( String strName )
    {
        this._strName = strName;
    }

    /**
     * Get the html content of the action button
     * @return The html content of the action button
     */
    public String getHtmlContent( )
    {
        return _strHtmlContent;
    }

    /**
     * Set the html content of the action button
     * @param strHtmlContent The html content of the action button
     */
    public void setHtmlContent( String strHtmlContent )
    {
        this._strHtmlContent = strHtmlContent;
    }

}
