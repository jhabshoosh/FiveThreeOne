package com.habna.dev.fivethreeone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.habna.dev.fivethreeone.Models.Lift;

import java.util.List;

public class LiftActivity extends AppCompatActivity {

  public static Lift lift;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lift);

    TextView trainingMaxText = (TextView) findViewById(R.id.trainingMaxText);
    trainingMaxText.setText("TRAINING MAX: " + lift.getTrainingMax());

    TextView set1TextView = (TextView) findViewById(R.id.set1Text);
    TextView set2TextView = (TextView) findViewById(R.id.set2Text);
    TextView set3TextView = (TextView) findViewById(R.id.set3Text);

    List<String> setStrings = lift.getDisplayText();

    set1TextView.setText(setStrings.get(0));
    set2TextView.setText(setStrings.get(1));
    set3TextView.setText(setStrings.get(2));
  }

}
