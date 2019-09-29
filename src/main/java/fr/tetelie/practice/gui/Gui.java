package fr.tetelie.practice.gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public interface Gui {

    Inventory inventory();

    public static Inventory clone(Gui gui)
    {
        Inventory clone = Bukkit.createInventory(null, gui.inventory().getSize(), gui.inventory().getTitle());
        clone.setContents(gui.inventory().getContents());
        return clone;
    }

}
