package com.koczuba.popularmovies.activities.movie.grid;

import com.koczuba.popularmovies.data.Movie;
import com.koczuba.popularmovies.networking.MovieClient;

public class MovieVM {

    String imageUrl;

    MovieVM(Movie movie) {
        this.imageUrl = movie.getPoster_path();
    }

    public String getImageUrl(){
        return MovieClient.buildPosterUrl(imageUrl).toString();
    }
}
