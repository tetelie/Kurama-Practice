package fr.tetelie.practice.ranked;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.fight.FightManager;
import fr.tetelie.practice.ladder.Ladder;
import fr.tetelie.practice.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;


public class Ranked implements Runnable {

    private int min_range;
    private int  max_range;

    private int ranked_time;

    private boolean active = false;

    public Ranked(){};

    public int getMin_range()
    {
        return this.min_range;
    }

    public void setMin_range(int new_range)
    {
        this.min_range = new_range;
    }

    public int getMax_range()
    {
        return this.max_range;
    }

    public void setMax_range(int new_range)
    {
        this.max_range = new_range;
    }

    public void setNewRange(int new_min, int new_max)
    {
        this.min_range = new_min;
        this.max_range = new_max;
    }

    public void setRanked_time(int ranked_time) {
        this.ranked_time = ranked_time;
    }

    public int getRanked_time()
    {
        return this.ranked_time;
    }

    public void start(PlayerManager playerManager, Player player)
    {

        // 3 sec = 3*2 = 6

        active = true;
        setNewRange(1000-100, 1000+100);
        player.sendMessage("§7» §dSearching in elo range §7[§5"+min_range+" - "+max_range+"§7]");
        ranked_time = 0;
        new BukkitRunnable() {
            public void run() {
                if(!active)
                {
                    this.cancel();
                    return;
                }
                ranked_time++;
                System.out.println(player.getName()+" , "+active);
                if((ranked_time % 4) != 0) return;
                if(ranked_time == 4)
                {
                    setMax_range(max_range+40);
                    if(min_range-40 >= 0) setMin_range(min_range-40);
                }else{
                    setMax_range(max_range+20);
                    if(min_range-20 >= 0) setMin_range(min_range-20);
                }
                player.sendMessage("§7» §dSearching in elo range §7[§5"+min_range+" - "+max_range+"§7]");
            }
        }.runTaskTimer((Plugin) Practice.getInstance(), 10L, 10L);
    }

    public void stop()
    {
        active = false;
    }

    @Override
    public void run() {
        while(true)
        {

            for(Ladder ladder : Practice.getInstance().ladders)
            {
                FightManager fightManager = Practice.getInstance().fight.get(ladder.displayName());
                if(fightManager.getRankedQueuePlayer().size() >= 2)
                {
                    Bukkit.broadcastMessage("Ranked queue size >= 2");
                }
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
