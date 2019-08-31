package fr.tetelie.practice.event;

import fr.tetelie.practice.match.MatchEndCause;
import fr.tetelie.practice.player.PlayerManager;
import fr.tetelie.practice.player.PlayerSatus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);
        e.getDrops().clear();
        Player player = e.getEntity();
        PlayerManager playerManager = PlayerManager.getPlayerManagers().get(player.getUniqueId());
        if (playerManager.getPlayerSatus() == PlayerSatus.FIGHT && playerManager.getCurrentFight() != null) {
            // send death animation to opponent
            playerManager.getCurrentFight().end(MatchEndCause.KILL, player, playerManager);
            player.spigot().respawn();
        }
    }

}
