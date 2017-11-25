package com.redstar.alexandr.test_task_for_it_01.Interfaces;

import android.graphics.Bitmap;

import com.redstar.alexandr.test_task_for_it_01.Callbacks.UserPageCallbacks;
import com.redstar.alexandr.test_task_for_it_01.ResponseData.UserDataItem;

public interface UserPageInterface {
    void setCallbacks (UserPageCallbacks callbacks);

    void setData (Bitmap img, UserDataItem[] data);

    void setMode (boolean isMyPage);

    void setRefresh (boolean isRefresh);

    void goToUserPage (long userId);

    void goToFriends (long userId);

    void goToBack ();

    void openErrorWindow ();
}
