package fr.tetelie.practice.historic;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

public @Getter @Setter class HistoricManager {

    private String title;
    private String author;
    String[] pages;

    public HistoricManager(String title, String author, String... pages)
    {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public HistoricManager(String title, String author) { this(title, author, new String[0]); }

    public void give(Player player, int slot)
    {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        ItemMeta itemMeta = book.getItemMeta();
        BookMeta bookMeta = (BookMeta)itemMeta;
        bookMeta.setTitle(title);
        bookMeta.setAuthor(author);
        if(pages.length != 0) bookMeta.setPages(pages);
        book.setItemMeta(bookMeta);
    }

}
