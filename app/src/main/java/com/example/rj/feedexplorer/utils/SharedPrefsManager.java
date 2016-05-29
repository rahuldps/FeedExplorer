package com.example.rj.feedexplorer.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by Rj on 5/29/16.
 */
public class SharedPrefsManager {
  private SharedPreferences prefs;
  private static SharedPrefsManager uniqueInstance;
  public static final String PREF_NAME = "com.locon.housing.rewrite";

  private SharedPrefsManager(Context appContext) {
    prefs = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
  }

  public static SharedPrefsManager getInstance() {
    if (uniqueInstance == null) {
      throw new IllegalStateException(
          "SharedPrefsManager is not initialized, call initialize(applicationContext) " +
              "static method first");
    }
    return uniqueInstance;
  }

  public static void initialize(Context appContext) {
    if (appContext == null) {
      throw new NullPointerException("Provided application context is null");
    }
    if (uniqueInstance == null) {
      synchronized (SharedPrefsManager.class) {
        if (uniqueInstance == null) {
          uniqueInstance = new SharedPrefsManager(appContext);
        }
      }
    }
  }

  private SharedPreferences getPrefs() {
    return prefs;
  }


  public <C> void setCollection(String key, C dataCollection) {
    SharedPreferences.Editor editor = getPrefs().edit();
    String value = Utils.createJSONStringFromObject(dataCollection);
    editor.putString(key, value);
    editor.apply();
  }

  public <C> C getCollection(String key, Type typeOfC) {
    String jsonData = getPrefs().getString(key, null);
    if (null != jsonData) {
      try {
        Gson gson = new Gson();
        C arrFromPrefs = gson.fromJson(jsonData, typeOfC);
        return arrFromPrefs;
      } catch (ClassCastException cce) {
      }
    }
    return null;
  }
}
