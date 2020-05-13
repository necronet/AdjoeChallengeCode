package com.adjoe.challenge.core;

import androidx.annotation.NonNull;

public class Album {

    public static String USER_ID_ATTR = "userId";
    public static String ID_ATTR = "id";
    public static String TITLE_ATTR = "title";

    private String userId;
    private String id;
    private String title;

    //Forcing an album to contain those three elements and be inmutable/readonly
    public Album(String userId, String id, String title) {
        this.id = id;
        this.userId = userId;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    @NonNull
    @Override
    public String toString() {
        return "[ Album: userId " +userId +"\n id"+ id + "\n title: "+title +" ]";
    }
}
