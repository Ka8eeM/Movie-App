package com.example.testtmdb.models;

import java.util.ArrayList;

public class ReviewModel  {
    private String id;
    private String page;
    private int total_pages;
    private int total_results;
    ArrayList<ReviewModelDetails> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public ArrayList<ReviewModelDetails> getResults() {
        return results;
    }

    public void setResults(ArrayList<ReviewModelDetails> results) {
        this.results = results;
    }
}
