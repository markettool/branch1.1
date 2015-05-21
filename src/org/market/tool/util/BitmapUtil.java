package org.market.tool.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

public class BitmapUtil {
	
	public static Bitmap getOriginBitmap(String path){
		try {
			Bitmap bitmap=BitmapFactory.decodeStream(new FileInputStream(path));
			return bitmap;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Bitmap getThumbilBitmap(String srcPath,int width)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // ͨ�����bitmap��ȡͼƬ�Ŀ�͸�&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, options);
        if (bitmap == null)
        {
        	Log.e("majie", "bitmapΪ��");
//        	return null;
        }
        float realWidth = options.outWidth;
        float realHeight = options.outHeight;
        Log.e("majie", "��ʵͼƬ�߶ȣ�" + realHeight + "���:" + realWidth);
        // �������ű�&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
        int scale = (int) ((realHeight < realWidth ? realHeight : realWidth) / width);
        if (scale <= 0)
        {
            scale = 1;
        }
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        // ע�����Ҫ��options.inJustDecodeBounds ��Ϊ false,���ͼƬ��Ҫ��ȡ�����ġ�&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
        bitmap = BitmapFactory.decodeFile(srcPath, options);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Log.e("majie", "����ͼ�߶ȣ�" + h + "���:" + w);
        return bitmap;
    }
	
	public static void saveBitmapToSdcard(Bitmap bitmap ,String desPath){
		File file=new File(desPath);
        try {
            FileOutputStream out=new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)){
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	   /***
     * ��ͼƬ���ڻ����м�
     * @param bm
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getCanvasBitmap(Bitmap bm,int width,int height){
//    	if(bm.getWidth()>width){
//    		return bm;
//    	}
    	Matrix matrix=new Matrix();
    	float xscale=width*1.0f/bm.getWidth();
    	float yscale=height*1.0f/bm.getHeight();
    	float scale=Math.min(xscale, yscale);
    	matrix.postScale(scale, scale);
    	Bitmap bitmap=Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    	Canvas canvas=new Canvas(bitmap);
//    	int left=(width-bm.getWidth())/2;
//    	int top=(height-bm.getHeight())/2;
    	canvas.drawBitmap(bm, 0, 0, null);
    	canvas.save(Canvas.ALL_SAVE_FLAG);
    	canvas.restore();
    	return bitmap;
    }
    
    public static Bitmap getBitmapFromRes(Context context,int resId){
    	Bitmap bitmap=BitmapFactory.decodeResource(context.getResources(), resId);
    	return bitmap;
    }

}
