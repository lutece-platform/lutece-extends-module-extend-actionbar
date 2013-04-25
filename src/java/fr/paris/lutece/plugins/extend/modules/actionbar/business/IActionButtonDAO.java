package fr.paris.lutece.plugins.extend.modules.actionbar.business;

import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.List;


/**
 * Interface of ActionButtonDAO
 */
public interface IActionButtonDAO
{
    /**
     * Create an action button
     * @param actionButton Action button to save
     * @param plugin The plugin
     */
    void create( ActionButton actionButton, Plugin plugin );

    /**
     * Get an action button from its id
     * @param nIdActionButton The id of the action button
     * @param plugin The plugin
     * @return The action button, or null if no action button has the given id
     */
    ActionButton findById( int nIdActionButton, Plugin plugin );

    /**
     * Update an action button
     * @param actionButton The action button to update
     * @param plugin The plugin
     */
    void update( ActionButton actionButton, Plugin plugin );

    /**
     * Delete an action button
     * @param nIdActionButton The id of the action button to delete
     * @param plugin The plugin
     */
    void delete( int nIdActionButton, Plugin plugin );

    /**
     * Find every action button
     * @param plugin The plugin
     * @return The list of action buttons
     */
    List<ActionButton> findAll( Plugin plugin );

    /**
     * Get the list of action buttons associated with a resource type. Action
     * buttons associated with every resource types are also returned.
     * @param strResourceType The resource type of buttons
     * @param plugin The plugin
     * @return The list of action buttons
     */
    List<ActionButton> findAllByResourceType( String strResourceType, Plugin plugin );

    /**
     * Find an action button from its name
     * @param strName The name of the action button
     * @param plugin The plugin
     * @return The action button, or null if no action button is associated with
     *         the given name
     */
    ActionButton findByName( String strName, Plugin plugin );

    /**
     * Get a list of actions buttons from a list of ids
     * @param listIdActions The list of ids of action buttons to get
     * @param plugin The plugin
     * @return The list of action button, or an empty list if the specified list
     *         is null or empty, or if no action buttons was found
     */
    List<ActionButton> findActionButtons( List<Integer> listIdActions, Plugin plugin );

    /**
     * Get the next available order
     * @param plugin The plugin
     * @return The next available order
     */
    int getNewOrder( Plugin plugin );

    /**
     * Update the order of an action button
     * @param nIdAction The id of the action to update the order of
     * @param nNewOrder The new order of the action button
     * @param plugin The plugin
     */
    void updateActionButtonOrder( int nIdAction, int nNewOrder, Plugin plugin );

    /**
     * Find action buttons by order. Only one action button should have a given
     * order
     * @param nNewOrder The new order
     * @param plugin The plugin
     * @return The list of action buttons with the given order
     */
    List<ActionButton> findByOrder( int nNewOrder, Plugin plugin );

    /**
     * Fill a blank in the order of action buttons. This method should be called
     * after the removal of an action button
     * @param order The id of the missing order
     * @param plugin The plugin
     */
    void fillBlankInOrder( int order, Plugin plugin );
}
