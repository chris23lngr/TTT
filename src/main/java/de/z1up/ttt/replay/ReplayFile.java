package de.z1up.ttt.replay;

import de.z1up.ttt.TTT;
import de.z1up.ttt.core.Core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReplayFile {

    private File file;
    private String replayID;
    private String replayId;

    public ReplayFile() {
        replayID = this.createReplayID(5);
    }

    void loadFile() {
        File dir = new File("logs//chat");

        if(!dir.exists()) {
            dir.mkdirs();
        }

        file = new File(replayID + ".yml");

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

    public String createReplayID(int length) {
        SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder(length);

        String rnd = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijlmnopqrstuvwxyz";

        for(int i = 0; i < length; ++i) {
            sb.append(rnd.charAt(random.nextInt(rnd.length())));
        }

        return sb.toString();
    }

    public String getReplayId() {
        return replayId;
    }
}
