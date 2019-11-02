package fr.tetelie.practice.command;

import fr.tetelie.practice.match.MatchManager;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;

        if(args.length == 0) {
            int ping = ((Player) sender).getPing();
            ChatColor color = ping <= 100 ? ChatColor.GREEN : ping <= 150 ? ChatColor.YELLOW : ChatColor.RED;
            sender.sendMessage("§6Your ping§7: " + color + ping +"§7ms");
            if(PlayerManager.getPlayerManagers().get(((Player) sender).getUniqueId()).getPlayerSatus() == PlayerSatus.FIGHT)
            {
                MatchManager matchManager = MatchManager.getMatchManager(((Player) sender).getUniqueId());
                UUID opponentUUID = matchManager.getUuid1() == ((Player) sender).getUniqueId() ? matchManager.getUuid2() : matchManager.getUuid1();
                if(Bukkit.getPlayer(opponentUUID) != null)
                {
                    Player target = Bukkit.getPlayer(opponentUUID);
                    int ping2 = target.getPing();
                    ChatColor color2 = ping2 <= 100 ? ChatColor.GREEN : ping2 <= 150 ? ChatColor.YELLOW : ChatColor.RED;
                    sender.sendMessage("§e"+target.getName()+"'s §6ping§7: " + color + ping2 + "§7ms");
                }
            }
        }else if(args.length == 1)
        {
            if(Bukkit.getPlayer(args[0]) != null)
            {
                Player target = Bukkit.getPlayer(args[0]);
                int ping = target.getPing();
                ChatColor color = ping <= 100 ? ChatColor.GREEN : ping <= 150 ? ChatColor.YELLOW : ChatColor.RED;
                sender.sendMessage("§e"+target.getName()+"'s §6ping§7: " + color + ping + "§7ms");
            }else{
                sender.sendMessage("§eThis player is not connected to this server!");
            }
        }

        return false;
    }
}
