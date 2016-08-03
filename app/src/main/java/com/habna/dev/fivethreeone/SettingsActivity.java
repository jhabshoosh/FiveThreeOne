package com.habna.dev.fivethreeone;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    setupActionBar();

    final Spinner unitSpinner = (Spinner) findViewById(R.id.unitSpinner);
    final ArrayAdapter<CharSequence> unitSpinnerAdapter = ArrayAdapter.createFromResource(this,
      R.array.units_array, android.R.layout.simple_spinner_item);
    unitSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    unitSpinner.setAdapter(unitSpinnerAdapter);
    unitSpinner.setSelection(unitSpinnerAdapter.getPosition(MainActivity.lbs ? "lbs" : "kg"));

    final Button applyButton = (Button) findViewById(R.id.applyButton);
    applyButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        setUnit(unitSpinnerAdapter.getItem(unitSpinner.getSelectedItemPosition()).toString());
        finish();
      }
    });
  }

  private void setUnit(String selected) {
    boolean newUnit = selected.equals("lbs") ? true : false;
    if (newUnit != MainActivity.lbs)  {
      MainActivity.lbs = newUnit;
      SharedPreferences unitPrefs = getApplicationContext().getSharedPreferences(MainActivity.UNIT_PREFS_KEY, 0);
      SharedPreferences.Editor editor = unitPrefs.edit();
      editor.putBoolean("UNIT", MainActivity.lbs);
      if (MainActivity.plan != null)  {
        PlanActivity.convertPlan();
      }
    }
  }

  /**
   * Set up the {@link android.app.ActionBar}, if the API is available.
   */
  private void setupActionBar() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      // Show the Up button in the action bar.
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      finish(); // or go to another activity
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
