package com.codepath.apps.simpletweet.models;

import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Tweet {



    private String body;
    private long uid;
    private String createdAt;
    private User user;


    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }



    public static Tweet getTweet(JSONObject obj) {
        Tweet tweet = new Tweet();
        try {
            tweet.body = obj.getString("text");
            tweet.uid = obj.getLong("id");
            tweet.createdAt = getRelativeTime(obj.getString("created_at"));
            tweet.user = User.getUser(obj.getJSONObject("user"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tweet;
    }

    public static List<Tweet> fromJson(JSONArray arr) {
        List<Tweet> list = new ArrayList<>();
        Tweet tweet;
        for (int i=0;i<arr.length();i++) {
            try {
                tweet = Tweet.getTweet(arr.getJSONObject(i));
                list.add(tweet);
                Log.d("DEBUG", "Id: "+tweet.getUid()+" Body: "+tweet.getBody()+" Created At: "+tweet.getCreatedAt());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static String getRelativeTime(String createdAt) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        long ct = new Date().getTime();
        try {
            ct = formatter.parse(createdAt).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DateUtils.getRelativeTimeSpanString(ct, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS).toString();
    }
}
