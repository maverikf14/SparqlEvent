package com.marco.sparqlevent;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
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
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.marco.sparqlevent.JSONWrapper.selectEventInCity.BindingsSEICity;
import com.marco.sparqlevent.JSONWrapper.selectEventInCity.ResultSEICity;
import com.marco.sparqlevent.JSONWrapper.selectEventInCity.selectEventInCityWrapper;
import com.marco.sparqlevent.JSONWrapper.selectNearEvent.BindingsNearEvent;
import com.marco.sparqlevent.JSONWrapper.selectNearEvent.SelectNearEventWrapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class EventActivity extends AppCompatActivity implements  QueryMakerFragment.OnFragmentInteractionListener{

    private GoogleMap googleMap;
    private Parser parserJSON;

    private ArrayList<Marker> eventMarkerArray;

    public LocalizzationService locationServiceConnection;
    private boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        parserJSON = new Parser();

        try {
            // Loading map
            initilizeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (googleMap != null) {

            //Focalizziamo la mappa su un punto prefissato
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.524940, 15.073690), 15));


        } else {
            GooglePlayServicesUtil.getErrorDialog(
                    GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)
                    , this, 0).show();
        }

    }

    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }

    @Override
    public void onStart(){
        super.onStart();
        Intent intent = new Intent(this, LocalizzationService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();

    }

    @Override
    protected void onPause() {
        super.onPause();

        //Se l'applicazione lascia il foreground
        //cancello la sottoscrizione al location listener
        //locationManager.removeUpdates(listenerFine);

    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocalizzationService.LocalBinder binder = (LocalizzationService.LocalBinder) service;
            locationServiceConnection = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

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


    @Override
    public void onFragmentInteraction(int cmd, String response) {
        switch(cmd){
            case 0: //Selezione evento in una citta
                selectEventInCityWrapper resp = parserJSON.parseSEICQuery(response);
                printEventMarker(resp);
                break;
            case 1: //Selezione evento vicino alla posizione
                SelectNearEventWrapper resp2 = parserJSON.parseSelectNearEvent(response);
                printNearEventMarker(resp2);
                break;
        }
    }

    private void printEventMarker(selectEventInCityWrapper eventList){
        ArrayList<BindingsSEICity> eventArray = eventList.getResults().getBindings();
        double latitude = 0;
        double longitude = 0;
        double stopCount = 0;

        if(eventMarkerArray == null){
            eventMarkerArray = new ArrayList();
        }

        if(!eventMarkerArray.isEmpty()){
            for (Marker tmp : eventMarkerArray){
                tmp.remove();
            }
        }

        eventMarkerArray.clear();

        for (BindingsSEICity tmp : eventArray){
            stopCount++;
            latitude = latitude + tmp.getLatitude().getValue();
            longitude = longitude + tmp.getLongitude().getValue();
            LatLng coordinates = new LatLng(tmp.getLatitude().getValue(), tmp.getLongitude().getValue());
            String snip = tmp.getAvrs().getValue() + "\n" + tmp.getAddress().getValue();
            eventMarkerArray.add(googleMap.addMarker(new MarkerOptions().title(tmp.getEventName().getValue()).snippet(snip).position(coordinates)));
        }

        LatLng cameraposition = new LatLng(latitude/stopCount, longitude/stopCount);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraposition, 14));
    }

    private void printNearEventMarker(SelectNearEventWrapper eventList){
        ArrayList<BindingsNearEvent> eventArray = eventList.getResults().getBindings();
        double latitude = 0;
        double longitude = 0;
        double stopCount = 0;

        if(eventMarkerArray == null){
            eventMarkerArray = new ArrayList();
        }

        if(!eventMarkerArray.isEmpty()){
            for (Marker tmp : eventMarkerArray){
                tmp.remove();
            }
        }

        eventMarkerArray.clear();

        LatLng myPosition = new LatLng(locationServiceConnection.getCurrentLatitude(), locationServiceConnection.getCurrentLongitude());
        eventMarkerArray.add(googleMap.addMarker(new MarkerOptions().title("Posizione Mia").position(myPosition).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))));
        for (BindingsNearEvent tmp : eventArray){
            stopCount++;
            latitude = latitude + tmp.getLatitude().getValue();
            longitude = longitude + tmp.getLongitude().getValue();
            LatLng coordinates = new LatLng(tmp.getLatitude().getValue(), tmp.getLongitude().getValue());
            String snip = tmp.getAddress().getValue() + " distance: " + tmp.getDistance().getValue() + "Km";
            eventMarkerArray.add(googleMap.addMarker(new MarkerOptions().title(tmp.getEventName().getValue()).snippet(snip).position(coordinates)));
        }

        LatLng cameraposition = new LatLng(latitude/stopCount, longitude/stopCount);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraposition, 14));
    }
}
