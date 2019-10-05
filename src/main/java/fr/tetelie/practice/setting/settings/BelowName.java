package fr.tetelie.practice.setting.settings;

import fr.tetelie.practice.setting.Setting;
import org.bukkit.entity.Player;

public class BelowName extends Setting {

    @Override
    public int slot() {
        return 5;
    }

    @Override
    public String[] values() {
        return new String[]{"ping", "cps", "exp", "elo", "win chance", "none"};
    }

    @Override
    public void change(Player player, int value) {

    }

}
