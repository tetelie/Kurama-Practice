package fr.tetelie.practice.inventory;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.ladder.Ladder;
import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public class FightInventory implements Runnable {

    @Override
    public void run() {
        Practice.getInstance().normalFight = Bukkit.createInventory(null, 9, "§eNormal Fight");
        Practice.getInstance().competitiveFight = Bukkit.createInventory(null, 9, "§6Competitive Fight");
        for(Ladder ladder : Practice.getInstance().ladders)
        {
            ItemStack item = new ItemBuilder(ladder.material(), 1, ladder.data()).setName(ladder.displayName()).toItemStack();
            Practice.getInstance().normalFight.addItem(item);
            Practice.getInstance().competitiveFight.addItem(item);
        }
        while(true)
        {
            // update ladder lore
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
