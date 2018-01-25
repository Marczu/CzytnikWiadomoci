package com.marcinmejner.czytnikwiadomoci;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> titles = new ArrayList<>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        DownloadTask task = new DownloadTask();
        String result;

        try {
            result = task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
