package org.xmlsh.jsonxmlspeed.client;

import com.google.gwt.core.client.JavaScriptObject;

public class JSONResponse extends JavaScriptObject {
    // You can add some static fields here, like status codes, etc.

    /**
     * Required by {@link JavaScriptObject}
     */
    protected JSONResponse() { }

    /**
     * Uses <code>eval</code> to parse a JSON response from the server
     * 
     * @param responseString the raw string containing the JSON repsonse
     * @return an JavaScriptObject, already cast to an appropriate type
     */
    public static  final native JSONResponse getResponse(String responseString) /*-{
        // You should be able to use a safe parser here
        // (like the one from json.org)
        return eval('(' + responseString + ')');
    }-*/;
    
    final native int  walk( )/*-{
        
        var n = 0;
        var walk = function(o){
          if( o == null || typeof(o) == "undefined" ) 
             return;
             
          n++;
         
          for(var prop in o){
            n++;
            if(o.hasOwnProperty(prop)){
              var val = o[prop];

              if(typeof val == 'object'){
                  walk(val);
              }            
            }
             else
                var val = o ;
          }
        };

        walk( this );
        return n; 
        
    }-*/;
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

