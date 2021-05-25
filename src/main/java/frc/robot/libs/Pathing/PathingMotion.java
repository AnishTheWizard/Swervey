package frc.robot.libs.Pathing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PathingMotion {

    private static PathingMotion pather = null;
    public static PathingMotion getInstance() {
        if(pather == null) 
            pather = new PathingMotion();
        return pather;
    }

    public static class Recorder {

        private static boolean isRecording;
        private static ArrayList<String> exportArr;


        public Recorder() {
            isRecording = false;
        }

        public static void toggleRecorder() {
            if(isRecording) {
                isRecording = false;
                export();
            }
        }

        public static void recordPose(double x, double y, double theta) {
            if(isRecording) {
                String appendage = x + ", " + y + ", " + theta;
                exportArr.add(appendage);
            }
        }
        
        public static void export() {
            BufferedWriter export;
            try {
                export = new BufferedWriter(new FileWriter("recording.csv"));
                for(String str : exportArr) {
                    export.write(str);
                    export.newLine();
                }
                export.flush();
                export.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }

        
    }

    public static class Executor {
        private static boolean isPlaying;

        public Executor() {
            isPlaying = false;
        }

        public static void toggleExecutor() {
            isPlaying = !isPlaying;
        }
    }
}
