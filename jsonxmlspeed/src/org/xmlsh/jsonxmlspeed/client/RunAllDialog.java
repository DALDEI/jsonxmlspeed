package org.xmlsh.jsonxmlspeed.client;


import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;

class RunAllDialog extends DialogBox
{

    /**
 * 
 */
    private final DataFilePanel dataFilePanel;
    private RunDialogForm runForm;

    public RunAllDialog(DataFilePanel dataFilePanel, boolean bAutoSubmit)
    {

        this.dataFilePanel = dataFilePanel;
        this.add(runForm = new RunDialogForm(this,bAutoSubmit));

        // Set the dialog box's caption.
        setText("Running Tests Please Wait ...");

        // Enable animation.
        setAnimationEnabled(true);

        // Enable glass background.
        setGlassEnabled(true);

        center();
    }

    public void done()
    {
        runForm.done();

    }
    

    public void sendResults()
        {
            // Set the dialog box's caption.
            setText("Sending Results ...");

            // Enable animation.
            setAnimationEnabled(true);

            // Enable glass background.
            setGlassEnabled(true);

            RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, "/cgi-bin/speedresults");
            String data = dataFilePanel.buildResults();
            try
            {

                Request request = builder.sendRequest(data, new RequestCallback() {
                    public void onError(Request request, Throwable exception)
                    {
                        Window.alert(exception.getLocalizedMessage());
                    }

                    @Override
                    public void onResponseReceived(Request request, Response response)
                    {
                        RunAllDialog.this.setText("Complete: " +  response.getText());
                        runForm.doneSubmit();
                    }
                });
            } catch (RequestException e)
            {
                Window.alert(e.getLocalizedMessage());
            }
        }

    public void closeDialog()
    {
       this.hide();
        
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

