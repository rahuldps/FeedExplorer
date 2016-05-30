package com.example.rj.feedexplorer.feedslistview.models;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rj.feedexplorer.R;
import com.example.rj.feedexplorer.feedslistview.BaseFeedCardViewSetter;

/**
 * Created by Rj on 5/30/16.
 */
public class DataCardViewSetter extends BaseFeedCardViewSetter {

  private Context context;
  private ItemModel itemModel;


  public DataCardViewSetter(Context context, ItemModel itemModel) {
    this.context = context;
    this.itemModel = itemModel;
  }

  @Override
  public View getView(View convertView, ViewGroup parent) {
    DataViewHolder holder;
    if (convertView == null) {
      convertView = buildView(convertView, parent);
      holder = new DataViewHolder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (DataViewHolder) convertView.getTag();
    }
    setViews(holder);
    return convertView;
  }

  @Override
  protected View buildView(View convertView, ViewGroup parent) {
    convertView = LayoutInflater.from(context).inflate(R.layout.feed_holder_layout, parent, false);
    return convertView;
  }

  public void setViews(DataViewHolder holder) {
    holder.url.setText(itemModel.getUrl());
    holder.title.setText(Html.fromHtml(itemModel.getTitle().trim()));
    holder.link.setText(Html.fromHtml(itemModel.getLink().trim()));
    holder.contentSnipet.setText(Html.fromHtml(itemModel.getContentSnippet().trim()));
  }

  public static class DataViewHolder {
    private TextView url;
    private TextView title;
    private TextView contentSnipet;
    private TextView link;

    DataViewHolder(View view) {
      url = (TextView) view.findViewById(R.id.url);
      title = (TextView) view.findViewById(R.id.title);
      contentSnipet = (TextView) view.findViewById(R.id.content_snippet);
      link = (TextView) view.findViewById(R.id.link);
    }
  }

}
