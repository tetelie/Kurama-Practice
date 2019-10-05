package fr.tetelie.practice.setting.settings;

import fr.tetelie.practice.setting.Setting;
import org.bukkit.entity.Player;

public class OpponentTagColor extends Setting {

    @Override
    public int slot() {
        return 21;
    }

    @Override
    public String[] values() {
        return new String[]{"§1dark blue", "§2dark green", "§3dark aqua", "§4dark red", "§5dark purple", "§6gold", "§7light gray", "§8dark gray", "§9blue", "§alight green", "§blight aqua", "§clight green", "§dlight purple", "§eyellow", "§fwhite"};
    }

    @Override
    public void change(Player player, int value) {

    }

}
