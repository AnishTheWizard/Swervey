/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.libs.Swerve;

import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.libs.Wrappers.*;

/**
 * Add your docs here.
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

    public SwerveModule(GenericMotor drive, GenericMotor steer, GenericEncoder steercoder, PIDController controller) {
        this.drive = drive;
        this.steer = steer;
        this.steercoder = steercoder;
        this.steerController = controller; //by storing the controller within the 
    }

    public void set(double translate, double theta) {
        drive.set(translate);
        double rotate = steerController.calculate(getRotation(), theta);
        steer.set(rotate);
    }

    public int getRotation() {
        return steercoder.getAbsolutePosition();
    }
}
