package com.koczuba.popularmovies.activities.movie;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import com.koczuba.popularmovies.App;
import com.koczuba.popularmovies.R;
import com.koczuba.popularmovies.activities.MovieSelectionActivity;
import com.koczuba.popularmovies.activities.movie.detail.MovieDetailVM;
import com.koczuba.popularmovies.activities.movie.grid.MovieGridVM;
import com.koczuba.popularmovies.data.Movie;
import com.koczuba.popularmovies.databinding.ActivityMovieMasterDetailBinding;

/**
 * Displays both movie detail view and movie grid view
 */
public class MovieMasterDetailActivity extends MovieSelectionActivity {

    private MovieGridVM masterViewModel;
    private MovieDetailVM detailViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.getClass().getSimpleName(),"oncreate");
        detailViewModel= App.get(this).movieDetailVM;
        masterViewModel= App.get(this).movieGridVM;
        masterViewModel.setListener(this);

        ActivityMovieMasterDetailBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_movie_master_detail);
        binding.setMasterViewModel(masterViewModel);
        binding.setDetailViewModel(detailViewModel);
        // there are some bugs in databinding library that prevents me from passing it via xml...
        binding.master.setViewModel(masterViewModel);
        binding.detail.setViewModel(detailViewModel);
        setupActionBar(binding.toolbar);
    }

    @Override
    public void onLoadFailure() {
        displayMessage(R.string.check_connection);
    }

    @Override
    public void displayDetails(Movie movie) {
        detailViewModel.setMovie(movie);
    }

    @Override
    protected void onPopularMoviesClicked() {
        masterViewModel.onPopularMoviesClicked();
    }

    @Override
    protected void onTopRatedMoviesClicked() {
        masterViewModel.onTopRatedMoviesClicked();
    }
}
