package com.koczuba.popularmovies.networking;


import com.koczuba.popularmovies.data.MovieListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesService {

    @GET("/3/movie/popular")
    Call<MovieListResponse> getPopularMovies(@Query("page") int page);
    @GET("/3/movie/top_rated")
    Call<MovieListResponse> getTopRatedMovies(@Query("page")int page);
}
