package com.example.galleryapp.Fragment.Album;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galleryapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

//Class này sẽ làm Adapter cho từng thành phần Albụm
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private ArrayList<String> names;
    private int[] bg_names;

    public AlbumAdapter(ArrayList<String> names, int[] bg_names)
    {
        this.names = names;
        this.bg_names = bg_names;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recyclerViewAlbum = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_layout_adapter,parent,false);
        return new AlbumViewHolder(recyclerViewAlbum);
    }


    private int getRandomIndex(){
        int index = 0;
        int size_bg = bg_names.length;
        Random dice = new Random();
        index = dice.nextInt(size_bg);
        return index;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        holder.album_textView.setText(names.get(position));
        int randomIndex = this.getRandomIndex();
        holder.album_imgView.setImageResource(bg_names[randomIndex]);

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder{

        //public ImageView album_imgView;
        public TextView album_textView;
        public RoundedImageView album_imgView;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            //album_imgView = itemView.findViewById(R.id.album_imgView);
            album_textView = itemView.findViewById(R.id.album_textView);
            album_imgView = itemView.findViewById(R.id.album_imgView);
        }
    }
}
