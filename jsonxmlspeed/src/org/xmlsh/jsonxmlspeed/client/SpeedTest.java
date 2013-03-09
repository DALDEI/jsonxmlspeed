package org.xmlsh.jsonxmlspeed.client;

import java.util.Date;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;

public class SpeedTest
{

    DataFile file;

    long start = 0;
    long network_raw = 0;
    long size_raw  = 0;
    long network_compressed = 0;
    long size_compressed = 0  ;
    long compressed_size = 0;
    long js_parse = 0 ;
    long js_query = 0;
    long jquery_parse = 0 ;
    long jquery_query = 0;
    
    DataFilePanel notifyPanel;

    private int  js_nodes = 0;
    private int  jq_nodes = 0;

    
    public SpeedTest(DataFile file)
    {
       this.file = file ;       
    }
    
    
    public void run(DataFilePanel notify){
        
        notifyPanel = notify;
        doGet( file.getUrl() );
        
    }
    
    
    private void getNoCompress( String url ,RequestCallback callback ){
        // Force no caching - hack
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url + "?" + new Date().getTime()); 


        try {
            
          builder.setHeader("Cache-Control", "no-cache");
          builder.setHeader("X-Compress", "false");
          Request response = builder.sendRequest(null , callback );
        } catch (RequestException e) {
            Window.alert(e.getLocalizedMessage());
        }
          
        
    }
    private void getCompress( String url ,RequestCallback callback ){
        // Force no caching - hack
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url + "?" + new Date().getTime()); 


        try {
            
            
          builder.setHeader("Cache-Control", "no-cache");
          builder.setHeader("X-Compress", "true");
          Request response = builder.sendRequest(null , callback );
        } catch (RequestException e) {
            Window.alert(e.getLocalizedMessage());
        }
          
        
    }
    
    public  void doGet(final String url) {
        

        
        start = System.currentTimeMillis();
        
        try {
            
           getNoCompress( url , new RequestCallback() {

            
            public void onError(Request request, Throwable exception) {
              Window.alert( exception.getLocalizedMessage());
            }

            @Override
            public void onResponseReceived(Request request, Response response)
            {
               network_raw = System.currentTimeMillis()  ;
               long szr = getContentLen(response);
               if( szr > 0 )
                   size_raw = szr ; 
               else
                   size_raw = file.getSize();
               

               
               if( response.getStatusCode() != 200 ){
                   Window.alert( response.getStatusText() );
                   done(false);
                   return ;
               }
               
               getCompress( url , new RequestCallback() {
                   public void onError(Request request, Throwable exception) {
                       Window.alert( exception.getLocalizedMessage());
                     }

                     @Override
                     public void onResponseReceived(Request request, Response response)
                     {
                           String text = response.getText();
                           network_compressed =  System.currentTimeMillis()  ;
                           
                           // Only used content length if avail - aka not in chunked mode
                           long szc = getContentLen(response);
                           if( szc > 0 )
                               size_compressed = szc;
                           else
                               size_compressed = file.getSize_compress();

                           
                           if( file.getType().equals("json")) {
                              JSONResponse doc =  JSONResponse.getResponse( text );
                              js_parse =  System.currentTimeMillis();
                              js_nodes =  doc.walk();
                              js_query  = System.currentTimeMillis();
                              
                              JQJsonResponse jqdoc = JQJsonResponse.getResponse(text);
                              jquery_parse =  System.currentTimeMillis();
                              jq_nodes =  jqdoc.walk();
                              jquery_query  = System.currentTimeMillis();
                              
                           }
                           else 
                           if( file.getType().equals("xml")) {
                               
                             XMLResponse doc =  XMLResponse.getResponse(text);
            
                             js_parse =  System.currentTimeMillis();
                             js_nodes =   doc.walk();
                             js_query  = System.currentTimeMillis();
                             
                             JQXmlResponse jqdoc = JQXmlResponse.getResponse(text);
                             jquery_parse =  System.currentTimeMillis();
                             jq_nodes =  jqdoc.walk();
                             jquery_query  = System.currentTimeMillis();
                             
                             
                             
                              
                           }
                           done(true);
                           
                     }

                   
               });
               
            }
           });
           
        } catch( Exception e )
        {
            Window.alert("Exception: " + e);
        }
                

      }
    
   

    private void done(boolean bComplete)
    {
        
      file.addMeter(network_raw - start, size_raw , network_compressed - network_raw , size_compressed , 
              js_parse - network_compressed , 
              js_query - js_parse , 
              js_nodes ,
              jquery_parse - js_query ,
              jquery_query - jquery_parse ,
              jq_nodes);
      
      if( notifyPanel != null )
          notifyPanel.runComplete( file, bComplete );
        
    }
    private long getContentLen(Response response)
    {
        String scl = response.getHeader("Content-Length");
        if( scl == null || scl.length() == 0 )
            return -1;
        return Long.parseLong(scl);
    }

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



