package com.example.galleryapp.Fragment.EditPhoto;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.galleryapp.Fragment.AlbumFragment;
import com.example.galleryapp.Fragment.EditPhoto.EditPhotoNavigation.EditPhotoCorrectionFragment;
import com.example.galleryapp.Fragment.EditPhoto.EditPhotoNavigation.EditPhotoCubeFragment;
import com.example.galleryapp.Fragment.EditPhoto.EditPhotoNavigation.EditPhotoRotateFragment;
import com.example.galleryapp.Fragment.PhotosFragment;

public class EditPhotoViewPagerAdapter extends FragmentStatePagerAdapter {

    public String filePath;
    public EditPhotoActivity activity;
    EditPhotoCubeFragment cubeFragment;
    //EditPhotoCorrectionFragment correctionFragment;
    //EditPhotoRotateFragment rotateFragment;

    public EditPhotoViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void addNewCube(Uri uri){
        cubeFragment.addNewCube(uri);
    }


    public void setFilePathAndActivity(String filePath,EditPhotoActivity activity){
        this.filePath = filePath;
        this.activity = activity;
        cubeFragment = new EditPhotoCubeFragment(filePath,activity);
        //correctionFragment = new EditPhotoCorrectionFragment(activity,filePath);
        //rotateFragment = new EditPhotoRotateFragment(activity,filePath);
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return cubeFragment;
            case 1:
                return new EditPhotoCorrectionFragment(activity,filePath);
            case 2:
                return new EditPhotoRotateFragment(activity,filePath);
            default:
                return cubeFragment;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
