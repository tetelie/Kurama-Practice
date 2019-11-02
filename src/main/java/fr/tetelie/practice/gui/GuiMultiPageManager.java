package fr.tetelie.practice.gui;

import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GuiMultiPageManager {

    private Inventory[] inventories;
    private String inventoryName;
    private ItemStack icon;
    private byte data = (byte) 0;

    public GuiMultiPageManager(List<ItemStack> itemStacks, String inventoryName, ItemStack icon, byte data) {
        this.inventoryName = inventoryName;
        this.icon = icon;
        this.data = data;
        refresh(itemStacks);
    }

    public void refresh(List<ItemStack> itemStacks)
    {
        int var0 = itemStacks.size() <= 36 ? 1 : (itemStacks.size()/36)+1;
        inventories = new Inventory[var0];
        for(int page = 1; page <= var0; page++)
        {
            int start = page == 1 ? 0 : (page-1) * 36;
            Inventory inventory = Bukkit.createInventory(null, 9*6, inventoryName);

            // add base inventory
            inventory.setItem(45, new ItemBuilder(Material.LEVER).setName("§6Previous page").addLoreLine("§8page " + (page - 1)).toItemStack());
            inventory.setItem(49, new ItemBuilder(Material.PAPER, page, (byte) 0).setName("§ePage " + page).toItemStack());
            inventory.setItem(53, new ItemBuilder(Material.ARROW).setName("§6Next page").addLoreLine("§8page " + (page + 1)).toItemStack());
            for (int i = 0; i <= 8; i++) inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, data).setName(" ").toItemStack());
            inventory.setItem(4, icon);

            if(itemStacks.size() != 0) {
                // add page content
                for (int slot = 9; slot <= 44; slot++) {
                    if (start >= itemStacks.size()) break;
                    inventory.setItem(slot, itemStacks.get(start));
                    start++;
                }
            }

            // save page
            inventories[page-1] = inventory;
        }
    }

    public void open(Player player, int page) {
        if(page <= 0 || page > inventories.length) return;
        if(page == 1) inventories[page-1].setItem(45, new ItemStack(Material.AIR));
        if(inventories.length == page) inventories[page-1].setItem(53, new ItemStack(Material.AIR));
        player.openInventory(inventories[page-1]);
    }

}
