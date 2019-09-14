package fr.tetelie.practice.inventory.inventories;

import fr.tetelie.practice.inventory.Kit;
import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PartyLeaderInventory implements Kit {

    String suffix = " §r§f(Right click)";

    @Override
    public ItemStack[] content() {
        return new ItemStack[]{
                new ItemBuilder(Material.DIAMOND_AXE).setUnBreakable().setName("§6§lDuel other party"+suffix).toItemStack(),
                new ItemBuilder(Material.GOLD_AXE).setUnBreakable().setName("§6§lParty Event"+suffix).toItemStack(),
                air,
                new ItemBuilder(Material.WATCH).setName("§6§lManage party settings"+suffix).toItemStack(),
                air,
                air,
                new ItemBuilder(Material.NAME_TAG).setName("§6§lParty infos"+suffix).toItemStack(),
                air,
                new ItemBuilder(Material.FIRE).setName("§6§lDisband party"+suffix).toItemStack()
        };
    }

    @Override
    public ItemStack[] armor() {
        return new ItemStack[0];
    }
}
