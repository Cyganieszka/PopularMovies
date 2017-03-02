package com.koczuba.popularmovies.activities.movie.detail;


import android.databinding.*;
import com.koczuba.popularmovies.BR;
import com.koczuba.popularmovies.Prefs;
import com.koczuba.popularmovies.R;
import com.koczuba.popularmovies.data.*;
import com.koczuba.popularmovies.networking.MovieClient;
import com.koczuba.popularmovies.networking.MoviesService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;

public class MovieDetailVM extends BaseObservable implements TrailerAdapter.OnTrailerClickListener, ReviewAdapter.OnReviewClickListener{

    private Movie movie;
    private MoviesService movieService;
    private MovieDetailVMListener listener;

    private ObservableInt totalReviewPages= new ObservableInt(0);
    private ObservableInt loadedReviewPages= new ObservableInt(0);
    private ObservableBoolean isLoading=new ObservableBoolean(false);
    private ObservableArrayList<Review> reviews= new ObservableArrayList<>();
    private ObservableArrayList<Trailer> trailers= new ObservableArrayList<>();

    public MovieDetailVM(MoviesService movieService) {
        this.movieService=movieService;

    }
    public void setMovie(Movie movie){
        this.movie=movie;
        reviews.clear();
        trailers.clear();
        totalReviewPages.set(0);
        loadedReviewPages.set(0);
        isLoading.set(false);
        loadTrailers();
        loadMoreReviews();
        notifyPropertyChanged(BR._all);
    }

    public void setListener(MovieDetailVMListener listener) {
        this.listener = listener;
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


    @Override
    public void onTrailerClick(Trailer selected) {
        if(listener!=null)listener.displayTrailer(selected);
    }

    @Override
    public void onReviewClick(Review selected) {
        //nothing
    }

    private void onReviewsLoaded(int totalPages, int loadedPage, ArrayList<Review> reviewsList){
        totalReviewPages.set(totalPages);
        loadedReviewPages.set(loadedPage);
        reviews.addAll(reviewsList);
    }

    private void onTrailersLoaded(ArrayList<Trailer> trailerList){
        trailers.addAll(trailerList);
    }

    ObservableArrayList<Review> getReviews() {
        return reviews;
    }


    ObservableArrayList<Trailer> getTrailers() {
        return trailers;
    }

    void loadTrailers() {
        Call<TrailerListResponse> trailersCall = movieService.getTrailerForMovie( movie.getId());
        loadTrailers(trailersCall);
    }

    void loadMoreReviews(){
        Call<ReviewListResponse> reviewsCall = movieService.getReviewForMovie(movie.getId(),loadedReviewPages.get()+1);
        loadReviews(reviewsCall);
    }

    private void loadReviews(retrofit2.Call<ReviewListResponse> call){

        if (!isLoading.get() &&( loadedReviewPages.get()< totalReviewPages.get() || loadedReviewPages.get()==0)) {
            isLoading.set(true);
            call.enqueue(new Callback<ReviewListResponse>() {
                @Override
                public void onResponse(retrofit2.Call<ReviewListResponse> call, Response<ReviewListResponse> response) {
                    ArrayList<Review> reviewList = response.body().getResults();
                    onReviewsLoaded(response.body().getTotal_pages(), loadedReviewPages.get() + 1, reviewList);
                    isLoading.set(false);
                }

                @Override
                public void onFailure(retrofit2.Call<ReviewListResponse> call, Throwable t) {
                    if (t instanceof UnknownHostException && listener != null) {
                        listener.onLoadFailure();
                        isLoading.set(false);
                    }
                }
            });
        }
    }

    private void loadTrailers(retrofit2.Call<TrailerListResponse> call){
        call.enqueue(new Callback<TrailerListResponse>() {
            @Override
            public void onResponse(retrofit2.Call<TrailerListResponse> call, Response<TrailerListResponse> response) {
                ArrayList<Trailer> trailerList = response.body().getResults();
                onTrailersLoaded(trailerList);
            }

            @Override
            public void onFailure(retrofit2.Call<TrailerListResponse> call, Throwable t) {
                if(t instanceof UnknownHostException && listener!=null) {
                    listener.onLoadFailure();
                }
            }
        });
    }

    public interface MovieDetailVMListener
    {
        void onLoadFailure();
        void displayTrailer(Trailer trailer);
    }

}
