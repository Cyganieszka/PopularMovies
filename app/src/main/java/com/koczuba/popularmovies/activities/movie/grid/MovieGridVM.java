package com.koczuba.popularmovies.activities.movie.grid;

import android.databinding.*;
import com.koczuba.popularmovies.BR;
import com.koczuba.popularmovies.Prefs;
import com.koczuba.popularmovies.data.Movie;
import com.koczuba.popularmovies.data.MovieListResponse;
import com.koczuba.popularmovies.networking.MoviesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.koczuba.popularmovies.R;

import java.net.UnknownHostException;
import java.util.ArrayList;


public class MovieGridVM extends BaseObservable implements MovieAdapter.OnPosterClickListener {

    private MoviesService movieService;
    private PosterGridVMListener listener;
    private int actionBarTitle;

    private ObservableInt totalPages= new ObservableInt(0);
    private ObservableInt loadedPages= new ObservableInt(0);
    private ObservableBoolean isLoading=new ObservableBoolean(false);
    private ObservableArrayList<Movie> movies= new ObservableArrayList<>();

    public MovieGridVM(MoviesService movieService) {
        this.movieService=movieService;
        updateActionBarTitle();
        loadMoreMovies();
    }

    ObservableArrayList<Movie> getMovies(){
        return movies;
    }

    public void setListener(PosterGridVMListener listener){
        this.listener=listener;
    }

    @Override
    public void onPosterClick(Movie selected) {
        if(listener!=null)listener.displayDetails(selected);
    }

    @Bindable
    public int getActionBarTitle(){
        return actionBarTitle;
    }

    private void setActionBarTitle(int resId){
        actionBarTitle=resId;
        notifyPropertyChanged(BR.actionBarTitle);
    }

    private void updateActionBarTitle(){
        if (Prefs.getSortType().equals(Prefs.POPULAR)) {
            setActionBarTitle(R.string.popular);
        } else if (Prefs.getSortType().equals(Prefs.TOP_RATED)) {
            setActionBarTitle(R.string.top_rated);
        }
    }

    private void onMoviesLoaded(int totalPages, int loadedPage,ArrayList<Movie> movieList){
        this.totalPages.set(totalPages);
        this.loadedPages.set(loadedPage);
        this.movies.addAll(movieList);
    }

    public void onPopularMoviesClicked(){
        clearMoviesList();
        Prefs.setSortType(Prefs.POPULAR);
        loadPopularMovies();
        updateActionBarTitle();
    }

    public void onTopRatedMoviesClicked(){
        clearMoviesList();
        Prefs.setSortType(Prefs.TOP_RATED);
        loadTopRatedMovies();
        updateActionBarTitle();
    }

    private void clearMoviesList(){
        totalPages.set(0);
        loadedPages.set(0);
        movies.clear();
    }

    public void loadMoreMovies(){
        if (!isLoading.get() &&( loadedPages.get()< totalPages.get() || loadedPages.get()==0)) {
            isLoading.set(true);

            if (Prefs.getSortType().equals(Prefs.POPULAR)) {
                loadPopularMovies();
            } else if (Prefs.getSortType().equals(Prefs.TOP_RATED)) {
                loadTopRatedMovies();
            }
        }
    }

    private void loadPopularMovies() {
        Call<MovieListResponse> moviesCall = movieService.getPopularMovies(loadedPages.get()+1);
        loadMovies(moviesCall);
    }

    private void loadTopRatedMovies() {
        Call<MovieListResponse> moviesCall = movieService.getTopRatedMovies(loadedPages.get()+1);
        loadMovies(moviesCall);
    }


    private void loadMovies(Call<MovieListResponse> moviesCall){
        moviesCall.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                ArrayList<Movie> movieList = response.body().getResults();
                onMoviesLoaded(response.body().getTotal_pages(),loadedPages.get()+1,movieList);
                isLoading.set(false);
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                isLoading.set(false);
                if(t instanceof UnknownHostException && listener!=null) {
                    listener.onLoadFailure();
                }
            }
        });
    }

    public interface PosterGridVMListener
    {
        void onLoadFailure();
        void displayDetails(Movie movie);
    }

}
