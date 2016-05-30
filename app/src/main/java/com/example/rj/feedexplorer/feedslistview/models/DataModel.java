package com.example.rj.feedexplorer.feedslistview.models;

import java.util.List;

/**
 * Created by Rj on 5/29/16.
 */
public class DataModel {
  private String query;
  private List<ItemModel> entries;

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public List<ItemModel> getEntries() {
    return entries;
  }

  public void setEntries(List<ItemModel> entries) {
    this.entries = entries;
  }
}