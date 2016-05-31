package com.example.rj.feedexplorer.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.google.gson.Gson;

/**
 * Created by Rj on 5/29/16.
 */
public class Utils {
  public static int getScreenWidthUsingDisplayMetrics(Context context) {
    DisplayMetrics displaymetrics = new DisplayMetrics();
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    wm.getDefaultDisplay().getMetrics(displaymetrics);
    return displaymetrics.widthPixels;
  }

  public static String createJSONStringFromObject(Object object) {
    Gson gson = new Gson();
    return gson.toJson(object);
  }
}
