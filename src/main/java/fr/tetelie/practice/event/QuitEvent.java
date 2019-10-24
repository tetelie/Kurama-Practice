package fr.tetelie.practice.event;

import fr.tetelie.practice.match.MatchEndCause;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();
        e.setQuitMessage("§7[§c-§7] §e"+player.getName());
        PlayerManager playerManager = PlayerManager.getPlayerManagers().get(player.getUniqueId());
        if (playerManager.getPlayerSatus() == PlayerSatus.FIGHT && playerManager.getCurrentFight() != null) {
            playerManager.getCurrentFight().end(MatchEndCause.DISCONECT, player, playerManager, player.getLocation());
        }
        playerManager.destroy();
    }

}
