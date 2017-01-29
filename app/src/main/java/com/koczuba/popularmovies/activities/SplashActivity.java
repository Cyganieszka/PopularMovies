package com.koczuba.popularmovies.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.koczuba.popularmovies.R;
import com.koczuba.popularmovies.activities.movie.MovieMasterDetailActivity;
import com.koczuba.popularmovies.activities.movie.MovieGridActivity;

/**
 *  Depends on smallest screen width, starts different activities
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Intent intent;
        if(getResources().getBoolean(R.bool.screen_small)){
            intent= new Intent(this,MovieGridActivity.class);
        }else{
            intent= new Intent(this,MovieMasterDetailActivity.class);
        }

        startActivity(intent);
        finish();
    }
}
