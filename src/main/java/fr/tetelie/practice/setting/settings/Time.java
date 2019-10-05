package fr.tetelie.practice.setting.settings;

import fr.tetelie.practice.setting.Setting;
import org.bukkit.entity.Player;

public class Time extends Setting {

    @Override
    public int slot() {
        return 3;
    }

    @Override
    public String[] values() {
        return new String[]
                {
                     "sunrise",
                     "day",
                     "sunset",
                     "night"
                };
    }

    @Override
    public void change(Player player, int value) {
        switch (value)
        {
            case 0:
                player.setPlayerTime(0, false);
                break;
            case 1:
                player.setPlayerTime(6250, false);
                break;
            case 2:
                player.setPlayerTime(13000, false);
                break;
            case 3:
                player.setPlayerTime(14000, false);
                break;
        }
    }
}
