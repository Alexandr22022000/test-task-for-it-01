package com.redstar.alexandr.test_task_for_it_01.Presenters;

import android.graphics.Bitmap;

import com.redstar.alexandr.test_task_for_it_01.Adapters.UsersAdapter;
import com.redstar.alexandr.test_task_for_it_01.Callbacks.MainCallbacks;
import com.redstar.alexandr.test_task_for_it_01.EnyObjects.ImageFunctions;
import com.redstar.alexandr.test_task_for_it_01.Interfaces.MainInterface;
import com.redstar.alexandr.test_task_for_it_01.Request.DownloadImageCallback;
import com.redstar.alexandr.test_task_for_it_01.Request.FindUsersCallback;
import com.redstar.alexandr.test_task_for_it_01.Request.GetFriendsCallback;
import com.redstar.alexandr.test_task_for_it_01.Request.RequestManager;
import com.redstar.alexandr.test_task_for_it_01.ResponseData.UsersData;
import com.redstar.alexandr.test_task_for_it_01.ResponseData.UsersDataItem;

import java.util.ArrayList;

class MainPresenter extends BasePresenter<MainInterface> {
    private RequestManager requestManager;
    private ArrayList<UsersDataItem> items = new ArrayList<>();
    private UsersDataItem[] usersIsLoading;
    private String query;
    private long userId;
    private int scrollPosition = 0;
    private boolean isLoading = false, isEnd = false, isError = false;

    private DownloadImageCallback downloadImageCallback = (Bitmap[] bitmaps, int status) -> {
        if (status == RequestManager.STATUS_ERROR) {
            loadingError();
            return;
        }

        for (int i= 0; i < bitmaps.length; i++) {
            items.add(new UsersDataItem(usersIsLoading[i].name, ImageFunctions.ComperesImage(bitmaps[i], 130), usersIsLoading[i].id));
        }

        isLoading = false;
        view.setRefresh(false);
        view.setItems(items, scrollPosition);
    };

    private GetFriendsCallback getFriendsCallback = this::response;

    private FindUsersCallback findUsersCallback = this::response;

    private MainCallbacks callbacks = new MainCallbacks() {
        @Override
        public void onClickItem(int itemId) {
            view.goToUserPage(items.get(itemId).id);
        }

        @Override
        public void onClickGoToMyPage() {
            view.goToUserPage(-1);
        }

        @Override
        public void onSearch(String newQuery) {
            isEnd = false;
            items = new ArrayList<>();
            query = newQuery;
            loadData(20, 0, false);
        }

        @Override
        public void onClickBack() {
            view.goToBack();
        }

        @Override
        public void onRefresh() {
            if (items.size() != 0) {
                loadData(items.size(), 0, true);
                items = new ArrayList<>();
            }
            else {
                view.setRefresh(false);
            }
        }

        @Override
        public void onScrollEnd() {
            if (items.size() != 0 && !isError) {
                loadData(20, items.size(), false);
            }
        }

        @Override
        public void setScrollPosition(int position) {
            scrollPosition = position;
        }
    };

    @Override
    void setCallbacksAndMode() {
        view.setCallbacks(callbacks);
        view.setMode(userId == -2);

        if (items.size() == 0) {
            ArrayList<UsersDataItem> list = new ArrayList<>(items);
            list.add(new UsersDataItem(null, null, UsersAdapter.BANNER_EMPTY));
            view.setItems(list, scrollPosition);
        }
        else {
            view.setItems(items, scrollPosition);
        }
    }

    MainPresenter(MainInterface view, long userId) {
        this.view = view;
        this.userId = userId;
        requestManager = new RequestManager();

        setCallbacksAndMode();

        if (userId != -2) {
            loadData(20, 0, false);
        }
    }

    private void loadData (int count, int offset, boolean isRefresh) {
        if (isEnd || isLoading) return;

        isLoading = true;
        isError = false;
        if (!isRefresh) {
            ArrayList<UsersDataItem> list = new ArrayList<>(items);

            list.add(new UsersDataItem(null, null, UsersAdapter.BANNER_LOADER));

            view.setItems(list, scrollPosition);
        }
        else {
            scrollPosition = 0;
        }

        if (userId != -2) {
            requestManager.getFriends(userId, count, offset, getFriendsCallback);
        }
        else {
            requestManager.findUsers(query, count, offset, findUsersCallback);
        }
    }

    private void loadingError () {
        isLoading = false;
        isError = true;
        view.setRefresh(false);
        ArrayList<UsersDataItem> list = new ArrayList<>(items);
        list.add(new UsersDataItem(null, null, UsersAdapter.BANNER_ERROR));
        view.setItems(list, scrollPosition);
    }

    private void response(UsersData.Item[] users, int status) {
        if (status == RequestManager.STATUS_ERROR) {
            loadingError();
            return;
        }

        if (users.length == 0) {
            isEnd = true;
            ArrayList<UsersDataItem> list = new ArrayList<>(items);
            list.add(new UsersDataItem(null, null, UsersAdapter.BANNER_END));
            view.setItems(list, scrollPosition);
            return;
        }

        String[] imagesUrl = new String[users.length];
        usersIsLoading = new UsersDataItem[users.length];

        for (int i = 0; i < users.length; i++) {
            imagesUrl[i] = users[i].photo50;
            usersIsLoading[i] = new UsersDataItem(users[i].firstName + " " + users[i].lastName, null, users[i].id);
        }

        requestManager.downloadImage(imagesUrl, downloadImageCallback);
    }
}
