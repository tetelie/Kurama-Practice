package fr.tetelie.practice.gui.guis;

import fr.tetelie.practice.gui.Gui;
import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class PanelGui implements Gui {

    @Override
    public Inventory inventory() {
        Inventory inv = Bukkit.createInventory(null, 9*3, "§6Panel");
        inv.setItem(10, new ItemBuilder(Material.REDSTONE_COMPARATOR).setName("§eSettings").addLoreLine("§7Manage your settings").toItemStack());
        inv.setItem(16, new ItemBuilder(Material.BREWING_STAND_ITEM).setName("§eStatistics").addLoreLine("§7Look at your statistics").toItemStack());
        inv.setItem(13, new ItemBuilder(Material.EMERALD).setName("§eLeaderboard").addLoreLine("§7Check out the best players on the server").toItemStack());
        return inv;
    }
}
