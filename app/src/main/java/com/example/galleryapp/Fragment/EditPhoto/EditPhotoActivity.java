package com.example.galleryapp.Fragment.EditPhoto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.util.Log;
import android.widget.ImageView;

import com.example.galleryapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditPhotoActivity extends AppCompatActivity {


    private ImageView preview_imageView;
    private RecyclerView filter_recyclerView;
    String[] cube_files;
    ArrayList<CubeDataLoader> cubes = new ArrayList<CubeDataLoader>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String filepath = bundle.getString("filepath");
        preview_imageView = (ImageView) findViewById(R.id.preview_imageView);
        filter_recyclerView = (RecyclerView) findViewById(R.id.filter_recyclerView);
        File file = new File(filepath);
        Picasso.get()
                .load(file)
                .into(preview_imageView);

        try{
            this.getAllCubes();
        }
        catch (IOException e){
            //Eat exception
        }
        int count = 0;
        for (String cube_file : cube_files
             ) {

            String path_cube_file = "cube/" + cube_file;

            cubes.add(new CubeDataLoader(this,path_cube_file));
            count++;

        }

        RenderScript renderScript = RenderScript.create(this);
        PreviewFilterImageAdapter adapter = new PreviewFilterImageAdapter(this,
                filepath,cubes,renderScript);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        filter_recyclerView.setLayoutManager(linearLayoutManager);
        filter_recyclerView.setAdapter(adapter);

    }

    private String[] getAllCubes() throws IOException {
        AssetManager assetManager = this.getAssets();
        cube_files = null;
        cube_files = assetManager.list("cube");
        return cube_files;
    }


}