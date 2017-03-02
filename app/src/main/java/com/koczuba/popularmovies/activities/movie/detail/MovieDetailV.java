package com.koczuba.popularmovies.activities.movie.detail;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.koczuba.popularmovies.databinding.ViewMovieDetailBinding;

public class MovieDetailV extends LinearLayout {

    private ViewMovieDetailBinding binding;
    MovieDetailVM viewModel;
    private Context ctx;

    public MovieDetailV(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())return;
        ctx=context;
        binding = ViewMovieDetailBinding.inflate(LayoutInflater.from(context), this, true);

    }

    public void setViewModel(MovieDetailVM viewModel){
        binding.setViewModel(viewModel);
        this.viewModel=viewModel;
        setupReviewsRecyclerView(binding.reviews);
        setupTrailersRecyclerView(binding.trailers);
    }

    private void setupTrailersRecyclerView(RecyclerView recyclerView){

        TrailerAdapter trailerAdapter = new TrailerAdapter(viewModel.getTrailers(),viewModel);
        GridLayoutManager layoutManager = new GridLayoutManager(ctx,2);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutManager.setSpanCount(4);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(trailerAdapter);
    }

    private void setupReviewsRecyclerView(RecyclerView recyclerView){

        ReviewAdapter reviewAdapter = new ReviewAdapter(viewModel.getReviews(),viewModel);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(reviewAdapter);
        recyclerView.addOnScrollListener(neverEndingScrollListener);
    }

    private RecyclerView.OnScrollListener neverEndingScrollListener= new RecyclerView.OnScrollListener() {
        private final int visibleThreshold = 20;
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager layoutManager =(LinearLayoutManager) recyclerView.getLayoutManager();

            int visibleItemCount = recyclerView.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

            int seen=firstVisibleItem+visibleItemCount;
            int remaining=totalItemCount-seen;

            if (remaining<visibleThreshold) {
                viewModel.loadMoreReviews();
            }
        }
    };
}
