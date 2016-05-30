package com.example.rj.feedexplorer.feedslistview;

import android.os.AsyncTask;

import com.example.rj.feedexplorer.feedslistview.models.ResponseDataModel;
import com.example.rj.feedexplorer.network.NetworkUtils;
import com.example.rj.feedexplorer.network.ServerResponse;

/**
 * Created by Rj on 5/29/16.
 */
public class FeedFetcherAsyncTask extends AsyncTask<Void, Integer, ResponseDataModel> {

  private String url;
  private FeedExplorerFragment feedExplorerFragment;

  FeedFetcherAsyncTask(String url, FeedExplorerFragment feedExplorerFragment) {
    this.url = url;
    this.feedExplorerFragment = feedExplorerFragment;
  }

  @Override
  protected ResponseDataModel doInBackground(Void... params) {
    ResponseDataModel responseDataModel = null;
    ServerResponse<ResponseDataModel> serverResponse = null;
    try {
      serverResponse = NetworkUtils.doGetCall(url, ResponseDataModel.class);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (serverResponse != null)
      responseDataModel = serverResponse.getData();

    return responseDataModel;
  }

  @Override
  protected void onPostExecute(ResponseDataModel responseDataModel) {
    super.onPostExecute(responseDataModel);
    if (feedExplorerFragment != null && responseDataModel != null) {
      feedExplorerFragment.onSuccess(responseDataModel);
    }
    if (responseDataModel == null) {
      feedExplorerFragment.onFailure();
    }

  }

  public interface ResultCallback {

    public void onSuccess(ResponseDataModel findModel);

    public void onFailure();

  }


}