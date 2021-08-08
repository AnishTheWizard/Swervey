package frc.robot.libs.Wrappers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.AnalogInput;
import frc.robot.Constants;

public class GenericEncoder {

    private int moduleOffset;

    private enum EncoderType{
        ANALOG,
        CANCODER
    }

    private ScheduledExecutorService service;

    private EncoderType encoderType;

    private AnalogInput analogInput;
    private CANCoder canCoder;

    private Object arbitraryObject = new Object();

    private int overflows;
    private int lastSensorPose;
    

    public GenericEncoder(AnalogInput anal, int moduleOffset) {
        this.analogInput = anal;
        this.moduleOffset = moduleOffset;
        encoderType = EncoderType.ANALOG;
        overflows = 0;
        lastSensorPose = 0;
        service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(this::trackOverflows, 0, 10, TimeUnit.MILLISECONDS);
    }

    public GenericEncoder(CANCoder canCoder, int moduleOffset) {
        this.canCoder = canCoder;
        this.moduleOffset = moduleOffset;
        encoderType = EncoderType.CANCODER;
        overflows = 0;
        lastSensorPose = 0;
        service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(this::trackOverflows, 0, 10, TimeUnit.MILLISECONDS);
    }


    public void trackOverflows() {
        synchronized(arbitraryObject) {
            int err;
            switch(encoderType) {
                case ANALOG:
                    err = analogInput.getValue() - lastSensorPose;
                    lastSensorPose = analogInput.getValue();
                    break;
                case CANCODER:
                    err = (int)(canCoder.getAbsolutePosition()/360) * Constants.TICKS_PER_ROTATION - lastSensorPose;
                    lastSensorPose = (int)(canCoder.getAbsolutePosition()/360) * Constants.TICKS_PER_ROTATION;
                    break;
                default:
                    err = 0;
                    break;  
            }
            if(Math.abs(err) > Constants.OVERFLOW_THRESHOLD) {//detect encoder jumps from 4095 -> 0 and vice versa, then add overflow calculations
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
                    position = (int)(canCoder.getAbsolutePosition()/360) * 4095 - moduleOffset;
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
