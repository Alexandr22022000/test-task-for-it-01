package com.redstar.alexandr.test_task_for_it_01.Request;

import com.redstar.alexandr.test_task_for_it_01.ResponseData.UserData;

public interface GetUserDataCallback {
    void onResponse (UserData.Response data, int status);
}
