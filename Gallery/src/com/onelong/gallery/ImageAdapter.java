package com.onelong.gallery;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {

	int mGalleryItemBackground;
	private Context mContext;
	private List<String> images;
	private ImageView[] mImages;
	private TextView name;

	public ImageAdapter(Context context, List<String> images, TextView name) {
		mContext = context;
		this.images = images;
		this.name = name;
		mImages = new ImageView[images.size()];
	}

	/**
	 * 创建图像队列
	 * @return boolean
	 * 多图片时这里需要修改为动态的
	 */
	public boolean createReflectedImages() {
		final int reflectionGap = 0;
		int index = 0;

		for (String pathName : images) {
			Bitmap originalImage = BitmapFactory.decodeFile(pathName);
			int width = originalImage.getWidth();
			int height = originalImage.getHeight();

			Matrix matrix = new Matrix();
			matrix.preScale(1, -1);//垂直翻转

			Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
					height-15, width, 15, matrix, false);

			Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
					(height + 15), Config.ARGB_8888);

			Canvas canvas = new Canvas(bitmapWithReflection);
			canvas.drawBitmap(originalImage, 0, 0, null);
			
			Paint deafaultPaint = new Paint();
			canvas.drawRect(0, height, width, height + reflectionGap,
					deafaultPaint);

			canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

			Paint paint = new Paint();
			LinearGradient shader = new LinearGradient(0, originalImage
					.getHeight(), 0, bitmapWithReflection.getHeight()
					+ reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);

			paint.setShader(shader);

			paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
			canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
					+ reflectionGap, paint);
			
			ImageView imageView = new ImageView(mContext);
			imageView.setImageBitmap(bitmapWithReflection);
			imageView.setLayoutParams(
					new GalleryFlow.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT)
					);
			//imageView.setScaleType(ScaleType.MATRIX);
			mImages[index++] = imageView;
		}
		return true;
	}

	public int getCount() {
		return images.size();
	}

	public Object getItem(int position) {
		return mImages[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		name.setText(images.get(position).toString());
		return mImages[position];
	}

	public float getScale(boolean focused, int offset) {
		return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
	}

}
