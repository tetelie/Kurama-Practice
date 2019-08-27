package fr.tetelie.practice.ladder.ladders;

import fr.tetelie.practice.ladder.Ladder;
import fr.tetelie.practice.inventory.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NoDebuff extends Ladder implements Kit {

    @Override
    public String name() {
        return "nodebuff";
    }

    @Override
    public String displayName() {
        return "§6§lNoDebuff";
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
