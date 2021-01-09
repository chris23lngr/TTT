package de.z1up.ttt.book;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import de.z1up.ttt.TTT;
import de.z1up.ttt.interfaces.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.error.YAMLException;

public class BookFile implements Configuration {

    private final String FILE_NAME = "rulebook.yml";
    private final String ATTRIBUTE_TITLE = "Title";
    private final String ATTRIBUTE_AUTHOR = "Autor";
    private final String ATTRIBUTE_PAGES = "Seiten";

    private RuleBook ruleBook;

    private File file;
    private YamlConfiguration configuration;

    public BookFile(RuleBook ruleBook) {
        this.ruleBook = ruleBook;

        loadFile();
        loadConfig();
        setDefaults();
    }

    @Override
    public void loadFile() {

        if(!TTT.getInstance().getDataFolder().exists()) {
            TTT.getInstance().getDataFolder().mkdirs();
        }

        file = new File(TTT.getInstance().getDataFolder(), FILE_NAME);

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

    }

    @Override
    public void loadConfig() {
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public void save() {
        try {
            configuration.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void setDefaults() {

        configuration.options().copyHeader(true);
        configuration.options().header("Please enter your book data below.");

        configuration.options().copyDefaults(true);
        configuration.addDefault(ATTRIBUTE_TITLE, "§6Spielregeln");
        configuration.addDefault(ATTRIBUTE_AUTHOR, "Renixinside");
        configuration.addDefault(ATTRIBUTE_PAGES, Arrays.asList("&eSeite 1", "&aSeite 2", "&bSeite 3"));

        save();

    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public HashMap<String, Object> read() {
        ruleBook.setTitle(configuration.getString(ATTRIBUTE_TITLE));
        ruleBook.setAuthor(configuration.getString(ATTRIBUTE_AUTHOR));
        ruleBook.setPages(configuration.getStringList(ATTRIBUTE_PAGES));
        return null;
    }
}
