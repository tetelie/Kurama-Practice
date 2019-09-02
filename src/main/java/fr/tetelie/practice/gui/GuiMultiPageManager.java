package fr.tetelie.practice.gui;

import fr.tetelie.practice.fight.FightType;
import fr.tetelie.practice.match.MatchManager;
import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.omg.CORBA.MARSHAL;

import java.util.ArrayList;
import java.util.List;

public class GuiMultiPageManager {

    private List<ItemStack> itemStacks = new ArrayList<>();
    private String inventoryName;
    private ItemStack icon;
    private byte data = (byte) 0;

    public GuiMultiPageManager(List<ItemStack> itemStacks, String inventoryName, ItemStack icon, byte data) {
        this.itemStacks = itemStacks;
        this.inventoryName = inventoryName;
        this.icon = icon;
        this.data = data;
    }

    public void open(Player player, int page) {
        int start = page == 1 ? 0 : (page-1) * 36;
        if(page != 1 && (start > itemStacks.size()-1 || start <= 0)) return;
        Inventory inventory = Bukkit.createInventory(null, 9 * 6, inventoryName);
        inventory.setItem(45, new ItemBuilder(Material.LEVER).setName("§6Previous page").addLoreLine("§8page " + (page - 1)).toItemStack());
        inventory.setItem(49, new ItemBuilder(Material.PAPER, page, (byte) 0).setName("§ePage " + page).toItemStack());
        inventory.setItem(53, new ItemBuilder(Material.ARROW).setName("§6Next page").addLoreLine("§8page " + (page + 1)).toItemStack());
        for (int i = 0; i <= 8; i++)
            inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, data).setName(" ").toItemStack());
        inventory.setItem(4, icon);

        for (int slot = 9; slot <= 44; slot++) {
            if (start >= itemStacks.size()) break;
            inventory.setItem(slot, itemStacks.get(start));
            start++;
        }
        player.openInventory(inventory);
    }

    public void setItemStacks(List<ItemStack> itemStacks) {
        this.itemStacks = itemStacks;
    }
}
