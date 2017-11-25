package com.redstar.alexandr.test_task_for_it_01.Request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.redstar.alexandr.test_task_for_it_01.ResponseData.UsersData;
import com.redstar.alexandr.test_task_for_it_01.ResponseData.UserData;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

public class RequestManager {
    public static final int STATUS_OK = 0, STATUS_ERROR = 1;

    public void findUsers (String query, int count, int offset, FindUsersCallback callback) {
        VKRequest request = VKApi.users().search(VKParameters.from(VKApiConst.Q, query, VKApiConst.FIELDS, "photo_50", VKApiConst.COUNT, count, VKApiConst.OFFSET, offset));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                UsersData murzik = gson.fromJson(response.json.toString(), UsersData.class);

                UsersData.Item[] users = new UsersData.Item[murzik.response.items.size()];
                for (int i = 0; i < users.length; i++) {
                    users[i] = murzik.response.items.get(i);
                }

                callback.onResponse(users, STATUS_OK);
            }
            @Override
            public void onError(VKError error) {
                callback.onResponse(null, STATUS_ERROR);
            }
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                callback.onResponse(null, STATUS_ERROR);
            }
        });
    }

    public void getFriends (long userId, int count, int offset, GetFriendsCallback callback) {
        VKRequest request;
        if (userId == -1) {
            request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "photo_50", VKApiConst.COUNT, count, VKApiConst.OFFSET, offset));
        }
        else {
            request = VKApi.friends().get(VKParameters.from(VKApiConst.USER_ID, userId, VKApiConst.FIELDS, "photo_50", VKApiConst.COUNT, 10, VKApiConst.OFFSET, 0));
        }

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                UsersData murzik = gson.fromJson(response.json.toString(), UsersData.class);

                UsersData.Item[] users = new UsersData.Item[murzik.response.items.size()];
                for (int i = 0; i < users.length; i++) {
                    users[i] = murzik.response.items.get(i);
                }

                callback.onResponse(users, STATUS_OK);
            }
            @Override
            public void onError(VKError error) {
                callback.onResponse(null, STATUS_ERROR);
            }
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                callback.onResponse(null, STATUS_ERROR);
            }
        });
    }

    public void userData (long userId, GetUserDataCallback callback) {
        VKRequest request;
        if (userId == -1) {
            request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_200,city,online,occupation,counters,status"));
        }
        else {
            request = VKApi.users().get(VKParameters.from(VKApiConst.USER_ID, userId, VKApiConst.FIELDS, "photo_200,city,online,occupation,counters,status"));
        }

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                UserData murzik = gson.fromJson(response.json.toString(), UserData.class);

                callback.onResponse(murzik.response.get(0), STATUS_OK);
            }
            @Override
            public void onError(VKError error) {
                callback.onResponse(null, STATUS_ERROR);
            }
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                callback.onResponse(null, STATUS_ERROR);
            }
        });
    }

    public void downloadImage (String[] url, DownloadImageCallback callback) {
        new DownloadImageAsyncTask(callback).execute(url);
    }
}
