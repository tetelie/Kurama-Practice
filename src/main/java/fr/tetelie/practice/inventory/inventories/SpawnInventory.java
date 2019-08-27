package fr.tetelie.practice.inventory.inventories;

import fr.tetelie.practice.util.ItemBuilder;
import fr.tetelie.practice.inventory.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SpawnInventory implements Kit {

    String suffix = " §r§f(Right click)";

    @Override
    public ItemStack[] content() {
        return new ItemStack[]{
                new ItemBuilder(Material.WOOD_SWORD).setUnBreakable().setName("§6§lFight"+suffix).toItemStack(),
                air,
                air,
                new ItemBuilder(Material.CAULDRON_ITEM).setName("§6§lHistoric"+suffix).toItemStack(),
                air,
                new ItemBuilder(Material.COMPASS).setName("§6§lSpectate"+suffix).toItemStack(),
                air,
                new ItemBuilder(Material.NETHER_STAR).setName("§6§lEvent"+suffix).toItemStack(),
                new ItemBuilder(Material.PAINTING).setName("§6§lPanel"+suffix).toItemStack()
        };
    }

    @Override
    public ItemStack[] armor() {
        return new ItemStack[0];
    }
}
