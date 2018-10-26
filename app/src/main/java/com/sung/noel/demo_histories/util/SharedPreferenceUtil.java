package com.sung.noel.demo_histories.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StringDef;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public class SharedPreferenceUtil {

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({_USER_DEFAULT_NAME})
    public @interface SharePreferenceName {
    }

    public final static String _USER_DEFAULT_NAME = "UserDefault";


    @Retention(RetentionPolicy.SOURCE)
    @StringDef({_USER_DEFAULT_SEARCH_HISTORY})
    public @interface SharedPreferenceKey {
    }

    public final static String _USER_DEFAULT_SEARCH_HISTORY = "SearchHistory";


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private Gson gson;

    public SharedPreferenceUtil(Context context, @SharePreferenceName String name) {
        this.context = context;
        gson = new Gson();
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    //---------------

    /***
     * 加入新紀錄
     */
    public void addHistory(String keyword) {
        ArrayList<String> histories = getHistories();
        if (!histories.contains(keyword) && !keyword.equals("")) {
            histories.add(keyword);
            editor.putString(_USER_DEFAULT_SEARCH_HISTORY, gson.toJson(histories)).commit();
        }
    }


    //---------------

    /***
     * 取得所有記錄
     */
    public ArrayList<String> getHistories() {
        return gson.fromJson(sharedPreferences.getString(_USER_DEFAULT_SEARCH_HISTORY, gson.toJson(new ArrayList<String>())), new TypeToken<ArrayList<String>>() {
        }.getType());
    }
    //---------------

    /***
     * 清除所有記錄
     */
    public void clearHistories() {
        editor.putString(_USER_DEFAULT_SEARCH_HISTORY, gson.toJson(new ArrayList<String>())).commit();
    }

    //-------------------

    /***
     * 清除該筆記錄
     */
    public void removeHistory(String keyword) {
        ArrayList<String> histories = getHistories();
        for (int i = 0; i < histories.size(); i++) {
            if (histories.get(i).equals(keyword)) {
                histories.remove(i);
            }
        }
        editor.putString(_USER_DEFAULT_SEARCH_HISTORY, gson.toJson(histories)).commit();
    }

}
