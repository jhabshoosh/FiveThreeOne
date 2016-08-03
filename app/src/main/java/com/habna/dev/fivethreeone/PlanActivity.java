package com.habna.dev.fivethreeone;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.habna.dev.fivethreeone.Models.Lift;

public class PlanActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_plan);
    setupActionBar();

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    TextView weekTypeText = (TextView) findViewById(R.id.weekTypeText);
    weekTypeText.setText(weekTypeToString(MainActivity.plan.getWeekType()));

    TextView chestText = (TextView) findViewById(R.id.chestHeader);
    TextView chestSet1 = (TextView) findViewById(R.id.chestSet1);
    TextView chestSet2 = (TextView) findViewById(R.id.chestSet2);
    TextView chestSet3 = (TextView) findViewById(R.id.chestSet3);
    TextView backText = (TextView) findViewById(R.id.backHeader);
    TextView backSet1 = (TextView) findViewById(R.id.backSet1);
    TextView backSet2 = (TextView) findViewById(R.id.backSet2);
    TextView backSet3 = (TextView) findViewById(R.id.backSet3);
    TextView shouldersText = (TextView) findViewById(R.id.shouldersHeader);
    TextView shouldersSet1 = (TextView) findViewById(R.id.shouldersSet1);
    TextView shouldersSet2 = (TextView) findViewById(R.id.shouldersSet2);
    TextView shouldersSet3 = (TextView) findViewById(R.id.shouldersSet3);
    TextView legsText = (TextView) findViewById(R.id.legsHeader);
    TextView legsSet1 = (TextView) findViewById(R.id.legsSet1);
    TextView legsSet2 = (TextView) findViewById(R.id.legsSet2);
    TextView legsSet3 = (TextView) findViewById(R.id.legsSet3);

    if (MainActivity.plan.doesHeEvenLift(Lift.BODY_TYPE.CHEST)) {
      chestText.append(MainActivity.plan.getTrainingMaxDisplay(Lift.BODY_TYPE.CHEST));
      chestSet1.setText(MainActivity.plan.getLift(Lift.BODY_TYPE.CHEST).getSetString(1));
      chestSet2.setText(MainActivity.plan.getLift(Lift.BODY_TYPE.CHEST).getSetString(2));
      chestSet3.setText(MainActivity.plan.getLift(Lift.BODY_TYPE.CHEST).getSetString(3));
    }else {
      chestText.setText(R.string.no_chest);
      chestText.setTextColor(Color.RED);
      chestSet1.setVisibility(View.INVISIBLE);
      chestSet2.setVisibility(View.INVISIBLE);
      chestSet3.setVisibility(View.INVISIBLE);
    }

    if (MainActivity.plan.doesHeEvenLift(Lift.BODY_TYPE.BACK))  {
      backText.append(MainActivity.plan.getTrainingMaxDisplay(Lift.BODY_TYPE.BACK));
      backSet1.setText(MainActivity.plan.getLift(Lift.BODY_TYPE.BACK).getSetString(1));
      backSet2.setText(MainActivity.plan.getLift(Lift.BODY_TYPE.BACK).getSetString(2));
      backSet3.setText(MainActivity.plan.getLift(Lift.BODY_TYPE.BACK).getSetString(3));
    }else {
      backText.setText(R.string.no_back);
      backText.setTextColor(Color.RED);
      backSet1.setVisibility(View.INVISIBLE);
      backSet2.setVisibility(View.INVISIBLE);
      backSet3.setVisibility(View.INVISIBLE);
    }

    if (MainActivity.plan.doesHeEvenLift(Lift.BODY_TYPE.SHOULDERS)) {
      shouldersText.append(MainActivity.plan.getTrainingMaxDisplay(Lift.BODY_TYPE.SHOULDERS));
      shouldersSet1.setText(MainActivity.plan.getLift(Lift.BODY_TYPE.SHOULDERS).getSetString(1));
      shouldersSet2.setText(MainActivity.plan.getLift(Lift.BODY_TYPE.SHOULDERS).getSetString(2));
      shouldersSet3.setText(MainActivity.plan.getLift(Lift.BODY_TYPE.SHOULDERS).getSetString(3));
    }else {
      shouldersText.setText(R.string.no_shoulders);
      shouldersText.setTextColor(Color.RED);
      shouldersSet1.setVisibility(View.INVISIBLE);
      shouldersSet2.setVisibility(View.INVISIBLE);
      shouldersSet3.setVisibility(View.INVISIBLE);
    }

    if (MainActivity.plan.doesHeEvenLift(Lift.BODY_TYPE.LEGS))  {
      legsText.append(MainActivity.plan.getTrainingMaxDisplay(Lift.BODY_TYPE.LEGS));
      legsSet1.setText(MainActivity.plan.getLift(Lift.BODY_TYPE.BACK).getSetString(1));
      legsSet2.setText(MainActivity.plan.getLift(Lift.BODY_TYPE.BACK).getSetString(2));
      legsSet3.setText(MainActivity.plan.getLift(Lift.BODY_TYPE.BACK).getSetString(3));
    }else {
      legsText.setText(R.string.no_legs);
      legsText.setTextColor(Color.RED);
      legsSet1.setVisibility(View.INVISIBLE);
      legsSet2.setVisibility(View.INVISIBLE);
      legsSet3.setVisibility(View.INVISIBLE);
    }

    final Button bumpWeekButton = (Button) findViewById(R.id.bumpWeekButton);
    bumpWeekButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        MainActivity.plan.bumpWeek();
        finish();
        startActivity(getIntent());
      }
    });

    final Button deloadButton = (Button) findViewById(R.id.deloadButton);
    deloadButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        MainActivity.plan.doDeload();
        finish();
        startActivity(getIntent());
      }
    });

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
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    MenuItem item = menu.findItem(R.id.action_currentPlan);
    item.setVisible(false);
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
      startActivity(new Intent(PlanActivity.this, SettingsActivity.class));
      return true;
    }else if (id == R.id.home)  {
      finish();
      return true;
    }else if (id == R.id.action_home) {
      startActivity(new Intent(PlanActivity.this, MainActivity.class));
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private String weekTypeToString(Lift.WEEK_TYPE weekType)    {
    switch (weekType)   {
      case FIVE:
        return "5";
      case THREE:
        return "3";
      case ONE:
        return "1";
      case DELOAD:
        return ":(";
    }
    return null;
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  public static void convertPlan()  {
    MainActivity.plan.switchUnits();
  }

}
