package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;

import com.fowlj.jokemaster.JokeActivity;

import java.io.IOException;


public class MainActivity extends ActionBarActivity
        implements EndpointsAsyncTask.AsyncTaskListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

    @Override
    public void onAsyncResponse(String joke) throws IOException {
        Intent intent = new Intent(this, JokeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(JokeActivity.JOKE_INTENT_TAG, joke);
        startActivity(intent);
    }
}

