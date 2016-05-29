package com.example.rj.feedexplorer;

/**
 * Created by Rj on 5/29/16.
 */
public interface IFragmentController {

  public static int OPEN_HOME_FRAGMENT = 1;
  public static int OPEN_FEED_EXPLORER_FRAGMENT = 2;


  public void performOperation(final int operation, Object input);
}
