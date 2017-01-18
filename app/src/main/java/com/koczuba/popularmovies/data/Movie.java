package com.koczuba.popularmovies.data;


import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable{

    String poster_path;
    String original_title;
    String overview;
    float vote_average;
    String release_date;


    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public float getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }


    protected Movie(Parcel in) {
        poster_path = in.readString();
        original_title = in.readString();
        overview = in.readString();
        vote_average = in.readFloat();
        release_date = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(poster_path);
        parcel.writeString(original_title);
        parcel.writeString(overview);
        parcel.writeFloat(vote_average);
        parcel.writeString(release_date);
    }
}
