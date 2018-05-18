package com.gpetuhov.android.samplecustommenuitem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    // Here we inflate menu
    getMenuInflater().inflate(R.menu.activity_main_menu, menu);
    // And also initialize reload menu item
    initReloadButton(menu);
    return true;
  }

  private void initReloadButton(Menu menu) {
    // For the menu item to rotate, we need to set action view for it.
    // This view is implemented in reload_action_view.xml in layout folder
    // and is associated with reload menu item in activity_main_menu.xml in menu folder.
    // If menu item has action view, it will be used instead of ordinary menu icon.
    // Also, action view captures user clicks and onOptionsItemSelected() will not be triggered,
    // so we need to set click listener to trigger onOptionsItemSelected() manually.

    // First, find our reload menu item
    final MenuItem reloadMenuItem = menu.findItem(R.id.activity_main_reload_menu_item);

    if (reloadMenuItem != null) {
      // And if found, get its action view
      View reloadActionView = reloadMenuItem.getActionView();

      if (reloadActionView != null) {
        // Then find reload button's ImageView, which will rotate on click
        ImageView reloadButton = reloadActionView.findViewById(R.id.reload_button);
        // And inflate animation for the rotation
        Animation rotation = AnimationUtils.loadAnimation( this, R.anim.rotate );

        if (reloadButton != null && rotation != null) {
          // Set click listener for the whole action view,
          // not the reload button itself, which is smaller
          // and can only be touched with a tip of a finger,
          // so that it would be easier for the user to click the menu item.
          reloadActionView.setOnClickListener(v -> {
            // On click, start rotation of the reload button ImageView
            reloadButton.startAnimation(rotation);
            // And manually trigger onOptionsItemSelected()
            onOptionsItemSelected(reloadMenuItem);
          });
        }
      }
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.activity_main_reload_menu_item:
        Toast.makeText(this, "update clicked", Toast.LENGTH_SHORT).show();
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
