package com.habna.dev.fivethreeone.Models;

import com.habna.dev.fivethreeone.Util.BODY_TYPE;
import com.habna.dev.fivethreeone.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Input: Maxs
 * Output: Schedule
 */
public class Calculator {

  private double benchMax;
  private double deadliftMax;
  private double ohpMax;
  private double squatMax;
  private Map<BODY_TYPE, Double> trainingMaxes;

  public Calculator(double benchMax, double deadliftMax, double ohpMax, double squatMax) {
    this.benchMax = benchMax;
    this.deadliftMax = deadliftMax;
    this.ohpMax = ohpMax;
    this.squatMax = squatMax;
    calculateTrainingMaxes();
  }

  public double getBenchMax() {
    return benchMax;
  }

  public void setBenchMax(double benchMax) {
    this.benchMax = benchMax;
  }

  public double getDeadliftMax() {
    return deadliftMax;
  }

  public void setDeadliftMax(double deadliftMax) {
    this.deadliftMax = deadliftMax;
  }

  public double getOhpMax() {
    return ohpMax;
  }

  public void setOhpMax(double ohpMax) {
    this.ohpMax = ohpMax;
  }

  public double getSquatMax() {
    return squatMax;
  }

  public void setSquatMax(double squatMax) {
    this.squatMax = squatMax;
  }

  private void calculateTrainingMaxes()  {
    trainingMaxes = new HashMap<>();
    trainingMaxes.put(Util.BODY_TYPE.CHEST, benchMax*.9);
    trainingMaxes.put(Util.BODY_TYPE.BACK, deadliftMax*.9);
    trainingMaxes.put(Util.BODY_TYPE.SHOULDERS, ohpMax*.9);
    trainingMaxes.put(Util.BODY_TYPE.LEGS, squatMax*.9);
  }
}
