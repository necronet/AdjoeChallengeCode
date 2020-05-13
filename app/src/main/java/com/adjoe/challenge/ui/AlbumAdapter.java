package com.adjoe.challenge.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.adjoe.challenge.R;
import com.adjoe.challenge.core.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {
    private List<Album> albums;

    public void setAlbums(List<Album> albums) {
        this.albums.addAll(albums);
        notifyDataSetChanged();
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textId, textUserId, textTitle;
        public AlbumViewHolder(View v) {
            super(v);
            textId = v.findViewById(R.id.textId);
            textUserId = v.findViewById(R.id.textUserId);
            textTitle = v.findViewById(R.id.textTitle);
        }
    }

    //Empty adapter
    public AlbumAdapter() {
        this(new ArrayList<Album>());
    }

    public AlbumAdapter(List<Album> albums) {
        this.albums = albums;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        // create a new view
        View v =LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_view, parent, false);

        AlbumViewHolder vh = new AlbumViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        holder.textId.setText(albums.get(position).getId());
        holder.textUserId.setText(albums.get(position).getUserId());
        holder.textTitle.setText(albums.get(position).getTitle());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return albums != null ? albums.size(): 0;
    }
}
