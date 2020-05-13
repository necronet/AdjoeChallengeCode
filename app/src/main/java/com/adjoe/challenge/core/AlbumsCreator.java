package com.adjoe.challenge.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlbumsCreator implements Creator<Album> {

    @Override
    public List<Album> getList(JSONArray jsonArray) {

        List<Album> albums = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                albums.add(create(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return albums;
    }

    @Override
    public Album create(JSONObject jsonObject) throws JSONException {
        return new Album(jsonObject.getString(Album.USER_ID_ATTR),
                         jsonObject.getString(Album.ID_ATTR),
                         jsonObject.getString(Album.TITLE_ATTR));
    }
}
