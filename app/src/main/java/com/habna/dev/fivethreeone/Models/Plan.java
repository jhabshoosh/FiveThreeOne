package com.habna.dev.fivethreeone.Models;

import com.habna.dev.fivethreeone.MainActivity;
import com.habna.dev.fivethreeone.Util;

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
  private Util.WEEK_TYPE weekType;
  private Map<Util.BODY_TYPE, Double> trainingMaxes;

  public final static double lbsToKg = 2.2046226218;

  public Plan(Util.WEEK_TYPE weekType, Map<Util.BODY_TYPE, Double> oneRepMaxes, final boolean isTrainingMaxes) {
    init(weekType, oneRepMaxes, isTrainingMaxes);
  }

  private void initPlan(Util.WEEK_TYPE weekType, final Map<Util.BODY_TYPE, Double> oneRepMaxes, boolean isTrainingMaxes) {
    this.weekType = weekType;
    trainingMaxes = new HashMap<>();
    double multiplier = isTrainingMaxes ? 1 : .9;
    if (oneRepMaxes.containsKey(Util.BODY_TYPE.CHEST)) {
      trainingMaxes.put(Util.BODY_TYPE.CHEST, roundMax(multiplier * oneRepMaxes.get(Util.BODY_TYPE.CHEST)));
    }
    if (oneRepMaxes.containsKey(Util.BODY_TYPE.BACK)) {
      trainingMaxes.put(Util.BODY_TYPE.BACK, roundMax(multiplier * oneRepMaxes.get(Util.BODY_TYPE.BACK)));
    }
    if (oneRepMaxes.containsKey(Util.BODY_TYPE.SHOULDERS)) {
      trainingMaxes.put(Util.BODY_TYPE.SHOULDERS, roundMax(multiplier * oneRepMaxes.get(Util.BODY_TYPE.SHOULDERS)));
    }
    if (oneRepMaxes.containsKey(Util.BODY_TYPE.LEGS)) {
      trainingMaxes.put(Util.BODY_TYPE.LEGS, roundMax(multiplier * oneRepMaxes.get(Util.BODY_TYPE.LEGS)));
    }
  }

  private void initLifts()  {
    lifts = new ArrayList<>();
    if (trainingMaxes.containsKey(Util.BODY_TYPE.CHEST)) {
      lifts.add(new Lift(Util.BODY_TYPE.CHEST, weekType, trainingMaxes.get(Util.BODY_TYPE.CHEST)));
    }
    if (trainingMaxes.containsKey(Util.BODY_TYPE.BACK)) {
      lifts.add(new Lift(Util.BODY_TYPE.BACK, weekType, trainingMaxes.get(Util.BODY_TYPE.BACK)));
    }
    if (trainingMaxes.containsKey(Util.BODY_TYPE.SHOULDERS)) {
      lifts.add(new Lift(Util.BODY_TYPE.SHOULDERS, weekType, trainingMaxes.get(Util.BODY_TYPE.SHOULDERS)));
    }
    if (trainingMaxes.containsKey(Util.BODY_TYPE.LEGS)) {
      lifts.add(new Lift(Util.BODY_TYPE.LEGS, weekType, trainingMaxes.get(Util.BODY_TYPE.LEGS)));
    }
  }

  public Lift getLift(Util.BODY_TYPE bodyType)  {
    for (Lift lift : lifts) {
      if (lift.getBodyType().equals(bodyType))  {
        return lift;
      }
    }
    return null;
  }

  public Util.WEEK_TYPE getWeekType() {
    return weekType;
  }

  public void bumpWeek()  {
    Util.WEEK_TYPE nextWeek = getNextWeek();
    if (Util.WEEK_TYPE.FIVE.equals(nextWeek)) {
      bumpMaxes();
    }
    init(nextWeek, trainingMaxes, true);
  }

  private void init(Util.WEEK_TYPE nextWeek, Map<Util.BODY_TYPE, Double> trainingMaxes,
                    boolean isTrainingMaxes) {
    initPlan(nextWeek, trainingMaxes, isTrainingMaxes);
    initLifts();
  }

  private void bumpMaxes()  {
    double inc = MainActivity.lbs ? 5 : 2.5;
    for (Util.BODY_TYPE key : trainingMaxes.keySet())  {
      Double value = trainingMaxes.get(key);
      if (Util.BODY_TYPE.CHEST.equals(key) || Util.BODY_TYPE.SHOULDERS.equals(key)) {
        trainingMaxes.put(key, roundMax(value + inc));
      }else {
        trainingMaxes.put(key, roundMax(value + inc));
      }
    }
  }

  private Util.WEEK_TYPE getNextWeek()  {
    switch (weekType) {
      case FIVE:
        return Util.WEEK_TYPE.THREE;
      case THREE:
        return Util.WEEK_TYPE.ONE;
      case ONE:
        return Util.WEEK_TYPE.FIVE;
      case DELOAD:
        return Util.WEEK_TYPE.FIVE;
    }
    return null;
  }

  public String getTrainingMaxDisplay(Util.BODY_TYPE bodyType) {
    return " " + trainingMaxes.get(bodyType).toString() + (MainActivity.lbs ? " lbs" : " kg");
  }

  public boolean doesHeEvenLift(Util.BODY_TYPE bodyType)  {
    return trainingMaxes.containsKey(bodyType);
  }

  public boolean doesLift() {
    return doesHeEvenLift(Util.BODY_TYPE.CHEST) || doesHeEvenLift(Util.BODY_TYPE.BACK) ||
      doesHeEvenLift(Util.BODY_TYPE.SHOULDERS) || doesHeEvenLift(Util.BODY_TYPE.LEGS);
  }

  private Double roundMax(double max)  {
    return Util.getActualRounding(max);
  }

  public void switchUnits() {
    double divisor = lbsToKg;
    if (MainActivity.lbs)  {
      divisor = 1 / divisor;
    }
    if (trainingMaxes.containsKey(Util.BODY_TYPE.CHEST))  {
      trainingMaxes.put(Util.BODY_TYPE.CHEST, roundMax(trainingMaxes.get(Util.BODY_TYPE.CHEST) / divisor));
    }
    if (trainingMaxes.containsKey(Util.BODY_TYPE.BACK))  {
      trainingMaxes.put(Util.BODY_TYPE.BACK, roundMax(trainingMaxes.get(Util.BODY_TYPE.BACK) / divisor));
    }
    if (trainingMaxes.containsKey(Util.BODY_TYPE.SHOULDERS))  {
      trainingMaxes.put(Util.BODY_TYPE.SHOULDERS, roundMax(trainingMaxes.get(Util.BODY_TYPE.SHOULDERS) / divisor));
    }
    if (trainingMaxes.containsKey(Util.BODY_TYPE.LEGS))  {
      trainingMaxes.put(Util.BODY_TYPE.LEGS, roundMax(trainingMaxes.get(Util.BODY_TYPE.LEGS) / divisor));
    }

    for (Lift lift : lifts) {
      lift.switchUnits(MainActivity.lbs);
    }
  }

  public void doDeload()  {
    init(Util.WEEK_TYPE.DELOAD, trainingMaxes, true);
  }
}
