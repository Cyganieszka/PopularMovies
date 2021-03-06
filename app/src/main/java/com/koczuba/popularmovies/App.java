package com.koczuba.popularmovies;

import android.app.Application;
import android.content.Context;
import com.koczuba.popularmovies.activities.movie.detail.MovieDetailVM;
import com.koczuba.popularmovies.activities.movie.grid.MovieGridVM;
import com.koczuba.popularmovies.networking.MovieClient;
import com.koczuba.popularmovies.networking.MoviesService;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Prefs.init(this);
        // some DI framework would do this job...
        movieService = MovieClient.createService(MoviesService.class);
        movieDetailVM=new MovieDetailVM(movieService);
        movieGridVM=new MovieGridVM(movieService);
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    private MoviesService movieService;
    public MovieDetailVM movieDetailVM;
    public MovieGridVM movieGridVM;
}
