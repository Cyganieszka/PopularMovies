package com.koczuba.popularmovies.data

import java.util.ArrayList

/**
 * https://developers.themoviedb.org/3/movies/get-popular-movies
 * https://developers.themoviedb.org/3/movies/get-top-rated-movies
 */

class MovieListResponse(val page: Int,
                        val results: ArrayList<Movie> = ArrayList<Movie>(),
                        val total_results: Int,
                        val total_pages: Int)