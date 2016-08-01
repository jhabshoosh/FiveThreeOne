package com.habna.dev.fivethreeone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.habna.dev.fivethreeone.Models.Lift;
import com.habna.dev.fivethreeone.Models.Plan;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private int chestMax;
    private int backMax;
    private int shouldersMax;
    private int legsMax;

    private Lift.WEEK_TYPE currentWeek;

    private final String TRAINING_MAX_PREFS_KEY = "TRAINING_MAX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getApplicationContext()
          .getSharedPreferences(TRAINING_MAX_PREFS_KEY, 0);
        chestMax = sharedPreferences.getInt("CHEST_MAX", -1);
        backMax = sharedPreferences.getInt("BACK_MAX", -1);
        shouldersMax = sharedPreferences.getInt("SHOULDERS_MAX", -1);
        legsMax = sharedPreferences.getInt("LEGS_MAX", -1);
        currentWeek = getWeekTypeByString(sharedPreferences.getString("WEEK_TYPE", ""));
        if (chestMax != -1 && backMax != -1 && shouldersMax != -1 && legsMax != -1 && currentWeek != null) {
            Map<Lift.BODY_TYPE, Double> maxMap = new HashMap<>();
            maxMap.put(Lift.BODY_TYPE.CHEST, (double) chestMax);
            maxMap.put(Lift.BODY_TYPE.BACK, (double) backMax);
            maxMap.put(Lift.BODY_TYPE.SHOULDERS, (double) shouldersMax);
            maxMap.put(Lift.BODY_TYPE.LEGS, (double) legsMax);
            Plan plan = new Plan(currentWeek, maxMap, true);
            displayPlan(plan);
        }

        final Spinner weekSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
          R.array.weeks_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekSpinner.setAdapter(adapter);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.trainingMaxCheckBox);

        final Button button = (Button) findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<Lift.BODY_TYPE, Double> oneRepMaxes = new HashMap<>();

                EditText chest = (EditText) findViewById(R.id.chestOneRepMax);
                chestMax = validateMax(chest.getText().toString());
                if (chestMax != -1) {
                    oneRepMaxes.put(Lift.BODY_TYPE.CHEST, (double) chestMax);
                }

                EditText back = (EditText) findViewById(R.id.backOneRepMax);
                backMax = validateMax(back.getText().toString());
                if (backMax != -1) {
                    oneRepMaxes.put(Lift.BODY_TYPE.BACK, (double) backMax);
                }

                EditText shoulders = (EditText) findViewById(R.id.shouldersOneRepMax);
                shouldersMax = validateMax(shoulders.getText().toString());
                if (shouldersMax != -1) {
                    oneRepMaxes.put(Lift.BODY_TYPE.SHOULDERS, (double) shouldersMax);
                }

                EditText legs = (EditText) findViewById(R.id.legsOneRepMax);
                legsMax = validateMax(legs.getText().toString());
                if (legsMax != -1) {
                    oneRepMaxes.put(Lift.BODY_TYPE.LEGS, (double) legsMax);
                }

                String weekStr = weekSpinner.getSelectedItem().toString().toUpperCase();
                Lift.WEEK_TYPE weekType;
                if ("FIVE".equals(weekStr)) {
                    weekType = Lift.WEEK_TYPE.FIVE;
                }else if ("THREE".equals(weekStr))  {
                    weekType = Lift.WEEK_TYPE.THREE;
                }else if ("ONE".equals(weekStr))    {
                    weekType = Lift.WEEK_TYPE.ONE;
                }else if ("DELOAD".equals(weekStr)) {
                    weekType = Lift.WEEK_TYPE.DELOAD;
                }else   {
                    throw new RuntimeException("Impossible week type on spinner");
                }
                Plan plan = new Plan(weekType, oneRepMaxes, checkBox.isChecked());
                saveTrainingMaxes(weekStr);
                displayPlan(plan);
            }
        });

        final Button planButton = (Button) findViewById(R.id.currentPlanButton);
        planButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPlan(PlanActivity.plan);
                finish();
                startActivity(getIntent());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayPlan(Plan plan) {
        PlanActivity.plan = plan;
        Intent intent = new Intent(MainActivity.this, PlanActivity.class);
        startActivity(intent);
    }

    private void saveTrainingMaxes(String weekStr)  {
        SharedPreferences sharedPreferences = getApplicationContext()
          .getSharedPreferences(TRAINING_MAX_PREFS_KEY, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("CHEST_MAX", chestMax);
        editor.putInt("BACK_MAX", backMax);
        editor.putInt("SHOULDERS_MAX", shouldersMax);
        editor.putInt("LEGS_MAX", legsMax);
        editor.putString("WEEK_TYPE", weekStr);
        editor.apply();
    }

    private Lift.WEEK_TYPE getWeekTypeByString(String weekStr)  {
        if (weekStr == "")  {
            return null;
        }
        if ("FIVE".equals(weekStr.toUpperCase())) {
            return Lift.WEEK_TYPE.FIVE;
        }else if ("THREE".equals(weekStr.toUpperCase()))  {
            return Lift.WEEK_TYPE.THREE;
        }else if ("ONE".equals(weekStr.toUpperCase()))  {
            return Lift.WEEK_TYPE.ONE;
        }else if("DELOAD".equals(weekStr.toUpperCase())) {
            return Lift.WEEK_TYPE.DELOAD;
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
}
