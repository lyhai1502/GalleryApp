package com.example.galleryapp.Fragment.PhotoDialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;

import java.io.File;

public class BitmapProcessor {

    private Bitmap bmp;
    private Context ctx;
    private RenderScript renderScript;

    public BitmapProcessor(){
        //Do Nothing
    }

    //Co hai cach dung class nay
    public BitmapProcessor(Bitmap input, Context ctx, RenderScript renderScript){
        this.bmp = input;
        this.ctx = ctx;
        this.renderScript = renderScript;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public Bitmap blur(float r){
        //Radius range (0 < r <= 25)
        if(r <= 0){
            r = 0.1f;
        }else if(r > 25){
            r = 25.0f;
        }

        Bitmap bmp_rescale = Bitmap.createScaledBitmap(bmp,
                (int) (bmp.getWidth() *0.05) ,
                (int) (bmp.getHeight() *0.05), true
                );

        Bitmap bitmap = Bitmap.createBitmap(
                bmp_rescale.getWidth(), bmp_rescale.getHeight(),
                Bitmap.Config.ARGB_8888);


        Allocation blurInput = Allocation.createFromBitmap(renderScript, bmp_rescale);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(r);
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        //renderScript.destroy();

        return bitmap;
    }


}
