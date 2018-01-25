package com.marcinmejner.czytnikwiadomoci;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Marc on 24.01.2018.
 */

public class DownloadTask extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... strings) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            StringBuilder result = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while (null != (line = reader.readLine())) {
                result.append(line);


            }
            Log.i("URLresoult", result.toString());


            JSONArray jsonArray = new JSONArray(result.toString());
            int numberOfItems = 20;

            if (jsonArray.length() < 20) {
                numberOfItems = jsonArray.length();
            }

            for (int i = 0; i < numberOfItems; i++) {

                String articleId = jsonArray.getString(i);
                url = new URL("https://hacker-news.firebaseio.com/v0/item/" + articleId + ".json?print=pretty");

                connection = (HttpURLConnection) url.openConnection();

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder result2 = new StringBuilder();
                String line2;
                while (null != (line2 = reader.readLine())){
                    result2.append(line2);
                    Log.i("dzejson", line2.toString());
                }






            }


            return result.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return null;
    }
}