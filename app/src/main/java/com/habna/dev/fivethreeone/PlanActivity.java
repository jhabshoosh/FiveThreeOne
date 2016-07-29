package com.habna.dev.fivethreeone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.habna.dev.fivethreeone.Models.Lift;
import com.habna.dev.fivethreeone.Models.Plan;

public class PlanActivity extends AppCompatActivity {

  public static Plan plan;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_plan);

    Intent intent = getIntent();

//    plan = (Plan) intent.getSerializableExtra("Plan");

    Button chestButton = (Button) findViewById(R.id.chestButton);
    Button backButton = (Button) findViewById(R.id.backButton);
    Button shouldersButton = (Button) findViewById(R.id.shouldersButton);
    Button legsButton = (Button) findViewById(R.id.legsButton);

    chestButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        displayLift(plan.getLift(Lift.BODY_TYPE.CHEST));
      }
    });

    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        displayLift(plan.getLift(Lift.BODY_TYPE.BACK));
      }
    });

    shouldersButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        displayLift(plan.getLift(Lift.BODY_TYPE.SHOULDERS));
      }
    });

    legsButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        displayLift(plan.getLift(Lift.BODY_TYPE.LEGS));
      }
    });
  }

  private void displayLift(Lift selectedLift)  {
    LiftActivity.lift = selectedLift;
    Intent intent = new Intent(PlanActivity.this, LiftActivity.class);
    startActivity(intent);
  }

}
