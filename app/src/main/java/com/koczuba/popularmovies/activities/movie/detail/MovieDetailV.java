package com.koczuba.popularmovies.activities.movie.detail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.koczuba.popularmovies.databinding.ViewMovieDetailBinding;

public class MovieDetailV extends LinearLayout{

    private ViewMovieDetailBinding binding;

    public MovieDetailV(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())return;
        binding = ViewMovieDetailBinding.inflate(LayoutInflater.from(context), this, true);
    }

    public void setViewModel(MovieDetailVM viewModel){
        binding.setViewModel(viewModel);

    }





}
