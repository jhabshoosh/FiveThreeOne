package com.habna.dev.fivethreeone;

import android.app.Activity;
import android.content.Intent;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by jhabs on 8/2/2016.
 */
public class Util {

  public static final String UNIT_PREFS_KEY = "UNIT";
  static final String TRAINING_MAX_PREFS_KEY = "TRAINING_MAX";

  public static double getActualRounding(double weight)  {
    if (MainActivity.lbs) {
      return getActualRoundingInLbs(weight);
    }
    return getActualRoundingInKg(weight);
  }

  private static double getActualRoundingInKg(double weight)  {
    BigDecimal bd = new BigDecimal(weight);
    bd = bd.setScale(1, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }

  private static double getActualRoundingInLbs(double weight) {
    long rounded = Math.round(weight);
    long nearestBase = rounded;
    int counter = 0;
    double base = MainActivity.lbs ? 5 : 2.5;
    while (nearestBase % base != 0)  {
      nearestBase--;
      counter++;
    }
    if (counter > 2)  {
      return nearestBase + base;
    }
    return nearestBase;
  }

  public static void refreshUnits(Activity activity, Intent intent) {
    activity.finish();
    activity.startActivity(intent);
  }

  public static String getWeekString(WEEK_TYPE weekType) {
    switch (weekType) {
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

  public enum BODY_TYPE {
    CHEST,
    BACK,
    SHOULDERS,
    LEGS
  }

  public enum WEEK_TYPE {
    FIVE,
    THREE,
    ONE,
    DELOAD
  }
}
