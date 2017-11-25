
package com.redstar.alexandr.test_task_for_it_01.ResponseData;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("response")
    @Expose
    public List<Response> response = null;

    public class Response {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("first_name")
        @Expose
        public String firstName;
        @SerializedName("last_name")
        @Expose
        public String lastName;
        @SerializedName("city")
        @Expose
        public City city;
        @SerializedName("photo_200")
        @Expose
        public String photo200;
        @SerializedName("online")
        @Expose
        public Integer online;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("counters")
        @Expose
        public Counters counters;
        @SerializedName("occupation")
        @Expose
        public Occupation occupation;

    }

    public class Occupation {

        @SerializedName("type")
        @Expose
        public String type;
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("name")
        @Expose
        public String name;

    }

    public class Counters {

        @SerializedName("albums")
        @Expose
        public Integer albums;
        @SerializedName("videos")
        @Expose
        public Integer videos;
        @SerializedName("audios")
        @Expose
        public Integer audios;
        @SerializedName("notes")
        @Expose
        public Integer notes;
        @SerializedName("photos")
        @Expose
        public Integer photos;
        @SerializedName("groups")
        @Expose
        public Integer groups;
        @SerializedName("gifts")
        @Expose
        public Integer gifts;
        @SerializedName("friends")
        @Expose
        public Integer friends;
        @SerializedName("online_friends")
        @Expose
        public Integer onlineFriends;
        @SerializedName("mutual_friends")
        @Expose
        public Integer mutualFriends;
        @SerializedName("followers")
        @Expose
        public Integer followers;
        @SerializedName("subscriptions")
        @Expose
        public Integer subscriptions;
        @SerializedName("pages")
        @Expose
        public Integer pages;

    }

    public class City {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("title")
        @Expose
        public String title;

    }
}
