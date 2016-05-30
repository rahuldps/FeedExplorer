package com.example.rj.feedexplorer.feedslistview;

import android.content.Context;

import com.example.rj.feedexplorer.feedslistview.models.BaseCardModel;
import com.example.rj.feedexplorer.feedslistview.models.DataCardViewSetter;
import com.example.rj.feedexplorer.feedslistview.models.HeadingModel;
import com.example.rj.feedexplorer.feedslistview.models.ItemModel;

/**
 * Created by Rj on 5/30/16.
 */
public class FeedRowTypeCardFactory {
  public static BaseFeedCardViewSetter getCheckListCardViewSetter(Context context,
                                                                  BaseCardModel baseCardModel) {

    switch (baseCardModel.getRowType()) {

      case HEADING_ROW:
        return new HeadingCardViewSetter(context, (HeadingModel) baseCardModel);

      case DATA_ROW:
        return new DataCardViewSetter(context, (ItemModel) baseCardModel);
    }

    return null;
  }
}
