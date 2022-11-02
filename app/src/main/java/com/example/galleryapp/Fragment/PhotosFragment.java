package com.example.galleryapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galleryapp.ImageLoader;
import com.example.galleryapp.MainActivity;
import com.example.galleryapp.R;

import java.util.ArrayList;
import java.util.List;

public class PhotosFragment extends Fragment {

    //Get all String Data
    private ArrayList<String> _imgPath;
    private MainActivity main;
    private RecyclerView photo_recyclerview;
    private ImageDataAdapter imageDataAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        main = (MainActivity) getActivity();


        ImageLoader imgLoader = new ImageLoader(main);
        _imgPath = imgLoader.get_imgPath();
        imageDataAdapter = new ImageDataAdapter(_imgPath,main);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        photo_recyclerview = view.findViewById(R.id.photo_recyclerview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(main,3);
        photo_recyclerview.setLayoutManager(gridLayoutManager);
        photo_recyclerview.setAdapter(imageDataAdapter);
        return view;
    }
}
