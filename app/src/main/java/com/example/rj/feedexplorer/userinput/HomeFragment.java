package com.example.rj.feedexplorer.userinput;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rj.feedexplorer.BaseFragment;
import com.example.rj.feedexplorer.IFragmentController;
import com.example.rj.feedexplorer.R;
import com.example.rj.feedexplorer.userinput.view.TagBasedLayout;
import com.example.rj.feedexplorer.utils.SharedPrefsManager;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rj on 5/29/16.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

  private TagBasedLayout tagBasedBox;
  private EditText editText;
  private Button submit;
  private LinearLayout linearLayout;
  private List<String> set = new ArrayList<>();
  private Toolbar toolbar;

  static Type serviceListType = new TypeToken<List<String>>() {
  }.getType();

  public static HomeFragment newInstance() {
    Bundle args = new Bundle();
    HomeFragment fragment = new HomeFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.user_input_layout, null);
    toolbar = (Toolbar) view.findViewById(R.id.toolbar);
    getActivityReference().setSupportActionBar(toolbar);
    getActivityReference().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getActivityReference().getSupportActionBar().setTitle("Add Keywords to Explore");
    toolbar.setTitleTextColor(getResources().getColor(R.color.white));
    if (getRecentSearchItems() != null) {
      set = getRecentSearchItems();
    }
    tagBasedBox = new TagBasedLayout(getContext(), set);
    linearLayout = (LinearLayout) view.findViewById(R.id.container_view);
    linearLayout.addView(tagBasedBox.getView());
    editText = (EditText) view.findViewById(R.id.chat_message_edit);
    submit = (Button) view.findViewById(R.id.chat_message_send);
    setHasOptionsMenu(true);
    submit.setOnClickListener(this);
    Button button = (Button) view.findViewById(R.id.check_feed);
    button.setOnClickListener(this);
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
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.chat_message_send:
        if (!set.contains(editText.getText().toString())) {
          if (editText.getText().toString().length() > 0) {
            set.add(editText.getText().toString());
            tagBasedBox.addItem(editText.getText() + "");
            SharedPrefsManager.getInstance().setCollection("string", set);
            editText.setText("");
          } else {
            Toast.makeText(getContext(), "Empty String Not Allowed", Toast.LENGTH_LONG).show();
          }
        } else {
          Toast.makeText(getContext(), "Show Something New", Toast.LENGTH_LONG).show();
        }

        break;
      case R.id.check_feed:
        if (set.size() > 0) {
          Log.w("set size check", " " + set.get(0));
          getFragmentController().performOperation(IFragmentController.OPEN_FEED_EXPLORER_FRAGMENT, set);
        } else {
          Toast.makeText(getContext(), "Enter atleast one item", Toast.LENGTH_SHORT).show();
        }
        break;

    }
  }


  public static List<String> getRecentSearchItems() {
    List<String> recentSearchItem = SharedPrefsManager.getInstance().getCollection("string", serviceListType);
    return recentSearchItem;
  }


}