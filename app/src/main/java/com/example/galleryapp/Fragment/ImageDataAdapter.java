package com.example.galleryapp.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galleryapp.R;

import java.io.File;
import java.util.List;


//Adapter này sẽ hiện thị dữ liệu theo layout: image_layout_gallery.xml

public class ImageDataAdapter extends RecyclerView.Adapter<ImageDataAdapter.ImageDataViewHolder> {

    List<String> img_path;

    public ImageDataAdapter(List<String> img_path){
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
    }

    @Override
    public int getItemCount() {
        return img_path.size();
    }

    // Implement RecyclerView thì cần phải có cái này !
    //Đây là một class kế thừa ViewHolder, cần implement để ImageDataAdapter nó implement ngược lại
    class ImageDataViewHolder extends RecyclerView.ViewHolder{

        public ImageView img_gallery;

        public ImageDataViewHolder(@NonNull View itemView) {
            super(itemView);

            img_gallery = (ImageView) itemView.findViewById(R.id.img_gallery);
        }
    }

}
