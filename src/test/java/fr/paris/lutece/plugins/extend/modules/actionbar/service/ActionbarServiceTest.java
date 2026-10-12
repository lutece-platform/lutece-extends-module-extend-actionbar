/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.extend.modules.actionbar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.paris.lutece.plugins.extend.modules.actionbar.business.ActionButton;
import fr.paris.lutece.plugins.extend.modules.actionbar.business.config.ActionbarExtenderConfig;
import fr.paris.lutece.plugins.extend.modules.actionbar.service.extender.ActionbarExtenderConfigServiceProducer;
import fr.paris.lutece.plugins.extend.modules.actionbar.service.extender.ActionbarResourceExtender;
import fr.paris.lutece.plugins.extend.service.extender.AbstractResourceExtender;
import fr.paris.lutece.plugins.extend.service.extender.IResourceExtender;
import fr.paris.lutece.plugins.extend.service.extender.config.IResourceExtenderConfigService;
import fr.paris.lutece.test.LuteceTestCase;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Action bar service, config service and extender tests.
 */
public class ActionbarServiceTest extends LuteceTestCase
{
    private static final String RESOURCE_TYPE = "unit_test_type";
    private static final int ID_EXTENDER = 987654;

    @Inject
    private ActionbarService _service;

    @Inject
    @Named( ActionbarExtenderConfigServiceProducer.BEAN_NAME )
    private IResourceExtenderConfigService _configService;

    /**
     * Creates, updates, moves and removes action buttons.
     */
    @Test
    public void testActionButtonLifecycle( )
    {
        ActionButton first = newButton( "first" );
        ActionButton second = newButton( "second" );
        _service.createActionButton( first );
        _service.createActionButton( second );

        assertTrue( first.getIdAction( ) > 0, "generated id" );
        assertTrue( second.getIdAction( ) > first.getIdAction( ), "second id" );
        ActionButton stored = _service.findActionButton( first.getIdAction( ) );
        assertNotNull( stored, "stored" );
        assertEquals( "first", stored.getName( ), "name" );
        assertEquals( first.getOrder( ) + 1, _service.findActionButton( second.getIdAction( ) ).getOrder( ), "order" );

        stored.setName( "renamed" );
        stored.setHtmlContent( "<a href=\"#\">x</a>" );
        _service.updateActionButton( stored );
        assertEquals( "renamed", _service.findActionButton( first.getIdAction( ) ).getName( ), "updated name" );

        _service.updateActionButtonOrder( _service.findActionButton( second.getIdAction( ) ), first.getOrder( ) );
        assertEquals( first.getOrder( ), _service.findActionButton( second.getIdAction( ) ).getOrder( ), "moved up" );
        assertEquals( first.getOrder( ) + 1, _service.findActionButton( first.getIdAction( ) ).getOrder( ), "moved down" );

        List<ActionButton> listByType = _service.findActionButtonsByResourceType( RESOURCE_TYPE );
        assertTrue( listByType.stream( ).anyMatch( b -> b.getIdAction( ) == first.getIdAction( ) ), "by resource type" );
        assertEquals( 2, _service.findActionButtons( Arrays.asList( first.getIdAction( ), second.getIdAction( ) ) ).size( ), "by ids" );

        int nOrderFirst = _service.findActionButton( first.getIdAction( ) ).getOrder( );
        _service.removeActionButton( second.getIdAction( ) );
        assertNull( _service.findActionButton( second.getIdAction( ) ), "removed" );
        assertEquals( nOrderFirst - 1, _service.findActionButton( first.getIdAction( ) ).getOrder( ), "order gap filled" );
        _service.removeActionButton( first.getIdAction( ) );
    }

    /**
     * Stores the buttons of an action bar, then every button, then removes the config.
     */
    @Test
    public void testExtenderConfig( )
    {
        ActionbarExtenderConfig config = new ActionbarExtenderConfig( );
        config.setIdExtender( ID_EXTENDER );
        config.setListActionButtonId( Arrays.asList( 1, 2 ) );
        _configService.create( config );

        ActionbarExtenderConfig stored = _configService.find( ID_EXTENDER );
        assertFalse( stored.getAllButtons( ), "not all buttons" );
        assertEquals( 2, stored.getListActionButtonId( ).size( ), "two buttons" );

        stored.setAllButtons( true );
        stored.setListActionButtonId( Arrays.asList( ) );
        _configService.update( stored );
        stored = _configService.find( ID_EXTENDER );
        assertTrue( stored.getAllButtons( ), "all buttons" );
        assertTrue( stored.getListActionButtonId( ).isEmpty( ), "no explicit button" );

        _configService.remove( ID_EXTENDER );
    }

    /**
     * The extender is a CDI bean the extend plugin finds by its key.
     */
    @Test
    public void testExtenderDiscovered( )
    {
        IResourceExtender extender = CDI.current( ).select( IResourceExtender.class ).stream( )
                .filter( e -> ActionbarResourceExtender.EXTENDER_TYPE.equals( e.getKey( ) ) ).findFirst( ).orElse( null );
        assertNotNull( extender, "extender" );
        assertTrue( extender.isConfigRequired( ), "config required" );
        assertNotNull( ( (AbstractResourceExtender) extender ).getResourceExtenderComponent( ), "component" );
    }

    /**
     * Build an action button
     * @param strName The name
     * @return The action button
     */
    private static ActionButton newButton( String strName )
    {
        ActionButton button = new ActionButton( );
        button.setName( strName );
        button.setHtmlContent( "<span>" + strName + "</span>" );
        button.setResourceType( RESOURCE_TYPE );
        return button;
    }
}
