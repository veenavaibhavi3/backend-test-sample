package com.noesisinformatica.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <br>Written by @author noesisdev
 * <br>Created on 02/03/2014
 */
public class DataService {

    private Map<Long, String> data = new HashMap<Long, String>();
    private long lastUsedId = 1000;

    public DataService(){

        // save some initial data
        saveTerm("First term");
        saveTerm("Second term");
        saveTerm("Another term");
        saveTerm("Miscellaneous term");
        saveTerm("");
    }

    String getTermForId(Long id){
        return data.get(id);
    }

    void saveTerm(String term){
        // check if term already exists in data
        boolean exists = false;
        for(String t: data.values())
        {
            if(term.equals(t)){
                exists = true;
                break;
            }
        }

        // add term if it does not already exist
        if(!exists)
        {
            // get last used Id and generate next id
            long key = getLastUsedId() + 1;
            data.put(key, term);
            // set key as last used id, so it is available for next use
            setLastUsedId(key);
        }
    }

    Collection<String> getAllTerm(){
        return data.values();
    }

    public long getLastUsedId() {
        return lastUsedId;
    }

    public void setLastUsedId(long lastUsedId) {
        this.lastUsedId = lastUsedId;
    }
}
