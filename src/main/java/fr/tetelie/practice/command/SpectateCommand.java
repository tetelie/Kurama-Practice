package fr.tetelie.practice.command;

import fr.tetelie.practice.match.MatchManager;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpectateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        if(args.length == 0) {
            sender.sendMessage("§cCorrect Usage: §7/spectate <player>");
        }else if(args.length == 1)
        {
            Player player = Bukkit.getPlayer(((Player) sender).getUniqueId());
            PlayerManager playerManager = PlayerManager.getPlayerManagers().get(player.getUniqueId());
            if(Bukkit.getPlayer(args[0]) != null) {
                Player target = Bukkit.getPlayer(args[0]);
                PlayerManager targetm = PlayerManager.getPlayerManagers().get(target.getUniqueId());
                if(targetm.getPlayerSatus() == PlayerSatus.FIGHT) {
                    if (playerManager.getPlayerSatus() == PlayerSatus.FREE || playerManager.getPlayerSatus() == PlayerSatus.SPECTATE) {
                        if (MatchManager.getMatchManager(target.getUniqueId()) != null) {
                            MatchManager matchManager = MatchManager.getMatchManager(target.getUniqueId());
                            playerManager.spectate(player, matchManager);
                        }
                    }else {
                        sender.sendMessage("§cYou can't do that now!");
                        return false;
                    }
                }else{
                    sender.sendMessage("§cThis player is not in fight!");
                }
            }else{
                sender.sendMessage("§eThis player is not connected to this server!");
                return false;
            }
        }
        return false;
    }
}
