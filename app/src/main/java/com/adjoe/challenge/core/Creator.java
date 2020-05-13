package com.adjoe.challenge.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public interface Creator<T> {

    List<T> getList(JSONArray jsonArray);
    T create(JSONObject jsonObject) throws JSONException;
}
