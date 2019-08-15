package fr.tetelie.practice.defaultInventory;

import fr.tetelie.practice.uti.ItemBuilder;
import fr.tetelie.practice.uti.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SpawnInventory implements Kit {

    @Override
    public ItemStack[] content() {
        return new ItemStack[]{ Kit.air, new ItemBuilder(Material.STONE_SWORD).setName("Yes, i'm a real sword ^-^").toItemStack() };
    }

    @Override
    public ItemStack[] armor() {
        return new ItemStack[0];
    }
}
