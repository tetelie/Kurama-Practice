package fr.tetelie.practice.uti;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Kit {

    public ItemStack[] content();

    public ItemStack[] armor();

    public static ItemStack air = new ItemStack(Material.AIR);

}
