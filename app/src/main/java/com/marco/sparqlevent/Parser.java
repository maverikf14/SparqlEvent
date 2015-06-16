package com.marco.sparqlevent;

import com.google.gson.Gson;
import com.marco.sparqlevent.JSONWrapper.averageRateConcertQuery.AverageVoteConcertQuery;
import com.marco.sparqlevent.JSONWrapper.studentQuery.StudentQueryWrapper;

/**
 * Created by Utente on 16/06/2015.
 */
public class Parser {

    public Parser() {
    }

    public StudentQueryWrapper parseStudentQuery(String inputJSON){
        Gson reader = new Gson();
        StudentQueryWrapper resultQuery = reader.fromJson(inputJSON, StudentQueryWrapper.class);
        return resultQuery;
    }

    public AverageVoteConcertQuery parseAverageVoteConcertQuery(String inputJSON){
        Gson reader = new Gson();
        AverageVoteConcertQuery resultQuery = reader.fromJson(inputJSON, AverageVoteConcertQuery.class);
        return resultQuery;
    }

}
