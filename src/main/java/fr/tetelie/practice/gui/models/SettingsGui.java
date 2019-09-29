package fr.tetelie.practice.gui.models;

import fr.tetelie.practice.gui.Gui;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SettingsGui implements Gui {

    @Override
    public Inventory inventory() {
        Inventory inv = Bukkit.createInventory(null, 9*3, "§6§lSettings");
        inv.setItem(3, new ItemBuilder(Material.WATCH).setName("§6§lTime").addLoreLine("§7soon..").toItemStack());
        inv.setItem(4, new ItemBuilder(Material.BOOK).setName("§6§lPrivate message").addLoreLine("§7soon..").toItemStack());
        inv.setItem(5, new ItemBuilder(Material.RAW_FISH, 1, (byte)2).setName("§6§lBelow name").addLoreLine("§7soon..").toItemStack());

        inv.setItem(12, new ItemBuilder(Material.PAINTING).setName("§6§lScoreboard").addLoreLine("§7soon..").toItemStack());
        inv.setItem(13, new ItemBuilder(Material.MELON).setName("§6§lDuel request").addLoreLine("§7soon..").toItemStack());
        inv.setItem(14, new ItemBuilder(Material.FIRE).setName("§6§lRematch item").addLoreLine("§7soon..").toItemStack());

        inv.setItem(21, new ItemBuilder(Material.SIGN).setName("§6§lOpponent tag color").addLoreLine("§7soon..").toItemStack());
        inv.setItem(22, new ItemBuilder(Material.EXP_BOTTLE).setName("§6§lExp bar").addLoreLine("§7soon..").toItemStack());
        inv.setItem(23, new ItemBuilder(Material.SKULL_ITEM, 1, (byte)1).setName("§6§lKill effect").addLoreLine("§7soon..").toItemStack());
        return inv;
    }

}
