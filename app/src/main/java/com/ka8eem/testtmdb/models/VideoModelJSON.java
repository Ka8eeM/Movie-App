package com.ka8eem.testtmdb.models;

import java.util.ArrayList;

public class VideoModelJSON {
    String id;
    ArrayList<VideoModel> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<VideoModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<VideoModel> results) {
        this.results = results;
    }
}
