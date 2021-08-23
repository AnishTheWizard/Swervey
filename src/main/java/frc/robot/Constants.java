/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int[] MODULE_OFFSETS = {2092,1061,2889,960};//mod 1 is 2120 {2120, 1052, 2787, 968}
    
    public static final double WIDTH = 23.5;
    public static final double LENGTH = 21.5;

    public static final double[] DRIVE_GAINS = {0, 0, 0};
    public static final double[] STEER_GAINS = {0.117, 0.0, 0.0}; //0.226
    public static final double[] ROTATIONAL_GAINS = {0, 0, 0};

    public static final int NUMBER_OF_MODULES = 4;

    public static final int TICKS_PER_ROTATION = 4096;

    public static final int OVERFLOW_THRESHOLD = 2048;
 
    public static final int WHEEL_DIAMETER = 4;

    public static final double GEAR_RATIO = 8.31/1;
    public static final double DRIVE_TICKS_PER_ROTATION = TICKS_PER_ROTATION * GEAR_RATIO;

    public static final double CONTROLLER_DEADBAND = 0.1;

    public static double PERCENT_SPEED = 0.26;
}
