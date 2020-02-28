package fr.tetelie.practice.inventory;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.fight.FightManager;
import fr.tetelie.practice.fight.FightType;
import fr.tetelie.practice.ladder.Ladder;
import fr.tetelie.practice.leaderboard.Top;
import fr.tetelie.practice.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

import javax.print.attribute.standard.PageRanges;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class LeaderboardInventory implements Runnable {

    @Override
    public void run() {
        Practice.getInstance().leaderboardInventory = Bukkit.createInventory(null, 18, "§eLeaderboard");
        for(Ladder ladder : Practice.getInstance().ladders)
        {
            ItemStack item = new ItemBuilder(ladder.material(), 1, ladder.data()).setName(ladder.displayName()).toItemStack();
            Practice.getInstance().leaderboardInventory.setItem(ladder.id()+9 ,item);
        }
        ItemStack item = new ItemBuilder(Material.NETHER_STAR, 1, (byte)0).setName("§dTop Global").toItemStack();
        Practice.getInstance().leaderboardInventory.setItem(4 ,item);

        while(true) {
            if (Bukkit.getOnlinePlayers().size() >= 1) {
                Practice.getInstance().leaderboard.refresh();
                Top[] top = Practice.getInstance().leaderboard.getTop();
                Top global_top = Practice.getInstance().leaderboard.getGlobal();
                for (Ladder ladder : Practice.getInstance().ladders) {
                    ItemStack current = Practice.getInstance().leaderboardInventory.getItem(ladder.id() + 9);
                    ItemMeta meta = current.getItemMeta();

                    meta.setLore(top[ladder.id()].getLore());
                    //current.setAmount(Practice.getInstance().fight.get(ladder.displayName()).getFight(FightType.NORMAL));
                    current.setItemMeta(meta);
                }

                ItemStack current = Practice.getInstance().leaderboardInventory.getItem(4);
                ItemMeta meta = current.getItemMeta();

                meta.setLore(global_top.getLore());
                //current.setAmount(Practice.getInstance().fight.get(ladder.displayName()).getFight(FightType.NORMAL));
                current.setItemMeta(meta);

                try {
                    Thread.sleep(1000 * 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
