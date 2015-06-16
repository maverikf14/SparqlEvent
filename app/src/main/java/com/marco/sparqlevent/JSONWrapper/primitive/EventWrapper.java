package com.marco.sparqlevent.JSONWrapper.primitive;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Utente on 16/06/2015.
 */
public class EventWrapper {
    @SerializedName("type")String type;
    @SerializedName("value")String value;

    public EventWrapper(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "EventWrapper: " + "\n" +
                "type: " + type + "\n" +
                "value: " + value + "\n";
    }
}
