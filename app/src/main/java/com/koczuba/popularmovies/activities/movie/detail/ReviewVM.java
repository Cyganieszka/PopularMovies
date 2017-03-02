package com.koczuba.popularmovies.activities.movie.detail;

import com.koczuba.popularmovies.data.Review;


public class ReviewVM {

    private final Review review;

    ReviewVM(Review review) {
        this.review=review;
    }
    public String getAutor(){
        return review.getAutor();
    }
    public String getContent(){
        return review.getContent();
    }
}
