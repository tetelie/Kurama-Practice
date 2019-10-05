package fr.tetelie.practice.setting.settings;

import fr.tetelie.practice.setting.Setting;
import org.bukkit.entity.Player;

public class ExpBar extends Setting {

    @Override
    public int slot() {
        return 22;
    }

    @Override
    public String[] values() {
        return new String[]{"enderpearl cooldown", "ping", "cps", "exp", "global elo", "win chance", "none"};
    }

    @Override
    public void change(Player player, int value) {

    }

}
