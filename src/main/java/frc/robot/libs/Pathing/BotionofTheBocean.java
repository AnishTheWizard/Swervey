package frc.robot.libs.Pathing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BotionofTheBocean {

    private static BotionofTheBocean pather = null;
    public static BotionofTheBocean getInstance() {
        if(pather == null) 
            pather = new BotionofTheBocean();
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
        private static int line; 

        public Executor() {
            isPlaying = false;
        }

        public static void toggleExecutor() {
            isPlaying = !isPlaying;
        }

        public static double[] read() {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader("recording.csv"));
                String line = reader.readLine();
                while(line != null) {
                    String[] data = line.split(", ");
                    

                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return new double[]{0.0, 0.0};
        }


    }
}
