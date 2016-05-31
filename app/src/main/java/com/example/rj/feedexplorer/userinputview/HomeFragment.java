package com.example.rj.feedexplorer.userinputview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
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
import com.example.rj.feedexplorer.userinputview.view.TagBasedLayout;
import com.example.rj.feedexplorer.utils.Constants;
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
  private Button addWordsButton;
  private LinearLayout linearLayout;
  private List<String> inputList = new ArrayList<>();
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
    setupToolBar(view);
    if (getRecentSearchItems() != null) {
      inputList = getRecentSearchItems();
    }
    tagBasedBox = new TagBasedLayout(getContext(), inputList);
    linearLayout = (LinearLayout) view.findViewById(R.id.container_view);
    linearLayout.addView(tagBasedBox.getView());
    editText = (EditText) view.findViewById(R.id.add_words_edit_text);
    addWordsButton = (Button) view.findViewById(R.id.add_words);
    setHasOptionsMenu(true);
    addWordsButton.setOnClickListener(this);
    Button button = (Button) view.findViewById(R.id.check_feed);
    button.setOnClickListener(this);
    return view;

  }

  private void setupToolBar(View view) {
    toolbar = (Toolbar) view.findViewById(R.id.toolbar);
    getActivityReference().setSupportActionBar(toolbar);
    getActivityReference().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getActivityReference().getSupportActionBar().setTitle(getResources().getString(R.string.add_words));
    toolbar.setTitleTextColor(getResources().getColor(R.color.white));
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
      case R.id.add_words:
        handleWordAddition();
        break;
      case R.id.check_feed:
        if (inputList.size() > 0) {
          getFragmentController().performOperation(IFragmentController.OPEN_FEED_EXPLORER_FRAGMENT, inputList);
        } else {
          Toast.makeText(getContext(), getResources().getString(R.string.enter_word), Toast.LENGTH_SHORT).show();
        }
        break;

    }
  }

  private void handleWordAddition() {
    String inputString = editText.getText().toString().trim();
    if (!inputList.contains(inputString)) {
      if (inputString.length() > 0) {
        inputList.add(inputString);
        tagBasedBox.addItem(inputString);
        SharedPrefsManager.getInstance().setCollection(Constants.PREFERENCE_KEY, inputList);
        editText.setText("");
      } else {
        Toast.makeText(getContext(), getResources().getString(R.string.empty_string), Toast.LENGTH_LONG).show();
      }
    } else {
      Toast.makeText(getContext(), getResources().getString(R.string.show_new), Toast.LENGTH_LONG).show();
    }
  }


  public static List<String> getRecentSearchItems() {
    List<String> recentSearchItem = SharedPrefsManager.getInstance().getCollection(Constants.PREFERENCE_KEY, serviceListType);
    return recentSearchItem;
  }


}