package de.z1up.ttt.book;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import de.z1up.ttt.TTT;
import org.bukkit.configuration.file.YamlConfiguration;

public class BookFile {

    private final String FILE_NAME = "rulebook.yml";
    private final String ATTRIBUTE_TITLE = "Title";
    private final String ATTRIBUTE_AUTHOR = "Autor";
    private final String ATTRIBUTE_PAGES = "Seiten";

    RuleBook ruleBook;
    YamlConfiguration configuration;
    File file;

    public BookFile(RuleBook ruleBook) {
        this.ruleBook = ruleBook;

        loadFile();
        loadConfig();
        setDefaultData();
    }

    public void setDefaultData() {

        if(file == null) loadFile();
        if(configuration == null) loadConfig();


        configuration.options().copyDefaults(true);

        configuration.addDefault(ATTRIBUTE_TITLE, "§6Spielregeln");
        configuration.addDefault(ATTRIBUTE_AUTHOR, "Renixinside");
        configuration.addDefault(ATTRIBUTE_PAGES, Arrays.asList("Test123", "Test123"));

        save();
    }

    public void readData() {
        ruleBook.setTitle(configuration.getString(ATTRIBUTE_TITLE));
        ruleBook.setAuthor(configuration.getString(ATTRIBUTE_AUTHOR));
        ruleBook.setSeiten(configuration.getStringList(ATTRIBUTE_PAGES));
    }

    void loadFile() {

        if(!TTT.getInstance().getDataFolder().exists()) {
            TTT.getInstance().getDataFolder().mkdirs();
        }

        File file = new File(TTT.getInstance().getDataFolder(), FILE_NAME);

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.file = file;

    }

    void loadConfig() {
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getConfig() {
        return configuration;
    }

    void save() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
