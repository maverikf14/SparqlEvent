package com.marco.sparqlevent.JSONWrapper.selectEventInCity;

import com.google.gson.annotations.SerializedName;
import com.marco.sparqlevent.JSONWrapper.primitive.DoubleWrapper;
import com.marco.sparqlevent.JSONWrapper.primitive.StringWrapper;

/**
 * Created by Utente on 17/06/2015.
 */
public class BindingsSEICity {
    @SerializedName("address")StringWrapper address;
    @SerializedName("eventName")StringWrapper eventName;
    @SerializedName("latitude")DoubleWrapper latitude;
    @SerializedName("longitude")DoubleWrapper longitude;
    @SerializedName("avrs")DoubleWrapper avrs;

    public BindingsSEICity(StringWrapper address, StringWrapper eventName, DoubleWrapper latitude, DoubleWrapper longitude, DoubleWrapper avrs) {
        this.address = address;
        this.eventName = eventName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.avrs = avrs;
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

    public DoubleWrapper getAvrs() {
        return avrs;
    }

    public void setAvrs(DoubleWrapper avrs) {
        this.avrs = avrs;
    }

    @Override
    public String toString() {
        return "BindingsSEICity: " + "\n" +
                "address: " + address + "\n" +
                "eventName=" + eventName + "\n" +
                "latitude=" + latitude + "\n" +
                "longitude=" + longitude + "\n" +
                "avrs=" + avrs;
    }
}
