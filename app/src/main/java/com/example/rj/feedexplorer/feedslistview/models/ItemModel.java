package com.example.rj.feedexplorer.feedslistview.models;

import com.example.rj.feedexplorer.feedslistview.FeedRowTypes;

/**
 * Created by Rj on 5/29/16.
 */
public class ItemModel extends BaseCardModel {
  private String url;
  private String title;
  private String contentSnippet;
  private String link;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContentSnippet() {
    return contentSnippet;
  }

  public void setContentSnippet(String contentSnippet) {
    this.contentSnippet = contentSnippet;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  @Override
  public FeedRowTypes getRowType() {
    return FeedRowTypes.DATA_ROW;
  }
}
