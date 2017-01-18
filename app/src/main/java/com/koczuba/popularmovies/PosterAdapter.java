package com.koczuba.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.koczuba.popularmovies.data.Movie;
import com.koczuba.popularmovies.networking.MovieClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterViewHolder>{

    private ArrayList<Movie> movies= new ArrayList<>();
    private Context ctx;
    private final OnPosterClickListener listener;

    public PosterAdapter(Context ctx, OnPosterClickListener listener) {
        this.listener = listener;
        this.ctx=ctx;
    }

    public void addMovies(ArrayList<Movie>  movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public void clearMovies(){
        movies.clear();
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getMovies(){
        return movies;
    }




    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_poster, parent, false);
        return new PosterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Picasso.with(ctx).load(MovieClient.buildPosterUrl(movie.getPoster_path()).toString()).into(holder.posterImage);
    }

    @Override
    public int getItemCount() {
        if(movies==null)return 0;
        return movies.size();
    }

    class PosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView posterImage;

        PosterViewHolder(View itemView) {
            super(itemView);
            posterImage = (ImageView) itemView.findViewById(R.id.poster_image);
            posterImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Movie selected =movies.get(getAdapterPosition());
            listener.onPosterClick(selected);
        }
    }


    public interface OnPosterClickListener{
        void onPosterClick(Movie selected);
    }
}
