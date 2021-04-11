/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.libs.Wrappers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * Add your docs here.
 */
public class GenericMotor {

    //created for 2 current motor types
    private TalonFX talonF;
    private VictorSPX victor;

    // tf = TalonFX, v = VictorSPX, s = SparkMAX, ts = TalonSRX
    private String motorType; // i think it was an enum, but no clue how to use those

    public GenericMotor(TalonFX motor) {
        this.talonF = motor;
        motorType = "tf";
    }
    
    public GenericMotor(VictorSPX motor) {
        this.victor = motor;
        motorType = "v";
    }

    public void set(double speed) {
        switch(motorType) {
            case "tf":
                talonF.set(ControlMode.PercentOutput, speed); // TODO don't have to constantly spew numbers at the motor
                break;
            case "v":
                victor.set(ControlMode.PercentOutput, speed);
                break;            
        }
    }


}
