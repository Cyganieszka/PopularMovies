package com.koczuba.popularmovies.activities.movie.detail;

import com.koczuba.popularmovies.data.Trailer;


public class TrailerVM {

    private final Trailer trailer;

    TrailerVM(Trailer trailer) {
        this.trailer=trailer;
    }

    public String getTrailerName(){
        return trailer.getName();
    }
}
