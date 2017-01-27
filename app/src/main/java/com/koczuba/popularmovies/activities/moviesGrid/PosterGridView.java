package com.koczuba.popularmovies.activities.moviesGrid;

import com.koczuba.popularmovies.data.Movie;

import java.util.ArrayList;


public interface PosterGridView {
    void setActionBarTitle(int titleResource);
    void clearList();
    void displayMessage(int messageResource);
    void addMovies(ArrayList<Movie> movies);
    void showDetailActivity(Movie movie);
}
