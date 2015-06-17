package com.marco.sparqlevent.JSONWrapper.selectNearEvent;

import com.google.gson.annotations.SerializedName;
import com.marco.sparqlevent.JSONWrapper.primitive.DoubleWrapper;
import com.marco.sparqlevent.JSONWrapper.primitive.StringWrapper;

/**
 * Created by Utente on 18/06/2015.
 */
public class BindingsNearEvent {
    @SerializedName("address")StringWrapper address;
    @SerializedName("eventName")StringWrapper eventName;
    @SerializedName("latitude")DoubleWrapper latitude;
    @SerializedName("longitude")DoubleWrapper longitude;
    @SerializedName("distanceKm")DoubleWrapper distance;

    public BindingsNearEvent(StringWrapper address, StringWrapper eventName, DoubleWrapper latitude, DoubleWrapper longitude, DoubleWrapper avrs) {
        this.address = address;
        this.eventName = eventName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = avrs;
    }

    public StringWrapper getAddress() {
        return address;
    }

    public void setAddress(StringWrapper address) {
        this.address = address;
    }

    public StringWrapper getEventName() {
        return eventName;
    }

    public void setEventName(StringWrapper eventName) {
        this.eventName = eventName;
    }

    public DoubleWrapper getLatitude() {
        return latitude;
    }

    public void setLatitude(DoubleWrapper latitude) {
        this.latitude = latitude;
    }

    public DoubleWrapper getLongitude() {
        return longitude;
    }

    public void setLongitude(DoubleWrapper longitude) {
        this.longitude = longitude;
    }

    public DoubleWrapper getDistance() {
        return distance;
    }

    public void setDistance(DoubleWrapper avrs) {
        this.distance = avrs;
    }

    @Override
    public String toString() {
        return "BindingsSEICity: " + "\n" +
                "address: " + address + "\n" +
                "eventName=" + eventName + "\n" +
                "latitude=" + latitude + "\n" +
                "longitude=" + longitude + "\n" +
                "distance=" + distance;
    }
}
