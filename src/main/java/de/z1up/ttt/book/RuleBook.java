package de.z1up.ttt.book;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RuleBook {

    private String title;
    private String author;
    private List<String> seiten;

    BookFile bookFile;

    public RuleBook() {
        bookFile = new BookFile(this);
        bookFile.readData();
    }

    public ItemStack getBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta)book.getItemMeta();
        meta.setTitle("§6" + title);
        meta.setAuthor(author);
        List<String> red = new ArrayList();
        Iterator var3 = seiten.iterator();

        while(var3.hasNext()) {
            String line = (String)var3.next();
            red.add(ChatColor.translateAlternateColorCodes('&', line));
        }

        meta.setPages(red);
        book.setItemMeta(meta);
        return book;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSeiten(List<String> seiten) {
        this.seiten = seiten;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
