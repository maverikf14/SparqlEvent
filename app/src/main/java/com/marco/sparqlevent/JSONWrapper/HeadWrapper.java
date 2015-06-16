package com.marco.sparqlevent.JSONWrapper;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Utente on 16/06/2015.
 */
public class HeadWrapper {
    @SerializedName("vars")ArrayList<String> vars;
    @SerializedName("link")ArrayList<String> link;

    /**
     *
     */
    public HeadWrapper() {
    }

    /**
     *
     * @param vars
     * @param link
     */
    public HeadWrapper(ArrayList<String> vars, ArrayList<String> link) {
        this.vars = vars;
        this.link = link;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getVars() {
        return vars;
    }

    /**
     *
     * @param vars
     */
    public void setVars(ArrayList<String> vars) {
        this.vars = vars;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getLink() {
        return link;
    }

    /**
     *
     * @param link
     */
    public void setLink(ArrayList<String> link) {
        this.link = link;
    }

    @Override
    public String toString() {
        String tmp = "HeadWrapper: \n" +
                "vars: " + vars + "\n" +
                "link: " + link + "\n";
        return tmp;
    }
}
