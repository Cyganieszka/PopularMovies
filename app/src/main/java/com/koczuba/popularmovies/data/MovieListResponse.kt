package com.koczuba.popularmovies.data

import java.util.ArrayList

/**
 * Because kotlin data classes are smaller...
 */

class MovieListResponse(val page: Int,
                        val results: ArrayList<Movie> = ArrayList<Movie>(),
                        val total_results: Int,
                        val total_pages: Int)