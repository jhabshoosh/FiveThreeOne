package com.habna.dev.fivethreeone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.habna.dev.fivethreeone.Models.Lift;
import com.habna.dev.fivethreeone.Models.Plan;

public class PlanActivity extends AppCompatActivity {

  public static Plan plan;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_plan);

    TextView weekTypeText = (TextView) findViewById(R.id.weekTypeText);
    weekTypeText.setText(weekTypeToString(plan.getWeekType()) + " WEEK");

    TextView chestText = (TextView) findViewById(R.id.chestText);
    TextView backText = (TextView) findViewById(R.id.backText);
    TextView shouldersText = (TextView) findViewById(R.id.shouldersText);
    TextView legsText = (TextView) findViewById(R.id.legsText);

    final Button chestButton = (Button) findViewById(R.id.chestButton);
    final Button backButton = (Button) findViewById(R.id.backButton);
    final Button shouldersButton = (Button) findViewById(R.id.shouldersButton);
    final Button legsButton = (Button) findViewById(R.id.legsButton);

    if (plan.doesHeEvenLift(Lift.BODY_TYPE.CHEST)) {
      chestText.setText(plan.getTrainingMaxDisplay(Lift.BODY_TYPE.CHEST));
    }else {
      chestButton.setVisibility(View.GONE);
    }

    if (plan.doesHeEvenLift(Lift.BODY_TYPE.BACK))  {
      backText.setText(plan.getTrainingMaxDisplay(Lift.BODY_TYPE.BACK));
    }else {
      backButton.setVisibility(View.GONE);
    }

    if (plan.doesHeEvenLift(Lift.BODY_TYPE.SHOULDERS)) {
      shouldersText.setText(plan.getTrainingMaxDisplay(Lift.BODY_TYPE.SHOULDERS));
    }else {
      shouldersButton.setVisibility(View.GONE);
    }

    if (plan.doesHeEvenLift(Lift.BODY_TYPE.LEGS))  {
      legsText.setText(plan.getTrainingMaxDisplay(Lift.BODY_TYPE.LEGS));
    }else {
      legsButton.setVisibility(View.GONE);
    }

    final Button bumpWeekButton = (Button) findViewById(R.id.bumpWeekButton);
    bumpWeekButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        plan.bumpWeek();
        finish();
        startActivity(getIntent());
      }
    });

    Button planSwitchUnitButton = (Button) findViewById(R.id.planSwitchUnitButton);
    planSwitchUnitButton.setText(plan.isLbs() ? "Convert to kg" : "Convert to lbs");
    planSwitchUnitButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        plan.switchUnits();
        finish();
        startActivity(getIntent());
      }
    });

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

  private String weekTypeToString(Lift.WEEK_TYPE weekType)    {
    switch (weekType)   {
      case FIVE:
        return "FIVE";
      case THREE:
        return "THREE";
      case ONE:
        return "ONE";
      case DELOAD:
        return "DELOAD";
    }
    return null;
  }


}
