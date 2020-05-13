package com.adjoe.challenge.http;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServiceWorker<T> {

    public interface OnCompleteCallback {
        void onComplete(String response);
        void onError();
    }

    private String remoteUrl;

    public HttpServiceWorker(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public void execute(final OnCompleteCallback onCompleteCallback) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = connect();
                    onCompleteCallback.onComplete(read(urlConnection));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    onCompleteCallback.onError();
                } catch (IOException e) {
                    e.printStackTrace();
                    onCompleteCallback.onError();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }

            }
        });

    }


    private String read(URLConnection urlConnection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        //using String Builder to optimize usage of string GB
        StringBuilder response = new StringBuilder();

        String line = null;
        while( (line = reader.readLine()) != null ) {
            response.append(line);
        }

        return response.toString();
    }

    private HttpURLConnection connect() throws IOException {
        return (HttpURLConnection) new URL(remoteUrl).openConnection();
    }

}
