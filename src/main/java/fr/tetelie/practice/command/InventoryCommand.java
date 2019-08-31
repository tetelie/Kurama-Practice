package fr.tetelie.practice.command;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class InventoryCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(PlayerManager.getPlayerManagers().get(((Player) sender).getUniqueId()).getPlayerSatus() != PlayerSatus.FIGHT) {
            if (args.length == 0) {
                sender.sendMessage("§cCorrect Usage§7: §6/inventory §e<uuid>");
            } else {
                UUID uuid = UUID.fromString(args[0]);
                if (Practice.getInstance().matchPreviewInventoryMap.containsKey(uuid)) {
                    ((Player) sender).openInventory(Practice.getInstance().matchPreviewInventoryMap.get(uuid).getPreviewInventory());
                } else {
                    sender.sendMessage("§cThis inventory does'nt exist ?!");
                }
            }
        }else{
            sender.sendMessage("§cYou can't do that now!");
        }
        return false;
    }
}
