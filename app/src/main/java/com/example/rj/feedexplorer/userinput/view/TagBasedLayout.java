package com.example.rj.feedexplorer.userinput.view;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rj.feedexplorer.R;
import com.example.rj.feedexplorer.utils.Utils;

import java.util.List;

/**
 * Created by Rj on 5/29/16.
 */
public class TagBasedLayout implements View.OnClickListener {


  protected LinearLayout checkBoxLayout;
  private Context context;
  private LinearLayout childLayout;
  private LinearLayout.LayoutParams childlayoutparams;
  private int width;
  private List<String> set;

  public TagBasedLayout(Context context, List<String> set) {
    this.context = context;
    this.set = set;

    initView();
  }

  protected void initView() {
    this.checkBoxLayout = new LinearLayout(context);
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    layoutParams
        .setMargins(0, context.getResources().getDimensionPixelSize(R.dimen.dimen_6dp)
            , 0,
            0);
    checkBoxLayout.setLayoutParams(layoutParams);
    this.checkBoxLayout.setOrientation(LinearLayout.VERTICAL);

    populateTagBasedLayout(this.checkBoxLayout,
        R.layout.tagbased_checkbox, this);
  }


  public View getView() {
    return this.checkBoxLayout;
  }


  public void addItem(String tag) {
    Log.w("tag", " " + "hahhaha " + set.size());
    if (childLayout != null) {
      this.checkBoxLayout.removeView(childLayout);
    }
    addchildrenToLayout(R.layout.tagbased_checkbox, this.checkBoxLayout, this, tag);
    this.checkBoxLayout.addView(childLayout);
  }


  public void populateTagBasedLayout(ViewGroup parent,
                                     @LayoutRes
                                     int checkboxLayout,
                                     @Nullable
                                     View.OnClickListener listner) {
    width = Utils.getScreenWidthUsingDisplayMetrics(parent.getContext());
    childLayout = new LinearLayout(parent.getContext());
    childlayoutparams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    childLayout.setLayoutParams(childlayoutparams);

    int i = 0;
    while (i < set.size()) {
      addchildrenToLayout(checkboxLayout, parent, listner, (String) set.get(i));
      i++;

    }
    parent.addView(childLayout);
  }

  private void addchildrenToLayout(int checkboxLayout, ViewGroup parent, View.OnClickListener listener, String text) {
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    LinearLayout filterTagBasedCheckBox = (LinearLayout) layoutInflater
        .inflate(checkboxLayout, childLayout, false);
    TextView textView = (TextView) filterTagBasedCheckBox.findViewById(R.id.filter_text);

    Log.w("children added", " ");

    textView.setText(text);
    filterTagBasedCheckBox.setTag(text);
    if (listener != null) {
      filterTagBasedCheckBox.setOnClickListener(listener);
    }
    childLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    filterTagBasedCheckBox
        .measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
    if (childLayout.getMeasuredWidth() + filterTagBasedCheckBox.getMeasuredWidth() <
        width) {
      childLayout.addView(filterTagBasedCheckBox);
    } else {
      parent.addView(childLayout);
      childLayout = new LinearLayout(context);
      childLayout.setLayoutParams(childlayoutparams);
      childLayout.addView(filterTagBasedCheckBox);

    }
  }


  @Override
  public void onClick(View v) {
    Log.w("setcount", " " + set.size() + " ");
    Boolean bool = set.remove(v.getTag().toString());
    checkBoxLayout.removeAllViews();
    populateTagBasedLayout(this.checkBoxLayout,
        R.layout.tagbased_checkbox, this);
    Log.w("setcount", " " + set.size() + " " + bool);
  }


}