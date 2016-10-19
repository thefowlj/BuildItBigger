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
    CountDownLatch latch;
    EndpointsAsyncTask endpointsAsyncTask;

    @Before
    public void setup() {
        mContext = InstrumentationRegistry.getContext();
    }

    @Test
    public void testJokesLib() {
        assertTrue(new Jokes().getJoke() != null);
    }


    @Test
    public void verifyAsyncTaskResponse() throws Throwable {
        endpointsAsyncTask =  new EndpointsAsyncTask(this);
        latch = new CountDownLatch(1);

        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                endpointsAsyncTask.execute();
                try {
                    assertTrue("Latch Test", latch.await(10, TimeUnit.SECONDS));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onAsyncResponse(String joke) throws IOException {
        assertTrue(joke != null);
        assertFalse(joke.equals(""));
        latch.countDown();
    }
}
