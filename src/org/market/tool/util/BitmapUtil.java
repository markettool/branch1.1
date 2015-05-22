package org.market.tool.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lidroid.xutils.BitmapUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
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
    	float xscale=width/bm.getWidth();
    	float yscale=height/bm.getHeight();
    	if(xscale>yscale){
    		bm=zoomByWidth(bm, width);
    	}else{
    		bm=zoomByHeight(bm, height);
    	}
    	Bitmap bitmap=Bitmap.createBitmap(width, height, Config.ARGB_8888);
    	Canvas canvas=new Canvas(bitmap);
    	int left=(width-bm.getWidth())/2;
    	int top=(height-bm.getHeight())/2;
    	canvas.drawBitmap(bm, left, top, null);
    	canvas.save(Canvas.ALL_SAVE_FLAG);
    	canvas.restore();
    	return bitmap;
    }
    
    public static Bitmap getBitmapFromRes(Context context,int resId){
    	Bitmap bitmap=BitmapFactory.decodeResource(context.getResources(), resId);
    	return bitmap;
    }
    
    /**
	 * ����ͼƬ
	 * @param bitmap	��Ҫ�������ŵ�ͼƬ
	 * @param wf		�ڿ�������ŵı���
	 * @param hf		�ڸ߶������ŵı���
	 * @return			�������ź��ͼƬ����
	 */
	public static Bitmap zoom(Bitmap bitmap, float wf, float hf) {
		Matrix matrix = new Matrix();
		matrix.postScale(wf, hf);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}
	
	/**
	 * ����ͼƬ
	 * @param bitmap	��Ҫ�������ŵ�ͼƬ
	 * @param newWidth	��ͼƬ�Ŀ�ȣ����ֻ���ո߶Ƚ������ţ��봫��-1
	 * @param newHeight	��ͼƬ�ĸ߶ȣ����ֻ���տ�Ƚ������ţ��봫��-1
	 * @return			�������ź��ͼƬ����
	 */
	public static Bitmap zoom(Bitmap bitmap, int newWidth, int newHeight){
		float wf = 1.0f;
		float hf = 1.0f;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		if(newWidth <= 0){//���ո߶Ƚ�������
			hf = ((float)newHeight) / height;
			wf = hf;
		} else if(newHeight <= 0){//���տ�Ƚ�������
			wf = ((float)newWidth) / width;
			hf = wf;
		} else {
			wf = ((float)newWidth) / width;
			hf = ((float)newHeight) / height;
		}
		return zoom(bitmap, wf, hf);
	}
	
	/**
	 * ���տ�ȵȱ�������ͼƬ
	 * @param bitmap	��Ҫ�������ŵ�ͼƬ
	 * @param newWidth	��ͼƬ�Ŀ�ȣ����ֻ���ո߶Ƚ������ţ��봫��-1
	 * @return			�������ź��ͼƬ����
	 */
	public static Bitmap zoomByWidth(Bitmap bitmap, int newWidth){
		return zoom(bitmap, newWidth, -1);
	}
	
	/**
	 * ���տ�ȵȱ�������ͼƬ
	 * @param bitmap	��Ҫ�������ŵ�ͼƬ
	 * @param newHeight	��ͼƬ�Ŀ�ȣ����ֻ���ո߶Ƚ������ţ��봫��-1
	 * @return			�������ź��ͼƬ����
	 */
	public static Bitmap zoomByHeight(Bitmap bitmap, int newHeight){
		return zoom(bitmap, -1, newHeight);
	}

}
