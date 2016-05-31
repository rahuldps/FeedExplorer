package com.example.rj.feedexplorer.feedslistview;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Rj on 5/30/16.
 */
public abstract class BaseFeedCardViewSetter {

  public abstract View getView(View convertView, ViewGroup parent);

  protected abstract View buildView(View convertView, ViewGroup parent);

}
