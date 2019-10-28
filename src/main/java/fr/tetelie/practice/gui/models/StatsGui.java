package fr.tetelie.practice.gui.models;

import fr.tetelie.practice.gui.Gui;
import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class StatsGui implements Gui {

    @Override
    public Inventory inventory() {
        Inventory inv = Bukkit.createInventory(null, 9*3, "§6§lStatistics");
        inv.setItem(4, new ItemBuilder(Material.SKULL_ITEM, 1, (byte)3).setName("§6Profile").addLoreLine("a").toItemStack());
        inv.setItem(10, new ItemBuilder(Material.FEATHER).setName("§6Normal Fight").addLoreLine("a").toItemStack());
        inv.setItem(16, new ItemBuilder(Material.EXP_BOTTLE).setName("§6Competitive Fight").addLoreLine("a").toItemStack());
        inv.setItem(22, new ItemBuilder(Material.CAULDRON_ITEM).setName("§eElos").addLoreLine("a").toItemStack());
        return inv;
    }

}
