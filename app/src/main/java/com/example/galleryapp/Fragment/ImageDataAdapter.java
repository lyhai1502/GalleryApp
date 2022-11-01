package com.example.galleryapp.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.galleryapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.List;


//Adapter này sẽ hiện thị dữ liệu theo layout: image_layout_gallery.xml

public class ImageDataAdapter extends RecyclerView.Adapter<ImageDataAdapter.ImageDataViewHolder> {

    List<String> img_path;
    Context context;

    public ImageDataAdapter(List<String> img_path, Context context){
        this.context = context;
        this.img_path = img_path;
    }

    @NonNull
    @Override
    public ImageDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout_gallery,parent,false);
        return new ImageDataViewHolder(view);
    }

    //Xử lý với mỗi position từ mảng đầu vào là img_path thì sẽ đưa lên giao diện như thế nào
    @Override
    public void onBindViewHolder(@NonNull ImageDataViewHolder holder, int position) {
        String img_path_current = img_path.get(position);
        File imageFile = new File(img_path_current);
        Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

        holder.img_gallery.setImageBitmap(myBitmap);

        //Xử lý event khi bấm nhanh vào hình ảnh
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ItemPath: ",img_path_current);
            }
        });

        //Xử lý event khi bấm lâu ở hình ảnh
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ImagePopup imagePopup = new ImagePopup(context);
                imagePopup.setImageOnClickClose(true);

                imagePopup.initiatePopup(Drawable.createFromPath(img_path_current));
                imagePopup.viewPopup();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return img_path.size();
    }

    // Implement RecyclerView thì cần phải có cái này !
    //Đây là một class kế thừa ViewHolder, cần implement để ImageDataAdapter nó implement ngược lại
    class ImageDataViewHolder extends RecyclerView.ViewHolder{

        public RoundedImageView img_gallery;
        public View itemView;

        public ImageDataViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            img_gallery = (RoundedImageView) itemView.findViewById(R.id.img_gallery);


        }
    }

}
