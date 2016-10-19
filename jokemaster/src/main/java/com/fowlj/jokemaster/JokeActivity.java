package com.fowlj.jokemaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class JokeActivity extends AppCompatActivity {

    public static final String JOKE_INTENT_TAG = "joke";
    private static final String DEFAULT_MSG = "I have no jokes to tell :(";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String joke = getIntent().getStringExtra(JOKE_INTENT_TAG);
        TextView jokeTextView = (TextView) findViewById(R.id.joke_textview);
        jokeTextView.setText(joke == null ? DEFAULT_MSG : joke);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
