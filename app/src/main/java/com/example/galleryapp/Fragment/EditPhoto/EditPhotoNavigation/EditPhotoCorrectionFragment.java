package com.example.galleryapp.Fragment.EditPhoto.EditPhotoNavigation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.galleryapp.Fragment.EditPhoto.EditPhotoActivity;
import com.example.galleryapp.Fragment.PhotoDialog.BitmapProcessor;
import com.example.galleryapp.R;

import java.io.File;


public class EditPhotoCorrectionFragment extends Fragment {

    public EditPhotoActivity activity;

    public SeekBar brightness_seekBar;
    public SeekBar tint_seekBar;
    public SeekBar blur_seekBar;

    public Bitmap bitmap_src;
    public String filePath;
    public Bitmap Bitmap_current = null;

    public EditPhotoCorrectionFragment(EditPhotoActivity activity, String filePath){
        this.activity = activity;
        this.filePath = filePath;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_photo_correction,container,false);

        brightness_seekBar = (SeekBar) view.findViewById(R.id.brightness_seekbar);
        tint_seekBar = (SeekBar) view.findViewById(R.id.tint_seekbar);
        blur_seekBar = (SeekBar) view.findViewById(R.id.blur_seekbar);

        Log.d("Filepath_Correction",filePath);
        File imageFile = new File(filePath);
        bitmap_src = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        bitmap_src = Bitmap.createScaledBitmap(bitmap_src,
                (int) (bitmap_src.getWidth() *0.05) ,
                (int) (bitmap_src.getHeight() *0.05), true
        );

        brightness_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Bitmap brightness_new = BitmapProcessor.changeBrightness(bitmap_src,progress);
                activity.preview_imageView.setImageBitmap(brightness_new);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        tint_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Bitmap tint_new = BitmapProcessor.tintImage(bitmap_src,progress);
                activity.preview_imageView.setImageBitmap(tint_new);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        blur_seekBar.setMax(5);
        BitmapProcessor bmp_procesor = new BitmapProcessor(bitmap_src,activity, RenderScript.create(activity));
        blur_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Bitmap blur = bmp_procesor.blur(progress);
                activity.preview_imageView.setImageBitmap(blur);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        return view;
    }
}