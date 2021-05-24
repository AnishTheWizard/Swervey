/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.libs.Swerve;

import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.Constants;
import frc.robot.libs.Utils.MathUtility;
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
    private PIDController steerController;

    private double x, y;

    public SwerveModule(GenericMotor drive, GenericMotor steer, GenericEncoder steercoder, PIDController controller) {
        this.drive = drive;
        this.steer = steer;
        this.steercoder = steercoder;
        this.steerController = controller;

        this.x = 0.0;
        this.y = 0.0;
    }

    public void set(double translate, double theta) {
        int targetTicks = MathUtility.toTicks(theta);
        int ticksErr = getError(targetTicks);

        if(ticksErr > Constants.TICKS_PER_ROTATION * 0.25) {
            ticksErr -= Constants.TICKS_PER_ROTATION/2;
            translate *= -1;
        }

        double rotateMag = steerController.calculate(ticksErr);

        drive.set(translate);
        steer.set(rotateMag);
    }

    public void updatePose(double x, double y) {
        this.x += drive.getSensorOffset() * x;
        this.y += drive.getSensorOffset() * y;
    }

    public double[] getModulePosition() {
        return new double[]{x, y};
    }

    public int getError(int target) {
        int currentPose = steercoder.getAbsolutePosition();
        int err = target - currentPose;

        if(Math.abs(err) > Constants.OVERFLOW_THRESHOLD) {//fix encoder jumps from 4095 -> 0, and 0 -> 4095
            if(err < 0) {
                target+=Constants.TICKS_PER_ROTATION;
            }
            else if(err > 0){
                currentPose += Constants.TICKS_PER_ROTATION;
            }
            err = target - currentPose;
        }
        return err;
    }
}
