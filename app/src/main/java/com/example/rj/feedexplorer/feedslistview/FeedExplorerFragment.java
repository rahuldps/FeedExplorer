package com.example.rj.feedexplorer.feedslistview;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.rj.feedexplorer.BaseFragment;
import com.example.rj.feedexplorer.R;
import com.example.rj.feedexplorer.feedslistview.models.BaseCardModel;
import com.example.rj.feedexplorer.feedslistview.models.HeadingModel;
import com.example.rj.feedexplorer.feedslistview.models.ResponseDataModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rj on 5/29/16.
 */
public class FeedExplorerFragment extends BaseFragment implements FeedFetcherAsyncTask.ResultCallback, ListViewAdapter.NotifyLastItemCalled {

  private List<String> input;
  private ListView listView;
  private View footerView;
  private List<BaseCardModel> baseCardModels;
  private ListViewAdapter listViewAdapter;
  private int lastItemAccessed = 0;
  private Boolean loader = false;
  private Toolbar toolbar;


  public static FeedExplorerFragment newInstance(List<String> input) {
    Bundle args = new Bundle();
    FeedExplorerFragment fragment = new FeedExplorerFragment();
    args.putSerializable("input", (Serializable) input);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    input = (List<String>) getArguments().getSerializable("input");

  }


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.feed_view_layout, null);
    toolbar = (Toolbar) view.findViewById(R.id.toolbar);
    getActivityReference().setSupportActionBar(toolbar);
    getActivityReference().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getActivityReference().getSupportActionBar().setTitle("Watch Out Feeds");
    baseCardModels = new ArrayList<>();
    listView = (ListView) view.findViewById(R.id.feed_list);
    setFooterView();
    setHasOptionsMenu(true);
    listViewAdapter = new ListViewAdapter(getContext(), baseCardModels, this);
    listView.setAdapter(listViewAdapter);
    listView.setEmptyView(view.findViewById(R.id.emptyView));
    if (baseCardModels.size() == 0) {
      fetchFeed();
    }
    return view;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        getFragmentController().onBackPressed();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onSuccess(ResponseDataModel responseDataModel) {
    List<BaseCardModel> baseModels = convertDataIntoMeaningFulModels(responseDataModel);
    baseCardModels.addAll(baseModels);
    if (footerView != null) {
      listView.removeFooterView(footerView);
    }
    listViewAdapter.notifyDataSetChanged();
    loader = false;
  }

  private List<BaseCardModel> convertDataIntoMeaningFulModels(ResponseDataModel responseDataModel) {
    List<BaseCardModel> baseCardModels = new ArrayList<>();
    HeadingModel headingModel = new HeadingModel();
    headingModel.setTitle(responseDataModel.getResponseData().getQuery());
    baseCardModels.add(headingModel);
    for (int count = 0; count < responseDataModel.getResponseData().getEntries().size(); count++) {
      baseCardModels.add(responseDataModel.getResponseData().getEntries().get(count));
    }
    return baseCardModels;
  }

  @Override
  public void onFailure() {
    loader = true;

  }

  private void fetchFeed() {
    FeedFetcherAsyncTask feedFetcherAsyncTask = new FeedFetcherAsyncTask(BuildUrl(), this);
    feedFetcherAsyncTask.execute();
    listView.addFooterView(footerView);

  }

  private String BuildUrl() {
    String baseUrl = "https://ajax.googleapis.com/ajax/services/feed/find";
    Uri.Builder builder = Uri.parse(baseUrl).buildUpon();
    builder.appendQueryParameter("v", "1.0");
    builder.appendQueryParameter("q", input.get(lastItemAccessed));
    lastItemAccessed = lastItemAccessed + 1;
    return builder.build().toString();

  }

  private void setFooterView() {
    footerView = LayoutInflater.from(getContext()).inflate(R.layout.feed_footer_list, null, false);
    listView.addFooterView(footerView);
  }

  @Override
  public void onlastitemCalled() {
    if (lastItemAccessed < input.size() && loader == false) {
      loader = true;
      fetchFeed();
    }


  }
}
