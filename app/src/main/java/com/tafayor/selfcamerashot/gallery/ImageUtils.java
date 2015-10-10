/*
 * Copyright (C) 2015 Ouadban Youssef(tafayor.dev@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.tafayor.selfcamerashot.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.view.View;

import com.tafayor.selfcamerashot.R;
import com.tafayor.selfcamerashot.taflib.helpers.LogHelper;


import java.io.IOException;


public class ImageUtils
{




    public static Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


    public static Bitmap createThumbnailFromFile(String path, int biggerSideSize)
    {


        Bitmap b = null;

        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opt);
        int CurrentWidth = opt.outWidth;
        int CurrentHeight = opt.outHeight;


        int scale = 1;
        int PowerOf2 = 0;
        int ResW = CurrentWidth;
        int ResH = CurrentHeight;
        if (ResW > biggerSideSize || ResH > biggerSideSize)
        {
            while(1==1)
            {
                PowerOf2++;
                scale = (int) Math.pow(2,PowerOf2);
                ResW = (int)((double)opt.outWidth / (double)scale);
                ResH = (int)((double)opt.outHeight / (double)scale);
                if(Math.max(ResW,ResH ) < biggerSideSize)
                {
                    PowerOf2--;
                    scale = (int) Math.pow(2,PowerOf2);
                    ResW = (int)((double)opt.outWidth / (double)scale);
                    ResH = (int)((double)opt.outHeight / (double)scale);
                    break;
                }

            }
        }



        BitmapFactory.Options opt2 = new BitmapFactory.Options();
        opt2.inSampleSize = scale;
        Bitmap decodedBitmap = BitmapFactory.decodeFile(path, opt2);



        int w = decodedBitmap.getWidth();
        int h = decodedBitmap.getHeight();
        if(w>=h)
        {
            w = biggerSideSize;
            h =(int)( (double)decodedBitmap.getHeight() * ((double)w/decodedBitmap.getWidth()));
        }
        else
        {
            h = biggerSideSize;
            w =(int)( (double)decodedBitmap.getWidth() * ((double)h/decodedBitmap.getHeight()));
        }



        if(opt2.outHeight==h && opt2.outWidth==w)
        {
            return decodedBitmap;
        }



        b = Bitmap.createScaledBitmap(decodedBitmap, w,h,true);
        decodedBitmap.recycle();

        return b;
    }



    public static Bitmap createThumbnailFromFile(String path, int width , int height)
    {


        Bitmap b = null;

        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opt);
        int CurrentWidth = opt.outWidth;
        int CurrentHeight = opt.outHeight;


        int scale = 1;
        int PowerOf2 = 0;
        int ResW = CurrentWidth;
        int ResH = CurrentHeight;
        if (ResW > width || ResH > height)
        {
            while(1==1)
            {
                PowerOf2++;
                scale = (int) Math.pow(2,PowerOf2);
                ResW = (int)((double)opt.outWidth / (double)scale);
                ResH = (int)((double)opt.outHeight / (double)scale);
                if(Math.max(ResW,ResH ) < Math.max(width, height))
                {
                    PowerOf2--;
                    scale = (int) Math.pow(2,PowerOf2);
                    ResW = (int)((double)opt.outWidth / (double)scale);
                    ResH = (int)((double)opt.outHeight / (double)scale);
                    break;
                }

            }
        }



        BitmapFactory.Options opt2 = new BitmapFactory.Options();
        opt2.inSampleSize = scale;
        Bitmap decodedBitmap = BitmapFactory.decodeFile(path, opt2);

        if(decodedBitmap == null) return null;

        b = Bitmap.createScaledBitmap(decodedBitmap, width, height,true);
        decodedBitmap.recycle();



        return b;
    }









    public static Bitmap exifRotateBitmap(Bitmap bitmap, int orientation)
    {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (Exception e) {
            LogHelper.logx(e);
            return null;
        }
    }
}
