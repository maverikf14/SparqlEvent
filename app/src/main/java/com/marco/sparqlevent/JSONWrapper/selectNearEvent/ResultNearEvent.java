package com.marco.sparqlevent.JSONWrapper.selectNearEvent;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Utente on 18/06/2015.
 */
public class ResultNearEvent {
    @SerializedName("bindings")ArrayList<BindingsNearEvent> bindings;

    public ResultNearEvent(ArrayList<BindingsNearEvent> bindings) {
        this.bindings = bindings;
    }

    public ArrayList<BindingsNearEvent> getBindings() {
        return bindings;
    }

    public void setBindings(ArrayList<BindingsNearEvent> bindings) {
        this.bindings = bindings;
    }

    @Override
    public String toString() {
        return "ResultStudentQuery: " + "\n" +
                "bindings: " + bindings + "\n";
    }
}
