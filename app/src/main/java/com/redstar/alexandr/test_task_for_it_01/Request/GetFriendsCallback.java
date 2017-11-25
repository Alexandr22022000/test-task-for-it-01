package com.redstar.alexandr.test_task_for_it_01.Request;

import com.redstar.alexandr.test_task_for_it_01.ResponseData.UsersData;

public interface GetFriendsCallback {
    void onResponse (UsersData.Item[] items, int status);
}
