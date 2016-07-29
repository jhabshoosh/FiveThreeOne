package com.habna.dev.fivethreeone.Models;

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
  private Map<Lift.BODY_TYPE, Double> oneRepMaxes;
  private Map<Lift.BODY_TYPE, Double> trainingMaxes;

  public Plan(Lift.WEEK_TYPE weekType, Map<Lift.BODY_TYPE, Double> oneRepMaxes, final boolean isTrainingMaxes) {
    initPlan(weekType, oneRepMaxes, isTrainingMaxes);
    initLifts(isTrainingMaxes);
  }

  private void initPlan(Lift.WEEK_TYPE weekType, Map<Lift.BODY_TYPE, Double> oneRepMaxes, boolean isTrainingMaxes) {
    this.weekType = weekType;
    this.oneRepMaxes = oneRepMaxes;
    if (isTrainingMaxes)  {
      trainingMaxes = new HashMap<>(trainingMaxes);
    }else {
      trainingMaxes = new HashMap<>();
      trainingMaxes.put(Lift.BODY_TYPE.CHEST, .9 * oneRepMaxes.get(Lift.BODY_TYPE.CHEST));
      trainingMaxes.put(Lift.BODY_TYPE.BACK, .9 * oneRepMaxes.get(Lift.BODY_TYPE.BACK));
      trainingMaxes.put(Lift.BODY_TYPE.SHOULDERS, .9 * oneRepMaxes.get(Lift.BODY_TYPE.SHOULDERS));
      trainingMaxes.put(Lift.BODY_TYPE.LEGS, .9 * oneRepMaxes.get(Lift.BODY_TYPE.LEGS));
    }
  }

  private void initLifts(final boolean isTrainingMaxes)  {
    lifts = new ArrayList<>();
    lifts.add(new Lift(Lift.BODY_TYPE.CHEST, weekType, trainingMaxes.get(Lift.BODY_TYPE.CHEST)));
    lifts.add(new Lift(Lift.BODY_TYPE.BACK, weekType, trainingMaxes.get(Lift.BODY_TYPE.BACK)));
    lifts.add(new Lift(Lift.BODY_TYPE.SHOULDERS, weekType, trainingMaxes.get(Lift.BODY_TYPE.SHOULDERS)));
    lifts.add(new Lift(Lift.BODY_TYPE.LEGS, weekType, trainingMaxes.get(Lift.BODY_TYPE.LEGS)));
  }

  public Lift getLift(Lift.BODY_TYPE bodyType)  {
    for (Lift lift : lifts) {
      if (lift.getBodyType().equals(bodyType))  {
        return lift;
      }
    }
    return null;
  }

  public List<Lift> getLifts() {
    return lifts;
  }

  public Lift.WEEK_TYPE getWeekType() {
    return weekType;
  }

  public Map<Lift.BODY_TYPE, Double> getOneRepMaxes() {
    return oneRepMaxes;
  }

  public Map<Lift.BODY_TYPE, Double> getTrainingMaxes() {
    return trainingMaxes;
  }

  public void setTrainingMaxes(Map<Lift.BODY_TYPE, Double> trainingMaxes) {
    this.trainingMaxes = trainingMaxes;
  }

  public void bumpWeek()  {
    Lift.WEEK_TYPE nextWeek = getNextWeek();
    if (Lift.WEEK_TYPE.FIVE.equals(nextWeek)) {
      bumpMaxes();
    }
    initPlan(nextWeek, trainingMaxes, true);
    initLifts(true);
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
}
