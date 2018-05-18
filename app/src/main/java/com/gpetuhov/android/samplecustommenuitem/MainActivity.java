package com.gpetuhov.android.samplecustommenuitem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

  private FrameLayout redCircle;
  private TextView countTextView;
  private int alertCount = 0;

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
    return true;
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    // We have to init menu items with custom view every time menu is drawn,
    // so we do it here instead of onCreateOptionsMenu()
    initReloadButton(menu);
    initAlertButton(menu);
    return super.onPrepareOptionsMenu(menu);
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

  private void initAlertButton(Menu menu) {
    final MenuItem alertMenuItem = menu.findItem(R.id.activity_main_alerts_menu_item);

    FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();

    redCircle = rootView.findViewById(R.id.view_alert_red_circle);
    countTextView = rootView.findViewById(R.id.view_alert_count_textview);

    rootView.setOnClickListener(v -> onOptionsItemSelected(alertMenuItem));
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.activity_main_reload_menu_item:
        Toast.makeText(this, "update clicked", Toast.LENGTH_SHORT).show();
        alertCount = (alertCount + 1) % 11; // cycle through 0 - 10
        updateAlertIcon();
        return true;

      case R.id.activity_main_alerts_menu_item:
        Toast.makeText(this, "count cleared", Toast.LENGTH_SHORT).show();
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void updateAlertIcon() {
    // if alert count extends into two digits, just show the red circle
    if (0 < alertCount && alertCount < 10) {
      countTextView.setText(String.valueOf(alertCount));
    } else {
      countTextView.setText("");
    }

    redCircle.setVisibility((alertCount > 0) ? VISIBLE : GONE);
  }
}
