/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.libs.Swerve;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
 * Fix generic encoder with multithread - unpog
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
    private final double ROTATION_ANGLE;
    private final double DIAG_DIST;

    public Swerve(GenericMotor[] drives, GenericMotor[] steers, GenericEncoder[] encoders) {

        steerController = new PIDController(Constants.dtGains[0], Constants.dtGains[1], Constants.dtGains[2]);

        for(int i = 0; i < Constants.NUMBER_OF_MODULES; i++) {
            GenericMotor drive = drives[i];
            GenericMotor steer = steers[i];
            GenericEncoder steercoder = encoders[i];

            modules[i] = new SwerveModule(drive, steer, steercoder, steerController);
        }

        gyro = new Gyro(new TalonSRX(RobotMap.GYRO));

        ROTATION_ANGLE = Math.atan2((Constants.WIDTH/2), (Constants.LENGTH/2));
        ROTATION_ANGLES = new double[]{ROTATION_ANGLE, Math.PI/2 + ROTATION_ANGLE + Math.PI, ROTATION_ANGLE + Math.PI, ROTATION_ANGLE+Math.PI/2};

        DIAG_DIST = Math.sqrt(Math.pow(Constants.WIDTH, 2) + Math.pow(Constants.LENGTH, 2));

        speeds = new double[]{0.0, 0.0, 0.0, 0.0};
        thetas = new double[]{0.0, 0.0, 0.0, 0.0};
        // this.x = 0;
        // this.y = 0;
    }

    public void control(double x, double y, double rotateMag) {
        // given x, y : find driveSpeed, rotateSpeed

        if(rotateMag < Constants.CONTROLLER_DEADBAND && rotateMag > -Constants.CONTROLLER_DEADBAND) {
            rotateMag = 0;
        }
        if (x < Constants.CONTROLLER_DEADBAND && x > -Constants.CONTROLLER_DEADBAND) {
            x = 0;
        }
        if (y < Constants.CONTROLLER_DEADBAND && y > -Constants.CONTROLLER_DEADBAND) {
            y = 0;

        }

        for(int i = 0; i < Constants.NUMBER_OF_MODULES; i++) {
            double rotationX, rotationY;

            rotationX = rotateMag * Math.cos(ROTATION_ANGLES[i] + gyro.getRobotRotation());
            rotationY = rotateMag * Math.sin(ROTATION_ANGLES[i] + gyro.getRobotRotation());

            // double earlyTheta = Math.atan2(y, x);
            // earlyTheta -= gyro.getRobotRotation();
            // double hypot = Math.hypot(x, y);
            // x = hypot * Math.cos(earlyTheta);
            // y = hypot * Math.sin(earlyTheta);

            double targetVectorX = x + rotationX;
            double targetVectorY = y + rotationY;

            double mag = Math.hypot(targetVectorX, targetVectorY);
            double theta = Math.atan2(targetVectorY, targetVectorX);

            theta -= gyro.getRobotRotation(); // field centric TODO

            speeds[i] = mag * Constants.PERCENT_SPEED;
            if(!(x == 0 && y == 0 && rotateMag == 0)) {
                thetas[i] = theta;    
            }
            SmartDashboard.putNumber(i+"th speed", speeds[i]);
            SmartDashboard.putNumber(i+"th theta", thetas[i]);
            SmartDashboard.putNumber(i+"th module rotationX", rotationX);
            SmartDashboard.putNumber(i+"th module targetVecX", targetVectorX);
            SmartDashboard.putNumber(i+"th module current rotation RAD", MathUtility.toRadians(modules[i].getCurrentRotationalPose()));
            SmartDashboard.putNumber(i+"th module current rotation TICK",modules[i].getCurrentRotationalPose());
            
        }
     
        double transferAngle = gyro.getRobotRotation();

        speeds = MathUtility.normalize(speeds);
        SmartDashboard.putNumber("robo rotate",gyro.getRobotRotation());

        for(int i = 0; i < modules.length; i++) {
            modules[i].set(speeds[i], thetas[i], transferAngle);
        }

        
    }

    public double[] getPose() {
        double xSum = 0;
        double ySum = 0;
        for(int i = 0; i < Constants.NUMBER_OF_MODULES; i++) {
            double[] modPoses = modules[i].getModulePosition();
            xSum += modPoses[0];
            ySum += modPoses[1];
        }
        return new double[]{xSum/Constants.NUMBER_OF_MODULES, ySum/Constants.NUMBER_OF_MODULES, gyro.getRobotRotation()};
    }

    // public void toPose(double x, double y, double theta) {
    //     double[][] destinations = parseCoords(x, y, theta);
    // }

    // public double[][] parseCoords(double x, double y, double theta) {
    //     double[][] destinations = new double[Constants.NUMBER_OF_MODULES][2];
    //     for(int i = 0; i < Constants.NUMBER_OF_MODULES; i++) {
    //         double mod_theta = ROTATION_ANGLE + theta + (); // derive math for each consecutive module
    //         destinations[i] = {DIAG_DIST * Math.cos(mod_theta) + x, DIAG_DIST * Math.sin(mod_theta) + y};
    //     }
    //     return destinations;
    // }
    
    public double getRotation() {
        return gyro.getRobotRotation(); //current bobot rotation
    }

    public void resetPose() {
        for(SwerveModule mod : modules) {
            mod.resetPose();
        }
    }

    public void zeroGyro() {
        gyro.zeroRobotRotation();
    }
}
