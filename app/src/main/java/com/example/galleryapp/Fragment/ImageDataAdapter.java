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
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.galleryapp.Fragment.PhotoDialog.PhotoDialog;
import com.example.galleryapp.MainActivity;
import com.example.galleryapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.List;


//Adapter này sẽ hiện thị dữ liệu theo layout: image_layout_gallery.xml

public class ImageDataAdapter extends RecyclerView.Adapter<ImageDataAdapter.ImageDataViewHolder> {

    List<String> img_path;
    Context context;
    PhotoDialog photoDialog;

    public ImageDataAdapter(List<String> img_path, Context context){
        this.context = context;
        this.img_path = img_path;
        photoDialog = new PhotoDialog((MainActivity) context,img_path);
    }


    ///***
    // Phương thức này để thiết lập Animation cho Dialog
    // ***///
    private void setPhotoDialogAnimation(){
        Window window = photoDialog.getDialog().getWindow();
        window.setWindowAnimations(androidx.constraintlayout.widget.R.style.Animation_AppCompat_Dialog);
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

        //Lấy đường dẫn của ảnh, gắn vào imageView
        String img_path_current = img_path.get(position);
        File imageFile = new File(img_path_current);
        Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        holder.img_gallery.setImageBitmap(myBitmap);
        int current_position = position;

        //Xử lý event khi bấm nhanh vào hình ảnh
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) context;
                PhotoDialog photoDialog = new PhotoDialog((MainActivity) context,img_path);
                photoDialog.setCurrentPosition(current_position);
                photoDialog.show(mainActivity.getSupportFragmentManager(),"Photo Edit");

            }
        });

        //Xử lý event khi bấm lâu ở hình ảnh, ở đây là hiện ra một Popup
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //ImagePopup là thư viện riêng dùng để hiện popup theo ảnh
                ImagePopup imagePopup = new ImagePopup(context);
                imagePopup.setImageOnClickClose(true);
                //Tạo ra một Drawable mới từ đường dẫn để lấy ảnh chất lượng gốc
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
