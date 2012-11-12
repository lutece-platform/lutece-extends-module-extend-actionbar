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
     * Find an action button from its name
     * @param strName The name of the action button
     * @param plugin The plugin
     * @return The action button, or null if no action button is associated with
     *         the given name
     */
    ActionButton findByName( String strName, Plugin plugin );
}
