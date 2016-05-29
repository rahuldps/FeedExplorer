package com.example.rj.feedexplorer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity implements IFragmentController {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }


  @Override
  public void performOperation(int operation, Object input) {
    switch (operation) {
      case IFragmentController.OPEN_HOME_FRAGMENT:
        hanleHomeFragment(input);

        break;
      case IFragmentController.OPEN_FEED_EXPLORER_FRAGMENT:

        break;

    }

  }


  private void hanleHomeFragment(Object input) {
    replaceFragmentInDefaultLayout((Boolean) input, new HomeFragment());
  }

  private void replaceFragmentInDefaultLayout(Boolean addtobackStack, BaseFragment fragment) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(R.id.fragment_container, fragment, fragment.getName());
    if (addtobackStack)
      fragmentTransaction.addToBackStack(fragment.getName());
    fragmentTransaction.commit();
  }
}
