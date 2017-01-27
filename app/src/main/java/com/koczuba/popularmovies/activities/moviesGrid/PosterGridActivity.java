package com.koczuba.popularmovies.activities.moviesGrid;

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
import com.koczuba.popularmovies.activities.movieDetail.MovieDetailActivity;
import com.koczuba.popularmovies.PosterAdapter;
import com.koczuba.popularmovies.R;
import com.koczuba.popularmovies.data.Movie;
import java.util.ArrayList;

public class PosterGridActivity extends AppCompatActivity implements PosterGridView, PosterAdapter.OnPosterClickListener {

    private PosterAdapter posterAdapter;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;

    private static PosterGridPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_grid);
        setupRecyclerView();

        if(presenter==null) {
            presenter = new PosterGridPresenter(this);
        }else{
            presenter.restoreState(this);
        }

        if(savedInstanceState!=null && savedInstanceState.containsKey(Prefs.LAYOUT)) {
            // restore previous scroll position on list
            loadRecyclerViewState(savedInstanceState);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter!=null) {
            // keep loaded movies between screen orientation change
            presenter.saveState(posterAdapter.getMovies());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //keep scroll position on list
        outState.putParcelable(Prefs.LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    private void setupRecyclerView(){
        posterAdapter = new PosterAdapter(this,this);
        layoutManager = new GridLayoutManager(this,2);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutManager.setSpanCount(4);
        }
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(posterAdapter);
        recyclerView.addOnScrollListener(neverEndingScrollListener);
    }

    private RecyclerView.OnScrollListener neverEndingScrollListener= new RecyclerView.OnScrollListener() {
        private final int visibleThreshold = 20;
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = recyclerView.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

            int seen=firstVisibleItem+visibleItemCount;
            int remaining=totalItemCount-seen;

            if (remaining<visibleThreshold) {
                presenter.loadMoreMovies();
            }
        }
    };

    private void loadRecyclerViewState(Bundle savedInstanceState){
        recyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(Prefs.LAYOUT));
    }

    @Override
    public void onPosterClick(Movie selected) {
        presenter.onMovieClicked(selected);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.grid_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_popular :
                presenter.onPopularMoviesClicked();
                return true;
            case R.id.sort_top_rated :
                presenter.onTopRatedMoviesClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        presenter=null;
        finish();
    }

    @Override
    public void addMovies(ArrayList<Movie> movies){
        posterAdapter.addMovies(movies);
    }

    @Override
    public void displayMessage(int messageResource) {
        Toast.makeText(this, messageResource, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setActionBarTitle(int titleResource){
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            actionBar.setTitle(titleResource);
        }
    }

    @Override
    public void clearList(){
        posterAdapter.clearMovies();
    }

    @Override
    public void showDetailActivity(Movie movie){
        Intent intent= new Intent(this,MovieDetailActivity.class);
        intent.putExtra(Prefs.MOVIE,movie);
        startActivity(intent);
    }

}
