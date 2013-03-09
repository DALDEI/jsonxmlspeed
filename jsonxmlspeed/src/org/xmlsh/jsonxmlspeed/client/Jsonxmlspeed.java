package org.xmlsh.jsonxmlspeed.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Jsonxmlspeed implements EntryPoint
{

    public static final String kVERSION="1.1";
    public static boolean bDebug = false ;
    
    Shell shell;
    /**
     * This is the entry point method.
     */
    public void onModuleLoad()
    {
      
     // Add the nameField and sendButton to the RootPanel
        // Use RootPanel.get() to get the entire body element
        RootLayoutPanel rootPanel = RootLayoutPanel.get();
        
        rootPanel.add( shell = new Shell() );
        
        bDebug = Window.Location.getParameter("debug") != null ;
        
        
    }
    
    static boolean isDebug( ){ return bDebug ; }
}

//
//
//Copyright (C) 2013  David A. Lee.
//
//The contents of this file are subject to the "Simplified BSD License" (the "License");
//you may not use this file except in compliance with the License. You may obtain a copy of the
//License at http://www.opensource.org/licenses/bsd-license.php 
//
//Software distributed under the License is distributed on an "AS IS" basis,
//WITHOUT WARRANTY OF ANY KIND, either express or implied.
//See the License for the specific language governing rights and limitations under the License.
//
//The Original Code is: all this file.
//
//The Initial Developer of the Original Code is David A. Lee
//
//Portions created by (your name) are Copyright (C) (your legal entity). All Rights Reserved.
//
//Contributor(s): none.
//

