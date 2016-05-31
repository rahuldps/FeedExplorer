package com.example.rj.feedexplorer.feedslistview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.rj.feedexplorer.feedslistview.models.BaseCardModel;

import java.util.List;

/**
 * Created by Rj on 5/30/16.
 */
public class ListViewAdapter extends BaseAdapter {

  private List<BaseCardModel> baseCardModels;
  private Context context;
  private FeedExplorerFragment feedExplorerFragment;

  ListViewAdapter(Context context, List<BaseCardModel> baseCardModels, FeedExplorerFragment feedExplorerFragment) {
    this.baseCardModels = baseCardModels;
    this.context = context;
    this.feedExplorerFragment = feedExplorerFragment;

  }

  @Override
  public int getCount() {
    return baseCardModels.size();
  }

  @Override
  public int getItemViewType(int position) {
    return baseCardModels.get(position).getRowType().ordinal();
  }


  @Override
  public int getViewTypeCount() {
    return FeedRowTypes.values().length;
  }

  @Override
  public Object getItem(int position) {
    return baseCardModels.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    BaseFeedCardViewSetter viewSetter = FeedRowTypeCardFactory
        .getCheckListCardViewSetter(context, baseCardModels.get(position));
    if (position == baseCardModels.size() - 1) {
      feedExplorerFragment.onlastitemCalled();
    }
    return viewSetter.getView(convertView, parent);

  }

  public interface NotifyLastItemCalled {
    public void onlastitemCalled();

  }


}
