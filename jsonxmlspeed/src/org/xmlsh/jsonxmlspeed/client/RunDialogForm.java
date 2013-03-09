package org.xmlsh.jsonxmlspeed.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RunDialogForm extends Composite
{

    private static RunDialogFormUiBinder uiBinder = GWT.create(RunDialogFormUiBinder.class);
    @UiField TableRowElement progress_row;
    @UiField Button submitButton;
    @UiField Button closeButton ;
    private boolean bAutoSubmit;
    
    RunAllDialog panel;

    interface RunDialogFormUiBinder extends UiBinder<Widget, RunDialogForm>
    {
    }

    public RunDialogForm(RunAllDialog panel, boolean bAutoSubmit)
    {
        this.panel = panel ;
        this.bAutoSubmit = bAutoSubmit ;
        initWidget(uiBinder.createAndBindUi(this));

        if( bAutoSubmit ){
            submitButton.setVisible(false);
            closeButton.setVisible(false);
        } 
        else{
            submitButton.setEnabled(false);
            closeButton.setEnabled(false);
        }

        progress_row.getStyle().clearDisplay();
        

        
    }
    @UiHandler("submitButton")
    void onSubmitButtonClick(ClickEvent event) {
        submitButton.setEnabled(false);
        closeButton.setEnabled(false);
        panel.sendResults();
    }
    
    @UiHandler("closeButton")
    void onCloseButtonClick(ClickEvent event) {
        panel.closeDialog();
    }
    public void done()
    {

       submitButton.setEnabled(true);
       closeButton.setEnabled(true);
       progress_row.getStyle().setDisplay(Display.NONE);
       if( bAutoSubmit )
           panel.sendResults();
       else
           panel.setText("Test Complete - Please Submit Results");

        
    }
    public void doneSubmit()
    {
        if( bAutoSubmit )
            panel.closeDialog();
        else {
           submitButton.setEnabled(false);
           closeButton.setEnabled(true);
        }
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


