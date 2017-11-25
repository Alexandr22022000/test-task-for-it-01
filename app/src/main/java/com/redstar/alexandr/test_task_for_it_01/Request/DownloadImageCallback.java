package com.redstar.alexandr.test_task_for_it_01.Request;

import android.graphics.Bitmap;

public interface DownloadImageCallback {
    void onResponse (Bitmap[] bitmap, int status);
}
