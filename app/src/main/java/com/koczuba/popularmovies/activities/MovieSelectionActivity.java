package com.koczuba.popularmovies.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.koczuba.popularmovies.R;
import com.koczuba.popularmovies.activities.movie.grid.MovieGridVM;

/**
 * Common activity code for Movie GridActivity and MovieMasterDetailActivity
 */
public abstract class MovieSelectionActivity extends AppCompatActivity  implements MovieGridVM.PosterGridVMListener{


    protected void setupActionBar(Toolbar toolbar) {
        // used ActionBar declared in xml, to bind ActionBar title
        setSupportActionBar(toolbar);
    }

    protected void displayMessage(int messageResource) {
        Toast.makeText(this, messageResource, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.grid_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_popular :
                onPopularMoviesClicked();
                return true;
            case R.id.sort_top_rated :
                onTopRatedMoviesClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected abstract void onPopularMoviesClicked();
    protected abstract void onTopRatedMoviesClicked();

}
