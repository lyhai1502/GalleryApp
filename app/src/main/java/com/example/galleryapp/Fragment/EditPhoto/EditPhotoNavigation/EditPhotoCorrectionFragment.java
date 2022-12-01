package com.example.galleryapp.Fragment.EditPhoto.EditPhotoNavigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.galleryapp.R;


public class EditPhotoCorrectionFragment extends Fragment {



    public EditPhotoCorrectionFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_photo_correction,container,false);


        return view;
    }
}