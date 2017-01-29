package com.koczuba.popularmovies.activities.movie.grid;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.koczuba.popularmovies.databinding.ViewMovieGridBinding;

public class MovieGridV extends LinearLayout{

    private ViewMovieGridBinding binding;
    private Context ctx;
    private MovieGridVM viewModel;

    public MovieGridV(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode())return;
        this.ctx=context;
        binding = ViewMovieGridBinding.inflate(LayoutInflater.from(context), this, true);

    }
    public void setViewModel(com.koczuba.popularmovies.activities.movie.grid.MovieGridVM viewModel){

        binding.setViewModel(viewModel);
        this.viewModel=viewModel;
        setupRecyclerView(binding.recyclerView);
    }


    private void setupRecyclerView(RecyclerView recyclerView){

        MovieAdapter posterAdapter = new MovieAdapter(viewModel.getMovies(),viewModel);
        GridLayoutManager layoutManager = new GridLayoutManager(ctx,2);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutManager.setSpanCount(4);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(posterAdapter);
        recyclerView.addOnScrollListener(neverEndingScrollListener);
    }

    private RecyclerView.OnScrollListener neverEndingScrollListener= new RecyclerView.OnScrollListener() {
        private final int visibleThreshold = 20;
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            GridLayoutManager layoutManager =(GridLayoutManager) recyclerView.getLayoutManager();

            int visibleItemCount = recyclerView.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

            int seen=firstVisibleItem+visibleItemCount;
            int remaining=totalItemCount-seen;

            if (remaining<visibleThreshold) {
                viewModel.loadMoreMovies();
            }
        }
    };


}
