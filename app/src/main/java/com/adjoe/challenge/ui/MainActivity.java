package com.adjoe.challenge.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adjoe.challenge.R;
import com.adjoe.challenge.core.Album;
import com.adjoe.challenge.core.AlbumsCreator;
import com.adjoe.challenge.http.HttpServiceWorker;
import com.adjoe.challenge.receiver.AdjoeChallengeBroadcastReceiver;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class MainActivity extends AppCompatActivity implements HttpServiceWorker.OnCompleteCallback {

    Handler mainHandler = new Handler(Looper.getMainLooper());
    private RecyclerView recyclerAlbumView;
    private AlbumAdapter albumAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private AdjoeChallengeBroadcastReceiver receiver;

    private HttpServiceWorker serviceWorker;
    private AlbumsCreator creator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiver = new AdjoeChallengeBroadcastReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_USER_PRESENT);
        this.registerReceiver(receiver, filter);

        setupUI();
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }



    private void loadData() {
        //TODO: url should be dynamic and part of configuration
        serviceWorker = new HttpServiceWorker("https://jsonplaceholder.typicode.com/albums");

        //TODO: might not be a good idea to send an activity here as it is a source of leakage
        serviceWorker.execute(this);
    }

    private void setupUI() {
        creator = new AlbumsCreator();
        setContentView(R.layout.activity_main);
        recyclerAlbumView = findViewById(R.id.albums_list);

        recyclerAlbumView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerAlbumView.setLayoutManager(layoutManager);

        albumAdapter = new AlbumAdapter();
        recyclerAlbumView.setAdapter(albumAdapter);
    }

    @Override
    public void onComplete(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            final List<Album> albums = creator.getList(jsonArray);
            //Log.d("DEBUG", "Albums: "+albums.toString());

            //execute on main thread
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    albumAdapter.setAlbums(albums);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError() {
        Log.d("DEBUG","Error");
    }
}
