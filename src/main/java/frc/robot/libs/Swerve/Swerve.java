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
import frc.robot.libs.Wrappers.GenericEncoder;
import frc.robot.libs.Wrappers.GenericMotor;

/**
 * Add your docs here.
 */
public class Swerve {
    SwerveModule module = new SwerveModule(new GenericMotor(new TalonFX(0)), new GenericMotor(new VictorSPX(0)), new GenericEncoder(new AnalogInput(0))); //test module
    PIDController sController = new PIDController(0, 0, 0);

    public Swerve() {}

    public void control(double x, double y, double rotate) {
        // given x, y : find driveSpeed, rotateSpeed

        //first find magnitude of drive
        double mag = Math.hypot(x, y);

        //then find the speed needed to rotate
        double rotationSpeed = sController.calculate(module.getRotation(), rotate);

        module.set(mag, rotationSpeed);
    }
}
