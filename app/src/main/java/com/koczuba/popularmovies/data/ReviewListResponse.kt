package com.koczuba.popularmovies.data

import java.util.*

/**
 * https://developers.themoviedb.org/3/movies/get-movie-reviews
 */
class ReviewListResponse (val page: Int,
                          val results: ArrayList<Review> = ArrayList<Review>(),
                          val total_results: Int,
                          val total_pages: Int)
