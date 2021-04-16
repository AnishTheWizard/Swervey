/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.libs.Wrappers;

import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Add your docs here.
 */
public class GenericEncoder {
    private AnalogInput analogEncoder;
    private CANCoder canCoder;

    //a = analog, c = cancoder
    private String encoderType;


    public GenericEncoder(AnalogInput analogEncoder) {
        this.analogEncoder = analogEncoder;
        encoderType = "a";
    }

    public GenericEncoder(CANCoder canCoder) {
        this.canCoder = canCoder; //default [0, 360)
        encoderType = "c";
    }

    public int getAbsolutePosition() {// [0, 4095]
        return encoderType == "a" ? analogEncoder.getValue() : (int)(canCoder.getAbsolutePosition()/360) * 4095;
    }
}
