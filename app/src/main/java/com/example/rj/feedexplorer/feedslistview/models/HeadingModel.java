package com.example.rj.feedexplorer.feedslistview.models;

import com.example.rj.feedexplorer.feedslistview.core.FeedRowTypes;

/**
 * Created by Rj on 5/30/16.
 */
public class HeadingModel extends BaseCardModel {

  private String Title;

  public String getTitle() {
    return Title;
  }

  public void setTitle(String title) {
    Title = title;
  }

  @Override
  public FeedRowTypes getRowType() {
    return FeedRowTypes.HEADING_ROW;
  }
}
