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

        // Get the currentPreviewImage as a bitmap
        bitmap_src = ((BitmapDrawable)activity.preview_imageView.getDrawable()).getBitmap();


        brightness_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            boolean isBeenTouch = false;
            Bitmap currentBitmap_brightness = null;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Bitmap brightness_new = BitmapProcessor.changeBrightness(currentBitmap_brightness,progress);
                activity.preview_imageView.setImageBitmap(brightness_new);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                    currentBitmap_brightness = ((BitmapDrawable)activity.preview_imageView.getDrawable()).getBitmap();
                    currentBitmap_brightness = Bitmap.createScaledBitmap(currentBitmap_brightness,
                            (int) (currentBitmap_brightness.getWidth() *0.5) ,
                            (int) (currentBitmap_brightness.getHeight() *0.5), true
                    );
                    isBeenTouch = true;

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        tint_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            Bitmap currentBitmap_tint = null;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Bitmap tint_new = BitmapProcessor.tintImage(currentBitmap_tint,progress);
                activity.preview_imageView.setImageBitmap(tint_new);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                    currentBitmap_tint = ((BitmapDrawable)activity.preview_imageView.getDrawable()).getBitmap();
                    currentBitmap_tint = Bitmap.createScaledBitmap(currentBitmap_tint,
                            (int) (bitmap_src.getWidth() *0.5) ,
                            (int) (bitmap_src.getHeight() *0.5), true
                    );

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        blur_seekBar.setMax(25);

        blur_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            boolean isBeenTouch = false;
            Bitmap currentBitmap_blur = null;
            BitmapProcessor bmp_procesor = null;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Bitmap blur = bmp_procesor.blur(progress,1f);
                activity.preview_imageView.setImageBitmap(blur);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                currentBitmap_blur = ((BitmapDrawable)activity.preview_imageView.getDrawable()).getBitmap();
                currentBitmap_blur = Bitmap.createScaledBitmap(currentBitmap_blur,
                        (int) (bitmap_src.getWidth() *0.5) ,
                        (int) (bitmap_src.getHeight() *0.5), true
                );

                bmp_procesor = new BitmapProcessor(currentBitmap_blur,activity, RenderScript.create(activity));
                isBeenTouch = true;

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        return view;
    }
}