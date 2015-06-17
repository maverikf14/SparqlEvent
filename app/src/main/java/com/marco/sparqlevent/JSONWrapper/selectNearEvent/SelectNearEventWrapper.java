package com.marco.sparqlevent.JSONWrapper.selectNearEvent;

import com.google.gson.annotations.SerializedName;
import com.marco.sparqlevent.JSONWrapper.HeadWrapper;

/**
 * Created by Utente on 18/06/2015.
 */
public class SelectNearEventWrapper {
    @SerializedName("head")HeadWrapper head;
    @SerializedName("results")ResultNearEvent results;

    public SelectNearEventWrapper() {
    }

    public SelectNearEventWrapper(HeadWrapper head, ResultNearEvent results) {
        this.head = head;
        this.results = results;
    }

    public HeadWrapper getHead() {
        return head;
    }

    public void setHead(HeadWrapper head) {
        this.head = head;
    }

    public ResultNearEvent getResults() {
        return results;
    }

    public void setResults(ResultNearEvent results) {
        this.results = results;
    }

    @Override
    public String toString() {
        String tmp = "SelectNearEventWrapper: " + "\n" +
                "head: " + head + "\n" +
                "results: " + results + "\n";
        return tmp;
    }
}
