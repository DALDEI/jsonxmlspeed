package org.xmlsh.jsonxmlspeed.client;

import java.util.ArrayList;
import java.util.Date;

import org.xmlsh.jsonxmlspeed.client.DataFile.Meter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

// NOTE: Beware of time 
//  http://ejohn.org/blog/accuracy-of-javascript-time/

public class DataFilePanel extends Composite
{

    private static DataFilePanelUiBinder uiBinder = GWT.create(DataFilePanelUiBinder.class);
    @UiField
    FlexTable documents;
    @UiField
    Button runAllButton;
    @UiField
    Button runAllSubmitButton;
    @UiField
    Button clearButton;
    @UiField
    ScrollPanel scrollPanel;

    private ArrayList<DataFile> dataFiles;
    private String filesVersion ;
    private DataFile runAllFiles;
    private int runCol = 0;

    RunAllDialog runDialog = null;

    interface DataFilePanelUiBinder extends UiBinder<Widget, DataFilePanel>
    {
    }

    private void getDataFiles()
    {
        final ArrayList<DataFile> files = new ArrayList<DataFile>();

        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "data/index.xml");
        builder.setHeader("Cache-Control", "no-cache");
        try
        {

            Request response = builder.sendRequest(null, new RequestCallback() {
                public void onError(Request request, Throwable exception)
                {
                    Window.alert(exception.getLocalizedMessage());
                }

                @Override
                public void onResponseReceived(Request request, Response response)
                {

                    Document doc = XMLParser.parse(response.getText());
                    
                    filesVersion = doc.getDocumentElement().getAttribute("version");

                    NodeList nl = doc.getElementsByTagName("file");
                    for (int i = 0; i < nl.getLength(); i++)
                    {
                        Node node = nl.item(i);

                        DataFile file = parseFile((Element) node);

                        files.add(file);

                    }

                    init(files);

                }

            });
        } catch (RequestException e)
        {
            Window.alert(e.getLocalizedMessage());
        }
    }

    public DataFilePanel()
    {
        initWidget(uiBinder.createAndBindUi(this));
        scrollPanel.setAlwaysShowScrollBars(true);
        getDataFiles();

    }

    public void init(ArrayList<DataFile> files)
    {

        dataFiles = files;
        final DataFilePanel notify = this;

        documents.insertRow(0);
        documents.getRowFormatter().addStyleName(0, "FlexTable-Header");

        addColumn("Type");
        addColumn("Group");
        addColumn("Name");
        addColumn("Size Raw");
        addColumn("Size gzip");
        addColumn("Network Raw (ms)");
        addColumn("Network gzip (ms)");
        addColumn("JS Parse (ms)");
        addColumn("JS Query (ms)");
        if( Jsonxmlspeed.isDebug() )
            addColumn("JS Nodes");
        addColumn("JQ Parse (ms)");
        addColumn("JQ Query (ms)");
        if( Jsonxmlspeed.isDebug() ){
            addColumn("JQ Nodes");
            addColumn("Test");
        }
        for (DataFile f : files)
        {
            int line = documents.getRowCount();

            final DataFile dataFile = f;

            int col = updateTableRow(f, line);
            if( Jsonxmlspeed.isDebug() )
            {
                PushButton button = new PushButton("Run...", new ClickHandler() {
                    public void onClick(ClickEvent event)
                    {
                        SpeedTest test = new SpeedTest(dataFile);
                        test.run(notify);
                    }
                });
    
                documents.setWidget(line, runCol = col, button);
            }
        }
    }

    private int updateTableRow(DataFile f, int line)
    {
        int col = 0;
        documents.setText(line, col++, f.getType());
        documents.setText(line, col++, f.getDir());
        documents.setWidget(line, col++, new Anchor(f.getName(), f.getUrl(), "_new"));
        documents.setText(line, col++, String.valueOf(f.getSize()));
        documents.setText(line, col++, String.valueOf(f.getSize_compress()));

        Meter m = f.getLastMeter();
        if (m != null)
        {

            documents.setText(line, col++, String.valueOf(m.getNetwork_raw()));
            documents.setText(line, col++, String.valueOf(m.getNetwork_compressed()));
            documents.setText(line, col++, String.valueOf(m.getJSParse()));
            documents.setText(line, col++, String.valueOf(m.getJSQuery()));
            if( Jsonxmlspeed.isDebug() )
               documents.setText(line, col++, String.valueOf(m.getJSNodes()));

            documents.setText(line, col++, String.valueOf(m.getJQParse()));
            documents.setText(line, col++, String.valueOf(m.getJQQuery()));
            if( Jsonxmlspeed.isDebug() )
               documents.setText(line, col++, String.valueOf(m.getJQNodes()));
        } else
        {
            documents.setText(line, col++, "");
            documents.setText(line, col++, "");
            documents.setText(line, col++, "");
            documents.setText(line, col++, "");
            documents.setText(line, col++, "");
            documents.setText(line, col++, "");
            if( Jsonxmlspeed.isDebug() ){
                documents.setText(line, col++, "");
                documents.setText(line, col++, "");
            }


        }
        return col;
    }

    private void addColumn(String columnHeading)
    {
        Widget widget = new Label(columnHeading);
        int cell = documents.getCellCount(0);

        widget.setWidth("100%");
        widget.addStyleName("FlexTable-ColumnLabel");

        documents.setWidget(0, cell, widget);

        documents.getCellFormatter().addStyleName(0, cell, "FlexTable-ColumnLabelCell");
    }

    public void runComplete(DataFile file, boolean bSuccess)
    {

        int index = dataFiles.indexOf(file);
        if (index >= 0)
        {
            updateTableRow(file, index + 1);
            // scrollPanel.ensureVisible(documents.getWidget(index + 1, runCol));
            documents.getCellFormatter().getElement(index+1, 0).scrollIntoView();
            if (file == runAllFiles && index < dataFiles.size() - 1)
            {
                runAllFiles = dataFiles.get(index + 1);
                SpeedTest test = new SpeedTest(runAllFiles);
                test.run(this);

            } else
                runAllFiles = null;
        } else
            runAllFiles = null;

        if (runAllFiles == null)
        {
            if (runDialog != null)
                runDialog.done();
        }

    }

    @UiHandler("clearButton")
    void onClearButtonClick(ClickEvent event)
    {
        clearResults();

    }

    private void clearResults()
    {
        int line = 1;
        for (DataFile f : dataFiles)
        {
            f.clearResults();
            updateTableRow(f, line++);
        }
    }

    @UiHandler("runAllButton")
    void onRunAllButtonClick(ClickEvent event)
    {

        clearResults();
        runDialog = new RunAllDialog(this,false);

        runAllFiles = dataFiles.get(0);
        SpeedTest test = new SpeedTest(runAllFiles);
        test.run(this);

    }
    
    @UiHandler("runAllSubmitButton")
    void onRunAllSubmitButtonClick(ClickEvent event)
    {

        clearResults();
        runDialog = new RunAllDialog(this, true);

        runAllFiles = dataFiles.get(0);
        SpeedTest test = new SpeedTest(runAllFiles);
        test.run(this);

    }


  

    String buildResults()
    {
        DateTimeFormat formatter = DateTimeFormat.getFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        Document doc = XMLParser.createDocument();
        Element results = doc.createElement("results");
        results.setAttribute("result-time", formatter.format(new Date()));
        results.setAttribute("client-version", Jsonxmlspeed.kVERSION);
        results.setAttribute("file-version", filesVersion == null ? "" : filesVersion );

        Element browser = doc.createElement("browser");
        browser.setAttribute("app-code-name", Window.Navigator.getAppCodeName());
        browser.setAttribute("app-name", Window.Navigator.getAppName());
        browser.setAttribute("app-version", Window.Navigator.getAppVersion());
        browser.setAttribute("platform", Window.Navigator.getPlatform());
        browser.setAttribute("user-agent", Window.Navigator.getUserAgent());
        results.appendChild(browser);

        Element tests = doc.createElement("tests");
        for (DataFile f : dataFiles)
        {
            tests.appendChild(f.createResult(doc));
        }
        results.appendChild(tests);

        doc.appendChild(results);

        return doc.toString();
    }

    private DataFile parseFile(Element node)
    {
        return new DataFile(node);
    }

    public void closeDialog()
    {
        if (runDialog != null)
        {
            runDialog.hide();
            runDialog = null;
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


