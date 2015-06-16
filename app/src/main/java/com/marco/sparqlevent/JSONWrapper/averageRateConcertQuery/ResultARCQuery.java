package com.marco.sparqlevent.JSONWrapper.averageRateConcertQuery;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Utente on 16/06/2015.
 */
public class ResultARCQuery {
    @SerializedName("bindings")ArrayList<BindingARCQuery> bindings;

    public ResultARCQuery(ArrayList<BindingARCQuery> bindings) {
        this.bindings = bindings;
    }

    public ArrayList<BindingARCQuery> getBindings() {
        return bindings;
    }

    public void setBindings(ArrayList<BindingARCQuery> bindings) {
        this.bindings = bindings;
    }

    @Override
    public String toString() {
        return "ResultARCQuery: " + "\n" +
                "bindings: " + bindings + "\n";
    }
}
