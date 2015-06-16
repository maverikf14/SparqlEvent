package com.marco.sparqlevent.JSONWrapper.averageRateConcertQuery;

import com.google.gson.annotations.SerializedName;
import com.marco.sparqlevent.JSONWrapper.primitive.AverageWrapper;
import com.marco.sparqlevent.JSONWrapper.primitive.EventWrapper;

/**
 * Created by Utente on 16/06/2015.
 */
public class BindingARCQuery {
    @SerializedName("average")AverageWrapper average;
    @SerializedName("nome")EventWrapper event;

    public BindingARCQuery(AverageWrapper average, EventWrapper event) {
        this.average = average;
        this.event = event;
    }

    public AverageWrapper getAverage() {
        return average;
    }

    public void setAverage(AverageWrapper average) {
        this.average = average;
    }

    public EventWrapper getEvent() {
        return event;
    }

    public void setEvent(EventWrapper event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "BindingsARCQuery:" + "\n" +
                "Nome Evento: " + event.getValue() + "\n" +
                "average: " + average.getValue() + "\n";
    }
}
