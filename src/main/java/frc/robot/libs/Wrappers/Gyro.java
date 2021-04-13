/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.libs.Wrappers;

import com.ctre.phoenix.sensors.PigeonIMU;

/**
 * Add your docs here.
 */
public class Gyro {

    private PigeonIMU gyro;

    public Gyro(int port) {
        gyro = new PigeonIMU(port);
    }

    public double getRobotRotation() {
        double[] ypr = new double[3];
        gyro.getYawPitchRoll(ypr);
        return ypr[0];
    }

    public void zeroRobotRotation() {
        gyro.setYaw(getRobotRotation());
    }
}
