package fr.tetelie.practice.setting;

import fr.tetelie.practice.setting.settings.*;
import org.bukkit.entity.Player;

public abstract class Setting {

    public static Setting[] all = new Setting[]{new Time(),
            new PrivateMessage(),
            new BelowName(),
            new Scoreboard(),
            new DuelRequest(),
            new RematchItem(),
            new OpponentTagColor(),
            new ExpBar(),
            new KillEffect()};

    public abstract int slot();
    public abstract String[] values();
    public abstract void change(Player player, int value);


    public static int getSettingsBySlot(int slot)
    {
        int id = 0;
        for(Setting setting : all)
        {
            if(setting.slot() == slot) return id;
            id++;
        }
        return id;
    }

}
