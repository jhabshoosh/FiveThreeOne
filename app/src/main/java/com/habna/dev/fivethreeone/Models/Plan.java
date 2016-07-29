package com.habna.dev.fivethreeone.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jhabs on 7/28/2016.
 */
public class Plan implements Serializable {

  private List<Lift> lifts;
  private Lift.WEEK_TYPE weekType;
  private Map<Lift.BODY_TYPE, Double> oneRepMaxes;

  public Plan(Lift.WEEK_TYPE weekType, Map<Lift.BODY_TYPE, Double> oneRepMaxes, final boolean trainingMaxes) {
    this.weekType = weekType;
    this.oneRepMaxes = oneRepMaxes;
    initLifts(trainingMaxes);
  }

  private void initLifts(final boolean trainingMaxes)  {
    double multiplier = trainingMaxes ? 1 : .9;
    lifts = new ArrayList<>();
    lifts.add(new Lift(Lift.BODY_TYPE.CHEST, weekType,
      multiplier*oneRepMaxes.get(Lift.BODY_TYPE.CHEST)));
    lifts.add(new Lift(Lift.BODY_TYPE.BACK, weekType,
      multiplier*oneRepMaxes.get(Lift.BODY_TYPE.BACK)));
    lifts.add(new Lift(Lift.BODY_TYPE.SHOULDERS, weekType,
      multiplier*oneRepMaxes.get(Lift.BODY_TYPE.SHOULDERS)));
    lifts.add(new Lift(Lift.BODY_TYPE.LEGS, weekType,
      multiplier*oneRepMaxes.get(Lift.BODY_TYPE.LEGS)));
  }

  public Lift getLift(Lift.BODY_TYPE bodyType)  {
    for (Lift lift : lifts) {
      if (lift.getBodyType().equals(bodyType))  {
        return lift;
      }
    }
    return null;
  }

}
