package com.example.galleryapp.Fragment.EditPhoto;

import android.content.Context;
import android.util.Log;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CubeDataLoader {

    private int data[];
    private int size;
    private int data_size;
    private String nameFile;

    public CubeDataLoader(Context activity, InputStream inputStream, String fileName) {



        nameFile = fileName;
        data = null;
        int lut3dSize = 0;
        BufferedReader reader = null;
        int i = 0;

        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            // do reading, usually loop until end of file reading
            String line;
            String parts[];
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.length() == 0) {
                    continue;
                }

                parts = line.toLowerCase().split("\\s+");
                if (parts.length == 0) {
                    continue;
                }

                if (parts[0].equals("title")) {
                    // optional, or do nothing.
                } else if (parts[0].equals("lut_1d_size") || parts[0].equals("lut_2d_size")) {
                    throw new Exception("Unsupported .cube lut tag: " + parts[0]);
                } else if (parts[0].equals("lut_3d_size")) {
                    if (parts.length != 2) {
                        throw new Exception("Malformed LUT_3D_SIZE tag in .cube lut.");
                    }
                    lut3dSize = Integer.parseInt(parts[1]);
                    data = new int[lut3dSize * lut3dSize * lut3dSize];
                    size = lut3dSize;
                } else if (parts[0].equals("domain_min")) {
                    if (parts.length != 4 ||
                            Float.parseFloat(parts[1]) != 0.0f ||
                            Float.parseFloat(parts[2]) != 0.0f ||
                            Float.parseFloat(parts[3]) != 0.0f) {
                        throw new Exception("domain_min is not correct.");
                    }
                } else if (parts[0].equals("domain_max")) {
                    if (parts.length != 4 ||
                            Float.parseFloat(parts[1]) != 1.0f ||
                            Float.parseFloat(parts[2]) != 1.0f ||
                            Float.parseFloat(parts[3]) != 1.0f) {
                        throw new Exception("domain_max is not correct.");
                    }
                } else {
                    // It must be a float triple!
                    if (data == null || data.length == 0) {
                        throw new Exception("The file doesn't contain 'lut_3d_size'.");
                    }

                    // In a .cube file, each data line contains 3 floats.
                    // Please note: the blue component goes first!!!
                    data[i++] = getRGBColorValue(Float.parseFloat(parts[0]),
                            Float.parseFloat(parts[1]),
                            Float.parseFloat(parts[2]));


                }
            }
        } catch (IOException e) {
            //log the exception
        } catch (NumberFormatException e) {
            Log.d("LoadFileCubeActivity", "Converting string to digit failed.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        data_size = i;

    }

    public String getNameFile(){
        return this.nameFile;
    }

    public int[] getData() {
        return data;
    }

    public int getSize() {
        return size;
    }

    public int getData_size(){
        return data_size;
    }

    private int getRGBColorValue(float b, float g, float r) {
        int bcol = (int) (255 * clamp(b, 0.f, 1.f));
        int gcol = (int) (255 * clamp(g, 0.f, 1.f));
        int rcol = (int) (255 * clamp(r, 0.f, 1.f));
        return bcol | (gcol << 8) | (rcol << 16);
    }

    public static float clamp(float value, float min, float max) {
        return (value < min) ? min : (value > max) ? max : value;
    }



}
