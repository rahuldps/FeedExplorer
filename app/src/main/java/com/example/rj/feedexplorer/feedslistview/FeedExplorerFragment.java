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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rj.feedexplorer.BaseFragment;
import com.example.rj.feedexplorer.R;
import com.example.rj.feedexplorer.feedslistview.models.BaseCardModel;
import com.example.rj.feedexplorer.feedslistview.models.HeadingModel;
import com.example.rj.feedexplorer.feedslistview.models.ResponseDataModel;
import com.example.rj.feedexplorer.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rj on 5/29/16.
 */
public class FeedExplorerFragment extends BaseFragment implements FeedFetcherAsyncTask.ResultCallback, ListViewAdapter.NotifyLastItemCalled, View.OnClickListener {

  private static final String INPUT_DATA = "input";

  private List<String> input;
  private ListView listView;
  private View progressFooterView;
  private List<BaseCardModel> baseCardModels;
  private ListViewAdapter listViewAdapter;
  private int lastItemAccessed = 0;
  private Boolean isLoading = false;
  private Toolbar toolbar;
  private LinearLayout retryFooterContainer;
  private Button retryFooterbutton;
  private LinearLayout retryLayout;
  private Button retryButton;
  private TextView emptyView;


  public static FeedExplorerFragment newInstance(List<String> input) {
    Bundle args = new Bundle();
    FeedExplorerFragment fragment = new FeedExplorerFragment();
    args.putSerializable(INPUT_DATA, (Serializable) input);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    input = (List<String>) getArguments().getSerializable(INPUT_DATA);

  }


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.feed_view_layout, null);
    setupToolBar(view);
    retryFooterContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.retry_footer_view, null);
    retryFooterbutton = (Button) retryFooterContainer.findViewById(R.id.retry_container);
    retryFooterbutton.setOnClickListener(this);
    retryLayout = (LinearLayout) view.findViewById(R.id.retry_layout);
    retryButton = (Button) view.findViewById(R.id.retry_button);
    retryButton.setOnClickListener(this);
    baseCardModels = new ArrayList<>();
    listView = (ListView) view.findViewById(R.id.feed_list);
    setFooterView();
    setHasOptionsMenu(true);
    listViewAdapter = new ListViewAdapter(getContext(), baseCardModels, this);
    emptyView = (TextView) view.findViewById(R.id.emptyView);
    listView.setAdapter(listViewAdapter);
    if (baseCardModels.size() == 0) {
      fetchFeed();
    }
    return view;
  }

  private void setupToolBar(View view) {
    toolbar = (Toolbar) view.findViewById(R.id.toolbar);
    getActivityReference().setSupportActionBar(toolbar);
    toolbar.setTitleTextColor(getResources().getColor(R.color.white));
    getActivityReference().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getActivityReference().getSupportActionBar().setTitle(getResources().getString(R.string.watch_feed));
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
    emptyView.setVisibility(View.GONE);
    List<BaseCardModel> baseModels = convertDataIntoMeaningFulModels(responseDataModel);
    baseCardModels.addAll(baseModels);
    if (progressFooterView != null) {
      listView.removeFooterView(progressFooterView);
    }
    listViewAdapter.notifyDataSetChanged();
    isLoading = false;
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
    lastItemAccessed--;
    if (progressFooterView != null) {
      listView.removeFooterView(progressFooterView);
      if (baseCardModels.size() > 0)
        listView.addFooterView(retryFooterContainer);
    }
    if (baseCardModels.size() == 0) {
      retryLayout.setVisibility(View.VISIBLE);
      emptyView.setVisibility(View.GONE);
      listView.setVisibility(View.GONE);

    }
  }

  private void fetchFeed() {
    FeedFetcherAsyncTask feedFetcherAsyncTask = new FeedFetcherAsyncTask(BuildUrl(), this);
    feedFetcherAsyncTask.execute();
    listView.addFooterView(progressFooterView);

  }

  private String BuildUrl() {
    String baseUrl = Constants.BASE_URL;
    Uri.Builder builder = Uri.parse(baseUrl).buildUpon();
    builder.appendQueryParameter("v", "1.0");
    builder.appendQueryParameter("q", input.get(lastItemAccessed));
    lastItemAccessed = lastItemAccessed + 1;
    return builder.build().toString();

  }

  private void setFooterView() {
    progressFooterView = LayoutInflater.from(getContext()).inflate(R.layout.feed_footer_list, null);
    listView.addFooterView(progressFooterView);
  }

  @Override
  public void onlastitemCalled() {
    if (lastItemAccessed < input.size() && isLoading == false) {
      isLoading = true;
      fetchFeed();
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.retry_container:
      case R.id.retry_button:
        fetchFeed();
        listView.setVisibility(View.VISIBLE);
        if (retryFooterContainer != null) {
          listView.removeFooterView(retryFooterContainer);
        }
        break;

    }
  }
}
