package com.example.galleryapp.Fragment.PhotoDialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.RenderScript;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.galleryapp.Fragment.ImageDataAdapter;
import com.example.galleryapp.MainActivity;
import com.example.galleryapp.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.List;

import me.virtualiz.blurshadowimageview.BlurShadowImageView;

public class PhotoItemAdapter extends RecyclerView.Adapter<PhotoItemAdapter.PhotoItemViewHolder> {

    private List<String> img_path;
    private Context ctx;
    //Use for class Bitmap Processor, enhance the performance
    private RenderScript renderScript;

    public PhotoItemAdapter(List<String> img_path, Context context){
        this.img_path = img_path;
        this.ctx = context;
        renderScript = RenderScript.create(ctx);
    }




    @NonNull
    @Override
    public PhotoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.photo_dialog_adapter,parent,false);
        return new PhotoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoItemViewHolder holder, int position) {

        final int BACKGROUND_OPACITY = 80;

        String img_path_current = this.img_path.get(position);
        File imageFile = new File(img_path_current);
        Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        myBitmap = Bitmap.createScaledBitmap(myBitmap,
                (int) (myBitmap.getWidth() *0.8) ,
                (int) (myBitmap.getHeight() *0.8), true
        );


        //Class processor riêng chuyên làm nhiệm vụ xử lý bitmap
        BitmapProcessor bmp_processor = new BitmapProcessor(myBitmap,ctx,renderScript);
        Drawable backGround = new BitmapDrawable(ctx.getResources(), bmp_processor.blur(25.0f));
        //Drawable backGround = new BitmapDrawable(ctx.getResources(), myBitmap);

        holder.img.setImageBitmap(myBitmap);
        holder.img.setBackground(backGround);
        holder.img.getBackground().setAlpha(BACKGROUND_OPACITY);


    }

    @Override
    public int getItemCount() {
        return img_path.size();
    }

    public class PhotoItemViewHolder extends RecyclerView.ViewHolder {

        public PhotoView img;

        public PhotoItemViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (PhotoView) itemView.findViewById(R.id.photo_item_imgView);
        }
    }
}
