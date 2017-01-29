package com.koczuba.popularmovies.activities.movie.grid;


import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.koczuba.popularmovies.databinding.ItemPosterBinding;
import com.koczuba.popularmovies.utils.ObservableListAdapter;
import com.koczuba.popularmovies.data.Movie;


public class MovieAdapter extends ObservableListAdapter<Movie,MovieAdapter.PosterViewHolder>{


    private final OnPosterClickListener listener;

    MovieAdapter(ObservableArrayList<Movie> movies, OnPosterClickListener listener) {
        super(movies);
        this.listener = listener;
    }

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPosterBinding binding = ItemPosterBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new PosterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {
        holder.binding.setViewModel(new com.koczuba.popularmovies.activities.movie.grid.MovieVM(list.get(position)));
        holder.binding.executePendingBindings();
    }

    class PosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemPosterBinding binding;

        PosterViewHolder(ItemPosterBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            binding.posterImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Movie selected =list.get(getAdapterPosition());
            listener.onPosterClick(selected);
        }
    }


    public interface OnPosterClickListener{
        void onPosterClick(Movie selected);
    }
}
