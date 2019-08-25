package fr.tetelie.practice.gui.guis;

import fr.tetelie.practice.gui.Gui;
import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class FightGui implements Gui {

    @Override
    public Inventory inventory() {
        Inventory inv = Bukkit.createInventory(null, 9, "§6Fight");
        inv.setItem(2, new ItemBuilder(Material.FEATHER).setName("§eNormal").toItemStack());
        inv.setItem(6, new ItemBuilder(Material.EXP_BOTTLE).setName("§6Competitive").toItemStack());
        return inv;
    }
}
