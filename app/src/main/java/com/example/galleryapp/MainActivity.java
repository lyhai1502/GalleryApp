package com.example.galleryapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.galleryapp.Fragment.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.galleryapp.Fragment.Album.CreateAlbumDialog;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity
        implements  CreateAlbumDialog.ICreateAlbumDialog {

    //Màn hình chuyển qua lại giữa toàn bộ hình ảnh và album
    private ViewPager viewPager;
    //Thanh navigate chuyển qua lại cho viewPager
    private ChipNavigationBar navigationView;


    private ViewPagerAdapter viewPagerAdapter;
    public ArrayList<String> albumNames = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Calling splashScreen
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hiển thị lời chào buổi sáng và câu Quotes
        setUpTextElement();

        navigationView = (ChipNavigationBar) findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.view_pager);

        albumNames.add("All Images");
        albumNames.add("Favourite");
        albumNames.add("Trash Bin");

        navigationView.setItemSelected(R.id.action_photos,true);
        //Gắn adapter cho viewPager và navigationView, là một phương thức ở dưới class MainActivity
        setUpViewPager();
        navigationView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.action_photos:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_album:
                        viewPager.setCurrentItem(1);
                        break;
                }
            }
        });


        ImageView shuffle_button = (ImageView) findViewById(R.id.shuffleLayout_button);
        shuffle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPagerAdapter.changePhotoLayoutManager();
            }
        });
    }


    private void setUpTextElement(){
        Calendar calendar = Calendar.getInstance();
        int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
        TextView user_txtView = (TextView) findViewById(R.id.user_txtView);
        String partOfDay = "";
        if(hour24hrs <6){
            partOfDay = "night, best wishes for your late night work!";
        }
        else if( 6 <= hour24hrs && hour24hrs <= 12){
            partOfDay = "morning, have a nice day!";
        }
        else if( 12 < hour24hrs && hour24hrs <= 19){
            partOfDay = "evening, be energetic!";
        }
        else{
            partOfDay = "night, love to see you!";
        }
        user_txtView.setText(
                user_txtView.getText() + partOfDay
        );

    }


    private void setUpViewPager(){

        //Muốn thêm một trang chuyển thì vào class ViewPagerAdapter để đọc !
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        navigationView.setItemSelected(R.id.action_photos,true);
                        break;
                    case 1:
                        navigationView.setItemSelected(R.id.action_album,true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do nothing
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }


    //Hàm passAlbumName được implements từ interface ICreateAlbumDialog ở class CreateAlbumDialog
    //Dùng để truyền dữ liệu từ fragment Dialog về cho MainActivity
    @Override
    public void passAlbumName(String albumName) {
        albumNames.add(albumName);
        int lastIndex_albumNames = albumNames.size() -1;
        viewPagerAdapter.getAlbumAdapter().notifyItemRangeInserted(lastIndex_albumNames,1);


    }
}