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
    /**
     * Resource type value indicating that the action button can be used for
     * every resource type
     */
    public static final String EVERY_RESOURCE_TYPE = "*";

    private int _nIdAction;
    private String _strName;
    private String _strHtmlContent;
    private String _strResourceType;
    private int _nOrder;

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

    /**
     * Get the resource type associated with this action button
     * @return The resource type associated with this action button
     */
    public String getResourceType( )
    {
        return _strResourceType;
    }

    /**
     * Set the resource type associated with this action button
     * @param strResourceType The resource type associated with this action
     *            button
     */
    public void setResourceType( String strResourceType )
    {
        this._strResourceType = strResourceType;
    }

    /**
     * Get the order of the action button
     * @return The order of the action button
     */
    public int getOrder( )
    {
        return _nOrder;
    }

    /**
     * Set the order of the action button
     * @param nOrder The order of the action button
     */
    public void setOrder( int nOrder )
    {
        this._nOrder = nOrder;
    }

}
