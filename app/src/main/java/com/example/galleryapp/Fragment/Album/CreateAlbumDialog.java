package com.example.galleryapp.Fragment.Album;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.galleryapp.MainActivity;
import com.example.galleryapp.R;


//Class này để tạo ra một Dialog để nhập tên cho Album
// Sẽ sử dụng layout: create_album_dialog_layout.xml
public class CreateAlbumDialog extends AppCompatDialogFragment {

    private MainActivity activity;

    public CreateAlbumDialog(MainActivity activity){
        this.activity = activity;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Lấy view cho layout create_album_dialog_layout.xml
        View view = getActivity().getLayoutInflater().inflate(R.layout.create_album_dialog_layout,null);

        builder.setView(view);

        Dialog dialog = builder.create();
        Button createAlbum_confirmButton = view.findViewById(R.id.createAlbum_confirmButton);

        createAlbum_confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                EditText albumName_editTxt = view.findViewById(R.id.albumName_editTxt);

                activity.passAlbumName(albumName_editTxt.getText().toString());
            }
        });

        return dialog;
        //return super.onCreateDialog(savedInstanceState);
    }

    //Interface để truyền dữ liệu qua cho MainActivity
    public interface ICreateAlbumDialog{
        public void passAlbumName(String albumName);
    }

}
