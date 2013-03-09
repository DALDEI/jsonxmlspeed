package org.xmlsh.jsonxmlspeed.client;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

public class DataFile
{
    // Constant
    private     String   dir;
    private     String   url;
    private     long     size;
    private     long     size_compress;
    
    private     String   type;
    private     String   name ;
    private     String   md5;
  
    static class Meter {

        public Meter(long network_raw,long size_raw , long network_compressed, long size_compressed , long js_parse, long js_query, int js_nodes , long jquery_parse, long jquery_query, int jquery_nodes)
        {
            this.network_raw = network_raw;
            this.network_compressed = network_compressed;

            this.js_parse = js_parse;
            this.js_query = js_query;  
            this.jquery_parse = jquery_parse;
            this.jquery_query = jquery_query;  
            
            
            this.js_nodes = js_nodes;
            this.jquery_nodes = jquery_nodes ;
            this.size_raw = size_raw;
            this.size_compressed = size_compressed ;
        }
        private     long     network_raw;
        private     long     network_compressed;
        private     long     size_raw;
        private     long     size_compressed;
        private     long     jquery_parse;
        private     long     jquery_query;
        private     int      jquery_nodes;
        private     long     js_parse;
        private     long     js_query;
        private     int      js_nodes;
        public long getJSParse()
        {
            return js_parse;
        }
        public long getJSQuery()
        {
            return js_query;
        }
        public long getJQParse()
        {
            return jquery_parse;
        }
        public long getJQQuery()
        {
            return jquery_query;
        }
        public int getJSNodes()
        {
            return js_nodes;
        }
        public int getJQNodes()
        {
            return jquery_nodes;
        }
        public long getNetwork_raw()
        {
            return network_raw;
        }
        public long getNetwork_compressed()
        {
            return network_compressed;
        }
        public long getSize_raw()
        {
            return size_raw;
        }
        public long getSize_compressed()
        {
            return size_compressed;
        }
        
    };
    
    private Meter data = null ;
        
    public DataFile( Element node ){
            this(
                    node.getAttributes().getNamedItem("type").getNodeValue(),
                    node.getAttributes().getNamedItem("dir").getNodeValue(),
                    node.getAttributes().getNamedItem("name").getNodeValue(),
                    node.getAttributes().getNamedItem("uri").getNodeValue(),
                    node.getAttributes().getNamedItem("md5").getNodeValue(),
                    Integer.parseInt( node.getAttributes().getNamedItem("size").getNodeValue()),
                    Integer.parseInt( node.getAttributes().getNamedItem("compress-size").getNodeValue())

                    );
        
    }
    public DataFile(String type , String dir , String name , String url, String md5 , long size,long size_compress)
    {
        this.url = url;
        this.size = size;
        this.type = type;
        this.name = name;
        this.dir = dir ;
        this.md5 = md5 ;
        this.size_compress = size_compress ;
    }
    public String getDir()
    {
        return dir;
    }
    public String getName()
    {
        return name;
    }
   
    public void clearResults()
    {
       data = null;
        
    }
    
    public void addMeter( long network_raw, long size_raw , long network_compressed , long size_compressed , 
            long js_parse, long js_query,int js_nodes , long jquery_parse, long jquery_query,  int jq_nodes )
    {
        Meter m = new Meter( network_raw, size_raw, network_compressed,size_compressed, js_parse, js_query,js_nodes , jquery_parse,jquery_query, jq_nodes );
        data = m;
    }
    
    
    public Node createResult(Document doc)
    {
         Element result = doc.createElement("result");
         result.setAttribute("uri", url);
         result.setAttribute("group", dir);
         result.setAttribute("type", type);
         result.setAttribute("size",String.valueOf(size));
         result.setAttribute("md5", md5);
         if( data != null ){
             Meter m = data ;
             Element test = result ;
             test.setAttribute("network_raw",String.valueOf(m.network_raw));
             test.setAttribute("network_compressed",String.valueOf(m.network_compressed));
             
             test.setAttribute("size_raw",String.valueOf(m.size_raw));
             test.setAttribute("size_compressed",String.valueOf(m.size_compressed));
             

             test.setAttribute("js_parse",String.valueOf(m.js_parse));
             test.setAttribute("js_query",String.valueOf(m.js_query));
             test.setAttribute("jquery_parse",String.valueOf(m.jquery_parse));
             test.setAttribute("jquery_query",String.valueOf(m.jquery_query));
             
             
             test.setAttribute("js_nodes",String.valueOf(m.js_nodes));
             test.setAttribute("jquery_nodes",String.valueOf(m.jquery_nodes));

         }
       
         return result;
        
        
    }
    public String getUrl()
    {
        return url;
    }
    public long getSize()
    {
        return size;
    }
    public String getType()
    {
        return type;
    }
    public Meter getLastMeter()
    {
        return data ;

    }
    public long getSize_compress()
    {
        return size_compress;
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

