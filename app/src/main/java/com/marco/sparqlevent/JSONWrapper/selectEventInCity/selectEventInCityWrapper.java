package com.marco.sparqlevent.JSONWrapper.selectEventInCity;

import com.google.gson.annotations.SerializedName;
import com.marco.sparqlevent.JSONWrapper.HeadWrapper;

/**
 * Created by Utente on 17/06/2015.
 */
public class selectEventInCityWrapper {
    @SerializedName("head")HeadWrapper head;
    @SerializedName("results")ResultSEICity results;

    public selectEventInCityWrapper() {
    }

    public selectEventInCityWrapper(HeadWrapper head, ResultSEICity results) {
        this.head = head;
        this.results = results;
    }

    public HeadWrapper getHead() {
        return head;
    }

    public void setHead(HeadWrapper head) {
        this.head = head;
    }

    public ResultSEICity getResults() {
        return results;
    }

    public void setResults(ResultSEICity results) {
        this.results = results;
    }

    @Override
    public String toString() {
        String tmp = "selectEventInCityWrapper: " + "\n" +
                "head: " + head + "\n" +
                "results: " + results + "\n";
        return tmp;
    }
}
