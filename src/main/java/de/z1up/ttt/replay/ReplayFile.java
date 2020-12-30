package de.z1up.ttt.replay;

import de.z1up.ttt.util.Data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReplayFile {

    File file;
    String replayID;

    public ReplayFile() {
        replayID = Data.sbManager.getReplayId();
    }

    void loadFile() {
        File dir = new File("logs//chat");

        if(!dir.exists()) {
            dir.mkdirs();
        }

        file = new File("logs//chat");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public String getTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(calendar.getTime());
    }

    public void addLine(String text) {
        try {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(file, true));
            bw.write(text);
            bw.newLine();
            bw.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
