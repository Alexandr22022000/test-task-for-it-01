package com.redstar.alexandr.test_task_for_it_01.Request;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadImageAsyncTask extends AsyncTask<String[], Void, Bitmap[]> {
    private DownloadImageCallback callback;
    private Bitmap[] images;

    DownloadImageAsyncTask (DownloadImageCallback callback) {
        this.callback = callback;
    }

    /*
    ---Здесь происходит подгрузка изображений, именно она тормазит отображение списка---
     */
    @Override
    protected Bitmap[] doInBackground(String[]... strings) {
        /*Bitmap[] bitmaps = new Bitmap[strings[0].length];
        for (int i = 0; i < bitmaps.length; i++) {
            try {
                if (strings[0][i] != null) {
                    URL url = new URL(strings[0][i]);

                    URLConnection conection = url.openConnection(); conection.connect();
                    InputStream input = new BufferedInputStream(url.openStream());
                    bitmaps[i] = BitmapFactory.decodeStream(input);
                }
                else {
                    bitmaps[i] = null;
                }
            }
            catch (Exception e) {
                bitmaps[i] = null;
            }
        }*/

        images = new Bitmap[strings[0].length];

        for (int i = 0; i < strings[0].length; i++) {
            Thread thread = new Thread(new Loader(strings[0][i], i), i + " thread");
            thread.run();
        }

        while (true) {
            boolean isEnd = true;
            for (int i = 0; i < images.length; i++) {
                isEnd = images[0] != null && isEnd;
            }
            if (isEnd) return images;
        }
    }

    @Override
    protected void onPostExecute(Bitmap[] bitmaps) {
        super.onPostExecute(bitmaps);
        callback.onResponse(bitmaps, RequestManager.STATUS_OK);
    }

    private void OnDownloadImage (Bitmap bitmap, int i) { images[i] = bitmap; }

    class Loader extends Thread {
        private String url;
        private int i;

        Loader (String url, int i) {
            this.url = url;
            this.i = i;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(this.url);

                URLConnection conection = url.openConnection(); conection.connect();
                InputStream input = new BufferedInputStream(url.openStream());
                OnDownloadImage(BitmapFactory.decodeStream(input), i);
            }
            catch (Exception e) {
                OnDownloadImage(null, i);
            }

        }
    }
}
