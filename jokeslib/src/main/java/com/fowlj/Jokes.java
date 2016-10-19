package com.fowlj;

import java.util.Random;

public class Jokes {
    public static final String LOG_TAG = Jokes.class.getSimpleName();

    private static final String JOKES[] = {
            "Why did the chicken cross the road? Because he was having an existential crisis.",
            "What will still let you listen to music on your headphones in 2017? An Android phone.",
            "The US Presidential Election."
    };

    public String getJoke() {
        int i = new Random().nextInt(JOKES.length);
        System.out.println(LOG_TAG + ": i = " + Integer.toString(i));
        return JOKES[i];
    }

}
