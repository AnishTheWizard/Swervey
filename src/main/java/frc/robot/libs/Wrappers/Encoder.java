package frc.robot.libs.Wrappers;

import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.AnalogInput;
import frc.robot.Constants;

public class Encoder {

    private int moduleNum;
    private int moduleOffset;

    private enum EncoderType{
        ANALOG,
        CANCODER
    }

    private EncoderType encoderType;

    private AnalogInput analogInput;
    private CANCoder canCoder;

    private Object arbitraryObject = new Object();

    private int overflows;
    private int lastSensorPose;
    

    public Encoder(AnalogInput anal, int moduleNum, int moduleOffset) {
        this.analogInput = anal;
        this.moduleNum = moduleNum;
        this.moduleOffset = moduleOffset;
        encoderType = EncoderType.ANALOG;
        overflows = 0;
        lastSensorPose = 0;
    }

    public Encoder(CANCoder canCoder, int moduleNum, int moduleOffset) {
        this.canCoder = canCoder;
        this.moduleNum = moduleNum;
        this.moduleOffset = moduleOffset;
        encoderType = EncoderType.CANCODER;
        overflows = 0;
        lastSensorPose = 0;
    }


    public void trackOverflows() {
        synchronized(arbitraryObject) {
            int err;
            switch(encoderType) {
                case ANALOG:
                    err = analogInput.getValue() - lastSensorPose;
                    break;
                case CANCODER:
                    err = (int)(canCoder.getAbsolutePosition()/360) * 4095;
                    break;
                default:
                    err = 0;
                    break;  
            }
            if(Math.abs(err) > Constants.OVERFLOW_THRESHOLD) {//fix encoder jumps from 4095 -> 0, and 0 -> 4095
                if(err < 0) {
                    overflows++;
                }
                else if(err > 0){
                    overflows--;
                }
            }
        }
    }


    public int getAbsolutePosition() {
        synchronized(arbitraryObject) {
            int position;
            switch(encoderType) {
                case ANALOG:
                    position = analogInput.getValue() - moduleOffset;
                    break;
                case CANCODER:
                    position = (int)(canCoder.getAbsolutePosition()/360) * 4095;
                    break;
                default:
                    position = 0;
            }

            return position;
        }
    }

    public int getContinousPosition() {
        synchronized(arbitraryObject) {
            return overflows * Constants.TICKS_PER_ROTATION + getAbsolutePosition();
        }
    }
}
