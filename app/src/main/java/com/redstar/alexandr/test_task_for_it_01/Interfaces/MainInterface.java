package com.redstar.alexandr.test_task_for_it_01.Interfaces;

import com.redstar.alexandr.test_task_for_it_01.Callbacks.MainCallbacks;
import com.redstar.alexandr.test_task_for_it_01.ResponseData.UsersDataItem;

import java.util.ArrayList;

public interface MainInterface {
    void setCallbacks (MainCallbacks callbacks);

    void setItems (ArrayList<UsersDataItem> items, int scrollPosition);

    void setMode (boolean isSearch);

    void setRefresh (boolean isRefresh);

    void goToUserPage (long userId);

    void goToBack ();
}
