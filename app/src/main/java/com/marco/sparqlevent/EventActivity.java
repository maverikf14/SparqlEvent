package com.marco.sparqlevent;

import android.os.Bundle;
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
/*
import org.openrdf.OpenRDFException;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.repository.manager.RepositoryManager;
import org.openrdf.repository.manager.RepositoryProvider;
*/

import java.util.LinkedList;


public class EventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private final String URLPREF = "http://";
    private String urlSuffix = "/openrdf-sesame";
    private String url = "";


    private LinkedList<String> queryList;

    private TextView responseText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.query_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        queryList = new LinkedList();
        queryList.add(0 , "/openrdf-sesame");
        queryList.add(1 , "/openrdf-sesame/repositories");
        queryList.add(2 , "/openrdf-workbench/repositories/EventRepos/query?action=exec&queryLn=SPARQL&query=PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>PREFIX owl: <http://www.w3.org/2002/07/owl#>PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>PREFIX ka: <http://www.semanticweb.org/marco/ontologies/2015/5/Event#>SELECT ?event  ?avrswhere {?event rdf:type ka:Concert . ?event ka:average_rating_score ?tmp . ?tmp ka:av_score ?avrs . FILTER (?avrs > 3.5)}");
        queryList.add(3, "");
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
        String urlServer = getString(R.string.sesameUrl);
        //String repositoryID = getString(R.string.repositoryID);

        url = URLPREF + address.getText() + urlServer;
        responseText = (TextView)findViewById(R.id.responseTextView);
        responseText.setText("Url: " + url + "\n");
        /*
        RepositoryManager manager;

        try {
            manager  = RepositoryProvider.getRepositoryManager(url);
            Log.w("sendQuery", manager.getAllRepositoryInfos().toArray().toString());


        } catch (RepositoryConfigException e) {
            e.printStackTrace();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
        */
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        urlSuffix = queryList.get(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
