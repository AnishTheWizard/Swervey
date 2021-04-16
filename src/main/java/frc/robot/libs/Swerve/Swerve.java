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
import frc.robot.RobotMap;
import frc.robot.libs.Utility.MathUtility;
import frc.robot.libs.Wrappers.GenericEncoder;
import frc.robot.libs.Wrappers.GenericMotor;
import frc.robot.libs.Wrappers.Gyro;

/**
 * Add your docs here.
 * @author Anish Chandra
 * @
 */

/**
 * TODO PROBLEMS
 * How would rotation with right joy work - uwu
 * How would field centric mode work - uwu
 * How to fix the atan2's jump in
 * How to fix the encoder jump from 4095 -> 0
 * How to implement module offsets
 * How should the wheel move when theta > 90
 * 
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

    private final double ROTATION_ANGLE;

    private double[] speeds;
    private double[] thetas;

    

    public Swerve() {

        for(int i = 0; i < Constants.NUMBER_OF_MODULES; i++) {
            GenericMotor drive = new GenericMotor(new TalonFX(RobotMap.MODULES_DRIVE[i]));
            GenericMotor steer = new GenericMotor(new VictorSPX(RobotMap.MODULES_STEER[i]));
            GenericEncoder steercoder = new GenericEncoder(new AnalogInput(RobotMap.ENCODERS_STEER[i]));

            modules[i] = new SwerveModule(drive, steer, steercoder);
        }

        gyro = new Gyro(RobotMap.GYRO);
        steerController = new PIDController(Constants.dtGains[0], Constants.dtGains[1], Constants.dtGains[2]);
        ROTATION_ANGLE = Math.atan2((Constants.WIDTH/2), (Constants.LENGTH/2));
    }

    public void control(double x, double y, double rotateMag) {
        // given x, y : find driveSpeed, rotateSpeed

        if(rotateMag < 0.05 || rotateMag > -0.05) {//rotation deadband TODO could move to DT
            rotateMag = 0;
        }

        for(int i = 0; i < Constants.NUMBER_OF_MODULES; i++) {
            double rotationAngle = ROTATION_ANGLE;
            double rotationX, rotationY;

            if(i % 2 == 0) { //if module number is even TODO verify this
                rotationAngle += Math.PI;
                rotationAngle += Math.PI;
            }

            rotationX = rotateMag * Math.cos(rotationAngle);
            rotationY = rotateMag * Math.sin(rotationAngle); //TODO verify that this applies in all directions of rotation

            double targetVectorX = x + rotationX;
            double targetVectorY = y + rotationY;

            double mag = Math.hypot(targetVectorX, targetVectorY);
            double theta = Math.atan2(targetVectorY, targetVectorX); //TODO should the wheel spin more than 90 to get to a target?

            theta -= gyro.getRobotRotation();

            speeds[i] = mag;
            thetas[i] = theta;
        }

        speeds = MathUtility.normalize(speeds);
        thetas = MathUtility.normalize(thetas);

        for(int i = 0; i < speeds.length; i++) {
            double rotate = steerController.calculate(modules[i].getRotation(), thetas[i]);
            modules[i].set(speeds[i], rotate);
        }


    }
}
