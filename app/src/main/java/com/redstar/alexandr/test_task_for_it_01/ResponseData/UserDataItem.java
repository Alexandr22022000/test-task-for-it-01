package com.redstar.alexandr.test_task_for_it_01.ResponseData;

public class UserDataItem {
    public String name, value;

    public UserDataItem (String name, String value) {
        this.name = name + ": ";
        this.value = value;
    }
}
