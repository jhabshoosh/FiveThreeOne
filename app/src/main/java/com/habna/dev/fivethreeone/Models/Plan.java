package com.habna.dev.fivethreeone.Models;

import android.support.annotation.IntegerRes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jhabs on 7/28/2016.
 */
public class Plan implements Serializable {

  private List<Lift> lifts;
  private Lift.WEEK_TYPE weekType;
  private Map<Lift.BODY_TYPE, Double> trainingMaxes;

  public Plan(Lift.WEEK_TYPE weekType, Map<Lift.BODY_TYPE, Double> oneRepMaxes, final boolean isTrainingMaxes) {
    init(weekType, oneRepMaxes, isTrainingMaxes);
  }

  private void initPlan(Lift.WEEK_TYPE weekType, final Map<Lift.BODY_TYPE, Double> oneRepMaxes, boolean isTrainingMaxes) {
    this.weekType = weekType;
    trainingMaxes = new HashMap<>();
    double multiplier = isTrainingMaxes ? 1 : .9;
    if (oneRepMaxes.containsKey(Lift.BODY_TYPE.CHEST)) {
      trainingMaxes.put(Lift.BODY_TYPE.CHEST, multiplier * oneRepMaxes.get(Lift.BODY_TYPE.CHEST));
    }
    if (oneRepMaxes.containsKey(Lift.BODY_TYPE.BACK)) {
      trainingMaxes.put(Lift.BODY_TYPE.BACK, multiplier * oneRepMaxes.get(Lift.BODY_TYPE.BACK));
    }
    if (oneRepMaxes.containsKey(Lift.BODY_TYPE.SHOULDERS)) {
      trainingMaxes.put(Lift.BODY_TYPE.SHOULDERS, multiplier * oneRepMaxes.get(Lift.BODY_TYPE.SHOULDERS));
    }
    if (oneRepMaxes.containsKey(Lift.BODY_TYPE.LEGS)) {
      trainingMaxes.put(Lift.BODY_TYPE.LEGS, multiplier * oneRepMaxes.get(Lift.BODY_TYPE.LEGS));
    }
  }

  private void initLifts()  {
    lifts = new ArrayList<>();
    if (trainingMaxes.containsKey(Lift.BODY_TYPE.CHEST)) {
      lifts.add(new Lift(Lift.BODY_TYPE.CHEST, weekType, trainingMaxes.get(Lift.BODY_TYPE.CHEST)));
    }
    if (trainingMaxes.containsKey(Lift.BODY_TYPE.BACK)) {
      lifts.add(new Lift(Lift.BODY_TYPE.BACK, weekType, trainingMaxes.get(Lift.BODY_TYPE.BACK)));
    }
    if (trainingMaxes.containsKey(Lift.BODY_TYPE.SHOULDERS)) {
      lifts.add(new Lift(Lift.BODY_TYPE.SHOULDERS, weekType, trainingMaxes.get(Lift.BODY_TYPE.SHOULDERS)));
    }
    if (trainingMaxes.containsKey(Lift.BODY_TYPE.LEGS)) {
      lifts.add(new Lift(Lift.BODY_TYPE.LEGS, weekType, trainingMaxes.get(Lift.BODY_TYPE.LEGS)));
    }
  }

  public Lift getLift(Lift.BODY_TYPE bodyType)  {
    for (Lift lift : lifts) {
      if (lift.getBodyType().equals(bodyType))  {
        return lift;
      }
    }
    return null;
  }

  public Lift.WEEK_TYPE getWeekType() {
    return weekType;
  }

  public void bumpWeek()  {
    Lift.WEEK_TYPE nextWeek = getNextWeek();
    if (Lift.WEEK_TYPE.FIVE.equals(nextWeek)) {
      bumpMaxes();
    }
    init(nextWeek, trainingMaxes, true);
  }

  private void init(Lift.WEEK_TYPE nextWeek, Map<Lift.BODY_TYPE, Double> trainingMaxes,
                    boolean isTrainingMaxes) {
    initPlan(nextWeek, trainingMaxes, isTrainingMaxes);
    initLifts();
  }

  private void bumpMaxes()  {
    for (Lift.BODY_TYPE key : trainingMaxes.keySet())  {
      Double value = trainingMaxes.get(key);
      if (Lift.BODY_TYPE.CHEST.equals(key) || Lift.BODY_TYPE.SHOULDERS.equals(key)) {
        trainingMaxes.put(key, value + 5);
      }else {
        trainingMaxes.put(key, value + 10);
      }
    }
  }

  private Lift.WEEK_TYPE getNextWeek()  {
    switch (weekType) {
      case FIVE:
        return Lift.WEEK_TYPE.THREE;
      case THREE:
        return Lift.WEEK_TYPE.ONE;
      case ONE:
        return Lift.WEEK_TYPE.DELOAD;
      case DELOAD:
        return Lift.WEEK_TYPE.FIVE;
    }
    return null;
  }

  public String getTrainingMaxDisplay(Lift.BODY_TYPE bodyType) {
    return "Training Max: " + trainingMaxes.get(bodyType).toString() + " lbs.";
  }

  public boolean doesHeEvenLift(Lift.BODY_TYPE bodyType)  {
    return trainingMaxes.containsKey(bodyType);
  }
}
