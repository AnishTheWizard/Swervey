/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.libs.Swerve.Swerve;

/**
 * This class is in charge of linking the swerve class to the joysticks, as well as configure various components
 * High to Low ->
 * 1) Drivetrain
 * 2) Swerve
 * 3) SwerveModule
 * 4) Generic Motor and Encoder
 */

public class Drivetrain extends SubsystemBase {
  /**
   * Creates a new Drivetrain.
   */

  private static Drivetrain dt = new Drivetrain();

  public static Drivetrain getInstance() {//time to learn singleton classes
    return dt;
  }

  private Swerve swerve;
  public Drivetrain() {
    swerve = new Swerve();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    double x = RobotContainer.getInstance().getLeftX();
    double y = RobotContainer.getInstance().getLeftY();
    double rotate = Math.atan2(y, x);
    swerve.control(x, y, rotate);
  }
}
