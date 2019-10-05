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
        inv.setItem(0, new ItemBuilder(Material.FERMENTED_SPIDER_EYE).setName("§eSettings").addLoreLine("§7Manage your settings").toItemStack());
        inv.setItem(10, new ItemBuilder(Material.BOOK).setName("§eEditor").addLoreLine("§7Customize your kits").toItemStack());
        inv.setItem(13, new ItemBuilder(Material.LEASH).setName("§eTeam").addLoreLine("§7Create a party").toItemStack());
        inv.setItem(8, new ItemBuilder(Material.BREWING_STAND_ITEM).setName("§eStatistics").addLoreLine("§7Look at your statistics").toItemStack());
        inv.setItem(16, new ItemBuilder(Material.EMERALD).setName("§eQuests").addLoreLine("§7Access to the quest GUI").toItemStack());
        inv.setItem(3, new ItemBuilder(Material.FIREBALL).setName("§eClassement").addLoreLine("§7Check out the best players on the server").toItemStack());
        inv.setItem(5, new ItemBuilder(Material.DIAMOND).setName("Pass of fight").addLoreLine("§7Get rewards by playing on the server").toItemStack());
        return inv;
    }
}
