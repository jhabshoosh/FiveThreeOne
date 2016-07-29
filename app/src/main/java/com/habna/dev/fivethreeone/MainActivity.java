package com.habna.dev.fivethreeone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.habna.dev.fivethreeone.Models.Lift;
import com.habna.dev.fivethreeone.Models.Plan;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner weekSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
          R.array.weeks_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weekSpinner.setAdapter(adapter);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.trainingMaxCheckBox);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<Lift.BODY_TYPE, Double> oneRepMaxes = new HashMap<>();
                Double chestMax, backMax, shouldersMax, legsMax;
                EditText chest = (EditText) findViewById(R.id.chestOneRepMax);
                chestMax = Double.valueOf(chest.getText().toString());
                oneRepMaxes.put(Lift.BODY_TYPE.CHEST, chestMax);

                EditText back = (EditText) findViewById(R.id.backOneRepMax);
                backMax = Double.valueOf(back.getText().toString());
                oneRepMaxes.put(Lift.BODY_TYPE.BACK, backMax);

                EditText shoulders = (EditText) findViewById(R.id.shouldersOneRepMax);
                shouldersMax = Double.valueOf(shoulders.getText().toString());
                oneRepMaxes.put(Lift.BODY_TYPE.SHOULDERS, shouldersMax);

                EditText legs = (EditText) findViewById(R.id.legsOneRepMax);
                legsMax = Double.valueOf(legs.getText().toString());
                oneRepMaxes.put(Lift.BODY_TYPE.LEGS, legsMax);

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
                displayPlan(plan);
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
}
