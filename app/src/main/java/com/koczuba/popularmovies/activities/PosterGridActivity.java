package com.koczuba.popularmovies.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.koczuba.popularmovies.Prefs;
import com.koczuba.popularmovies.networking.MovieClient;
import com.koczuba.popularmovies.networking.MoviesService;
import com.koczuba.popularmovies.PosterAdapter;
import com.koczuba.popularmovies.R;
import com.koczuba.popularmovies.data.Movie;
import com.koczuba.popularmovies.data.MovieListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class PosterGridActivity extends AppCompatActivity implements PosterAdapter.OnPosterClickListener {

    private PosterAdapter posterAdapter;
    private MoviesService movieService;
    private RecyclerView recyclerView;

    private int pagesLoaded= 0;
    private int totalPages= 0;
    private boolean loading=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_grid);

        posterAdapter = new PosterAdapter(this,this);
        movieService = MovieClient.createService(MoviesService.class);

        setActionBarTitle();
        setupRecyclerView();

        if(savedInstanceState == null || !savedInstanceState.containsKey(Prefs.MOVIES)) {
            freshStart();
        }
        else {
            loadFromInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Prefs.MOVIES, posterAdapter.getMovies());
        outState.putParcelable(Prefs.LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    private void setupRecyclerView(){
        final GridLayoutManager layoutManager = new GridLayoutManager(this,2);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutManager.setSpanCount(4);
        }
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(posterAdapter);



        // load more pages on scroll
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private final int visibleThreshold = 20;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = recyclerView.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                int seen=firstVisibleItem+visibleItemCount;
                int remaining=totalItemCount-seen;


                if (!loading && (remaining<visibleThreshold) && pagesLoaded< totalPages) {
                    loading = true;
                    loadPopularMovies();
                }
            }
        });
    }


    private void freshStart(){
        setActionBarTitle();
        if(Prefs.getSortType().equals(Prefs.POPULAR)){
            loadPopularMovies();
        }else if(Prefs.getSortType().equals(Prefs.TOP_RATED)){
            loadTopRatedMovies();
        }
    }

    private void loadFromInstanceState(Bundle savedInstanceState){
        setActionBarTitle();
        posterAdapter.addMovies(savedInstanceState.<Movie>getParcelableArrayList(Prefs.MOVIES));
        recyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(Prefs.LAYOUT));
    }

    private void setActionBarTitle(){
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
            if(Prefs.getSortType().equals(Prefs.POPULAR)){
                actionBar.setTitle(R.string.popular);
            }else if(Prefs.getSortType().equals(Prefs.TOP_RATED)){
                actionBar.setTitle(R.string.top_rated);
            }
    }


    private void clearList(){
        pagesLoaded=0;
        totalPages=0;
        posterAdapter.clearMovies();
    }
    private void loadPopularMovies() {
        Call<MovieListResponse> moviesCall = movieService.getPopularMovies(pagesLoaded+1);
        loadMovies(moviesCall);
    }

    private void loadTopRatedMovies() {
        Call<MovieListResponse> moviesCall = movieService.getTopRatedMovies(pagesLoaded+1);
        loadMovies(moviesCall);
    }


    private void loadMovies(Call<MovieListResponse> moviesCall){
        moviesCall.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                ArrayList<Movie> movieList = response.body().getResults();
                totalPages=response.body().getTotal_pages();
                posterAdapter.addMovies(movieList);
                pagesLoaded++;
                loading = false;
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                loading = false;
                if(t instanceof UnknownHostException)
                        Toast.makeText(PosterGridActivity.this,R.string.check_connection,Toast.LENGTH_SHORT).show();
            }
        });
    }

    // display movie details on click

    @Override
    public void onPosterClick(Movie selected) {
        Intent intent= new Intent(this,MovieDetailActivity.class);
        intent.putExtra(Prefs.MOVIE,selected);
        startActivity(intent);
    }

    // menu: popular/top_rated switch

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.grid_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_popular :
                clearList();
                Prefs.setSortType(Prefs.POPULAR);
                loadPopularMovies();
                setActionBarTitle();
                return true;
            case R.id.sort_top_rated :
                clearList();
                Prefs.setSortType(Prefs.TOP_RATED);
                loadTopRatedMovies();
                setActionBarTitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
