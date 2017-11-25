
package com.redstar.alexandr.test_task_for_it_01.ResponseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersData {

    @SerializedName("response")
    @Expose
    public Response response;

    public class Response {

        @SerializedName("count")
        @Expose
        public Integer count;
        @SerializedName("items")
        @Expose
        public List<Item> items = null;

    }

    public static class Item {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("first_name")
        @Expose
        public String firstName;
        @SerializedName("last_name")
        @Expose
        public String lastName;
        @SerializedName("photo_50")
        @Expose
        public String photo50;

    }
}
