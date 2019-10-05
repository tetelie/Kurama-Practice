package fr.tetelie.practice.gui.models;

import fr.tetelie.practice.gui.Gui;
import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class QuestGui implements Gui {

    @Override
    public Inventory inventory() {
        Inventory inv = Bukkit.createInventory(null, 9*6, "§a§lQuests");
        for(int i = 0; i <= 8; i++) {
            inv.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 5).setName(" ").toItemStack());
        }
        inv.setItem(4, new ItemBuilder(Material.EYE_OF_ENDER).setName("§a§lQuests Gui").toItemStack());
        return inv;
    }

}
