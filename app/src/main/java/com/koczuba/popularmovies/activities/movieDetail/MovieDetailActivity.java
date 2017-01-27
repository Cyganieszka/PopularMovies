package com.koczuba.popularmovies.activities.movieDetail;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.koczuba.popularmovies.Prefs;
import com.koczuba.popularmovies.R;
import com.koczuba.popularmovies.data.Movie;
import com.koczuba.popularmovies.networking.MovieClient;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.movie_details);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if(getIntent().hasExtra(Prefs.MOVIE)){
            loadMovie((Movie) getIntent().getParcelableExtra(Prefs.MOVIE));
        }

    }

    private void loadMovie(Movie movie){
        TextView title=((TextView)findViewById(R.id.title));
        TextView release=((TextView)findViewById(R.id.release_date));
        TextView rating=((TextView)findViewById(R.id.rating));
        TextView overview=((TextView)findViewById(R.id.overview));
        ImageView poster=(ImageView) findViewById(R.id.poster);

        title.setText(movie.getOriginal_title());
        release.setText(movie.getRelease_date().substring(0,4));
        rating.setText(String.format(Locale.getDefault(),"%.2f/10",movie.getVote_average()));
        overview.setText(movie.getOverview());
        Picasso.with(this).load(MovieClient.buildPosterUrl(movie.getPoster_path()).toString()).into(poster);

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
}
