package com.redstar.alexandr.test_task_for_it_01.Request;

import com.redstar.alexandr.test_task_for_it_01.ResponseData.UsersData;

public interface FindUsersCallback {
    void onResponse (UsersData.Item[] users, int status);
}
