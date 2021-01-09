package de.z1up.ttt.interfaces;

import java.io.File;
import java.util.HashMap;

public interface Configuration {

    void loadFile();

    void loadConfig();

    void save();

    void setDefaults();

    File getFile();

    HashMap<String, Object> read();

}
