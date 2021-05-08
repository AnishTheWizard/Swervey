/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.libs.Swerve;

import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.libs.Utils.MathUtility;
import frc.robot.libs.Wrappers.GenericEncoder;
import frc.robot.libs.Wrappers.GenericMotor;
import frc.robot.libs.Wrappers.Gyro;

/**
 * Add your docs here.
 * @author AnishTheWizard
 */

/**
 * TODO PROBLEMS
 * How would rotation with right joy work - uwu
 * How would field centric mode work - uwu
 * How to fix the atan2's jump in - uwu
 * How to fix the encoder jump from 4095 -> 0 - uwu
 * How to implement module offsets - uwu
 * How should the wheel move when theta > 90 - uwu
 * Fix generic encoder with multithread
 * Write drive train implementation of Swerve class - uwu
 * 
 * Organizing PROBLEM
 * If certain modules have different encoder values, then its better to do math in the module class itself
 * 
 */


 /**
  * This class connects the 4 modules as well as computes the outer maths to come to certain motor outputs
  */
public class Swerve {
    private SwerveModule[] modules = new SwerveModule[Constants.NUMBER_OF_MODULES];

    private Gyro gyro;

    private PIDController steerController;


    private double[] speeds;
    private double[] thetas;

    private final double[] ROTATION_ANGLES;

    

    public Swerve(GenericMotor[] drives, GenericMotor[] steers, GenericEncoder[] encoders) {

        steerController = new PIDController(Constants.dtGains[0], Constants.dtGains[1], Constants.dtGains[2]);

        for(int i = 0; i < Constants.NUMBER_OF_MODULES; i++) {
            GenericMotor drive = drives[i];
            GenericMotor steer = steers[i];
            GenericEncoder steercoder = encoders[i];

            modules[i] = new SwerveModule(drive, steer, steercoder, steerController);
        }

        gyro = new Gyro(RobotMap.GYRO);
        
        double ROTATION_ANGLE = Math.atan2((Constants.WIDTH/2), (Constants.LENGTH/2));
        ROTATION_ANGLES = new double[]{ROTATION_ANGLE + Math.PI, Math.PI + ROTATION_ANGLE, ROTATION_ANGLE+Math.PI/2 + Math.PI, ROTATION_ANGLE};
    }

    public void control(double x, double y, double rotateMag) {
        // given x, y : find driveSpeed, rotateSpeed

        if(rotateMag < 0.05 || rotateMag > -0.05) {
            rotateMag = 0;
        }

        for(int i = 0; i < Constants.NUMBER_OF_MODULES; i++) {
            double rotationX, rotationY;

            rotationX = rotateMag * Math.cos(ROTATION_ANGLES[i]);
            rotationY = rotateMag * Math.sin(ROTATION_ANGLES[i]);

            double targetVectorX = x + rotationX;
            double targetVectorY = y + rotationY;

            double mag = Math.hypot(targetVectorX, targetVectorY);
            double theta = Math.atan2(targetVectorY, targetVectorX);

            theta -= gyro.getRobotRotation(); // field centric

            speeds[i] = mag;
            thetas[i] = theta;
        }

        speeds = MathUtility.normalize(speeds);
        thetas = MathUtility.normalize(thetas);

        for(int i = 0; i < modules.length; i++) {
            modules[i].set(speeds[i], thetas[i]);
        }
    }
}
