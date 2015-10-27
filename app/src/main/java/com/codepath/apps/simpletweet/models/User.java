package com.codepath.apps.simpletweet.models;

import org.json.JSONObject;

public class User {


    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public static User getUser(JSONObject obj) {
        User user = new User();
        try {
            user.name = obj.getString("name");
            user.uid = obj.getLong("id");
            user.screenName = "@"+obj.getString("screen_name");
            user.profileImageUrl = obj.getString("profile_image_url");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
