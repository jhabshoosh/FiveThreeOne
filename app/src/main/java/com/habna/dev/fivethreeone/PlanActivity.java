package com.habna.dev.fivethreeone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.habna.dev.fivethreeone.Models.Lift;
import com.habna.dev.fivethreeone.Models.Plan;

import org.w3c.dom.Text;

public class PlanActivity extends AppCompatActivity {

  public static Plan plan;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_plan);

    TextView weekTypeText = (TextView) findViewById(R.id.weekTypeText);
    weekTypeText.setText(weekTypeToString(plan.getWeekType()) + " WEEK");

    Button chestButton = (Button) findViewById(R.id.chestButton);
    Button backButton = (Button) findViewById(R.id.backButton);
    Button shouldersButton = (Button) findViewById(R.id.shouldersButton);
    Button legsButton = (Button) findViewById(R.id.legsButton);

    final Button bumpWeekButton = (Button) findViewById(R.id.bumpWeekButton);
    bumpWeekButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        plan.bumpWeek();
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
