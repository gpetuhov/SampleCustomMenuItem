package com.gpetuhov.android.samplecustommenuitem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    getMenuInflater().inflate(R.menu.activity_main_menu, menu);
    initReloadButton(menu);
    return true;
  }

  private void initReloadButton(Menu menu) {
    final MenuItem reloadMenuItem = menu.findItem(R.id.activity_main_update_menu_item);

    if (reloadMenuItem != null) {
      View reloadActionView = reloadMenuItem.getActionView();

      if (reloadActionView != null) {
        ImageView reloadButton = reloadActionView.findViewById(R.id.reload_button);
        if (reloadButton != null) {
          reloadButton.setOnClickListener(v -> onOptionsItemSelected(reloadMenuItem));
        }
      }
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.activity_main_update_menu_item:
        Toast.makeText(this, "update clicked", Toast.LENGTH_SHORT).show();
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
