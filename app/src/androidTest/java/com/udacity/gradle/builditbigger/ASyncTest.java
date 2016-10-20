package com.udacity.gradle.builditbigger;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.fowlj.Jokes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by jonfowler on 10/18/16.
 */
@RunWith(AndroidJUnit4.class)
public class ASyncTest implements EndpointsAsyncTask.AsyncTaskListener{
    Context mContext;
    EndpointsAsyncTask.AsyncTaskListener mAsyncTaskListener;
    boolean called;

    @Before
    public void setup() {
        mContext = InstrumentationRegistry.getContext();
        mAsyncTaskListener = this;
        called = false;
    }

    @Test
    public void testJokesLib() {
        assertTrue(new Jokes().getJoke() != null);
    }


    @Test
    public void verifyAsyncTaskResponse() throws Throwable {
        final CountDownLatch latch = new CountDownLatch(1);

        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                new EndpointsAsyncTask(mAsyncTaskListener) {
                    @Override
                    protected void onPostExecute(String joke) {
                        called = true;
                        try {
                            listener.onAsyncResponse(joke);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        latch.countDown();
                    }
                }.execute();
            }
        });

        latch.await(10, TimeUnit.SECONDS);
        assertTrue(called);
    }


    @Override
    public void onAsyncResponse(String joke) throws IOException {
        assertTrue(joke != null);
        assertFalse(joke.equals(""));
    }
}
