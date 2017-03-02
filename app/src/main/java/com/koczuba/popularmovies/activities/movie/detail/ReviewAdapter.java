package com.koczuba.popularmovies.activities.movie.detail;

import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.koczuba.popularmovies.data.Review;
import com.koczuba.popularmovies.databinding.ItemReviewBinding;
import com.koczuba.popularmovies.utils.ObservableListAdapter;

/**
 * Created by agnieszka on 01.02.2017.
 */
public class ReviewAdapter extends ObservableListAdapter<Review,ReviewAdapter.ReviewViewHolder> {


    private final OnReviewClickListener listener;


    ReviewAdapter(ObservableArrayList<Review> reviews, ReviewAdapter.OnReviewClickListener listener) {
        super(reviews);
        this.listener = listener;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemReviewBinding binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false);
        return new ReviewAdapter.ReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.binding.setViewModel(new ReviewVM(list.get(position)));
        holder.binding.executePendingBindings();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemReviewBinding binding;

        ReviewViewHolder(ItemReviewBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            //binding.posterImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Review selected =list.get(getAdapterPosition());
            listener.onReviewClick(selected);
        }
    }


    public interface OnReviewClickListener{
        void onReviewClick(Review selected);
    }
}
