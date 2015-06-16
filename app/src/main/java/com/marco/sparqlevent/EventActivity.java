package com.marco.sparqlevent;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class EventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private final String URLPREF = "http://";
    private String urlSuffix = "/openrdf-sesame";
    private String url = "";


    private LinkedList<String> queryList;

    private TextView responseText;

    /*File variable*/
    private File sd = Environment.getExternalStorageDirectory();
    private File f;
    private String fileResponseName;
    private int lastQueryPos;

    private Parser parserJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        parserJSON = new Parser();

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.query_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        queryList = new LinkedList();
        queryList.add(0, "/openrdf-workbench/repositories/EventRepos/query?query=PREFIX+rdf%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX+owl%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2002%2F07%2Fowl%23%3E%0APREFIX+xsd%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23%3E%0APREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E%0APREFIX+ka%3A+%3Chttp%3A%2F%2Fwww.semanticweb.org%2Fmarco%2Fontologies%2F2015%2F5%2FEvent%23%3E%0A%0ASELECT+%3Fpers+%0AWHERE+%7B+%3Fpers+rdf%3Atype+%3Ftype+.+%3Ftype+rdfs%3AsubClassOf*+ka%3AStudent+.%7D&Accept=application/sparql-results%2Bjson");
        queryList.add(1, "/openrdf-workbench/repositories/EventRepos/query?query=PREFIX+rdf%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX+owl%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2002%2F07%2Fowl%23%3E%0APREFIX+xsd%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23%3E%0APREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E%0APREFIX+ka%3A+%3Chttp%3A%2F%2Fwww.semanticweb.org%2Fmarco%2Fontologies%2F2015%2F5%2FEvent%23%3E%0A%0ASELECT+%3Faverage+%3Fnome%0Awhere+%7B%3Fevent+rdf%3Atype+ka%3AConcert+.+%3Fevent+ka%3Aaverage_rating_score+%3Ftmp+.+%3Ftmp+ka%3Aav_score+%3Faverage+.+FILTER+(%3Faverage+%3E+3.5)+.+%3Fevent+ka%3Anome+%3Fnome%7D&Accept=application/sparql-results%2Bjson");
        /*Da parsare*/
        queryList.add(2, "/openrdf-workbench/repositories/EventRepos/query?query=PREFIX+rdf%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX+owl%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2002%2F07%2Fowl%23%3E%0APREFIX+xsd%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23%3E%0APREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E%0APREFIX+ka%3A+%3Chttp%3A%2F%2Fwww.semanticweb.org%2Fmarco%2Fontologies%2F2015%2F5%2FEvent%23%3E%0A%0ASELECT+%3Fevent+(AVG(%3Fa)+AS+%3Faverage)+%0AWHERE%0A+%7B%3Fevent+rdf%3Atype+ka%3AConcert+.+%3Fevent+ka%3Arating_score+%3Frat+.+%3Frat+ka%3Ascore+%3Fa%7D%0AGROUP+BY+%3Fevent&Accept=application/sparql-results%2Bjson");
        queryList.add(3, "/openrdf-workbench/repositories/EventRepos/query?query=PREFIX+rdf%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX+owl%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2002%2F07%2Fowl%23%3E%0APREFIX+xsd%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23%3E%0APREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E%0APREFIX+ka%3A+%3Chttp%3A%2F%2Fwww.semanticweb.org%2Fmarco%2Fontologies%2F2015%2F5%2FEvent%23%3E%0A%0ASELECT+%3Fevent++%3Faverage%0Awhere+%7B%3Fevent+rdf%3Atype+%3Ftype+.+%3Ftype+rdfs%3AsubClassOf*+ka%3AMusic_event+.+%3Fevent+ka%3Aaverage_rating_score+%3Ftmp+.+%3Ftmp+ka%3Aav_score+%3Faverage+.+FILTER+(%3Faverage+%3E+3.5)+.+%3Fevent+ka%3Alocation+ka%3AAgrigento%7D&Accept=application/sparql-results%2Bjson");
        queryList.add(4, "/openrdf-workbench/repositories/EventRepos/query?query=PREFIX+rdf%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX+owl%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2002%2F07%2Fowl%23%3E%0APREFIX+xsd%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23%3E%0APREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E%0APREFIX+ka%3A+%3Chttp%3A%2F%2Fwww.semanticweb.org%2Fmarco%2Fontologies%2F2015%2F5%2FEvent%23%3E%0A%0ASELECT+%3Fevent++%3Faverage%0Awhere+%7B%3Fevent+rdf%3Atype+ka%3AConcert+.+%3Fevent+ka%3Aaverage_rating_score+%3Ftmp+.+%3Ftmp+ka%3Aav_score+%3Faverage+.+FILTER+(%3Faverage+%3E+3.5)+.+%3Fevent+ka%3Alocation+ka%3AAgrigento%7D&Accept=application/sparql-results%2Bjson");
        queryList.add(5, "/openrdf-workbench/repositories/EventRepos/query?query=PREFIX+rdf%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%3E%0APREFIX+owl%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2002%2F07%2Fowl%23%3E%0APREFIX+xsd%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2001%2FXMLSchema%23%3E%0APREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E%0APREFIX+ka%3A+%3Chttp%3A%2F%2Fwww.semanticweb.org%2Fmarco%2Fontologies%2F2015%2F5%2FEvent%23%3E%0A%0ASELECT+%3Fevent++(AVG(%3Fage)+AS+%3FaverageAge)%0AWHERE%0A+%7B%3Fevent+rdf%3Atype+%3Ftype+.+%3Ftype+rdfs%3AsubClassOf*+ka%3AEvent+.+%3Fevent+ka%3Apartecipating+%3Fperson+.+%3Fperson+ka%3Aage+%3Fage%7D%0AGROUP+BY+%3Fevent%0AHAVING+(%3FaverageAge+%3C+25)&Accept=application/sparql-results%2Bjson");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*Button Listener*/
    public void sendQuery(View view){
        EditText address = (EditText)findViewById(R.id.siteAddressText);

        url = URLPREF + address.getText() + urlSuffix;
        responseText = (TextView)findViewById(R.id.responseTextView);
        Log.d("sendQuery request",  url);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Receive the responce and send it to activity because through bundle
                        // we can only pass flat data
                        String localResp = "nessuna Risposta";

                        switch(lastQueryPos){
                            case 0:
                                localResp = parserJSON.parseStudentQuery(response).toString();
                                break;
                            case 1:
                                localResp = parserJSON.parseAverageVoteConcertQuery(response).toString();
                                break;
                        }

                        responseText.setText("\n" + localResp);

                        //write(response, fileResponseName);
                        Log.d("sendQuery responce", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "That didn't work!", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        queue.add(stringRequest);



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        urlSuffix = queryList.get(position);
        fileResponseName = "responseJSON"+position+".txt";
        lastQueryPos = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Function to write string in a file
     * @param Data
     */
    public void write (String Data, String nomeFile){
        FileWriter fw = null;
        BufferedWriter bw = null;
        try{
            f = new File(sd, nomeFile);
            fw = new FileWriter(f, true);
            bw = new BufferedWriter(fw);
            bw.write(Data);
            bw.close();
            fw.close();
            Toast.makeText(getApplicationContext(), "File saved",Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "File not saved",Toast.LENGTH_SHORT).show();
        }
    }
}
