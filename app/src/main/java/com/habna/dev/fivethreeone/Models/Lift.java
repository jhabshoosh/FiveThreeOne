package com.habna.dev.fivethreeone.Models;

import android.util.Pair;

import com.habna.dev.fivethreeone.MainActivity;
import com.habna.dev.fivethreeone.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a lift. Contains day type, body type and training max.
 */
public class Lift implements Serializable {

  Util.BODY_TYPE bodyType;
  Util.WEEK_TYPE weekType;
  double trainingMax;
  List<Pair<Double, Integer>> sets;

  protected final int NUM_SETS = 3;

  ;

  public Lift(Util.BODY_TYPE bodyType, Util.WEEK_TYPE dayType, double trainingMax) {
    this.bodyType = bodyType;
    this.weekType = dayType;
    this.trainingMax = trainingMax;
    calculateSets();
  }

  public Util.BODY_TYPE getBodyType() {
    return bodyType;
  }

  private void calculateSets()  {
    sets = new ArrayList<>();
    double startingWeightMultiplier = getStartingWeightMultiplier();
    for (int i = 0; i < NUM_SETS; i++)  {
      double weight = startingWeightMultiplier * trainingMax;
      int numReps = getNumReps(i+1);
      sets.add(new Pair<>(weight, numReps));
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
      StringBuilder setString = new StringBuilder("Set #" + setCount + ": " +
        Util.getActualRounding(set.first) + (MainActivity.lbs ? " lbs" : " kg") + " x " + set.second);
      if (setCount == NUM_SETS) {
        setString.append("*");
      }
      setStrings.add(setString.toString());
      setCount++;
    }
    return setStrings;
  }

  public double getTrainingMax() {
    return trainingMax;
  }

  public void switchUnits(boolean lbs)  {
    double divisor = Plan.lbsToKg;
    if (lbs)  {
      divisor = 1 / divisor;
    }
    List<Pair<Double, Integer>> newSet = new ArrayList<>();
    for (Pair<Double, Integer> set : sets)  {
      newSet.add(new Pair<>(set.first/divisor, set.second));
    }
    sets = newSet;
  }

  public String getSetString(int set) {
    final Pair<Double, Integer> setInfo = sets.get(set - 1);
    return Util.getActualRounding(setInfo.first) + (MainActivity.lbs ? " lbs" : " kg") + " x " + setInfo.second;
  }
}
