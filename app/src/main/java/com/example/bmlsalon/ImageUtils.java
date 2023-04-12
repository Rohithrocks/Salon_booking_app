package com.example.bmlsalon;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

public class ImageUtils {

    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int diameter = Math.min(width, height);

        Bitmap circularBitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circularBitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        float radius = diameter / 2f;
        canvas.drawCircle(radius, radius, radius, paint);

        return circularBitmap;
    }
}
