/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.libs.Wrappers;

import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.AnalogInput;
import frc.robot.Constants;

/**
 * Add your docs here.
 */
public class GenericEncoder {
    private AnalogInput analogEncoder;
    private CANCoder canCoder;

    //a = analog, c = cancoder
    private enum EncoderType {
        ANALOG,
        CAN
    }

    private EncoderType encoderType;


    public GenericEncoder(AnalogInput analogEncoder) {
        this.analogEncoder = analogEncoder;
        encoderType = EncoderType.ANALOG;
    }

    public GenericEncoder(CANCoder canCoder) {
        this.canCoder = canCoder; //default [0, 360)
        encoderType = EncoderType.CAN;
    }

    public int getAbsolutePosition() {// [0, 4095]
        switch(encoderType) {
            case ANALOG:
                return analogEncoder.getValue();
            case CAN:
                return (int)(canCoder.getAbsolutePosition()/360) * 4095;
            default:
                return -1;
        }
    }

    public int getError(int target) {
        int currentPose = getAbsolutePosition();
        int err = target - currentPose;

        if(Math.abs(err) > Constants.OVERFLOW_THRESHOLD) {//fix encoder jumps from 4095 -> 0, and 0 -> 4095
            if(err < 0) {
                target+=Constants.TICKS_PER_ROTATION;
            }
            else if(err > 0){
                currentPose += Constants.TICKS_PER_ROTATION;
            }
            err = target - currentPose;
        }
        return err;
    }

    public int toTicks(double radians) {
        if(radians < 0) {
            radians += Math.PI * 2; //atan returns -pi -> pi, if its negative, hard to map to encoder 0 -> 4096
        }
        return (int)((radians * Constants.TICKS_PER_ROTATION)/(Math.PI*2));
    }

    public double toRadians(int ticks) {
        return (ticks * Math.PI * 2)/Constants.TICKS_PER_ROTATION;
    }
}
