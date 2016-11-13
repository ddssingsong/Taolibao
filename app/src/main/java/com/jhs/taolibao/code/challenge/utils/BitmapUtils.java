package com.jhs.taolibao.code.challenge.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * @author jiao on 2016/7/25 11:40
 * @E-mail: jiaopeirong@iruiyou.com
 * 类说明:bitmap圆角处理工具类
 */
public class BitmapUtils {

	/**
	 * 获取圆角位图的方法
	 * 
	 * @param bitmap
	 *            需要转化成圆角的位图
	 * @param radius
	 *            圆角的度数，数值越大，圆角越大
	 * @return 处理后的圆角位图
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int radius) {
		if(bitmap == null){
			return null;
		}
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);  //指定图片的每一个像素占用的字节大小，8+8+8+8＝32/8=4字节
		Canvas canvas = new Canvas(output);
		
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, radius, radius, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN)); //设置相交的两张图片时显示的内容，SRC_IN显示原内容
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

}









