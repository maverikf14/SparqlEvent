package com.marco.sparqlevent.JSONWrapper.averageRateConcertQuery;

import com.google.gson.annotations.SerializedName;
import com.marco.sparqlevent.JSONWrapper.HeadWrapper;
import com.marco.sparqlevent.JSONWrapper.studentQuery.ResultStudentQuery;

/**
 * Created by Utente on 16/06/2015.
 */
public class AverageVoteConcertQuery {
    @SerializedName("head")HeadWrapper head;
    @SerializedName("results")ResultARCQuery results;

    public AverageVoteConcertQuery(HeadWrapper head, ResultARCQuery results) {
        this.head = head;
        this.results = results;
    }

    public HeadWrapper getHead() {
        return head;
    }

    public void setHead(HeadWrapper head) {
        this.head = head;
    }

    public ResultARCQuery getResults() {
        return results;
    }

    public void setResults(ResultARCQuery results) {
        this.results = results;
    }

    @Override
    public String toString() {
        String tmp = "AverageVoteConcertQueryWrapper: " + "\n" +
                "head: " + head + "\n" +
                "results: " + results + "\n";
        return tmp;
    }
}
