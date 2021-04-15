/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.libs.Swerve;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.Constants;
import frc.robot.libs.Wrappers.GenericEncoder;
import frc.robot.libs.Wrappers.GenericMotor;
import frc.robot.libs.Wrappers.Gyro;

/**
 * Add your docs here.
 */

/**
 * TODO PROBLEMS
 * How would rotation with right joy work
 * How would field centric mode work
 * How to fix the atan2's jump in
 * How to fix the encoder jump from 4095 -> 0
 * How to implement module offsets
 * 
 * Organizing PROBLEM
 * If certain modules have different encoder values, then its better to do math in the module class itself
 * 
 */


 /**
  * This class connects the 4 modules as well as computes the outer maths to come to certain motor outputs
  */
public class Swerve {
    private SwerveModule module = new SwerveModule(new GenericMotor(new TalonFX(0)), new GenericMotor(new VictorSPX(0)), new GenericEncoder(new AnalogInput(0)), new PIDController(0, 0, 0)); //test module (pose = front right)

    private final double rotationAngle = Math.atan2((Constants.WIDTH/2), (Constants.LENGTH/2)) + Math.PI/2;

    private Gyro gyro = new Gyro(0);

    public Swerve() {}

    public void control(double x, double y, double rotateMag) {
        // given x, y : find driveSpeed, rotateSpeed

        if(rotateMag < 0.05 || rotateMag > -0.05) {//rotation deadband
            rotateMag = 0;
        }

        double rotationX = rotateMag * Math.cos(rotationAngle);
        double rotationY = rotateMag * Math.sin(rotationAngle);

        double targetVectorX = x + rotationX;
        double targetVectorY = y + rotationY;

        double mag = Math.hypot(targetVectorX, targetVectorY);
        double theta = Math.atan2(targetVectorY, targetVectorX); //TODO should the wheel spin more than 90 to get to a target?

        theta -= gyro.getRobotRotation();

        module.set(mag, theta);
    }
}
