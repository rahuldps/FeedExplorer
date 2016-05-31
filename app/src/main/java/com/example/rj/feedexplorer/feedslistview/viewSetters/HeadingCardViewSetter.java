package com.example.rj.feedexplorer.feedslistview.viewSetters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rj.feedexplorer.R;
import com.example.rj.feedexplorer.feedslistview.models.HeadingModel;
import com.example.rj.feedexplorer.feedslistview.viewSetters.BaseFeedCardViewSetter;

/**
 * Created by Rj on 5/30/16.
 */
public class HeadingCardViewSetter extends BaseFeedCardViewSetter {

  private Context context;
  private HeadingModel headingModel;

  public HeadingCardViewSetter(Context context, HeadingModel headingModel) {
    this.headingModel = headingModel;
    this.context = context;
  }

  @Override
  public View getView(View convertView, ViewGroup parent) {
    HeadingViewHolder holder;
    if (convertView == null) {
      convertView = buildView(convertView, parent);
      holder = new HeadingViewHolder(convertView);
      convertView.setTag(holder);
    } else {
      holder = (HeadingViewHolder) convertView.getTag();
    }
    setViews(holder);
    return convertView;
  }

  @Override
  protected View buildView(View convertView, ViewGroup parent) {
    convertView = LayoutInflater.from(context).inflate(R.layout.feed_heading_layout, parent, false);
    return convertView;
  }

  public void setViews(HeadingViewHolder holder) {
    holder.heading.setText(headingModel.getTitle());
  }

  public static class HeadingViewHolder {

    public TextView heading;

    HeadingViewHolder(View view) {
      heading = (TextView) view.findViewById(R.id.feed_layout);
    }

  }
}
