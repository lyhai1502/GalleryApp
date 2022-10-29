package com.example.galleryapp.Fragment.Album;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galleryapp.R;

import java.io.File;
import java.util.ArrayList;

//Class này sẽ làm Adapter cho từng thành phần Albụm
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private ArrayList<String> names;

    public AlbumAdapter(ArrayList<String> names){
        this.names = names;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recyclerViewAlbum = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_layout_adapter,parent,false);
        return new AlbumViewHolder(recyclerViewAlbum);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {

        holder.album_textView.setText(names.get(position));

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder{

        //public ImageView album_imgView;
        public TextView album_textView;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            //album_imgView = itemView.findViewById(R.id.album_imgView);
            album_textView = itemView.findViewById(R.id.album_textView);

        }
    }
}
