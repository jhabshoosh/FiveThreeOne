package com.habna.dev.fivethreeone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.habna.dev.fivethreeone.Models.Plan;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static Plan plan;
    private float chestMax;
    private float backMax;
    private float shouldersMax;
    private float legsMax;
    private Util.WEEK_TYPE currentWeek;

    private final String LBS_SUFFIX = " (lbs)";
    private final String KG_SUFFIX = " (kg)";


    public static boolean lbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupActionBar();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences unitPrefs = getApplicationContext().getSharedPreferences(Util.UNIT_PREFS_KEY, 0);
        lbs = unitPrefs.getBoolean("UNIT", true);

        SharedPreferences sharedPreferences = getApplicationContext()
          .getSharedPreferences(Util.TRAINING_MAX_PREFS_KEY, 0);
        chestMax = sharedPreferences.getFloat("CHEST_MAX", -1);
        backMax = sharedPreferences.getFloat("BACK_MAX", -1);
        shouldersMax = sharedPreferences.getFloat("SHOULDERS_MAX", -1);
        legsMax = sharedPreferences.getFloat("LEGS_MAX", -1);
        currentWeek = getWeekTypeByString(sharedPreferences.getString("WEEK_TYPE", ""));
        if ((chestMax != -1 || backMax != -1 || shouldersMax != -1 || legsMax != -1) && currentWeek != null) {
            Map<Util.BODY_TYPE, Double> maxMap = new HashMap<>();
            if (chestMax != -1) {
                maxMap.put(Util.BODY_TYPE.CHEST, (double) chestMax);
            }
            if (backMax != -1) {
                maxMap.put(Util.BODY_TYPE.BACK, (double) backMax);
            }
            if (shouldersMax != -1) {
                maxMap.put(Util.BODY_TYPE.SHOULDERS, (double) shouldersMax);
            }
            if (legsMax != -1) {
                maxMap.put(Util.BODY_TYPE.LEGS, (double) legsMax);
            }
            plan = new Plan(currentWeek, maxMap, true);
        }

        final Spinner weekSpinner = (Spinner) findViewById(R.id.weekSpinner);
        ArrayAdapter<CharSequence> weekSpinnerAdapter = ArrayAdapter.createFromResource(this,
          R.array.weeks_array, android.R.layout.simple_spinner_item);
        weekSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekSpinner.setAdapter(weekSpinnerAdapter);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.trainingMaxCheckBox);

        EditText chestHint = (EditText) findViewById(R.id.chestOneRepMax);
        EditText backHint = (EditText) findViewById(R.id.backOneRepMax);
        EditText shouldersHint = (EditText) findViewById(R.id.shouldersOneRepMax);
        EditText legsHint = (EditText) findViewById(R.id.legsOneRepMax);
        String suffix = lbs ? LBS_SUFFIX : KG_SUFFIX;
        chestHint.setHint(chestHint.getHint() + suffix);
        backHint.setHint(backHint.getHint() + suffix);
        shouldersHint.setHint(shouldersHint.getHint() + suffix);
        legsHint.setHint(legsHint.getHint() + suffix);

        final Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<Util.BODY_TYPE, Double> oneRepMaxes = new HashMap<>();

                EditText chest = (EditText) findViewById(R.id.chestOneRepMax);
                chestMax = validateMax(chest.getText().toString());
                if (chestMax != -1) {
                    oneRepMaxes.put(Util.BODY_TYPE.CHEST, (double) chestMax);
                }

                EditText back = (EditText) findViewById(R.id.backOneRepMax);
                backMax = validateMax(back.getText().toString());
                if (backMax != -1) {
                    oneRepMaxes.put(Util.BODY_TYPE.BACK, (double) backMax);
                }

                EditText shoulders = (EditText) findViewById(R.id.shouldersOneRepMax);
                shouldersMax = validateMax(shoulders.getText().toString());
                if (shouldersMax != -1) {
                    oneRepMaxes.put(Util.BODY_TYPE.SHOULDERS, (double) shouldersMax);
                }

                EditText legs = (EditText) findViewById(R.id.legsOneRepMax);
                legsMax = validateMax(legs.getText().toString());
                if (legsMax != -1) {
                    oneRepMaxes.put(Util.BODY_TYPE.LEGS, (double) legsMax);
                }

                String weekStr = weekSpinner.getSelectedItem().toString().toUpperCase();
                Util.WEEK_TYPE weekType;
                if ("FIVE".equals(weekStr)) {
                    weekType = Util.WEEK_TYPE.FIVE;
                } else if ("THREE".equals(weekStr)) {
                    weekType = Util.WEEK_TYPE.THREE;
                } else if ("ONE".equals(weekStr)) {
                    weekType = Util.WEEK_TYPE.ONE;
                } else if ("DELOAD".equals(weekStr)) {
                    weekType = Util.WEEK_TYPE.DELOAD;
                } else {
                    throw new RuntimeException("Impossible week type on spinner");
                }

                plan = new Plan(weekType, oneRepMaxes, checkBox.isChecked());
                saveTrainingMaxes(weekStr);
                if (plan.doesLift()) {
                    displayPlan();
                } else {
                    final TextView badInputText = (TextView) findViewById(R.id.badInputText);
                    badInputText.setText("Do you even lift?");
                    badInputText.setTextColor(Color.RED);
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Util.refreshUnits(this, getIntent());
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem homeItem = menu.findItem(R.id.action_home);
        homeItem.setVisible(false);
        MenuItem currentPlanItem = menu.findItem(R.id.action_currentPlan);
        currentPlanItem.setVisible(displayCurrentPlanItem());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.action_currentPlan)    {
            displayPlan();
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayPlan() {
        Intent intent = new Intent(MainActivity.this, PlanActivity.class);
        startActivity(intent);
    }

    private void saveTrainingMaxes(String weekStr)  {
        SharedPreferences sharedPreferences = getApplicationContext()
          .getSharedPreferences(Util.TRAINING_MAX_PREFS_KEY, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("CHEST_MAX", chestMax);
        editor.putFloat("BACK_MAX", backMax);
        editor.putFloat("SHOULDERS_MAX", shouldersMax);
        editor.putFloat("LEGS_MAX", legsMax);
        editor.putString("WEEK_TYPE", weekStr);
        editor.apply();
    }

    private Util.WEEK_TYPE getWeekTypeByString(String weekStr)  {
        if (weekStr == "")  {
            return null;
        }
        if ("FIVE".equals(weekStr.toUpperCase())) {
            return Util.WEEK_TYPE.FIVE;
        }else if ("THREE".equals(weekStr.toUpperCase()))  {
            return Util.WEEK_TYPE.THREE;
        }else if ("ONE".equals(weekStr.toUpperCase()))  {
            return Util.WEEK_TYPE.ONE;
        }else if("DELOAD".equals(weekStr.toUpperCase())) {
            return Util.WEEK_TYPE.DELOAD;
        }
        return null;
    }

    private Integer validateMax(String max) {
        Integer result;
        try {
            result = Integer.valueOf(max);
        }   catch(NumberFormatException nfe)   {
            result = Integer.valueOf(-1);
        }
        return result;
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

    private static boolean displayCurrentPlanItem()    {
        return MainActivity.plan != null;
    }

}
