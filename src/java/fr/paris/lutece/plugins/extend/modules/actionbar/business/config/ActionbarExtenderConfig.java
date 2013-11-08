/*
 * Copyright (c) 2002-2013, Mairie de Paris
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
