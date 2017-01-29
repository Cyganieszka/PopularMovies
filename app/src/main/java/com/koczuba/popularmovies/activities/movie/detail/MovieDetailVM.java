package com.koczuba.popularmovies.activities.movie.detail;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.koczuba.popularmovies.BR;
import com.koczuba.popularmovies.Prefs;
import com.koczuba.popularmovies.R;
import com.koczuba.popularmovies.data.Movie;
import com.koczuba.popularmovies.networking.MovieClient;


import java.util.Locale;

public class MovieDetailVM extends BaseObservable{

    private Movie movie;

    public void setMovie(Movie movie){
        this.movie=movie;
        notifyPropertyChanged(BR._all);
    }

    @Bindable
    public Boolean getIsMovieSelected(){
        return movie!=null;
    }

    @Bindable
    public String getTitle(){
        return movie.getOriginal_title();
    }

    @Bindable
    public String getRelease(){
        return movie.getRelease_date();
    }

    @Bindable
    public String getRating(){
        return String.format(Locale.getDefault(), Prefs.RATING_FORMAT,movie.getVote_average());
    }

    @Bindable
    public String getOverview(){
        return  movie.getOverview();
    }

    public String getPosterUrl() {
        return MovieClient.buildPosterUrl(movie.getPoster_path()).toString();
    }

    @Bindable
    public int getActionBarTitle(){
        return R.string.movie_details;
    }




}
