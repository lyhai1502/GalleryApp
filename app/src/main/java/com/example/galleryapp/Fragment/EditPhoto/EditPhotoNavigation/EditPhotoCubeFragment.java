package com.example.galleryapp.Fragment.EditPhoto.EditPhotoNavigation;

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.renderscript.RenderScript;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.galleryapp.Fragment.EditPhoto.CubeDataLoader;
import com.example.galleryapp.Fragment.EditPhoto.EditPhotoActivity;
import com.example.galleryapp.Fragment.EditPhoto.PreviewFilterImageAdapter;
import com.example.galleryapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class EditPhotoCubeFragment extends Fragment {

    public RecyclerView filter_recyclerView;
    public PreviewFilterImageAdapter adapter;
    public RenderScript renderScript;
    public String filepath;
    public String[] cube_files;
    ArrayList<CubeDataLoader> cubes = new ArrayList<CubeDataLoader>();
    public EditPhotoActivity activity;

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
        FilterViewAsyncTask loadFilter_asyncTask = new FilterViewAsyncTask();
        loadFilter_asyncTask.execute();
        return view;
    }

    private String[] getAllCubes() throws IOException {
        AssetManager assetManager = activity.getAssets();
        cube_files = null;
        cube_files = assetManager.list("cube");
        return cube_files;
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
                    CubeDataLoader current = new CubeDataLoader(activity,inputStream,path_cube_file);
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
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }


    }

}