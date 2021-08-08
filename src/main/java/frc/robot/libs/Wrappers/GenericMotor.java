/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.libs.Wrappers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;
import com.revrobotics.CANSparkMax;

/**
 * Add your docs here.
 */
public class GenericMotor {

    //created for 2 current motor types
    private TalonFX falcon;
    private VictorSPX victor;
    private CANSparkMax spark;

    private double lastMotorSpeed;
    private double lastSensorPose;

    private enum MotorType {
        FALCON,
        VICTOR,
        SPARK,
    }

    private MotorType motorType;

    public GenericMotor(TalonFX motor) {
        this.falcon = motor;
        motorType = MotorType.FALCON;
        this.lastSensorPose = 0;
    }
    
    public GenericMotor(VictorSPX motor) {
        this.victor = motor;
        motorType = MotorType.VICTOR;
        this.lastSensorPose = 0;
    }

    public GenericMotor(CANSparkMax spark) {
        this.spark = spark;
        motorType = MotorType.SPARK;
        this.lastSensorPose = 0;
    }

    public void set(double speed) {
        if(speed != lastMotorSpeed) {
            switch(motorType) {
                case FALCON:
                    falcon.set(ControlMode.PercentOutput, speed);
                    break;
                case VICTOR:
                    victor.set(ControlMode.PercentOutput, speed);
                    break; 
                case SPARK:
                    spark.set(speed);
                    break;
                default:
                    break;          
            }

            lastMotorSpeed = speed;
        }
    }

    public int getSensorOffset() {
        double offset = 0;
        switch(motorType) {
            case FALCON:
                offset = falcon.getSelectedSensorPosition() - lastSensorPose;
                lastSensorPose = falcon.getSelectedSensorPosition();
            case VICTOR:
                offset = victor.getSelectedSensorPosition() - lastSensorPose;
                lastSensorPose = victor.getSelectedSensorPosition();
            case SPARK:
                offset = spark.getEncoder().getPosition() - lastSensorPose;
                lastSensorPose = spark.getEncoder().getPosition();
        }
        return (int) offset;
    }

    public double getCurrent() {
       return spark.getOutputCurrent();
    }

    public void setConfig(TalonFXConfiguration config) {
        this.falcon.configAllSettings(config);
    }
    
    public void setConfig(VictorSPXConfiguration config) {
        this.victor.configAllSettings(config);
    }
}