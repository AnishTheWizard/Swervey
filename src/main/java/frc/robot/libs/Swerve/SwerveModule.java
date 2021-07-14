/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.libs.Swerve;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    private boolean switcher; //alterates between true or false depending on when you change it

    private double x, y;

    public SwerveModule(GenericMotor drive, GenericMotor steer, GenericEncoder steercoder, PIDController controller) {
        this.drive = drive;
        this.steer = steer;
        this.steercoder = steercoder;
        this.steerController = controller;

        this.x = 0.0;
        this.y = 0.0;

        this.switcher = false;
    }

    public void set(double translate, double theta, double gyroAngle) {
        double xChange = drive.getSensorOffset();
        double yChange = drive.getSensorOffset();

        int targetTicks = MathUtility.toTicks(theta);
        double angleErr = MathUtility.toRadians(getError(targetTicks));

        SmartDashboard.putNumber("err", angleErr);

        if(angleErr > Math.PI/2) {
            angleErr -= Math.PI;
            translate *= -1;
            switcher = !switcher;
        }

        if(switcher) {
            xChange *= Math.cos(MathUtility.toRadians(steercoder.getAbsolutePosition()) + Math.PI/2 - gyroAngle);
            yChange *= Math.sin(MathUtility.toRadians(steercoder.getAbsolutePosition()) + Math.PI/2 - gyroAngle);
        }
        else {
            xChange *= Math.cos(MathUtility.toRadians(steercoder.getAbsolutePosition()) - gyroAngle);
            yChange *= Math.sin(MathUtility.toRadians(steercoder.getAbsolutePosition()) - gyroAngle);
        }

        this.x += xChange;
        this.y += yChange;

        double rotateMag = steerController.calculate(angleErr);

        drive.set(translate);
        steer.set(rotateMag);
        SmartDashboard.putNumber("trans", translate);
        SmartDashboard.putNumber("rotate mag", rotateMag);
    }

    public void resetPose() {
        this.x = 0;
        this.y = 0;
    }

    public double getDriveCurrent() {
        return drive.getCurrent();
    }

    public double[] getModulePosition() {
        return new double[]{MathUtility.toFeet(MathUtility.applyGearRatio(x)), MathUtility.toFeet(MathUtility.applyGearRatio(y))};
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

    public int getCurrentRotationalPose() {
        return steercoder.getAbsolutePosition();
    }
}
