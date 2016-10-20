package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.example.jonfowler.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static final String BASE_URL = "http://10.0.2.2:";
    private static final String PORT = "8081";
    private static final String URL_SUFFIX = "/_ah/api/";
    private static final String ROOT_URL = BASE_URL + PORT + URL_SUFFIX;

    private MyApi myApiService = null;
    AsyncTaskListener listener;

    public EndpointsAsyncTask(AsyncTaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(ROOT_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            return myApiService.loadJoke().execute().getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String joke) {
        try {
            listener.onAsyncResponse(joke);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface AsyncTaskListener {
        void onAsyncResponse(String joke) throws IOException;
    }
}
