package fr.tetelie.practice.event;

import fr.tetelie.practice.player.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();
        PlayerManager.getPlayerManagers().get(player.getUniqueId()).destroy();

    }

}
