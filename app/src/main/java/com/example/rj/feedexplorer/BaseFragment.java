package com.example.rj.feedexplorer;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Rj on 5/29/16.
 */
public class BaseFragment extends Fragment {

  protected FragmentActivity activity;

  private IFragmentController fragmentControllerActivity;

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    fragmentControllerActivity = (IFragmentController) activity;
    this.activity = (FragmentActivity) activity;
  }

  public IFragmentController getFragmentController() {
    if (fragmentControllerActivity == null) {
      fragmentControllerActivity = (IFragmentController) getActivity();
    }
    return fragmentControllerActivity;
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
