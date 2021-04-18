package frc.robot.libs.Utility;

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
}
