package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.fowlj.jokemaster.JokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.IOException;


public class MainActivity extends ActionBarActivity
        implements EndpointsAsyncTask.AsyncTaskListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    InterstitialAd mInterstitialAd;
    Intent mJokeIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                if(mJokeIntent != null) {
                    startActivity(mJokeIntent);
                } else {
                    Context c = getBaseContext();
                    Toast.makeText(c,
                            c.getString(R.string.loading_error_message),
                            Toast.LENGTH_SHORT).show();

                    /*
                    Ideally you could add a method or process that allows the app to repeatedly
                    try to check if the intent is no longer null for certain amount of time and then
                    launches or posts a timeout message.
                    */

                }
            }
        });

        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This method is defined in the layout to be called on clicking the "Tell Joke" button.
     */
    public void tellJoke(View view) {
        new EndpointsAsyncTask(this).execute();

        if(mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            startActivity(mJokeIntent);
        }
    }

    @Override
    public void onAsyncResponse(String joke) throws IOException {
        mJokeIntent = new Intent(this, JokeActivity.class);
        mJokeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mJokeIntent.putExtra(JokeActivity.JOKE_INTENT_TAG, joke);
        Log.d(LOG_TAG, "mJokeIntent: " + mJokeIntent.toString());
    }
}

