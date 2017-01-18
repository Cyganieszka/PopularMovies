package com.koczuba.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;


public class Prefs {

    private final SharedPreferences prefs;
    private static Prefs instance;
    private static final String appPreferences="app_preferences";

    private Prefs(Context ctx){
        prefs=ctx.getSharedPreferences(appPreferences, Context.MODE_PRIVATE);
    }

    static void init(Context ctx){
        if(instance==null){
            instance = new Prefs(ctx);
        }
    }

    public static final String MOVIE="movie";
    public static final String MOVIES="movies";
    public static final String LAYOUT="LAYOUT";

    private static final String SORT_TYPE="sort";
    public static final String POPULAR="popular";
    public static final String TOP_RATED="top_rated";

    public static String getSortType(){
        return instance.prefs.getString(SORT_TYPE,POPULAR);
    }
    public static void setSortType(String val){
        instance.prefs.edit().putString(SORT_TYPE,val).commit();
    }
}
