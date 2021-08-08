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

    public static int toTicks(double rad) {
        if(rad > 2 * Math.PI) { // limit the range of radians to 0 -> 2pi
            rad = rad % (2 * Math.PI);
        }
        else if(rad < 0) {
            rad = (2 * Math.PI) + (rad % (2 * Math.PI));
        }
        return (int)((rad * Constants.TICKS_PER_ROTATION)/(Math.PI*2));
    }

    public static double toRadians(int ticks) {
        return (ticks * Math.PI * 2)/Constants.TICKS_PER_ROTATION;
    }
}
