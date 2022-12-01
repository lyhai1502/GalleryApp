package com.example.galleryapp.Fragment.EditPhoto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EditPhotoActivity extends AppCompatActivity {


    public ImageView preview_imageView;
    public String filepath;
    public EditPhotoActivity ctx = this;
    PreviewFilterImageAdapter adapter;
    public ViewPager editPhoto_viewPager;
    public ChipNavigationBar navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        filepath = bundle.getString("filepath");
        preview_imageView = (ImageView) findViewById(R.id.preview_imageView);

        File file = new File(filepath);
        Picasso.get()
                .load(file)
                .into(preview_imageView);


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

        EditPhotoViewPagerAdapter editPhoto_viewPagerAdapter = new EditPhotoViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.
                BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        editPhoto_viewPagerAdapter.setFilePathAndActivity(filepath,ctx);

        editPhoto_viewPager = (ViewPager) findViewById(R.id.editPhoto_viewPager);
        editPhoto_viewPager.setAdapter(editPhoto_viewPagerAdapter);

        navigationBar = (ChipNavigationBar) findViewById(R.id.editPhoto_navigation);

        editPhoto_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0:
                        navigationBar.setItemSelected(R.id.action_cube,true);
                        break;
                    case 1:
                        navigationBar.setItemSelected(R.id.action_correction,true);
                        break;
                    case 2:
                        navigationBar.setItemSelected(R.id.action_rotate,true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        navigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch(i){
                    case R.id.action_cube:
                        editPhoto_viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_correction:
                        editPhoto_viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_rotate:
                        editPhoto_viewPager.setCurrentItem(2);
                        break;
                }
            }
        });


//        adapter = new PreviewFilterImageAdapter(this,
//                filepath,cubes,renderScript);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL, false);
//        filter_recyclerView.setLayoutManager(linearLayoutManager);
//        filter_recyclerView.setAdapter(adapter);
//        FilterViewAsyncTask loadFilter_asyncTask = new FilterViewAsyncTask();
//        loadFilter_asyncTask.execute();

    }

//    private String[] getAllCubes() throws IOException {
//        AssetManager assetManager = this.getAssets();
//        cube_files = null;
//        cube_files = assetManager.list("cube");
//        return cube_files;
//    }
//
//
//    public class FilterViewAsyncTask extends AsyncTask<Void,CubeDataLoader,Void>{
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            try{
//                getAllCubes();
//            }
//            catch (IOException e){
//                //Eat exception
//            }
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            for (String cube_file : cube_files
//            ) {
//
//                String path_cube_file = "cube/" + cube_file;
//                try{
//                InputStream inputStream = ctx.getAssets().open(path_cube_file);
//                CubeDataLoader current = new CubeDataLoader(ctx,inputStream,path_cube_file);
//                publishProgress(current);
//                }
//                catch (IOException e){
//                    //Eat exxception
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(CubeDataLoader... values) {
//            super.onProgressUpdate(values);
//            cubes.add(values[0]);
//            adapter.notifyDataSetChanged();
//        }
//
//        @Override
//        protected void onPostExecute(Void unused) {
//            super.onPostExecute(unused);
//        }
//
//
//    }
}