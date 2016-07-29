package com.habna.dev.fivethreeone.Models;

import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a lift. Contains day type, body type and training max.
 */
public class Lift implements Serializable {

  BODY_TYPE bodyType;
  WEEK_TYPE weekType;
  double trainingMax;
  List<Pair<Double, Integer>> sets;

  protected final int NUM_SETS = 3;

  public enum BODY_TYPE {
    CHEST,
    BACK,
    SHOULDERS,
    LEGS
  };

  public enum WEEK_TYPE {
    FIVE,
    THREE,
    ONE,
    DELOAD
  }

  public Lift() {
  }

  public Lift(BODY_TYPE bodyType, WEEK_TYPE dayType, double trainingMax) {
    this.bodyType = bodyType;
    this.weekType = dayType;
    this.trainingMax = trainingMax;
    calculateSets();
  }

  public BODY_TYPE getBodyType() {
    return bodyType;
  }

  public void setBodyType(BODY_TYPE bodyType) {
    this.bodyType = bodyType;
  }

  public WEEK_TYPE getDayType() {
    return weekType;
  }

  public void setDayType(WEEK_TYPE dayType) {
    this.weekType = dayType;
  }

  public double getTrainingMax() {
    return trainingMax;
  }

  public void setTrainingMax(double trainingMax) {
    this.trainingMax = trainingMax;
  }

  private void calculateSets()  {
    sets = new ArrayList<>();
    double startingWeightMultiplier = getStartingWeightMultiplier();
    for (int i = 0; i < NUM_SETS; i++)  {
      double weight = startingWeightMultiplier * trainingMax;
      int numReps = getNumReps(i+1);
      sets.add(new Pair<Double, Integer>(weight, numReps));
      startingWeightMultiplier += .1;
    }
  }

  private double getStartingWeightMultiplier()  {
    switch(weekType)  {
      case FIVE:
        return .65;
      case THREE:
        return .7;
      case ONE:
        return .75;
      case DELOAD:
        return .4;
    }
    return -1;
  }

  private int getNumReps(int setNum)  {
    switch (weekType) {
      case FIVE:
        return 5;
      case THREE:
        return 3;
      case ONE:
        return getOneWeekReps(setNum);
      case DELOAD:
        return 5;
    }
    return -1;
  }

  private int getOneWeekReps(int setNum)  {
    switch (setNum) {
      case 1:
        return 5;
      case 2:
        return 3;
      case 3:
        return 1;
    }
    return -1;
  }

  public List<String> getDisplayText()  {
    List<String> setStrings = new ArrayList<>();

    int setCount = 1;
    for (Pair<Double, Integer> set : sets)  {
      StringBuilder setString = new StringBuilder("Set #" + setCount + ": " + getActualRounding(set.first) + " x " + set.second);
      if (setCount == NUM_SETS) {
        setString.append("*");
      }
      setStrings.add(setString.toString());
      setCount++;
    }
    return setStrings;
  }

  private long getActualRounding(double weight)  {
    long rounded = Math.round(weight);
    long nearestBase = rounded;
    int counter = 0;
    while (nearestBase % 5 != 0)  {
      nearestBase--;
      counter++;
    }
    if (counter > 2)  {
      return nearestBase + 5;
    }
    return nearestBase;
  }

}
