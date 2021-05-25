package frc.robot.libs.Utils;

import frc.robot.Constants;

public class MathUtility {
    public static double[] normalize(double[] ds) {
        double maxVal = 1;
        for(double d : ds) {
            if(d > maxVal) {
                maxVal = d;
            }
        }

        for(int i = 0; i < ds.length; i++) {
            ds[i] /= maxVal;
        }

        return ds;
    }

    public static double toFeet(double x) { // from ticks
        return x * 12 / Constants.TICKS_PER_ROTATION;
    }

    public static double applyGearRatio(double ticks) {
        double turns = ticks/Constants.TICKS_PER_ROTATION;
        return turns / Constants.GEAR_RATIO;
    }

    public static int toTicks(double radians) {
        if(radians < 0) {
            radians += Math.PI * 2; //atan returns -pi -> pi, if its negative, hard to map to encoder 0 -> 4096
        }
        return (int)((radians * Constants.TICKS_PER_ROTATION)/(Math.PI*2));
    }

    public static double toRadians(int ticks) {
        return (ticks * Math.PI * 2)/Constants.TICKS_PER_ROTATION;
    }
}
