package com.example.galleryapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;



//Class này sinh ra với mục đích đọc hết tất cả các hình ảnh có trong máy điện thoại
public class ImageLoader {

    private ArrayList<String> _imgPath;
    private Activity context;


    public ImageLoader(Activity context){
        this.context = context;
        _imgPath = new ArrayList<String>();
        checkImageStoragePermission();
        getAllImagesPath();
    }


    private void checkImageStoragePermission(){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_MEDIA_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.MANAGE_EXTERNAL_STORAGE
                    },1
            );
        }

    }


    private void getAllImagesPath(){
        Uri uri;
        Cursor cursor;

        String imagePath;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        };

        cursor = context.getContentResolver().query(uri,projection,null,null,null);
        //int columIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while(cursor.moveToNext()){
            //imagePath = cursor.getString(columIndexData);
            imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            _imgPath.add(imagePath);
            Log.d("Path", imagePath);
        }
    }

    public ArrayList<String> get_imgPath(){
        return this._imgPath;
    }

}
