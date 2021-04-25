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
    public static final int[] MODULE_OFFSETS = {0, 0, 0, 0};
    
    public static final double WIDTH = 23.5;
    public static final double LENGTH = 21.5;

    public static final double[] dtGains = {0.0, 0.0, 0.0};
    public static final int NUMBER_OF_MODULES = 4;

    public static final int TICKS_PER_ROTATION = 4096; //TODO 4096?

    public static final int OVERFLOW_THRESHOLD = 3900;
}
