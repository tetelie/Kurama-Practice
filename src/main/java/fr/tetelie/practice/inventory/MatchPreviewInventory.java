package fr.tetelie.practice.inventory;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MatchPreviewInventory {

    private ItemStack[] content;
    private ItemStack[] armor;
    private double life;
    private double food;
    private String ownerName;

    public MatchPreviewInventory(final Player player)
    {
        this.content = player.getInventory().getContents();
        this.armor = player.getInventory().getArmorContents();
        this.life = player.getHealth();
        this.food = player.getFoodLevel();
        this.ownerName = player.getName();

        Practice.getInstance().matchPreviewInventoryMap.put(player.getUniqueId(), this);
    }

    public Inventory getPreviewInventory()
    {
        Inventory preview = Bukkit.createInventory(null, 9*5, "§6Preview of §e"+ownerName);
        preview.setContents(content);
        preview.setItem(36, armor[3]);
        preview.setItem(37, armor[2]);
        preview.setItem(38, armor[1]);
        preview.setItem(39, armor[0]);
        preview.setItem(43, new ItemBuilder(Material.BREWING_STAND_ITEM).setName("§6Remaining life(s)").addLoreLine("§e"+life).toItemStack());
        preview.setItem(44, new ItemBuilder(Material.MELON).setName("§6Remaining food(s)").addLoreLine("§e"+food).toItemStack());
        return preview;
    }

    public void destroy()
    {
        if(Practice.getInstance().matchPreviewInventoryMap.containsKey(this)) Practice.getInstance().matchPreviewInventoryMap.remove(this);
    }

}
