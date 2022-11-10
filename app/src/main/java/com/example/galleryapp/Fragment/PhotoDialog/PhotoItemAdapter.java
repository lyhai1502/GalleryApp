package com.example.galleryapp.Fragment.PhotoDialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.galleryapp.Fragment.ImageDataAdapter;
import com.example.galleryapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.List;

import me.virtualiz.blurshadowimageview.BlurShadowImageView;

public class PhotoItemAdapter extends RecyclerView.Adapter<PhotoItemAdapter.PhotoItemViewHolder> {

    private List<String> img_path;
    private Context ctx;

    public PhotoItemAdapter(List<String> img_path, Context context){
        this.img_path = img_path;
        this.ctx = context;
    }


    @NonNull
    @Override
    public PhotoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.photo_dialog_adapter,parent,false);
        return new PhotoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoItemViewHolder holder, int position) {
        String img_path_current = this.img_path.get(position);
        File imageFile = new File(img_path_current);
        Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        holder.img.setImageBitmap(myBitmap);
    }

    @Override
    public int getItemCount() {
        return img_path.size();
    }

    public class PhotoItemViewHolder extends RecyclerView.ViewHolder {

        public RoundedImageView img;

        public PhotoItemViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (RoundedImageView) itemView.findViewById(R.id.photo_item_imgView);
        }
    }
}
