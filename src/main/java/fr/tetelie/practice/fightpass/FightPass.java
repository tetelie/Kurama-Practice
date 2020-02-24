package fr.tetelie.practice.fightpass;

import fr.tetelie.practice.fightpass.palliate.Palliate;
import fr.tetelie.practice.fightpass.palliate.palliates.free.FreePalliate1;
import fr.tetelie.practice.fightpass.palliate.palliates.free.FreePalliate2;
import fr.tetelie.practice.fightpass.palliate.palliates.premium.PremiumPalliate1;
import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class FightPass {

    Palliate[] free_palliates = new Palliate[]{new FreePalliate1(), new FreePalliate2()};
    Palliate[] premium_palliates = new Palliate[]{new PremiumPalliate1()};
    public static Inventory[] inventories = new Inventory[12];

    public FightPass()
    {
        for(int inv = 1; inv <= 12; inv++)
        {
            Inventory inventory = Bukkit.createInventory(null, 9*6, "§6Fight pass");
            for(int i = 9; i <= 17; i++)
            {
                if(i == 13)
                {
                    inventory.setItem(i, new ItemBuilder(Material.IRON_INGOT).setName("Free pass").toItemStack());
                }else {
                    inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 15).setName(" ").toItemStack());
                }
            }

            for(int i = 27; i <= 35; i++)
            {
                if(i == 31){
                    inventory.setItem(i, new ItemBuilder(Material.GOLD_INGOT).setName("Premium pass").toItemStack());
                }else{
                    inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte)15).setName(" ").toItemStack());
                }
            }

            int free = 18;
            int premium = 36;
            int palliate = 0;

            int testt = inv == 1 ? 0 : (9*inv)+1;

            for(int test = testt; test <= testt+8; test++)
            {
                System.out.println(test+8);
                inventory.setItem(palliate, new ItemBuilder(Material.STAINED_GLASS_PANE, test+1, (byte)14).toItemStack());
                if(free_palliates.length > test) {
                    inventory.setItem(free, new ItemBuilder(Material.PAPER).setName(free_palliates[test].reward_name()).setLore(free_palliates[test].reward_lore()).toItemStack());
                }
                if(premium_palliates.length > test){
                    inventory.setItem(premium, new ItemBuilder(Material.PAPER).setName(premium_palliates[test].reward_name()).setLore(premium_palliates[test].reward_lore()).toItemStack());
                }
                palliate++;
                free++;
                premium++;
            }

            inventory.setItem(47, new ItemBuilder(Material.GOLD_INGOT).setName("§6Purchase Premium Fightpass").toItemStack());
            inventory.setItem(49, new ItemBuilder(Material.BOOK).setName("§6Page " + inv).toItemStack());
            inventory.setItem(51, new ItemBuilder(Material.EMERALD).setName("§6Quests").toItemStack());

            if(inv != 1) inventory.setItem(45, new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("§6Previous page").addLoreLine((inv-1)+"").toItemStack());
            if(inv != 12) inventory.setItem(53, new ItemBuilder(Material.REDSTONE_TORCH_ON).setName("§6Next page").addLoreLine((inv+1)+"").toItemStack());

            inventories[inv-1] = inventory;

        }
    }

    public static void open(Player player, int page)
    {
        player.openInventory(inventories[page]);
    }

}
