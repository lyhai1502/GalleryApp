package com.example.galleryapp.Fragment.EditPhoto.EditPhotoNavigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.galleryapp.R;


public class EditPhotoRotateFragment extends Fragment {

    public EditPhotoRotateFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_photo_rotate,container,false);
        return view;
    }
}