package com.fjby.travel.leyou.utils;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;

/**
 * 
 * <P>
 * Bitmap处理方法
 * <P>
 * 
 * @author Sunny Ding
 * @version 1.00
 */
public class BitmapUtils {
	/**
	 * 改变Bitmap透明度
	 * 
	 * @param sourceImg
	 *            原始Bitmap
	 * @param number
	 *            需要改变的透明度百分比
	 * @return   目标Bitmap
	 * 
	 * */
	public static Bitmap getTransparentBitmap(Bitmap sourceImg, int number) {
		int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];

		sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0,
				sourceImg.getWidth(), sourceImg.getHeight());// 获得图片的ARGB值
		number = number * 255 / 100;
		for (int i = 0; i < argb.length; i++) {
			if (argb[i] != 0) {// 透明的颜色不作处理
				argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);
			}
		}
		// 用处理好的数组建Bitmap
		sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(),
				sourceImg.getHeight(), Config.ARGB_8888);
		return sourceImg;
	}
	
	/**
	 * 建立Bitmap防止Out Of Memery Crash
	 * 
	 * @param res
	 *            原始资源
	 * @param resId
	 *            资源id
	 * @param reqWidth 
	 *            目标Bitmap宽度
	 * @param reqHeight 
	 *            目标Bitmap长度  
	 * @return  目标Bitmap
	 * 
	 * */
	/*public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
			int reqWidth, int reqHeight) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPreferredConfig = Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		// BitmapFactory.decodeResource(res, resId, options);
		// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize =computeScale(options, reqWidth,
				reqHeight);
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		InputStream is =res.openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, options);
	}*/


	/**
	 * 根据View(主要是ImageView)的宽和高来获取图片的缩略图
	 *
	 * @param path
	 * @param viewWidth
	 * @param viewHeight
	 * @return
	 */
	public static Bitmap decodeThumbBitmapForRec(Resources res,int imageRec, int viewWidth, int viewHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		//设置为true,表示解析Bitmap对象，该对象不占内存
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, imageRec, options);
		//设置缩放比例
		options.inSampleSize = computeScale(options, viewWidth, viewHeight);
		//设置为false,解析Bitmap对象加入到内存中
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, imageRec, options);
	}
	/**
	 * 根据View(主要是ImageView)的宽和高来获取图片的缩略图
	 *
	 * @param path
	 * @param viewWidth
	 * @param viewHeight
	 * @return
	 */
	public static Bitmap decodeThumbBitmapForFile(String filePath, int viewWidth, int viewHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		//设置为true,表示解析Bitmap对象，该对象不占内存
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		//设置缩放比例
		options.inSampleSize = computeScale(options, viewWidth, viewHeight);
		//设置为false,解析Bitmap对象加入到内存中
		options.inJustDecodeBounds = false;
		return 	BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 根据View(主要是ImageView)的宽和高来计算Bitmap缩放比例。默认不缩放
	 *
	 * @param options
	 * @param width
	 * @param height
	 */
	public static int computeScale(BitmapFactory.Options options, int viewWidth, int viewHeight) {
		int inSampleSize = 8;
		if (viewWidth == 0 || viewHeight == 0) {
			return inSampleSize;
		}
		int bitmapWidth = options.outWidth;
		int bitmapHeight = options.outHeight;

		//假如Bitmap的宽度或高度大于我们设定图片的View的宽高，则计算缩放比例
		if (bitmapWidth > viewWidth || bitmapHeight > viewHeight) {
			int widthScale = Math.round((float) bitmapWidth / (float) viewWidth);
			int heightScale = Math.round((float) bitmapHeight / (float) viewHeight);

			//为了保证图片不缩放变形，我们取宽高比例最小的那个
			inSampleSize = widthScale > heightScale ? widthScale : heightScale;
		}
		LogUtil.e("缩放比例computeScale-inSampleSize= " + inSampleSize);
		return inSampleSize;
	}
}
