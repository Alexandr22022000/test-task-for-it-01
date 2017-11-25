package com.redstar.alexandr.test_task_for_it_01.EnyObjects;

import android.graphics.Bitmap;

public class ImageFunctions {
    public static Bitmap ComperesImage(Bitmap bitmap, int size) {
        int w = bitmap.getWidth(), h = bitmap.getHeight();
        float di;
        if (w > h) {
            di = (float)h / w;
            bitmap = Bitmap.createScaledBitmap(bitmap, size, Math.round(size * di), false);
        }
        else {
            di = (float)w / h;
            bitmap = Bitmap.createScaledBitmap(bitmap,  Math.round(size * di), size, false);
        }
        return bitmap;
    }
}
