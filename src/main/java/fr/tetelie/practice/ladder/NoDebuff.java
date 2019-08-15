package fr.tetelie.practice.ladder;

import fr.tetelie.practice.uti.Kit;
import fr.tetelie.practice.uti.Ladder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NoDebuff extends Ladder implements Kit {

    @Override
    public String name() {
        return "nodebuff";
    }

    @Override
    public String displayName() {
        return "§c§lNoDebuff";
    }

    @Override
    public Material material() {
        return Material.POTION;
    }

    @Override
    public byte data() {
        return (byte)16421;
    }

    @Override
    public boolean isAlterable() {
        return true;
    }

    @Override
    public boolean additionalInventory() {
        return true;
    }

    @Override
    public ItemStack[] content() {
        return new ItemStack[0];
    }

    @Override
    public ItemStack[] armor() {
        return new ItemStack[0];
    }

}
