package com.example.galleryapp.Fragment.PhotoDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.galleryapp.MainActivity;
import com.example.galleryapp.R;

import java.util.List;

public class PhotoDialog extends AppCompatDialogFragment {

    private MainActivity activity;
    private List<String> img_path;
    private int currentPosition;
    private PhotoItemAdapter photoItemAdapter;
    private ViewPager2 viewPager;
    private AlertDialog.Builder builder;
    private View view;


    public PhotoDialog(MainActivity activity, List<String> img_path){
        this.activity = activity;
        this.img_path = img_path;
        this.currentPosition = 0;
        photoItemAdapter = new PhotoItemAdapter(img_path,activity);
        builder = new AlertDialog.Builder(activity);
        view = activity.getLayoutInflater().inflate(R.layout.photo_dialog_layout,null);
        builder.setView(view);
        viewPager = view.findViewById(R.id.photo_viewPager);
    }



    //Setter Getter for currentPosition
    public void setCurrentPosition(int position){
        this.currentPosition = position;
        viewPager.setCurrentItem(position);

    }

    public int getCurrentPosition(){
        return this.currentPosition;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = builder.create();
        viewPager.setAdapter(photoItemAdapter);
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleX(0.85f + r*0.15f);
            }
        });


        viewPager.setPageTransformer(compositePageTransformer);
        viewPager.setCurrentItem(this.currentPosition);
        return dialog;
    }
}
