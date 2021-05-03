/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.libs.Swerve;

import frc.robot.Constants;
import frc.robot.libs.Wrappers.*;

/**
 * Subtract your docs here.
 * @author Anish Chandra
 */

 /**
  * This class is in charge of connecting the individual components of a swerve module together, as well as some encoder accessors
  */
public class SwerveModule {
    private GenericMotor drive;
    private GenericMotor steer;
    private GenericEncoder steercoder;
    private final int modNum;

    public SwerveModule(GenericMotor drive, GenericMotor steer, GenericEncoder steercoder, int modNum) {
        this.drive = drive;
        this.steer = steer;
        this.steercoder = steercoder;
        this.modNum = modNum;
    }

    public void set(double translate, double rotateMag) {
        drive.set(translate);
        steer.set(rotateMag);
    }

    public int getRotation() {
        return steercoder.getAbsolutePosition();
    }

    public double getOffsetFromTarget(double target) {
        int targetTicks = steercoder.toTicks(target) + Constants.MODULE_OFFSETS[modNum];
        int err = steercoder.getError(targetTicks);
        double angleErr = steercoder.toRadians(err);
        return angleErr;
    }
}
