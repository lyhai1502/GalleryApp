package com.example.galleryapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.galleryapp.Fragment.ViewPagerAdapter;
import com.example.galleryapp.Options.activity_favorites;
import com.example.galleryapp.Options.activity_recyclebin;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.galleryapp.Fragment.Album.CreateAlbumDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements  CreateAlbumDialog.ICreateAlbumDialog {

    //Màn hình chuyển qua lại giữa toàn bộ hình ảnh và album
    private ViewPager viewPager;
    //Thanh navigate chuyển qua lại cho viewPager
    private BottomNavigationView navigationView;
    private ViewPagerAdapter viewPagerAdapter;
    public ArrayList<String> albumNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.view_pager);

        albumNames.add("First");
        albumNames.add("Second");

        //Gắn adapter cho viewPager và navigationView, là một phương thức ở dưới class MainActivity
        setUpViewPager();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_photos:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_album:
                        viewPager.setCurrentItem(1);
                        break;
                }
                return true;
            }
        });
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
                        navigationView.getMenu().findItem(R.id.action_photos).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.action_album).setChecked(true);
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

    //Hàm này sẽ là hàm thực hiện các chức năng khi ta bấm nút
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemCamera:
                Toast.makeText(this,"Camera", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemAddAlbum:
                viewPager.setCurrentItem(1);

                CreateAlbumDialog dialog = new CreateAlbumDialog(this);
                dialog.show(getSupportFragmentManager(),"create album");


                //Toast.makeText(this,"Add new album", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.itemFavorites:
                Toast.makeText(this,"Ảnh yêu thích", Toast.LENGTH_SHORT).show();
                Intent intentFavorites = new Intent();
                openFavorites(intentFavorites);
                return true;
            case R.id.itemRecycleBin:
                Toast.makeText(this,"Thùng rác", Toast.LENGTH_SHORT).show();
                Intent intentRecycleBin = new Intent();
                openRecycleBin(intentRecycleBin);
                return true;
            case R.id.itemSetting:
                Toast.makeText(this,"Cài đặt", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.more:
                Toast.makeText(this,"Các chức năng phụ", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openFavorites(Intent intent){
        intent = new Intent(this, activity_favorites.class);
        startActivity(intent);
    }

    private void openRecycleBin(Intent intent){
        intent = new Intent(this, activity_recyclebin.class);
        startActivity(intent);
    }


    //Hàm passAlbumName được implements từ interface ICreateAlbumDialog ở class CreateAlbumDialog
    //Dùng để truyền dữ liệu từ fragment Dialog về cho MainActivity
    @Override
    public void passAlbumName(String albumName) {
        albumNames.add(albumName);
        viewPagerAdapter.getAlbumAdapter().notifyDataSetChanged();
        Log.d("albumName",albumName);

    }
}