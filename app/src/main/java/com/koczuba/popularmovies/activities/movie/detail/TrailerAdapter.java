package com.koczuba.popularmovies.activities.movie.detail;

import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.koczuba.popularmovies.data.Review;
import com.koczuba.popularmovies.data.Trailer;
import com.koczuba.popularmovies.databinding.ItemReviewBinding;
import com.koczuba.popularmovies.databinding.ItemTrailerBinding;
import com.koczuba.popularmovies.utils.ObservableListAdapter;

/**
 * Created by agnieszka on 01.02.2017.
 */
public class TrailerAdapter extends ObservableListAdapter<Trailer,TrailerAdapter.TrailerViewHolder> {


    private final OnTrailerClickListener listener;


    TrailerAdapter(ObservableArrayList<Trailer> trailers, TrailerAdapter.OnTrailerClickListener listener) {
        super(trailers);
        this.listener = listener;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTrailerBinding binding = ItemTrailerBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new TrailerAdapter.TrailerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.binding.setViewModel(new TrailerVM(list.get(position)));
        holder.binding.executePendingBindings();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemTrailerBinding binding;

        TrailerViewHolder(ItemTrailerBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            //binding.posterImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Trailer selected =list.get(getAdapterPosition());
            listener.onTrailerClick(selected);
        }
    }


    public interface OnTrailerClickListener{
        void onTrailerClick(Trailer selected);
    }
}
