package com.koczuba.popularmovies.data

import android.os.Parcel
import android.os.Parcelable

/**
 * Because kotlin data classes are smaller...
 */

class Movie private constructor(val poster_path: String,
                                val original_title: String,
                                val overview: String,
                                val vote_average: Float,
                                val release_date: String) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(source: Parcel): Movie = Movie(source)
            override fun newArray(size: Int): Array<Movie?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString(), source.readFloat(), source.readString())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(poster_path)
        dest?.writeString(original_title)
        dest?.writeString(overview)
        dest?.writeFloat(vote_average)
        dest?.writeString(release_date)
    }
}
