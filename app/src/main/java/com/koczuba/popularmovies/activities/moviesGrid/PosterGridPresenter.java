package com.koczuba.popularmovies.activities.moviesGrid;

import com.koczuba.popularmovies.Prefs;
import com.koczuba.popularmovies.R;
import com.koczuba.popularmovies.data.Movie;
import com.koczuba.popularmovies.data.MovieListResponse;
import com.koczuba.popularmovies.networking.MovieClient;
import com.koczuba.popularmovies.networking.MoviesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.net.UnknownHostException;
import java.util.ArrayList;

class PosterGridPresenter {

    private PosterGridView view;
    private MoviesService movieService;
    private boolean loading=true;
    private int pagesLoaded= 0;
    private int totalPages= 0;
    private ArrayList<Movie> movies;

    PosterGridPresenter(PosterGridView view) {
        this.view = view;
        movieService = MovieClient.createService(MoviesService.class);
        if(Prefs.getSortType().equals(Prefs.POPULAR)){
            loadPopularMovies();
        }else if(Prefs.getSortType().equals(Prefs.TOP_RATED)){
            loadTopRatedMovies();
        }
        setActionBarTitle();
    }

    void restoreState(PosterGridView view){
        this.view = view;
        setActionBarTitle();
        if(movies.size()>0) {
            view.addMovies(movies);
        }else{
            loadMoreMovies();
        }
    }

    void saveState(ArrayList<Movie> movies){
        this.movies=movies;
    }

    void onMovieClicked(Movie movie){
        if (view != null) {
            view.showDetailActivity(movie);
        }
    }
    void onPopularMoviesClicked(){
        clearMoviesList();
        Prefs.setSortType(Prefs.POPULAR);
        loadPopularMovies();
        setActionBarTitle();
    }
    void onTopRatedMoviesClicked(){
        clearMoviesList();
        Prefs.setSortType(Prefs.TOP_RATED);
        loadTopRatedMovies();
        setActionBarTitle();
    }

    void loadMoreMovies(){
        if (!loading && pagesLoaded< totalPages) {
            loading = true;

            if (Prefs.getSortType().equals(Prefs.POPULAR)) {
                loadPopularMovies();
            } else if (Prefs.getSortType().equals(Prefs.TOP_RATED)) {
                loadTopRatedMovies();
            }
        }
    }

    private void addMoviesToList(ArrayList<Movie> movies){
        if (view != null) {
            view.addMovies(movies);
        }
    }

    private void loadMovies(Call<MovieListResponse> moviesCall){
        moviesCall.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                ArrayList<Movie> movieList = response.body().getResults();
                totalPages=response.body().getTotal_pages();
                addMoviesToList(movieList);
                pagesLoaded++;
                loading = false;
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                loading = false;
                if(t instanceof UnknownHostException && view!=null) {
                    view.displayMessage(R.string.check_connection);
                }

            }
        });
    }

    private void loadPopularMovies() {
        Call<MovieListResponse> moviesCall = movieService.getPopularMovies(pagesLoaded+1);
        loadMovies(moviesCall);
    }

    private void loadTopRatedMovies() {
        Call<MovieListResponse> moviesCall = movieService.getTopRatedMovies(pagesLoaded+1);
        loadMovies(moviesCall);
    }

    private void clearMoviesList(){
        pagesLoaded=0;
        totalPages=0;
        if (view != null) {
            view.clearList();
        }
    }

    private void setActionBarTitle() {
        if (view != null) {
            if (Prefs.getSortType().equals(Prefs.POPULAR)) {
                view.setActionBarTitle(R.string.popular);
            } else if (Prefs.getSortType().equals(Prefs.TOP_RATED)) {
                view.setActionBarTitle(R.string.top_rated);
            }
        }
    }

}
