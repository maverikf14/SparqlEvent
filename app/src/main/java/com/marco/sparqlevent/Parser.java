package com.marco.sparqlevent;

import com.google.gson.Gson;
import com.marco.sparqlevent.JSONWrapper.averageRateConcertQuery.AverageVoteConcertQuery;
import com.marco.sparqlevent.JSONWrapper.selectEventInCity.selectEventInCityWrapper;
import com.marco.sparqlevent.JSONWrapper.selectNearEvent.SelectNearEventWrapper;

/**
 * Created by Utente on 16/06/2015.
 */
public class Parser {

    public Parser() {
    }

    public AverageVoteConcertQuery parseAverageVoteConcertQuery(String inputJSON){
        Gson reader = new Gson();
        AverageVoteConcertQuery resultQuery = reader.fromJson(inputJSON, AverageVoteConcertQuery.class);
        return resultQuery;
    }

    public selectEventInCityWrapper parseSEICQuery(String inputJSON){
        Gson reader = new Gson();
        selectEventInCityWrapper resultQuery = reader.fromJson(inputJSON, selectEventInCityWrapper.class);
        return resultQuery;
    }

    public SelectNearEventWrapper parseSelectNearEvent(String inputJSON){
        Gson reader = new Gson();
        SelectNearEventWrapper resultQuery = reader.fromJson(inputJSON, SelectNearEventWrapper.class);
        return resultQuery;
    }
}
