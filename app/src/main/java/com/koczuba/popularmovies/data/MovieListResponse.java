package com.koczuba.popularmovies.data;

import java.util.ArrayList;


public class MovieListResponse {
    private int page;
    private ArrayList<Movie> results=new ArrayList<>();
    private int total_results;
    private int total_pages;

    public ArrayList<Movie>  getResults() {
        return results;
    }

    public int getTotal_pages() {
        return total_pages;
    }
}