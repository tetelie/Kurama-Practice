package fr.tetelie.practice.quest;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.event.custom.QuestCompleteEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Quest implements Listener {

    private static List<Quest> all = new ArrayList<>();

    static {
        all = new ArrayList<>();
    }

    public abstract String name();

    public abstract ItemStack iconItem();

    public abstract void giveReward(Player player);

    public Quest() {
        Bukkit.getPluginManager().registerEvents(this, Practice.getInstance());
    }

    public static List<Quest> getAll() {
        return all;
    }

    public static void setAll(List<Quest> all) {
        Quest.all = all;
    }

    public void finish(Player player)
    {
        // save in player profile
        QuestCompleteEvent event = new QuestCompleteEvent(player, this);
        Bukkit.getPluginManager().callEvent(event);
    }

    public static String[] getQuestLore(String objective, String reward)
    {
        String line = "§7§m-------------------------------------------";
        return new String[]
                {
                        line,
                        " ",
                        "§6§lObjective§7: §e" + objective,
                        " ",
                        "§6§lReward§7: §e" + reward,
                        " ",
                        line
                };
    }

    public static List<String> getQuestLore(String reward, String... objective)
    {
        String line = "§7§m-------------------------------------------";
        List list = new ArrayList();
        list.add(line);
        list.add(" ");
        list.add("§6§lObjective§7: §e" + objective[0]);
        for(int i = 1; i <= objective.length-1; i++)
        {
            list.add("§e"+objective[i]);
        }
        list.add(" ");
        list.add("§6§lReward§7: §e" + reward);
        list.add(" ");
        list.add(line);
        return list;
    }

}
