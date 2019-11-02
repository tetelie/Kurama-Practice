package fr.tetelie.practice.inventory.inventories;

import fr.tetelie.practice.inventory.Kit;
import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class SpectateInventory implements Kit {

    @Override
    public ItemStack[] content() {
        return new ItemStack[]{
                air,
                new ItemBuilder(Material.INK_SACK, 1, (byte)11).setName("§6Back to spawn").addEnchant(Enchantment.DURABILITY, 1).toItemStack()
        };
    }

    @Override
    public ItemStack[] armor() {
        return new ItemStack[0];
    }

}
