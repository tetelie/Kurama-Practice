package fr.tetelie.practice.command;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.player.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EloCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        PlayerManager playerManager = PlayerManager.getPlayerManagers().get(((Player) sender).getUniqueId());
        for(int i = 0; i <= Practice.getInstance().getLadders().size()-1; i++)
        {
            sender.sendMessage(Practice.getInstance().ladders.get(i).displayName() + ": " + playerManager.getElos()[i]);
        }
        return false;
    }

}
