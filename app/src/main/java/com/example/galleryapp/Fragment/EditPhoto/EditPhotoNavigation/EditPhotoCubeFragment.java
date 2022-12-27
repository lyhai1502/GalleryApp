package com.example.galleryapp.Fragment.EditPhoto.EditPhotoNavigation;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.renderscript.RenderScript;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.galleryapp.Fragment.EditPhoto.CubeDataLoader;
import com.example.galleryapp.Fragment.EditPhoto.EditPhotoActivity;
import com.example.galleryapp.Fragment.EditPhoto.PreviewFilterImageAdapter;
import com.example.galleryapp.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class EditPhotoCubeFragment extends Fragment {

    public RecyclerView filter_recyclerView;
    public PreviewFilterImageAdapter adapter;
    public RenderScript renderScript;
    public String filepath;
    public String[] cube_files;

    //hai biến này là static vì khi gọi Intent thì Activity sẽ gọi lại OnStart => Fragment này bị gọi lại onCreateView
    static ArrayList<CubeDataLoader> cubes = new ArrayList<CubeDataLoader>();
    static Boolean isCubeLoaded = false;
    public EditPhotoActivity activity;

    public Button addPreset_button;
    public int PICKFILE_REQUEST_CODE = 1010;

    public EditPhotoCubeFragment(String filePath, EditPhotoActivity activity){
        this.filepath = filePath;
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_photo_cube,container,false);
        filter_recyclerView = (RecyclerView) view.findViewById(R.id.filter_recyclerView);

        renderScript = RenderScript.create(activity);

        adapter = new PreviewFilterImageAdapter(activity,
                filepath,cubes,renderScript);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity,RecyclerView.HORIZONTAL, false);
        filter_recyclerView.setLayoutManager(linearLayoutManager);
        filter_recyclerView.setAdapter(adapter);

        addPreset_button = (Button) view.findViewById(R.id.addPreset_button);
        addPreset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadFile = new Intent(Intent.ACTION_GET_CONTENT);
                loadFile.setType("*/*");
                
                activity.startActivityForResult(loadFile, PICKFILE_REQUEST_CODE);


            }
        });

        if(isCubeLoaded == false){
            FilterViewAsyncTask loadFilter_asyncTask = new FilterViewAsyncTask();
            loadFilter_asyncTask.execute();
            isCubeLoaded = true;
        }
        else{
            //Do nothing
        }

        return view;
    }

    private String[] getAllCubes() throws IOException {
        AssetManager assetManager = activity.getAssets();
        cube_files = null;
        cube_files = assetManager.list("cube");
        return cube_files;
    }

    private String getFileName(Uri uri) {
        File file = new File(uri.getPath());
        return file.getName();
    }

    public void addNewCube(Uri uri) {
        try{
            InputStream inputStream = activity.getContentResolver().openInputStream(uri);
            CubeDataLoader newCube = new CubeDataLoader(activity,inputStream,getFileName(uri));
            cubes.add(0,newCube);
            Log.d("Add more cube","Add successful!");
            adapter.notifyDataSetChanged();
            filter_recyclerView.setAdapter(adapter);
        }
        catch(IOException e){
            Log.d("Add more cube","Cannot read file");
        }
    }


    public class FilterViewAsyncTask extends AsyncTask<Void, CubeDataLoader,Void> {

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
                try{
                    InputStream inputStream = activity.getAssets().open(path_cube_file);
                    CubeDataLoader current = new CubeDataLoader(activity,inputStream,cube_file);
                    publishProgress(current);
                }
                catch (IOException e){
                    //Eat exxception
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(CubeDataLoader... values) {
            super.onProgressUpdate(values);
            cubes.add(values[0]);

            adapter.notifyDataSetChanged();
            Integer adapter_size = adapter.getItemCount();
            Log.d("Add more cube",Integer.toString(adapter_size));
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }


    }

}