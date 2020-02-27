package fr.tetelie.practice.ladder.ladders;

import fr.tetelie.practice.arena.ArenaType;
import fr.tetelie.practice.ladder.Ladder;
import fr.tetelie.practice.inventory.Kit;
import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
    public ArenaType arenaType() {
        return ArenaType.NORMAL;
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
    public int id() {
        return 0;
    }

    @Override
    public ItemStack[] armor() {
        ItemStack[] Armor = {new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.DURABILITY, 3).addEnchant(Enchantment.PROTECTION_FALL, 4).toItemStack(),
                new ItemBuilder(Material.DIAMOND_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.DURABILITY, 3).toItemStack(),
                new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.DURABILITY, 3).toItemStack(),
                new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.DURABILITY, 3).toItemStack()
        };
        return Armor;
    }

    @Override
    public ItemStack[] content() {
        ItemStack[] Contents = {new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 3).addEnchant(Enchantment.DURABILITY, 3).addEnchant(Enchantment.FIRE_ASPECT, 2).toItemStack(),
                new ItemBuilder(Material.ENDER_PEARL, 16, (short)0).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)8259).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)8226).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.COOKED_BEEF, 64, (short)0).toItemStack(),

                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)8226).toItemStack(),

                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)8226).toItemStack(),

                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)16421).toItemStack(),
                new ItemBuilder(Material.POTION, 1, (short)8226).toItemStack(),

        };
        return Contents;
    }

}
