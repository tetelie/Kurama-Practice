package fr.tetelie.practice.inventory;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.fight.FightManager;
import fr.tetelie.practice.ladder.Ladder;
import fr.tetelie.practice.fight.FightType;
import fr.tetelie.practice.match.MatchManager;
import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class FightInventory implements Runnable {

    @Override
    public void run() {
        Practice.getInstance().normalFight = Bukkit.createInventory(null, 18, "§eNormal Fight");
        Practice.getInstance().competitiveFight = Bukkit.createInventory(null, 18, "§6Competitive Fight");
        for(Ladder ladder : Practice.getInstance().ladders)
        {
            ItemStack item = new ItemBuilder(ladder.material(), 1, ladder.data()).setName(ladder.displayName()).toItemStack();
            Practice.getInstance().normalFight.addItem(item);
            Practice.getInstance().competitiveFight.addItem(item);
            Practice.getInstance().fight.put(ladder.displayName(), new FightManager());
        }

        while(true)
        {
            // update ladder lore
            int slot = 0;
            for(Ladder ladder : Practice.getInstance().ladders)
            {
                ItemStack current = Practice.getInstance().normalFight.getItem(slot);
                ItemMeta meta = current.getItemMeta();
                meta.setLore(Arrays.asList("§eIn Queue§7: §f" + Practice.getInstance().fight.get(ladder.displayName()).getQueue(FightType.NORMAL), "§eIn Fight§7: §f" + Practice.getInstance().fight.get(ladder.displayName()).getFight(FightType.NORMAL), " ", "§7Left click to join fight.", "§7Right click to view current match(s)."));
                current.setItemMeta(meta);

                ItemStack current2 = Practice.getInstance().competitiveFight.getItem(slot);
                ItemMeta meta2 = current2.getItemMeta();
                meta2.setLore(Arrays.asList("§eIn Queue§7: §f" + Practice.getInstance().fight.get(ladder.displayName()).getQueue(FightType.COMPETITIVE), "§eIn Fight§7: §f" + Practice.getInstance().fight.get(ladder.displayName()).getFight(FightType.COMPETITIVE), " ", "§7Left click to join fight.", "§7Right click to view current match(s)."));
                current2.setItemMeta(meta2);

                slot++;
            }

            try {
                Thread.sleep(1000);
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
