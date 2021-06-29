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
    private final int moduleNum;

    private enum EncoderType {
        ANALOG,
        CAN
    }

    private EncoderType encoderType;

    public GenericEncoder(AnalogInput analogEncoder, int moduleNum) {
        this.analogEncoder = analogEncoder;
        encoderType = EncoderType.ANALOG;
        this.moduleNum = moduleNum;
    }

    public GenericEncoder(CANCoder canCoder, int moduleNum) {
        this.canCoder = canCoder; //default [0, 360)
        encoderType = EncoderType.CAN;
        this.moduleNum = moduleNum;
    }

    public int getAbsolutePosition() {// [0, 4095]
        switch(encoderType) {
            case ANALOG:
                return analogEncoder.getValue() - Constants.MODULE_OFFSETS[moduleNum];
            case CAN:
                return ((int)(canCoder.getAbsolutePosition()/360) * 4095) + Constants.MODULE_OFFSETS[moduleNum];
            default:
                return -1;
        }
    }
}
