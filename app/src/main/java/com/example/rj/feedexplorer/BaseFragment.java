package com.example.rj.feedexplorer;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Rj on 5/29/16.
 */
public class BaseFragment extends Fragment {

  protected AppCompatActivity activity;

  private IFragmentController fragmentControllerActivity;

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    fragmentControllerActivity = (IFragmentController) activity;
    this.activity = (AppCompatActivity) activity;
  }

  public IFragmentController getFragmentController() {
    if (fragmentControllerActivity == null) {
      fragmentControllerActivity = (IFragmentController) getActivity();
    }
    return fragmentControllerActivity;
  }

  public AppCompatActivity getActivityReference() {
    return activity;
  }

  public void onDetach() {
    this.activity = null;
    this.fragmentControllerActivity = null;
    super.onDetach();
  }


  public String getName() {
    return this.getClass().getCanonicalName();
  }

}
