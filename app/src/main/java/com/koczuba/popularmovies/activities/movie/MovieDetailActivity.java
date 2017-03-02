package com.koczuba.popularmovies.activities.movie;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.koczuba.popularmovies.App;
import com.koczuba.popularmovies.Prefs;
import com.koczuba.popularmovies.R;
import com.koczuba.popularmovies.activities.movie.detail.MovieDetailVM;
import com.koczuba.popularmovies.activities.movie.detail.TrailerAdapter;
import com.koczuba.popularmovies.data.Movie;
import com.koczuba.popularmovies.data.Trailer;
import com.koczuba.popularmovies.databinding.ActivityMovieDetailBinding;

/**
 * Displays movie detail view
 */
public class MovieDetailActivity extends AppCompatActivity implements TrailerAdapter.OnTrailerClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MovieDetailVM viewModel= App.get(this).movieDetailVM;

        if(getIntent().hasExtra(Prefs.MOVIE)){
            viewModel.setMovie((Movie) getIntent().getParcelableExtra(Prefs.MOVIE));
        }

        ActivityMovieDetailBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_movie_detail);
        binding.setViewModel(viewModel);
        setupActionBar(binding.toolbar);
        binding.viewMovieDetail.setViewModel(viewModel);

    }

    private void setupActionBar(Toolbar toolbar) {
        // used ActionBar declared in xml, to bind ActionBar title
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTrailerClick(Trailer selected) {
        watchYoutubeVideo(selected.getKey());
    }
    private void watchYoutubeVideo(String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

}
