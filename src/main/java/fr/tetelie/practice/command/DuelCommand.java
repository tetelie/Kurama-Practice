package fr.tetelie.practice.command;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.duel.DuelManager;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DuelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        PlayerManager playerManager = PlayerManager.getPlayerManagers().get(((Player) sender).getUniqueId());
        if(playerManager.getPlayerSatus() == PlayerSatus.FREE) {
            if (args.length == 0) {
                sender.sendMessage("§6Correct Usage§7: §e/duel <player>");
            } else if (args.length == 1) {
                if (args[0].equals(sender.getName())) {
                    sender.sendMessage("§cYou can't duel yourself!");
                    return true;
                }
                if (Bukkit.getPlayer(args[0]) != null) {
                    Player target = Bukkit.getPlayer(args[0]);
                    PlayerManager targetm = PlayerManager.getPlayerManagers().get(target.getUniqueId());
                    if(targetm.getPlayerSatus() == PlayerSatus.FREE)
                    {
                        if(DuelManager.getDuelBySender(playerManager.getUuid()) != null) {
                            if (DuelManager.getDuelBySender(playerManager.getUuid()).getReciever() == target.getUniqueId()) {
                                sender.sendMessage("§cYou have already send a duel to this player!");
                                return true;
                            }else{
                                DuelManager.getDuelBySender(playerManager.getUuid()).destroy();
                                if(((Player) sender).getInventory().getItem(1).getType() == Material.FIRE) playerManager.sendKit(Practice.getInstance().spawnKit);
                            }
                        }
                        playerManager.setCurrentDuelPlayer(target.getUniqueId());
                        ((Player) sender).openInventory(Practice.getInstance().duelGui.inventory());
                    }else{
                        sender.sendMessage("§eThis player is currently busy!");
                    }
                } else {
                    sender.sendMessage("§eThis player is not connected to this server!");
                }
            } else {
                sender.sendMessage("§cInvalid length!");
            }
        }else{
            sender.sendMessage("§cYou can't do that now!");
        }
        return false;
    }

}
