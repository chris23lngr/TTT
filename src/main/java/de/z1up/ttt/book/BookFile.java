package de.z1up.ttt.book;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import de.z1up.ttt.TTT;
import de.z1up.ttt.interfaces.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

public class BookFile implements Configuration {

    private final String FILE_NAME = "rulebook.yml";
    private final String ATTRIBUTE_TITLE = "Title";
    private final String ATTRIBUTE_AUTHOR = "Autor";
    private final String ATTRIBUTE_PAGES = "Seiten";

    private RuleBook ruleBook;

    public BookFile(RuleBook ruleBook) {
        this.ruleBook = ruleBook;

        loadFile();
        loadConfig();
        setDefaults();
    }

    @Override
    public void loadFile() {

    }

    @Override
    public void loadConfig() {

    }

    @Override
    public void save() {

    }

    @Override
    public void setDefaults() {

    }

    @Override
    public File getFile() {
        return null;
    }

    @Override
    public HashMap<String, Object> read() {
        return null;
    }
}
