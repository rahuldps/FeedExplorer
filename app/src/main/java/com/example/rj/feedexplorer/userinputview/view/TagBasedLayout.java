package com.example.rj.feedexplorer.userinputview.view;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rj.feedexplorer.R;
import com.example.rj.feedexplorer.utils.Constants;
import com.example.rj.feedexplorer.utils.SharedPrefsManager;
import com.example.rj.feedexplorer.utils.Utils;

import java.util.List;

/**
 * Created by Rj on 5/29/16.
 */
public class TagBasedLayout implements View.OnClickListener {


  protected LinearLayout parentView;
  private Context context;
  private LinearLayout childRowLayout;
  private LinearLayout.LayoutParams childlayoutparams;
  private int width;
  private List<String> inputList;

  public TagBasedLayout(Context context, List<String> inputList) {
    this.context = context;
    this.inputList = inputList;
    initView();
  }

  protected void initView() {
    this.parentView = new LinearLayout(context);
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    layoutParams
        .setMargins(0, context.getResources().getDimensionPixelSize(R.dimen.dimen_20dp)
            , 0,
            0);
    parentView.setLayoutParams(layoutParams);
    this.parentView.setOrientation(LinearLayout.VERTICAL);

    populateTagBasedLayout(this.parentView,
        R.layout.tagbased_checkbox, this);
  }


  public View getView() {
    return this.parentView;
  }


  public void addItem(String tag) {
    if (childRowLayout != null) {
      this.parentView.removeView(childRowLayout);
    }
    addchildrenToLayout(R.layout.tagbased_checkbox, this.parentView, this, tag);
    this.parentView.addView(childRowLayout);
  }


  public void populateTagBasedLayout(ViewGroup parent,
                                     @LayoutRes
                                     int checkboxLayout,
                                     @Nullable
                                     View.OnClickListener listner) {
    width = Utils.getScreenWidthUsingDisplayMetrics(parent.getContext());
    childRowLayout = new LinearLayout(parent.getContext());
    childlayoutparams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    childRowLayout.setLayoutParams(childlayoutparams);

    int i = 0;
    while (i < inputList.size()) {
      addchildrenToLayout(checkboxLayout, parent, listner, (String) inputList.get(i));
      i++;
    }
    parent.addView(childRowLayout);
  }

  private void addchildrenToLayout(int checkboxLayout, ViewGroup parent, View.OnClickListener listener, String text) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    LinearLayout filterTagBasedCheckBox = (LinearLayout) layoutInflater
        .inflate(checkboxLayout, childRowLayout, false);
    TextView tagBoxTextView = (TextView) filterTagBasedCheckBox.findViewById(R.id.filter_text);
    tagBoxTextView.setText(text);
    filterTagBasedCheckBox.setTag(text);
    if (listener != null) {
      filterTagBasedCheckBox.setOnClickListener(listener);
    }
    childRowLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    filterTagBasedCheckBox
        .measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    if (childRowLayout.getMeasuredWidth() + filterTagBasedCheckBox.getMeasuredWidth() <
        width) {
      childRowLayout.addView(filterTagBasedCheckBox);
    } else {
      parent.addView(childRowLayout);
      childRowLayout = new LinearLayout(context);
      childRowLayout.setLayoutParams(childlayoutparams);
      childRowLayout.addView(filterTagBasedCheckBox);

    }
  }


  @Override
  public void onClick(View v) {
    inputList.remove(v.getTag().toString());
    parentView.removeAllViews();
    populateTagBasedLayout(this.parentView,
        R.layout.tagbased_checkbox, this);
    SharedPrefsManager.getInstance().setCollection(Constants.PREFERENCE_KEY, inputList);
  }


}