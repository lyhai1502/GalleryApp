package com.example.galleryapp.Fragment.PhotoDialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsic3DLUT;
import android.renderscript.ScriptIntrinsicBlur;
import android.renderscript.Type;
import android.util.Log;

import com.example.galleryapp.Fragment.EditPhoto.CubeDataLoader;

import java.io.File;
import java.util.Arrays;

public class BitmapProcessor {

    private Bitmap bmp;
    private Context ctx;
    private RenderScript renderScript;
    ScriptIntrinsic3DLUT mScriptlut;

    public BitmapProcessor(){
        //Do Nothing
    }

    //Co hai cach dung class nay
    public BitmapProcessor(Bitmap input, Context ctx, RenderScript renderScript){
        this.bmp = input;
        this.ctx = ctx;
        this.renderScript = renderScript;
        this.mScriptlut = ScriptIntrinsic3DLUT.create(renderScript, Element.U8_4(renderScript));
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

    public Bitmap applyLUT(CubeDataLoader loader){
        Bitmap mBitmap = null;
        Bitmap mLutBitmap = null;
        Bitmap mOutputBitmap = null;
        Allocation mAllocIn = null;
        Allocation mAllocOut = null;
        Allocation mAllocCube;

        int redDim, greenDim, blueDim;

        if (mBitmap == null) {
            mBitmap = bmp;

            mBitmap = Bitmap.createScaledBitmap(mBitmap,
                    (int) (mBitmap.getWidth() *0.1) ,
                    (int) (mBitmap.getHeight() *0.1), true
            );

            mOutputBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), mBitmap.getConfig());

            mAllocIn = Allocation.createFromBitmap(renderScript, mBitmap);
            mAllocOut = Allocation.createFromBitmap(renderScript, mOutputBitmap);
        }


        int size = loader.getSize();
        int data_size = loader.getData_size();
        int[] lut = Arrays.copyOf(loader.getData(),data_size);

        redDim = greenDim = blueDim = size;

        Type.Builder tb = new Type.Builder(renderScript, Element.U8_4(renderScript));
        tb.setX(redDim).setY(greenDim).setZ(blueDim);
        Type t = tb.create();
        mAllocCube = Allocation.createTyped(renderScript, t);
        mAllocCube.copyFromUnchecked(lut);
        mScriptlut.setLUT(mAllocCube);
        mScriptlut.forEach(mAllocIn, mAllocOut);

        mAllocOut.copyTo(mOutputBitmap);

        return mOutputBitmap;
    }


}
