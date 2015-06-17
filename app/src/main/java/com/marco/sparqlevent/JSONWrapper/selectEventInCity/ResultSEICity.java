package com.marco.sparqlevent.JSONWrapper.selectEventInCity;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by Utente on 17/06/2015.
 */
public class ResultSEICity {
    @SerializedName("bindings")ArrayList<BindingsSEICity> bindings;

    public ResultSEICity(ArrayList<BindingsSEICity> bindings) {
        this.bindings = bindings;
    }

    public ArrayList<BindingsSEICity> getBindings() {
        return bindings;
    }

    public void setBindings(ArrayList<BindingsSEICity> bindings) {
        this.bindings = bindings;
    }

    @Override
    public String toString() {
        return "ResultStudentQuery: " + "\n" +
                "bindings: " + bindings + "\n";
    }
}
