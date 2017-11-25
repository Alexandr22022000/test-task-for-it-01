package com.redstar.alexandr.test_task_for_it_01.ResponseData;

import android.graphics.Bitmap;

public class UsersDataItem {
    public String name;
    public Bitmap img;
    public long id;

    public UsersDataItem(String name, Bitmap img, long id) {
        this.name = name;
        this.img = img;
        this.id = id;
    }
}
