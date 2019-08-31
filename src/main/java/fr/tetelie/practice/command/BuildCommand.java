package fr.tetelie.practice.command;

import fr.tetelie.practice.Practice;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BuildCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        PlayerManager playerManager = PlayerManager.getPlayerManagers().get(((Player) sender).getUniqueId());
        if(playerManager.getPlayerSatus() == PlayerSatus.FREE || playerManager.getPlayerSatus() == PlayerSatus.BUILD)
        {
            Player player = (Player)sender;
            if(playerManager.getPlayerSatus() == PlayerSatus.FREE)
            {
                playerManager.reset(player, GameMode.CREATIVE);
                player.getInventory().setItem(0, new ItemStack(Material.WOOD_AXE));
                player.getInventory().setItem(1, new ItemStack(Material.DIAMOND_SPADE));
                playerManager.setPlayerSatus(PlayerSatus.BUILD);
                sender.sendMessage("§aBuild enable!");
            }else{
                playerManager.reset(player, GameMode.ADVENTURE);
                playerManager.sendKit(Practice.getInstance().spawnKit);
                playerManager.teleport(player, Practice.getInstance().spawn);
                playerManager.setPlayerSatus(PlayerSatus.FREE);
                sender.sendMessage("§cBuild disable!");
            }
        }else{
            sender.sendMessage("§cYou can't do that now!");
        }
        return false;
    }

}
