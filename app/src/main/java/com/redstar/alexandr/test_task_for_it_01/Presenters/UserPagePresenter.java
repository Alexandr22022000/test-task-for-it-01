package com.redstar.alexandr.test_task_for_it_01.Presenters;

import android.graphics.Bitmap;

import com.redstar.alexandr.test_task_for_it_01.Callbacks.UserPageCallbacks;
import com.redstar.alexandr.test_task_for_it_01.EnyObjects.ImageFunctions;
import com.redstar.alexandr.test_task_for_it_01.Interfaces.UserPageInterface;
import com.redstar.alexandr.test_task_for_it_01.Request.DownloadImageCallback;
import com.redstar.alexandr.test_task_for_it_01.Request.GetUserDataCallback;
import com.redstar.alexandr.test_task_for_it_01.Request.RequestManager;
import com.redstar.alexandr.test_task_for_it_01.ResponseData.UserData;
import com.redstar.alexandr.test_task_for_it_01.ResponseData.UserDataItem;

import java.util.ArrayList;

class UserPagePresenter extends BasePresenter<UserPageInterface> {
    private long userId;
    private UserDataItem[] userDataItems = new UserDataItem[0];
    private Bitmap image = null;
    private RequestManager requestManager;

    private DownloadImageCallback downloadImageCallback = (Bitmap[] bitmap, int status) -> {
        view.setRefresh(false);

        if (status == RequestManager.STATUS_ERROR) {
            view.openErrorWindow();
            return;
        }

        image = ImageFunctions.ComperesImage(bitmap[0], 200);
        view.setData(image, userDataItems);
    };

    private GetUserDataCallback getUserDataCallback = (UserData.Response data, int status) -> {
        if (status == RequestManager.STATUS_ERROR) {
            view.openErrorWindow();
            view.setRefresh(false);
            return;
        }

        ArrayList<UserDataItem> list = new ArrayList<>();

        list.add(new UserDataItem("Имя", data.firstName));
        list.add(new UserDataItem("Фамилия", data.lastName));
        list.add(new UserDataItem("Online", data.online != 0 ? "Да" : "Нет"));

        if (data.status != null && !data.status.isEmpty()) list.add(new UserDataItem("Статус", data.status));
        if (data.occupation != null) list.add(new UserDataItem("Место работы", data.occupation.name));
        if (data.city != null) list.add(new UserDataItem("Город", data.city.title));

        if (data.counters.friends != null) list.add(new UserDataItem("Друзья", data.counters.friends + ""));
        if (data.counters.groups != null) list.add(new UserDataItem("Группы", data.counters.groups + ""));
        if (data.counters.subscriptions != null) list.add(new UserDataItem("Подпищики", data.counters.subscriptions + ""));
        if (data.counters.pages != null) list.add(new UserDataItem("Страницы", data.counters.pages + ""));
        if (data.counters.photos != null) list.add(new UserDataItem("Фото", data.counters.photos + ""));
        if (data.counters.albums != null) list.add(new UserDataItem("Альбомы", data.counters.albums + ""));
        if (data.counters.videos != null) list.add(new UserDataItem("Видео", data.counters.videos + ""));
        if (data.counters.audios != null) list.add(new UserDataItem("Аудио", data.counters.audios + ""));
        if (data.counters.notes != null) list.add(new UserDataItem("Заметки", data.counters.notes + ""));

        userDataItems = new UserDataItem[list.size()];
        for (int i = 0; i < userDataItems.length; i++) {
            userDataItems[i] = list.get(i);
        }

        requestManager.downloadImage(new String[]{data.photo200}, downloadImageCallback);
    };

    private UserPageCallbacks callbacks = new UserPageCallbacks() {
        @Override
        public void onClickBack() {
            view.goToBack();
        }

        @Override
        public void onClickFriends() {
            view.goToFriends(userId);
        }

        @Override
        public void onClickMyPage() {
            view.goToUserPage(-1);
        }

        @Override
        public void onRefresh() {
            requestManager.userData(userId, getUserDataCallback);
        }
    };

    @Override
    void setCallbacksAndMode() {
        view.setCallbacks(callbacks);
        view.setMode(userId == -1);
        view.setData(image, userDataItems);
    }

    UserPagePresenter (UserPageInterface view, long userId) {
        this.view = view;
        this.userId = userId;
        requestManager = new RequestManager();

        setCallbacksAndMode();
        view.setRefresh(true);
        requestManager.userData(userId, getUserDataCallback);
    }
}
