package com.example.rj.feedexplorer;

import android.app.Application;
import android.content.Context;

import com.example.rj.feedexplorer.utils.SharedPrefsManager;

/**
 * Created by Rj on 5/29/16.
 */
public class MainApplication extends Application {
  private static Context context;

  @Override
  public void onCreate() {
    super.onCreate();
    context = this;
    SharedPrefsManager.initialize(this);

  }

  public static Context getContext() {
    return context;
  }
}
