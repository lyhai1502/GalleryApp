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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);

        album_recyclerview = view.findViewById(R.id.album_recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        album_recyclerview.setLayoutManager(gridLayoutManager);

        MainActivity mainActivity = (MainActivity) getActivity();
        adapter = new AlbumAdapter(mainActivity.albumNames);

        album_recyclerview.setAdapter(adapter);
        return view;
    }// onCreateView

    public AlbumAdapter getAlbumAdapter(){
        return adapter;
    }
}
