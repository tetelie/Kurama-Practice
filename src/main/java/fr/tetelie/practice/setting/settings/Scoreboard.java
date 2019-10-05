package fr.tetelie.practice.setting.settings;

import fr.tetelie.practice.setting.Setting;
import org.bukkit.entity.Player;

public class Scoreboard extends Setting {

    @Override
    public int slot() {
        return 12;
    }

    @Override
    public String[] values() {
        return new String[]{"allow", "deny"};
    }

    @Override
    public void change(Player player, int value) {

    }

}
