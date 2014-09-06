package org.xmlsh.jsonxmlspeed.client;

import com.google.gwt.core.client.JavaScriptObject;


public final class XMLResponse   extends JavaScriptObject 
{


    /**
     * Required by {@link JavaScriptObject}
     */
    protected XMLResponse() { }
    public static  final native  XMLResponse getResponse(String responseString)  /*-{
        
        function getIEParser() {
            try { return new ActiveXObject("Msxml2.DOMDocument"); } catch (e) { }
            try { return new ActiveXObject("MSXML.DOMDocument"); } catch (e) { }
            try { return new ActiveXObject("MSXML3.DOMDocument"); } catch (e) { }
            try { return new ActiveXObject("Microsoft.XmlDom"); } catch (e) { }
            try { return new ActiveXObject("Microsoft.DOMDocument"); } catch (e) { }
            
            throw new Error("XMLParserImplIE6.createDocumentImpl: Could not find appropriate version of DOMDocument.");

        };
        
        
            if ($wnd.DOMParser)
            {
              parser=new DOMParser();
              xmlDoc=parser.parseFromString(responseString,"text/xml");
            }
            else // Internet Explorer
            {
              xmlDoc=  getIEParser();
              xmlDoc.async=false;
              xmlDoc.loadXML(txt); 
            }

        return xmlDoc ;

    }-*/;

    final  native int walk()/*-{
    
    
    var n =0;
    var walk = function( o )
    {
        if( o == null || typeof(o) == "undefined" )
           return ;
           
        n++;
        if( o.attributes != null && typeof(  o.attributes) != "undefined" )
            for( var x = 0; x <  o.attributes.length; x++ ) {
                n++;
            }
        
        if(  o.childNodes != null && typeof(  o.childNodes) != "undefined" )
            for( var x = 0; x <  o.childNodes.length; x++ ) {
                walk(  o.childNodes[x] );
                
            }
        
    }
    walk(this);
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


