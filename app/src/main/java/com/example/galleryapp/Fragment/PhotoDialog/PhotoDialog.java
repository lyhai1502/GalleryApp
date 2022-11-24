package com.example.galleryapp.Fragment.PhotoDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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


//Class này là một class kế thừa DialogFragment nên nhận layout là photo_dialog_layout

public class PhotoDialog extends AppCompatDialogFragment {

    private MainActivity activity;
    private List<String> img_path;
    private int currentPosition;
    private PhotoItemAdapter photoItemAdapter;
    private ViewPager2 viewPager;
    private AlertDialog.Builder builder;
    private View view;
    //Biến này tự gọi chính nó, dùng cho setEvent cho nút hủy chính nó
    private PhotoDialog thisPhotoDialog = this;

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
                float r = 1f - Math.abs(position);
                page.setScaleX(0.90f + r*0.0005f);
            }
        });


        ImageView closePhotoDialog_imageView = view.findViewById(R.id.closePhotoDialog_imageView);
        closePhotoDialog_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisPhotoDialog.dismiss();
            }
        });


        viewPager.setPageTransformer(compositePageTransformer);
        viewPager.setCurrentItem(this.currentPosition);
        return dialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
