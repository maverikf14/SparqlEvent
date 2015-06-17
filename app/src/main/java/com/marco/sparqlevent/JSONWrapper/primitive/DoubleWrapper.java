package com.marco.sparqlevent.JSONWrapper.primitive;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Utente on 17/06/2015.
 */
public class DoubleWrapper {
    @SerializedName("datatype")String datatype;
    @SerializedName("type")String type;
    @SerializedName("value")String value;

    /**
     *
     * @param datatype
     * @param type
     * @param value
     */
    public DoubleWrapper(String datatype, String type, String value) {
        this.datatype = datatype;
        this.type = type;
        this.value = value;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return Double.parseDouble(value);
    }

    public void setValue(double value) {
        this.value = Double.toString(value);
    }

    @Override
    public String toString() {
        return "DoubleWrapper: " + "\n" +
                "datatype: " + datatype + "\n" +
                "type: " + type + "\n" +
                "value: " + value + "\n";
    }
}
