package com.example.galleryapp.Fragment.EditPhoto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.galleryapp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditPhotoActivity extends AppCompatActivity {


    public ImageView preview_imageView;
    public RecyclerView filter_recyclerView;
    public String filepath;
    String[] cube_files;
    ArrayList<CubeDataLoader> cubes = new ArrayList<CubeDataLoader>();
    RenderScript renderScript;
    public EditPhotoActivity ctx = this;
    PreviewFilterImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        filepath = bundle.getString("filepath");
        preview_imageView = (ImageView) findViewById(R.id.preview_imageView);
        filter_recyclerView = (RecyclerView) findViewById(R.id.filter_recyclerView);
        File file = new File(filepath);
        Picasso.get()
                .load(file)
                .into(preview_imageView);

        renderScript = RenderScript.create(this);

        preview_imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Picasso.get()
                        .load(file)
                        .into(preview_imageView);
                return true;
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();

        adapter = new PreviewFilterImageAdapter(this,
                filepath,cubes,renderScript);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
        filter_recyclerView.setLayoutManager(linearLayoutManager);
        filter_recyclerView.setAdapter(adapter);
        FilterViewAsyncTask loadFilter_asyncTask = new FilterViewAsyncTask();
        loadFilter_asyncTask.execute();

    }

    private String[] getAllCubes() throws IOException {
        AssetManager assetManager = this.getAssets();
        cube_files = null;
        cube_files = assetManager.list("cube");
        return cube_files;
    }


    public class FilterViewAsyncTask extends AsyncTask<Void,CubeDataLoader,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try{
                getAllCubes();
            }
            catch (IOException e){
                //Eat exception
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (String cube_file : cube_files
            ) {

                String path_cube_file = "cube/" + cube_file;
                CubeDataLoader current = new CubeDataLoader(ctx,path_cube_file);
                publishProgress(current);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(CubeDataLoader... values) {
            super.onProgressUpdate(values);
            cubes.add(values[0]);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }


    }
}