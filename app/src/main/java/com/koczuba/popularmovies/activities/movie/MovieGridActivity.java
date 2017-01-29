package com.koczuba.popularmovies.activities.movie;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.koczuba.popularmovies.App;
import com.koczuba.popularmovies.Prefs;
import com.koczuba.popularmovies.R;
import com.koczuba.popularmovies.activities.MovieSelectionActivity;
import com.koczuba.popularmovies.activities.movie.MovieDetailActivity;
import com.koczuba.popularmovies.activities.movie.grid.MovieGridVM;
import com.koczuba.popularmovies.activities.movie.grid.MovieGridVM;
import com.koczuba.popularmovies.data.Movie;
import com.koczuba.popularmovies.databinding.ActivityPosterGridBinding;

/**
 * Displays movie grid view
 */
public class MovieGridActivity extends MovieSelectionActivity {

    private MovieGridVM viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel= App.get(this).movieGridVM;
        viewModel.setListener(this);

        ActivityPosterGridBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_poster_grid);
        binding.setViewModel(viewModel);
        setupActionBar(binding.toolbar);
        binding.viewMovieGrid.setViewModel(viewModel);

    }

    @Override
    public void onLoadFailure() {
        displayMessage(R.string.check_connection);
    }

    @Override
    public void displayDetails(Movie movie) {
        showDetailActivity(movie);
    }


    private void showDetailActivity(Movie movie){
        Intent intent= new Intent(this,MovieDetailActivity.class);
        intent.putExtra(Prefs.MOVIE,movie);
        startActivity(intent);
    }

    @Override
    protected void onPopularMoviesClicked() {
        viewModel.onPopularMoviesClicked();
    }

    @Override
    protected void onTopRatedMoviesClicked() {
        viewModel.onTopRatedMoviesClicked();
    }
}
