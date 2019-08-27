package fr.tetelie.practice.inventory.inventories;

import fr.tetelie.practice.inventory.Kit;
import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class QueueInventory implements Kit {

    String suffix = " §r§f(Right click)";

    @Override
    public ItemStack[] content() {
        return new ItemStack[]{
                air,
                new ItemBuilder(Material.INK_SACK, 1, (byte)14).setName("§6Leave the queue.").addEnchant(Enchantment.DURABILITY, 1).toItemStack()
        };
    }

    @Override
    public ItemStack[] armor() {
        return new ItemStack[0];
    }
}
