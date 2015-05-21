package org.market.tool.util;

import org.market.tool.R;

import android.content.Context;
import android.graphics.Bitmap;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.core.BitmapSize;

/**
 * Author: wyouflf
 * Date: 13-11-12
 * Time: ����10:24
 */
public class BitmapHelp {
    private BitmapHelp() {
    }

    private static BitmapUtils bitmapUtils;

    /**
     * BitmapUtils���ǵ����� ������Ҫ���ض����ȡʵ���ķ���
     *
     * @param appContext application context
     * @return
     */
    public static BitmapUtils getBitmapUtils(Context appContext) {
        if (bitmapUtils == null) {
            bitmapUtils = new BitmapUtils(appContext);
            bitmapUtils.configDiskCacheEnabled(true);
            bitmapUtils.configMemoryCacheEnabled(true);
            bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        }
        return bitmapUtils;
    }
    
    public static BitmapDisplayConfig getDisplayConfig(Context context,int width,int height){
    	BitmapDisplayConfig config = new BitmapDisplayConfig();  
//	    config.setLoadingDrawable(getResources().getDrawable(R.drawable.loading));  
	    config.setLoadFailedDrawable(context.getResources().getDrawable(R.drawable.wwj_748));
	    config.setBitmapMaxSize(new BitmapSize(width, height));
	    return config;
    }
}
