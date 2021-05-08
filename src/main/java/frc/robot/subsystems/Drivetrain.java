/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.RobotMap;
import frc.robot.libs.Swerve.Swerve;
import frc.robot.libs.Wrappers.GenericEncoder;
import frc.robot.libs.Wrappers.GenericMotor;

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

  private static Drivetrain dt = null;

  public static Drivetrain getInstance() {
    if(dt == null)
      dt = new Drivetrain();
    return dt;
  }

  private Swerve swerve;

  private TalonFX[] falcons = new TalonFX[Constants.NUMBER_OF_MODULES];
  private VictorSPX[] victors = new VictorSPX[Constants.NUMBER_OF_MODULES];

  private AnalogInput[] analogInputs = new AnalogInput[Constants.NUMBER_OF_MODULES];

  private GenericMotor[] drives = new GenericMotor[Constants.NUMBER_OF_MODULES];
  private GenericMotor[] steers = new GenericMotor[Constants.NUMBER_OF_MODULES];

  private GenericEncoder[] encoders = new GenericEncoder[Constants.NUMBER_OF_MODULES];

  public Drivetrain() {
    // Instantiate each component at the lowest level
    for(int i = 0; i < Constants.NUMBER_OF_MODULES; i++) {
      falcons[i] = new TalonFX(RobotMap.MODULES_DRIVE[i]);
      victors[i] = new VictorSPX(RobotMap.MODULES_STEER[i]);
      analogInputs[i] = new AnalogInput(RobotMap.ENCODERS_STEER[i]);
    }
    
    //config components here


    //apply generics
    for(int i = 0; i < Constants.NUMBER_OF_MODULES; i++) {
      drives[i] = new GenericMotor(falcons[i]);
      steers[i] = new GenericMotor(victors[i]);
      encoders[i] = new GenericEncoder(analogInputs[i], i);
    }
    swerve = new Swerve(drives, steers, encoders);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    double x = RobotContainer.getInstance().getLeftX();
    double y = RobotContainer.getInstance().getLeftY();
    double rotate = RobotContainer.getInstance().getRightX();
    swerve.control(-x, y, -rotate); //right x is negative
  }
}
