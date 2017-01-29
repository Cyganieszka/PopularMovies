package com.koczuba.popularmovies.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;


public class CustomBindingAdapters {

    // workaround for binding text resource
    @BindingAdapter({"actionBarTitle"})
    public static void setStringResource(Toolbar toolbar, int resource) {
        toolbar.setTitle(resource);
    }

    // workaround for binding image provided by picasso
    @BindingAdapter({"posterUrl"})
    public static void loadPoster(ImageView view, String posterUrl) {
        if(posterUrl.length()>0)
        Picasso.with(view.getContext()).load(posterUrl).into(view);
    }


}
