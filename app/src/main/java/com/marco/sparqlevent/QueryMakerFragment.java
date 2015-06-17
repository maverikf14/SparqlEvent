package com.marco.sparqlevent;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QueryMakerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QueryMakerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QueryMakerFragment extends Fragment implements Button.OnClickListener{

    private OnFragmentInteractionListener mListener;

    private final String URLPREF = "http://";
    private String urlSuffix = "/openrdf-sesame";
    private String url = "";

    /*File variable*/
    private File sd = Environment.getExternalStorageDirectory();
    private File f;
    private String fileResponseName;
    private int lastQueryPos;



    /*position query*/
    private String currentLatitude = "37.506471";
    private String currentLongitude = "15.086486";

    /*Query variable*/
    private double averageRate = 0;
    private String citySelected = "";
    private int queryIndex;
    private String eventTypeSelected;

    /*Adapter*/
    ArrayAdapter<CharSequence> queryAdapter;
    ArrayAdapter<CharSequence> cityAdapter;
    ArrayAdapter<CharSequence> eventAdapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment QueryMakerFragment.
     */
    public static QueryMakerFragment newInstance() {
        QueryMakerFragment fragment = new QueryMakerFragment();
        return fragment;
    }

    public QueryMakerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_query_maker, container, false);

        //Query spinner
        Spinner querySpinner = (Spinner) view.findViewById(R.id.querySpinner);
        querySpinner.setOnItemSelectedListener(new QuerySpinnerListener());
        queryAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.query_array, android.R.layout.simple_spinner_item);
        queryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        querySpinner.setAdapter(queryAdapter);

        //City Spinner
        Spinner citySpinner = (Spinner) view.findViewById(R.id.citySpinner);
        citySpinner.setOnItemSelectedListener(new CitySpinnerListener());
        cityAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.city_array, android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);

        //event Spinner
        Spinner eventSpinner = (Spinner) view.findViewById(R.id.eventTypeSpinner);
        eventSpinner.setOnItemSelectedListener(new EventSpinnerListener());
        eventAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.event_array, android.R.layout.simple_spinner_item);
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(eventAdapter);

        Button sendQueryButton = (Button)view.findViewById(R.id.queryButton);
        sendQueryButton.setOnClickListener(this);

        return view;
    }


    private String getQueryString(int pos){
        switch (pos){
            case 0: //Seleziona Evento in una citta
                return "/openrdf-workbench/repositories/EventRepos/query?action=exec&queryLn=SPARQL&query=PREFIX+Event%3A+%3Chttp%3A%2F%2Fwww.semanticweb.org%2Fmarco%2Fontologies%2F2015%2F5%2FEvent%23%3E%0ASELECT+%3FeventName+%3Flatitude+%3Flongitude+%3Faddress+%3Favrs%0Awhere+%7B%0A%3Fevent+rdf%3Atype+%3Ftype+.+%0A%3Ftype+rdfs%3AsubClassOf*+Event%3A"+eventTypeSelected+"+.+%0A%3Fevent+Event%3Aaverage_rating_score+%3Ftmp+.+%0A%3Fevent+Event%3Anome+%3FeventName+.%0A%3Fevent+Event%3Alatitude+%3Flatitude+.%0A%3Fevent+Event%3Alongitude+%3Flongitude+.%0A%3Fevent+Event%3Aaddress+%3Faddress+.%0A%3Ftmp+Event%3Aav_score+%3Favrs%0AFILTER+(%3Favrs+%3E+"+averageRate+")+.+%0A%3Fevent+Event%3Alocation+Event%3A"+citySelected+"+%7D&Accept=application/sparql-results%2Bjson";
            case 1: //Selezione Evento vicino alla posizione
                return "/openrdf-workbench/repositories/EventRepos/query?action=exec&queryLn=SPARQL&query=PREFIX+ka%3A+%3Chttp%3A%2F%2Fwww.semanticweb.org%2Fmarco%2Fontologies%2F2015%2F5%2FEvent%23%3E%0ASELECT+%3FeventName+%3Faddress+%3Flatitude+%3Flongitude+%3FdistanceKm+%0AWHERE+%7B%0A%09bind%28"+currentLatitude+"+as+%3Fmylat%29.%0A%09bind%28"+currentLongitude+"+as+%3Fmylon%29.%0A++++bind%282+as+%3Fradius%29.%0A%0A++++%3Fevent++rdf%3Atype+%3Ftype.%0A%09%3Ftype+rdfs%3AsubClassOf*+ka%3A"+eventTypeSelected+".%0A++++%3Fevent+ka%3Alongitude+%3Flongitude.+%0A++++%3Fevent+ka%3Alatitude+%3Flatitude.%0A%09%3Fevent+ka%3Anome+%3FeventName.%0A%09%3Fevent+ka%3Aaddress+%3Faddress.%0A++++bind%28%3Flatitude-%3Fmylat+as+%3Fdifflat%29.%0A++++bind%28%3Flongitude-%3Fmylon+as+%3Fdifflon%29.+%0A++++bind%28%3Fdifflat+*+110.54+as+%3FdifflatKm%29.%0A++++bind%28%3Fdifflon+*+111.320+*+%281-%3Fdifflat*%3Fdifflat%2F2%29+as+%3FdifflonKm%29.%0A++++bind%28%3FdifflatKm*%3FdifflatKm+%2B+%3FdifflonKm+*+%3FdifflonKm+as+%3FsquareDistanceKm%29%0A++++%0A++++bind%28%3FsquareDistanceKm+%2F+%28%3Fradius*%3Fradius%29+as+%3FsquareDistanceM1%29.%0A++++bind%28%3FsquareDistanceM1-1+as+%3FsquareDistanceM%29.%0A++++%0A++++bind%281+%2B+1%2F2*+%28%3FsquareDistanceM%29++-+1%2F8+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+%0A++++%2B+1%2F16+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29%0A++++-+5%2F128+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29%0A++++%2B+7%2F256+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29%0A++++-+21%2F1024+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29%0A++++%2B+33%2F2048+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29%0A++++-+429%2F32768+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29+*+%28%3FsquareDistanceM%29%0A++++as+%3FdistanceM%29.%0A++++%0A++++bind%28%3FdistanceM*%3Fradius+as+%3FdistanceKm%29.%0A++++FILTER+%28%3FdistanceKm+%3C+%28%3Fradius%29+%26%26+%3FdistanceKm%3E0%29%0A%7DORDER+BY+%3FdistanceKm%0ALIMIT+3&Accept=application/sparql-results%2Bjson";
            case 2:  //Seleziona Eventi e calcola voto medio
                return "";
            default:
                return "";
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        int buttonId = v.getId();
        Log.d("onClick", String.valueOf(v.getId()));

        switch(buttonId){
            case R.id.queryButton:

                EditText addressText = (EditText)getActivity().findViewById(R.id.addressText);
                EditText rateText = (EditText)getActivity().findViewById(R.id.rateAverageText);

                EventActivity evtAct = (EventActivity)getActivity();
                currentLatitude = Double.toString(evtAct.locationServiceConnection.getCurrentLatitude());
                currentLongitude = Double.toString(evtAct.locationServiceConnection.getCurrentLongitude());

                averageRate = Double.parseDouble(rateText.getText().toString());
                urlSuffix = getQueryString(queryIndex);
                url = URLPREF + addressText.getText() + urlSuffix;
                fileResponseName = "responseJSON"+queryIndex+".txt";
                sendQuery();
                break;
        }
    }

    public void sendQuery(){

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Receive the responce and send it to activity because through bundle
                        // we can only pass flat data
                        if (mListener != null) {
                            mListener.onFragmentInteraction(queryIndex, response);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),
                        "That didn't work!", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        queue.add(stringRequest);
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
            Toast.makeText(getActivity(), "File saved",Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "File not saved",Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(int cmd, String response);
    }

    //INNER CLASS


    protected class QuerySpinnerListener implements Spinner.OnItemSelectedListener{

        public QuerySpinnerListener() {
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        queryIndex = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    protected class CitySpinnerListener implements Spinner.OnItemSelectedListener{

        public CitySpinnerListener() {
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            citySelected = cityAdapter.getItem(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    protected class EventSpinnerListener implements Spinner.OnItemSelectedListener{

        public EventSpinnerListener() {
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            eventTypeSelected = eventAdapter.getItem(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
