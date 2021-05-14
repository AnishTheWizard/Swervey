package frc.robot.libs.Pathing;

public class Recorder {
    

    private static Recorder recorder = null;

    public static Recorder getInstance() {
        if(recorder == null)
            recorder = new Recorder();
        return recorder;
    }

    private boolean toggle;


    public Recorder() {
        toggle = false;
    }

    public void toggleRecorder() {
        toggle = !toggle;
    }
}
