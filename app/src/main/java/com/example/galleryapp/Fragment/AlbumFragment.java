package com.example.galleryapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galleryapp.Fragment.Album.AlbumAdapter;
import com.example.galleryapp.MainActivity;
import com.example.galleryapp.R;

public class AlbumFragment extends Fragment {

    private RecyclerView album_recyclerview;
    private AlbumAdapter adapter;

    private int[] bg = {
            R.drawable.bg1,
            R.drawable.bg2,
            R.drawable.bg3,
            R.drawable.bg4,
            R.drawable.bg5,
            R.drawable.bg6,
            R.drawable.bg7,
            R.drawable.bg8,
            R.drawable.bg9,
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);

        album_recyclerview = view.findViewById(R.id.album_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        album_recyclerview.setLayoutManager(layoutManager);

        MainActivity mainActivity = (MainActivity) getActivity();
        adapter = new AlbumAdapter(mainActivity.albumNames, bg);

        album_recyclerview.setAdapter(adapter);
        return view;
    }// onCreateView

    public AlbumAdapter getAlbumAdapter(){
        return adapter;
    }
}
